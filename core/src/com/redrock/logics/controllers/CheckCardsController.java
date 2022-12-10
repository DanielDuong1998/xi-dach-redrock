package com.redrock.logics.controllers;

import com.badlogic.gdx.utils.Array;
import com.redrock.logics.models.PointCardModel;

public class CheckCardsController {
  private static final CheckCardsController inst = new CheckCardsController();

  private PointCardController pointCardController = PointCardController.inst();
  private CardController cardController = CardController.inst();

  private CheckCardsController(){

  }

  /**
   * this function is used to get point of cards
   * @param cards need to find the point
   * @return PointCardModel
   */
  public PointCardModel getPointCards(Array<Integer> cards){
    if(cards.size < 2 || cards.size > 5)
      throw new IndexOutOfBoundsException("Can not get Point cards with size" + cards.size);

    switch (cards.size){
      case 2:
        return this.getPointCardsSize2(cards);
      case 3:
        return this.getPointCardsSize3(cards);
      case 4:
        return this.getPointCardsSize4(cards);
      default:
        return this.getPointCardsSize5(cards);
    }
  }

  private PointCardModel getPointCardsSize2(Array<Integer> cards){
    int cardValue0 = this.cardController.getValue(cards.get(0));
    int cardValue1 = this.cardController.getValue(cards.get(1));

    if(cardValue0 == 1 && cardValue1 == 1 )
      return new PointCardModel(4, 0);

    if((cardValue0 == 1 && cardValue1 == 10) || (cardValue0 == 10 && cardValue1 == 1))
      return new PointCardModel(3, 0);

    if(cardValue0 == 1 || cardValue1 == 1)
      return new PointCardModel(1, cardValue0 + cardValue1 + 10);

    return new PointCardModel(1, cardValue0 + cardValue1);
  }

  private PointCardModel getPointCardsSize3(Array<Integer> cards){
    int cardValue0 = this.cardController.getValue(cards.get(0));
    int cardValue1 = this.cardController.getValue(cards.get(1));
    int cardValue2 = this.cardController.getValue(cards.get(2));

    if((cardValue0 == 1 && cardValue1 != 1 && cardValue2 != 1) ||
        (cardValue0 != 1 && cardValue1 == 1 && cardValue2 != 1) ||
        (cardValue0 != 1 && cardValue1 != 1 && cardValue2 == 1)){
      int result1 = cardValue0 + cardValue1 + cardValue2 + 10;
      int result2 = cardValue0 + cardValue1 + cardValue2 + 9;
      int result3 = cardValue0 + cardValue1 + cardValue2;

      if(result1 <= 21) return new PointCardModel(1, result1);
      if(result2 <= 21) return new PointCardModel(1, result2);
      if(result3 <= 21) return new PointCardModel(1, result3);

      return new PointCardModel(0, result3);
    }

    int result = cardValue0 + cardValue1 + cardValue2;
    if(result <= 21) return new PointCardModel(1, result);

    return new PointCardModel(0, result);
  }

  private PointCardModel getPointCardsSize4(Array<Integer> cards){
    int cardValue0 = this.cardController.getValue(cards.get(0));
    int cardValue1 = this.cardController.getValue(cards.get(1));
    int cardValue2 = this.cardController.getValue(cards.get(2));
    int cardValue3 = this.cardController.getValue(cards.get(3));

    int result = cardValue0 + cardValue1 + cardValue2 + cardValue3;
    if(result <= 21) return new PointCardModel(1, result);

    return new PointCardModel(0, result);
  }

  private PointCardModel getPointCardsSize5(Array<Integer> cards){
    int cardValue0 = this.cardController.getValue(cards.get(0));
    int cardValue1 = this.cardController.getValue(cards.get(1));
    int cardValue2 = this.cardController.getValue(cards.get(2));
    int cardValue3 = this.cardController.getValue(cards.get(3));
    int cardValue4 = this.cardController.getValue(cards.get(4));

    int result = cardValue0 + cardValue1 + cardValue2 + cardValue3 + cardValue4;
    if(result <= 21) return new PointCardModel(2, 21 - result);

    return new PointCardModel(0, result);
  }

  public int compareCards(Array<Integer> cards1, Array<Integer> cards2){
    PointCardModel point1 = this.getPointCards(cards1);
    PointCardModel point2 = this.getPointCards(cards2);

    return this.pointCardController.compare(point1, point2);
  }

  public static CheckCardsController inst(){return inst;}
}
