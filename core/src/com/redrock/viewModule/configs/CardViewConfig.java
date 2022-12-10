package com.redrock.viewModule.configs;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class CardViewConfig {
  public static HashMap<Integer, HashMap<Integer, Vector2>> playerPositionsMap = new HashMap<>();
  static {
    HashMap<Integer, Vector2> map2 = new HashMap<>();
    HashMap<Integer, Vector2> map3 = new HashMap<>();
    HashMap<Integer, Vector2> map4 = new HashMap<>();
    HashMap<Integer, Vector2> map5 = new HashMap<>();
    HashMap<Integer, Vector2> map6 = new HashMap<>();

    playerPositionsMap.put(2, map2);
    playerPositionsMap.put(3, map3);
    playerPositionsMap.put(4, map4);
    playerPositionsMap.put(5, map5);
    playerPositionsMap.put(6, map6);

    map2.put(0, new Vector2(599, 94));
    map2.put(1, new Vector2(581, 549));

    map3.put(0, new Vector2(581, 94));
    map3.put(1, new Vector2(201, 549));
    map3.put(2, new Vector2(997, 549));

    map4.put(0, new Vector2(581, 94));
    map4.put(1, new Vector2(201, 304));
    map4.put(2, new Vector2(599, 549));
    map4.put(3, new Vector2(997, 304));

    map5.put(0, new Vector2(581, 94));
    map5.put(1, new Vector2(201, 404));
    map5.put(2, new Vector2(201, 94));
    map5.put(3, new Vector2(997, 94));
    map5.put(4, new Vector2(997, 404));

    map6.put(0, new Vector2(599, 94));
    map6.put(1, new Vector2(201, 404));
    map6.put(2, new Vector2(201, 94));
    map6.put(3, new Vector2(581, 549));
    map6.put(4, new Vector2(997, 94));
    map6.put(5, new Vector2(997, 404));
  }
}
