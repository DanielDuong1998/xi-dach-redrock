package com.redrock.logics.controllers;

import com.redrock.logics.models.SuitModel;

public class CardController {
  private static final CardController inst = new CardController();

  private CardController() {}

  public int createCard(int suitValue, int value){
    String binSuitStr = Integer.toBinaryString(suitValue);
    binSuitStr = binSuitStr.length() <= 1 ? "0" + binSuitStr : binSuitStr;

    String binValueStr = Integer.toBinaryString(value);

    return Integer.parseInt(binValueStr + binSuitStr, 2);
  }


  public int getValue(int card){
    return Math.min(10, card >> 2);
  }

  /**
   * This func is used to find the suit of the cards.
   * @param card need to check the suit.
   * @return the bool, true if the suit is red.
   */
  public SuitModel.Suit getSuit(int card){
    return SuitModel.mapIdToSuit.get(this.getSuitValue(card));
  }

  public int getSuitValue(int card){
    return Math.min((card & 3), 10);
  }

  public static CardController inst() {
    return inst;
  }
}
