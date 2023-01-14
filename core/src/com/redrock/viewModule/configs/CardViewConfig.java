package com.redrock.viewModule.configs;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class CardViewConfig {
  public static HashMap<Integer, HashMap<Integer, Vector2>> playerPositionsMap = new HashMap<>();
  public static HashMap<Integer, Vector2> avatarPositionMap = new HashMap<>();
  public static HashMap<Integer, Vector2> scoreFramePositionMap = new HashMap<>();
  public static HashMap<Integer, Vector2> resultFramePositionMap = new HashMap<>();
  public static HashMap<Integer, Vector2> showCardPositionMap = new HashMap<>();

  public static Vector2 cardDeckPosition = new Vector2(866, 592);
  public static float ratioCardView = 0.7f;
  public static float maxWithPlayerDeckCards = 200;

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

    map6.put(0, new Vector2(604, 227));
    map6.put(1, new Vector2(325, 294));
    map6.put(2, new Vector2(175, 534));
    map6.put(3, new Vector2(685, 534));
    map6.put(4, new Vector2(1019, 534));
    map6.put(5, new Vector2(869, 294));

    avatarPositionMap.put(0, new Vector2(572, 6));
    avatarPositionMap.put(1, new Vector2(163, 118));
    avatarPositionMap.put(2, new Vector2(13, 358));
    avatarPositionMap.put(3, new Vector2(473, 498));
    avatarPositionMap.put(4, new Vector2(1131, 358));
    avatarPositionMap.put(5, new Vector2(981, 118));

    scoreFramePositionMap.put(0, new Vector2(33, 345));
    scoreFramePositionMap.put(1, new Vector2(170, 302));
    scoreFramePositionMap.put(2, new Vector2(170, 302));
    scoreFramePositionMap.put(3, new Vector2(220, 160));
    scoreFramePositionMap.put(4, new Vector2(-105, 302));
    scoreFramePositionMap.put(5, new Vector2(-107, 302));

    resultFramePositionMap.put(0, new Vector2(1.5f, 248));
    resultFramePositionMap.put(1, new Vector2(131, 203));
    resultFramePositionMap.put(2, new Vector2(131, 203));
    resultFramePositionMap.put(3, new Vector2(174, 100));
    resultFramePositionMap.put(4, new Vector2(-131, 203));
    resultFramePositionMap.put(5, new Vector2(-131, 203));

    showCardPositionMap.put(1, new Vector2(333, 250));
    showCardPositionMap.put(2, new Vector2(183, 490));
    showCardPositionMap.put(3, new Vector2(686, 490));
    showCardPositionMap.put(4, new Vector2(1027, 490));
    showCardPositionMap.put(5, new Vector2(877, 250));
  }
}
