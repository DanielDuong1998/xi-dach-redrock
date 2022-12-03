package com.redrock.logics.models;

/**
 * type = {0, 1, 2, 3, 4}
 */
public class PointCardModel {
  public int type = 0;
  public int value = 0;
  public String valueStr;

  public PointCardModel(int type, int value){
    this.type = type;
    this.value = value;

    this.initValueStr();
  }

  private void initValueStr(){
    switch (type){
      case 4:
        this.valueStr = "XI VANG";
        break;

      case 3:
        this.valueStr = "XI DACH";
        break;

      case 2:
        this.valueStr = "NGU LINH: " + (21 - this.value);
        break;

      case 1:
        this.valueStr = "" + this.value;
        break;

      default:
        this.valueStr = "QUAC";
    }
  }
}
