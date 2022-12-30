package com.redrock.logics;

import com.badlogic.gdx.utils.Array;
import com.redrock.Main;
import com.redrock.logics.controllers.BoardController;
import com.redrock.sdk.component.GenericModule;
import com.redrock.sdk.modules.generic.GenericMessage;
import com.redrock.viewModule.messages.PickACardForPlayerMessage;

public class LogicCenterModule extends GenericModule {
  private static final LogicCenterModule inst = new LogicCenterModule();
  private BoardController boardController = BoardController.inst();

  private LogicCenterModule(){
    Main.moduleMessage().register(this, PickACardForPlayerMessage.class);
  }

  @Override
  public void handleMsg(GenericMessage msg) {
    if(msg.getClass() == PickACardForPlayerMessage.class){
      this.pickCardForPlayer();
    }
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
