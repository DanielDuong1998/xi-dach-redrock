package com.redrock.viewModule.viewComponents.pools;

import com.badlogic.gdx.utils.Array;
import com.redrock.logics.models.SuitModel;
import com.redrock.viewModule.viewComponents.CardComponent;

public class CardPool {
  private static final CardPool inst = new CardPool();
  private Array<CardComponent> pool = new Array<>();

  private CardPool(){
  }

  public CardComponent getInst(){
    for(CardComponent card : this.pool){
      if(card.isFree){
        card.isFree = false;

        return card;
      }
    }

    CardComponent card = new CardComponent(SuitModel.Suit.HEART, 1);
    card.isFree = false;
    this.pool.add(card);

    return card;
  }

  public CardComponent getInst(SuitModel.Suit suit, int value){
    for(CardComponent card : this.pool){
      if(card.isFree){
        card.isFree = false;
        card.changeValue(suit, value);

        return card;
      }
    }

    CardComponent card = new CardComponent(suit, value);
    card.isFree = false;
    this.pool.add(card);

    return card;
  }

  public void reset(){
    for(CardComponent card: this.pool){
      card.reset();
    }
  }

  public static CardPool inst(){return inst;}
}
