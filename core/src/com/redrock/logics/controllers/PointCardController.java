package com.redrock.logics.controllers;

import com.redrock.logics.models.PointCardModel;

public class PointCardController {
  private static final PointCardController inst = new PointCardController();

  private PointCardController(){

  }

  public int compare(PointCardModel point1, PointCardModel point2){
    int type1 = point1.type;
    int type2 = point2.type;
    int value1 = point1.value;
    int value2 = point2.value;

    if(type1 != type2) return type1 > type2 ? 1 : -1;

    if(value1 != value2) return value1 > value2 ? 1 : -1;

    return 0;
  }

  public static PointCardController inst(){return inst;}
}
