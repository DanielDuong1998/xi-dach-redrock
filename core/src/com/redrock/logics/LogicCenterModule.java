package com.redrock.logics;

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

  public static LogicCenterModule inst(){return inst;}
}
