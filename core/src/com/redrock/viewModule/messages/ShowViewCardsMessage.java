package com.redrock.viewModule.messages;

import com.redrock.sdk.modules.generic.GenericMessage;

public class ShowViewCardsMessage extends GenericMessage {
  public int playerIndex;

  public ShowViewCardsMessage(int playerIndex){
    this.playerIndex = playerIndex;
  }

  @Override
  public void reset() {

  }
}
