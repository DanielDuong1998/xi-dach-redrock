package com.redrock.viewModule.viewComponents;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;
import com.redrock.logics.models.SuitModel;

import java.util.HashMap;

public class CardComponent extends AlignGroup {
  private SuitModel.Suit suit;
  public boolean isFree = true;
  private int suitValue;
  private int value;

  private Image shape;
  private Image closeShape;

  public CardComponent(SuitModel.Suit suit, int value){
    this.suit = suit;
    this.value = value;
    this.suitValue = SuitModel.mapSuitToId.get(this.suit);

    this.initUI();
  }

  private void initUI(){
    String cardTextureName = this.suitValue + "" + this.value;

    this.shape = new Image(Main.asset().getTG(cardTextureName));
    this.closeShape = new Image(Main.asset().getTG("00"));

    this.setSize(this.shape.getWidth(), this.shape.getHeight());

    this.addActor(this.shape, 0, 0, AL.c);
    this.addActor(this.closeShape, 0, 0, AL.c);
  }

  public void changeValue(SuitModel.Suit suit, int value){
    this.suit = suit;
    this.value = value;

    this.updateCardUI();
  }

  private void updateCardUI(){
    String textureRegionCardName = "" + SuitModel.mapSuitToId.get(this.suit) + this.value;
    TextureRegionDrawable textureRegionDrawable = textureCardsMap.get(textureRegionCardName);
    this.shape.setDrawable(textureRegionDrawable);
  }

  @Override
  public void setSize(float width, float height) {
    super.setSize(width, height);

    this.shape.setSize(width, height);
    this.closeShape.setSize(width, height);
  }

  public void reset(){
    this.isFree = true;
    this.remove();
  }

  private static HashMap<String, TextureRegionDrawable> textureCardsMap = new HashMap<>();
  static {
    for(int suitIndex = 0; suitIndex < 4; suitIndex++){
      for(int cardIndex = 1; cardIndex <= 13; cardIndex++){
        String textureRegionCardName = suitIndex + "" + cardIndex;
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(Main.asset().getTG(textureRegionCardName));
        textureCardsMap.put(textureRegionCardName, textureRegionDrawable);
      }
    }
  }
}
