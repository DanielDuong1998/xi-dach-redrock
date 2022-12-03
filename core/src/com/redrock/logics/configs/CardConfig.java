package com.redrock.logics.configs;

import java.util.HashMap;

public class CardConfig {
  public static HashMap<Integer, HashMap<Integer, Integer>> ratioPickCards = new HashMap<>();
  static {
    HashMap<Integer, Integer> map1 = new HashMap<>();
    HashMap<Integer, Integer> map2 = new HashMap<>();

    ratioPickCards.put(2, map1);
    ratioPickCards.put(3, map1);
    ratioPickCards.put(4, map2);

    map1.put(16, 50);
    map1.put(17, 30);
    map1.put(18, 15);
    map1.put(19, 5);
    map1.put(20, 2);

    map2.put(16, 70);
    map2.put(17, 60);
    map2.put(18, 40);
    map2.put(19, 20);
    map2.put(20, 5);
  }
}
