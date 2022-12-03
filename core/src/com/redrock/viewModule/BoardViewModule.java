package com.redrock.viewModule;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;
import com.redrock.logics.controllers.BoardController;
import com.redrock.sdk.component.GenericModule;
import com.redrock.viewModule.configs.CardViewConfig;
import com.redrock.viewModule.viewComponents.CardComponent;
import com.redrock.viewModule.viewComponents.pools.CardPool;

import java.util.HashMap;

public class BoardViewModule extends GenericModule {
  private static final BoardViewModule inst = new BoardViewModule();

  private AlignGroup logicGroup;

  private BoardController boardController = BoardController.inst();
  private Array<Array<CardComponent>> playersCards = new Array<>();
  private CardPool cardPool = CardPool.inst();

  private BoardViewModule() {

  }

  public void init(AlignGroup logicGroup){
    this.logicGroup = logicGroup;
  }

  public void displayDistributeCardsAnimations() {
    this.initCards();
  }

  private void initCards() {
    this.tryClearPlayersCardsBeforeInit();

    this.displayBotsCards();
    this.displayPlayerCards();
  }

  private void displayBotsCards(){
    int amountPlayer = this.boardController.getPlayerAmount();
    HashMap<Integer, Vector2> positionsMap = CardViewConfig.playerPositionsMap.get(amountPlayer);

    for(int botIndex = 1; botIndex < amountPlayer; botIndex++) {
      CardComponent card = this.cardPool.getInst();
      card.setOrigin(Align.center);
      card.setSize(card.getWidth()*0.7f, card.getHeight()*0.7f);
      Vector2 cardPosition = positionsMap.get(botIndex);
      this.logicGroup.addActor(card, cardPosition.x, cardPosition.y, AL.tl);
    }
  }

  private void displayPlayerCards(){

  }

  private void tryClearPlayersCardsBeforeInit() {
    if (this.playersCards.size > 0) {
      for (Array<CardComponent> playerCards : this.playersCards)
        if (playerCards.size > 0)
          playerCards.removeRange(0, playerCards.size - 1);

      this.playersCards.removeRange(0, this.playersCards.size - 1);
    }
  }

  public static BoardViewModule inst() {
    return inst;
  }
}
