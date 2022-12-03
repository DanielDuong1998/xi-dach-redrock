package com.redrock.logics.models;

import java.util.HashMap;

public class SuitModel {
  public enum Suit {
    HEART,
    DIAMONDS,
    CLUBS,
    SPADES
  }

  public static final HashMap<Suit, Integer> mapSuitToId = new HashMap<>();

  static {
    mapSuitToId.put(Suit.HEART, 0);
    mapSuitToId.put(Suit.DIAMONDS, 1);
    mapSuitToId.put(Suit.CLUBS, 2);
    mapSuitToId.put(Suit.SPADES, 3);
  }

  public static final HashMap<Integer, Suit> mapIdToSuit = new HashMap<>();

  static {
    mapIdToSuit.put(0, Suit.HEART);
    mapIdToSuit.put(1, Suit.DIAMONDS);
    mapIdToSuit.put(2, Suit.CLUBS);
    mapIdToSuit.put(3, Suit.SPADES);
  }
}

