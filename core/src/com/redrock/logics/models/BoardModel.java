package com.redrock.logics.models;

import com.badlogic.gdx.utils.Array;

public class BoardModel {
  public boolean isDealerMode = true;

  public int dealerSpecialCardType = 0;
  public int dealerIndex = 0;
  public int amountPlayer = 6;

  public Array<Array<Integer>> playerCards = new Array<>();

  public Array<Integer> cards = new Array<>();
  public Array<Integer> goldPlayerIndexes = new Array<>();
  public Array<Integer> blackJackPlayerIndexes = new Array<>();
}