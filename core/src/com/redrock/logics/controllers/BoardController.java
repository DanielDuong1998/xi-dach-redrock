package com.redrock.logics.controllers;

import com.badlogic.gdx.utils.Array;
import com.redrock.Main;
import com.redrock.logics.configs.CardConfig;
import com.redrock.logics.models.BoardModel;
import com.redrock.logics.models.PointCardModel;
import com.redrock.viewModule.messages.CardForPlayerReceivedMessage;

public class BoardController {
  private static final BoardController inst = new BoardController();
  private final BoardModel boardModel;
  private final CheckCardsController checkController = CheckCardsController.inst();

  private BoardController() {
    this.boardModel = new BoardModel();
  }

  /**
   * This function is used to initialize cards.
   */
  public void initCards() {
    this.tryClearCardsBeforeInit();

    for (int suitIndex = 0; suitIndex < 4; suitIndex++) {
      for (int value = 1; value <= 13; value++) {
        this.boardModel.cards.add(CardController.inst().createCard(suitIndex, value));
      }
    }

    this.boardModel.cards.shuffle();
  }

  private void tryClearCardsBeforeInit() {
    if (this.boardModel.cards.size > 0)
      this.boardModel.cards.removeRange(0, this.boardModel.cards.size - 1);
  }

  /**
   * This function is used to initialize player slot
   */
  public void initPlayersCards() {
    this.tryClearPlayersCardsBeforeInit();

    for (int playerIndex = 0; playerIndex < this.boardModel.amountPlayer; playerIndex++) {
      Array<Integer> cardsForPlayer = new Array<>();
      this.boardModel.playerCards.add(cardsForPlayer);
    }
  }

  private void tryClearPlayersCardsBeforeInit() {
    if (this.boardModel.playerCards.size > 0) {
      for (int i = 0; i < this.boardModel.playerCards.size; i++)
        if (this.boardModel.playerCards.get(i).size > 0)
          this.boardModel.playerCards.get(i).removeRange(0, this.boardModel.playerCards.get(i).size - 1);

      this.boardModel.playerCards.removeRange(0, this.boardModel.playerCards.size - 1);
    }
  }

  /**
   * This function is used to start distribute cards for players.
   */
  public void distributeCardsForPlayers() {
    for (int roundDistributeIndex = 0; roundDistributeIndex < 2; roundDistributeIndex++) {
      for (int playerIndex = 0; playerIndex < this.boardModel.amountPlayer; playerIndex++) {
        int card = this.boardModel.cards.pop();
        this.boardModel.playerCards.get(playerIndex).add(card);
      }
    }
  }

  /**
   * Initialize the dealerIndex
   */
  public void initDealerIndex() {
    if (this.boardModel.isDealerMode) {
      this.boardModel.dealerIndex = 0;
    }

    this.boardModel.dealerIndex = (int) Math.floor(Math.random() * (this.boardModel.amountPlayer - 1) + 1);
  }

  /**
   * This function is used to find Black Jack cards or
   * Gold cards after distribute cards.
   */
  public void findSpecialCardsAfterDistribute() {
    this.tryClearSpecialCards();

    for (int i = 0; i < this.boardModel.playerCards.size; i++) {
      PointCardModel point = this.checkController.getPointCards(this.boardModel.playerCards.get(i));

      if (point.type == 4) {
        this.boardModel.goldPlayerIndexes.add(i);

        if (i == this.boardModel.dealerIndex)
          this.boardModel.dealerSpecialCardType = 2;
      }

      if (point.type == 3) {
        this.boardModel.blackJackPlayerIndexes.add(i);

        if (i == this.boardModel.dealerIndex)
          this.boardModel.dealerSpecialCardType = 1;
      }
    }
  }

  private void tryClearSpecialCards() {
    if (this.boardModel.goldPlayerIndexes.size > 0)
      this.boardModel.goldPlayerIndexes.removeRange(0, this.boardModel.goldPlayerIndexes.size - 1);

    if (this.boardModel.blackJackPlayerIndexes.size > 0)
      this.boardModel.blackJackPlayerIndexes.removeRange(0, this.boardModel.blackJackPlayerIndexes.size - 1);
  }

  public void handlePickCardsForBots() {
    if (this.boardModel.isDealerMode) {
      for (int playerIndex = 1; playerIndex < this.boardModel.amountPlayer; playerIndex++) {
        this.pickCardForBot(playerIndex);
      }
    }
  }

  public void pickCardForBot(int playerIndex) {
    while (this.shouldPickCardForBot(this.boardModel.playerCards.get(playerIndex))) {
      this.boardModel.playerCards.get(playerIndex).add(this.boardModel.cards.pop());
    }
  }

  private boolean shouldPickCardForBot(Array<Integer> cards) {
    if (cards.size < 2 || cards.size > 5)
      throw new IndexOutOfBoundsException("size cards invalid to pick more cards. Size cards: " + cards.size);

    if(cards.size == 5)
      return false;

    PointCardModel point = this.checkController.getPointCards(cards);
    int cardSize = cards.size;
    int type = point.type;

    if (type == 1) {
      int pointValue = point.value;
      int ratio = this.getRatioBotPickCard(cardSize, pointValue);

      int valueRatio = (int) Math.floor(Math.random() * 100 + 1);
      return valueRatio <= ratio;
    }

    return false;
  }

  private int getRatioBotPickCard(int cardSize, int pointValue){
    if(pointValue >= 16 && pointValue <= 20)
      return CardConfig.ratioPickCards.get(cardSize).get(pointValue);

    return 100;
  }

  /**
   * this function is used to try to pick a cord for the player.
   */
  public void pickACardForPlayer() {
    PointCardModel point = this.checkController.getPointCards(this.boardModel.playerCards.get(0));
    if (point.type == 0 || (point.type == 1 && point.value == 21)) return;

    if (this.boardModel.playerCards.get(0).size >= 2 && this.boardModel.playerCards.get(0).size <= 4){
      int card = this.boardModel.cards.pop();
      this.boardModel.playerCards.get(0).add(card);

      CardForPlayerReceivedMessage message = new CardForPlayerReceivedMessage();
      message.card = card;
      Main.moduleMessage().sendMsg(message);
    }
  }

  public Array<Integer> getGoldPlayerIndexes() {
    return boardModel.goldPlayerIndexes;
  }

  public Array<Integer> getBlackjackPlayerIndexes() {
    return boardModel.blackJackPlayerIndexes;
  }

  /**
   * This functions is used to return the type of point card of the Dealer.
   * the value type equal 0, this point is normal
   * the value type equal 1, this point is blackjack
   * the value type equal 2, this point is gold cards
   * @return the type of the Dealer point cards.
   */
  public int getDealerHasSpecialCardType() {
    return this.boardModel.dealerSpecialCardType;
  }

  public int getDealerIndex(){return this.boardModel.dealerIndex;}

  /**
   * this func is used to return the current amount player.
   *
   * @return current amount player.
   */
  public int getPlayerAmount() {
    return this.boardModel.amountPlayer;
  }

  public Array<Integer> getPlayerCards(int playerIndex) {
    return this.boardModel.playerCards.get(playerIndex);
  }

  public Array<Array<Integer>> getPlayerCards() {
    return this.boardModel.playerCards;
  }

  public static BoardController inst() {
    return inst;
  }
}
