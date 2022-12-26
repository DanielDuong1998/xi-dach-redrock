package com.redrock.logics;

import com.badlogic.gdx.utils.Array;
import com.redrock.logics.controllers.BoardController;
import com.redrock.sdk.component.GenericModule;

public class LogicCenterModule extends GenericModule {
  private static final LogicCenterModule inst = new LogicCenterModule();
  private BoardController boardController = BoardController.inst();

  private LogicCenterModule(){

  }

  public void startGame(){
    this.boardController.initCards();
    this.boardController.initPlayersCards();
    this.boardController.distributeCardsForPlayers();
    this.boardController.findSpecialCardsAfterDistribute();
  }

  public Array<Integer> getPlayerCards(int playerIndex){
    return this.boardController.getPlayerCards(playerIndex);
  }

  public void startPickCardsForBots(){
    this.boardController.handlePickCardsForBots();
  }

  public void pickCardForPlayer(){
    this.boardController.pickACardForPlayer();
  }

  public static LogicCenterModule inst(){return inst;}
}
