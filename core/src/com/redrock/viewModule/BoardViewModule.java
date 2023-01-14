package com.redrock.viewModule;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;
import com.redrock.logics.LogicCenterModule;
import com.redrock.logics.controllers.BoardController;
import com.redrock.logics.controllers.CardController;
import com.redrock.logics.models.SuitModel;
import com.redrock.sdk.component.GenericModule;
import com.redrock.sdk.modules.generic.GenericMessage;
import com.redrock.viewModule.configs.CardViewConfig;
import com.redrock.viewModule.messages.CardForPlayerReceivedMessage;
import com.redrock.viewModule.messages.DistributeCardsCompletedMessage;
import com.redrock.viewModule.messages.EndGameMessage;
import com.redrock.viewModule.messages.ShowViewCardsMessage;
import com.redrock.viewModule.messages.StartPickCardsMessage;
import com.redrock.viewModule.viewComponents.AvatarComponent;
import com.redrock.viewModule.viewComponents.CardComponent;
import com.redrock.viewModule.viewComponents.pools.AvatarPool;
import com.redrock.viewModule.viewComponents.pools.CardPool;

import java.util.HashMap;

public class BoardViewModule extends GenericModule {
  private static final BoardViewModule inst = new BoardViewModule();

  private AlignGroup logicGroup, interactiveGroup, notInteractiveGroup;

  private final CardController cardController = CardController.inst();
  private final BoardController boardController = BoardController.inst();
  private final LogicCenterModule logicCenterModule = LogicCenterModule.inst();
  private final CardPool cardPool = CardPool.inst();
  private final AvatarPool avatarPool = AvatarPool.inst();

  private final Array<Array<CardComponent>> playersCards = new Array<>();
  private final Array<AvatarComponent> avatars = new Array<>();

  private BoardViewModule() {
    Main.moduleMessage().register(this, DistributeCardsCompletedMessage.class);
    Main.moduleMessage().register(this, StartPickCardsMessage.class);
    Main.moduleMessage().register(this, EndGameMessage.class);
    Main.moduleMessage().register(this, CardForPlayerReceivedMessage.class);
    Main.moduleMessage().register(this, ShowViewCardsMessage.class);
  }

  @Override
  public void handleMsg(GenericMessage msg) {
    if (msg.getClass() == DistributeCardsCompletedMessage.class) {
      this.handleDistributeCardsCompleted();
    } else if (msg.getClass() == EndGameMessage.class) {
      this.handleEndGame();
    } else if (msg.getClass() == StartPickCardsMessage.class) {
      this.handleStartPickCards();
    } else if (msg.getClass() == CardForPlayerReceivedMessage.class) {
      this.movePlayerCardPicked((CardForPlayerReceivedMessage) msg);
    } else if (msg.getClass() == ShowViewCardsMessage.class) {
      this.showViewCards((ShowViewCardsMessage) msg);
    }
  }

  private void handleDistributeCardsCompleted() {
    this.updatePlayerCardsValue();
    this.updateBotsCardsValue();
    this.displayAlignPlayerCards();
    this.enablePlayerCards();
    this.tryDisplaySpecialCards();
  }

  private void updatePlayerCardsValue() {
    Array<Integer> playerCardsValue = this.logicCenterModule.getPlayerCards(0);

    for (int cardValueIndex = 0; cardValueIndex < playerCardsValue.size; cardValueIndex++) {
      int cardValue = playerCardsValue.get(cardValueIndex);

      int value = this.cardController.getValue(cardValue);
      SuitModel.Suit suit = this.cardController.getSuit(cardValue);

      this.playersCards.get(0).get(cardValueIndex).changeValue(suit, value);
    }
  }

  private void updateBotsCardsValue() {
    for (int botIndex = 1; botIndex < this.boardController.getPlayerAmount(); botIndex++) {
      Array<Integer> botCardsValues = this.logicCenterModule.getPlayerCards(botIndex);

      for (int cardValueIndex = 0; cardValueIndex < botCardsValues.size; cardValueIndex++) {
        int cardValue = botCardsValues.get(cardValueIndex);

        int value = this.cardController.getValue(cardValue);
        SuitModel.Suit suit = this.cardController.getSuit(cardValue);

        this.playersCards.get(botIndex).get(cardValueIndex).changeValue(suit, value);
      }
    }
  }

  private void enablePlayerCards() {
    for (CardComponent card : this.playersCards.get(0)) {
      card.setIsEnableClick(true);
    }
  }

  private void tryDisplaySpecialCards() {
    int dealerSpecialCardType = this.boardController.getDealerHasSpecialCardType();

    switch (dealerSpecialCardType) {
      case 0:
        this.tryDisplayBotSpecialCards();
        this.logicGroup.addAction(Actions.sequence(
            Actions.delay(0.3f),
            Actions.run(() -> Main.moduleMessage().sendMsg(new StartPickCardsMessage()))
        ));
        break;
      case 1:
      case 2:
        for (Array<CardComponent> playerCards : this.playersCards) {
          for (CardComponent card : playerCards) {
            card.displayCard();
          }
        }

        for (int botIndex = 1; botIndex < this.boardController.getPlayerAmount(); botIndex++) {
          this.displayAlignCards(botIndex);
        }

        Main.moduleMessage().sendMsg(new EndGameMessage());
        break;
    }
  }

  private void tryDisplayBotSpecialCards() {
    this.tryDisplayBotGoldCards();
    this.tryDisplayBotBlackjackCards();
  }

  private void tryDisplayBotGoldCards() {
    Array<Integer> goldPlayerIndexes = this.boardController.getGoldPlayerIndexes();

    for (int cardSetIndex = 0; cardSetIndex < goldPlayerIndexes.size; cardSetIndex++) {
      int playerCardIndex = goldPlayerIndexes.get(cardSetIndex);
      if (playerCardIndex != this.boardController.getDealerIndex()) {
        this.displayAlignCards(playerCardIndex);
        this.showCards(playerCardIndex);
      }
    }
  }

  private void tryDisplayBotBlackjackCards() {
    Array<Integer> blackjackPlayerIndex = this.boardController.getBlackjackPlayerIndexes();

    for (int cardSetIndex = 0; cardSetIndex < blackjackPlayerIndex.size; cardSetIndex++) {
      int playerCardIndex = blackjackPlayerIndex.get(cardSetIndex);
      if (playerCardIndex != this.boardController.getDealerIndex()) {
        this.displayAlignCards(playerCardIndex);
        this.showCards(playerCardIndex);
      }
    }
  }

  private void displayAlignCards(int playerIndex) {
    for (int cardIndex = 0; cardIndex < this.playersCards.get(playerIndex).size; cardIndex++) {
      CardComponent card = this.playersCards.get(playerIndex).get(cardIndex);

      card.addAction(Actions.moveTo(card.getX() + 30 * cardIndex, card.getY(), 0.3f, Interpolation.linear));
    }
  }

  private void showCards(int playerIndex) {
    for (int cardIndex = 0; cardIndex < this.playersCards.get(playerIndex).size; cardIndex++) {
      CardComponent card = this.playersCards.get(playerIndex).get(cardIndex);
      card.displayCard();
    }
  }

  private void displayAlignPlayerCards() {
    Array<Integer> horizontalDistances = new Array<>();
    horizontalDistances.add(-20, 20);

    for (int i = 0; i < horizontalDistances.size; i++) {
      CardComponent card = this.playersCards.get(0).get(i);
      card.addAction(
          Actions.moveTo(card.getX() + horizontalDistances.get(i), card.getY(), 0.3f, Interpolation.linear)
      );
    }
  }

  private void handleStartPickCards() {
    this.logicCenterModule.startPickCardsForBots();
    this.displayPickBotCardsAnimation();
  }

  private void movePlayerCardPicked(CardForPlayerReceivedMessage message) {
    SuitModel.Suit suit = this.cardController.getSuit(message.card);
    int value = this.cardController.getValue(message.card);

    CardComponent card = CardPool.inst().getInst(suit, value);
    this.playersCards.get(0).add(card);

    Array<Float> xPositions = this.findXPositionForPlayerCards(0);
    Vector2 startPoint = new Vector2(CardViewConfig.cardDeckPosition.x, CardViewConfig.cardDeckPosition.y);
    Vector2 endPoint = new Vector2(xPositions.get(xPositions.size - 1), playersCards.get(0).get(0).getY());

    card.setPosition(startPoint.x, startPoint.y);
    card.setSize(card.getWidth() * CardViewConfig.ratioCardView, card.getHeight() * CardViewConfig.ratioCardView);
    this.interactiveGroup.addActor(card);
    card.setIsEnableClick(true);

    card.move(startPoint, endPoint, 0.2f, Interpolation.fastSlow);
    for (int i = 0; i < playersCards.get(0).size - 1; i++) {
      Vector2 oldPoint = new Vector2(this.playersCards.get(0).get(i).getX(), this.playersCards.get(0).get(i).getY());
      Vector2 newPoint = new Vector2(xPositions.get(i), oldPoint.y);

      this.playersCards.get(0).get(i).move(oldPoint, newPoint, 0.2f, Interpolation.fastSlow);
    }
  }

  private void showViewCards(ShowViewCardsMessage message) {
    Array<Float> xPositions = this.findXPositionForPlayerCards(message.playerIndex);

    for (int i = 0; i < playersCards.get(message.playerIndex).size; i++) {
      Vector2 oldPoint = new Vector2(this.playersCards.get(message.playerIndex).get(i).getX(), this.playersCards.get(message.playerIndex).get(i).getY());
      Vector2 newPoint = new Vector2(xPositions.get(i), oldPoint.y);

      this.playersCards.get(message.playerIndex).get(i).move(oldPoint, newPoint, 0.2f, Interpolation.fastSlow);
      this.playersCards.get(message.playerIndex).get(i).displayCard();
    }
  }

  private Array<Float> findXPositionForPlayerCards(int playerIndex) {
    final float maxWithPlayerDeckCards = this.playersCards.get(playerIndex).size > 2 ?
        CardViewConfig.maxWithPlayerDeckCards : 101.9f;

    Array<Float> xPositions = new Array<>();
    float widthCard = this.playersCards.get(playerIndex).get(0).getWidth();
    float startXPositionCard = CardViewConfig.playerPositionsMap.get(this.boardController.getPlayerAmount()).get(playerIndex).x + widthCard / 2;
    float xPositionLastCard = startXPositionCard + maxWithPlayerDeckCards / 2 - widthCard;
    float distancePerCards = (maxWithPlayerDeckCards - widthCard) / (playersCards.get(playerIndex).size - 1);

    xPositions.add(xPositionLastCard);

    for (int i = 0; i < this.playersCards.get(playerIndex).size - 1; i++) {
      float xPosition = xPositions.get(i) - distancePerCards;
      xPositions.add(xPosition);
    }

    xPositions.reverse();

    return xPositions;
  }

  private void displayPickBotCardsAnimation() {
    int amountPlayer = this.boardController.getPlayerAmount();
    HashMap<Integer, Vector2> positionsMap = CardViewConfig.playerPositionsMap.get(amountPlayer);
    int cardPickedSize = 0;

    for (int botIndex = 1; botIndex < amountPlayer; botIndex++) {
      int pickCardSize = this.logicCenterModule.getPlayerCards(botIndex).size - 2;

      for (int cardIndex = 0; cardIndex < pickCardSize; cardIndex++) {
        CardComponent card = this.cardPool.getInst();
        this.playersCards.get(botIndex).add(card);
        card.setOrigin(Align.center);
        card.setSize(card.getWidth() * 0.7f, card.getHeight() * 0.7f);

        this.interactiveGroup.addActor(card);

        Vector2 startPoint = new Vector2(CardViewConfig.cardDeckPosition.x, CardViewConfig.cardDeckPosition.y);
        Vector2 endPoint = positionsMap.get(botIndex);
        card.setPosition(startPoint.x, startPoint.y);

        card.moveWithDelay(startPoint, endPoint, 0.3f, Interpolation.fastSlow, 0.5f * cardPickedSize);

        cardPickedSize++;
      }
    }
  }

  private void handleEndGame() {
    // show all cards
    // show point cards
    // show animation end game
    //
  }

  public void init(AlignGroup logicGroup) {
    this.logicGroup = logicGroup;

    this.interactiveGroup = new AlignGroup();
    this.notInteractiveGroup = new AlignGroup();

    this.notInteractiveGroup.setTouchable(Touchable.disabled);

    this.interactiveGroup.setSize(logicGroup.getWidth(), logicGroup.getHeight());
    this.notInteractiveGroup.setSize(logicGroup.getWidth(), logicGroup.getHeight());

    this.logicGroup.addActor(this.interactiveGroup, this.notInteractiveGroup);
  }

  public void displayDistributeCardsAnimations() {
    this.initAvatars();
    this.initCards();
  }

  private void initAvatars() {
    for (int playerIndex = 0; playerIndex < this.boardController.getPlayerAmount(); playerIndex++) {
      AvatarComponent avatarComponent = this.avatarPool.getInst();
      this.notInteractiveGroup.addActor(avatarComponent);
      this.avatars.add(avatarComponent);

      avatarComponent.updatePlayerIndex(playerIndex);
      avatarComponent.addGroupForShowCardButton(this.interactiveGroup);

      if (playerIndex != 0)
        avatarComponent.updatePositionForShowCardButton(CardViewConfig.showCardPositionMap.get(playerIndex));
    }

    this.updateAvatarPositions();
    this.updateScoreFramePositions();
    this.updateResultFramePositions();
  }

  private void updateAvatarPositions() {
    for (AvatarComponent avatar : this.avatars) {
      final int avatarIndex = this.avatars.indexOf(avatar, true);
      avatar.setPosition(CardViewConfig.avatarPositionMap.get(avatarIndex).x, CardViewConfig.avatarPositionMap.get(avatarIndex).y);
    }
  }

  private void updateScoreFramePositions() {
    for (AvatarComponent avatar : this.avatars) {
      final int avatarIndex = this.avatars.indexOf(avatar, true);
      avatar.setScoreFramePositionFromPoint(CardViewConfig.scoreFramePositionMap.get(avatarIndex));
    }
  }

  private void updateResultFramePositions() {
    for (AvatarComponent avatar : this.avatars) {
      final int avatarIndex = this.avatars.indexOf(avatar, true);
      avatar.setResultFramePositionFromPoint(CardViewConfig.resultFramePositionMap.get(avatarIndex));
    }
  }

  private void initCards() {
    this.tryClearPlayersCardsBeforeInit();
    this.initPlayerCardsSlot();
    this.initDeckView();
    this.moveCards();
  }

  private void tryClearPlayersCardsBeforeInit() {
    if (this.playersCards.size > 0) {
      for (Array<CardComponent> playerCards : this.playersCards)
        if (playerCards.size > 0)
          playerCards.removeRange(0, playerCards.size - 1);

      this.playersCards.removeRange(0, this.playersCards.size - 1);
    }
  }

  private void initPlayerCardsSlot() {
    int amountPlayer = this.boardController.getPlayerAmount();

    for (int playerIndex = 0; playerIndex < amountPlayer; playerIndex++)
      this.playersCards.add(new Array<>());
  }

  private void initDeckView() {
    CardComponent card = this.cardPool.getInst();
    card.setOrigin(Align.center);
    card.setSize(card.getWidth() * 0.7f, card.getHeight() * 0.7f);
    this.interactiveGroup.addActor(card, CardViewConfig.cardDeckPosition.x, CardViewConfig.cardDeckPosition.y, AL.bl);
  }

  private void moveCards() {
    int amountPlayer = this.boardController.getPlayerAmount();
    HashMap<Integer, Vector2> positionsMap = CardViewConfig.playerPositionsMap.get(amountPlayer);

    for (int cardIndex = 0; cardIndex < 2; cardIndex++) {
      for (int playerIndex = 0; playerIndex < amountPlayer; playerIndex++) {
        CardComponent card = this.cardPool.getInst();
        card.setOrigin(Align.center);
        card.setSize(card.getWidth() * 0.7f, card.getHeight() * 0.7f);

        this.interactiveGroup.addActor(card);

        Vector2 startPoint = new Vector2(CardViewConfig.cardDeckPosition.x, CardViewConfig.cardDeckPosition.y);
        Vector2 endPoint = positionsMap.get(playerIndex);
        card.setPosition(startPoint.x, startPoint.y);

        if (cardIndex > 0 && playerIndex == amountPlayer - 1)
          card.moveWithDelay(startPoint, endPoint,
              0.3f, Interpolation.fastSlow, 0.1f * (playerIndex + cardIndex * amountPlayer),
              () -> Main.moduleMessage().sendMsg(new DistributeCardsCompletedMessage()));
        else
          card.moveWithDelay(startPoint, endPoint, 0.3f, Interpolation.fastSlow, 0.1f * (playerIndex + cardIndex * amountPlayer));

        this.playersCards.get(playerIndex).add(card);
      }
    }

  }

  public static BoardViewModule inst() {
    return inst;
  }
}
