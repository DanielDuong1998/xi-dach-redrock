package com.redrock.viewModule.viewComponents;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;
import com.redrock.logics.models.SuitModel;

import java.util.HashMap;

public class CardComponent extends AlignGroup {
  private SuitModel.Suit suit;
  public boolean isFree = true;
  private boolean isEnableClick = false;
  private int value;
  private int suitValue;

  private Image shape;
  private Image closeShape;

  public CardComponent(SuitModel.Suit suit, int value) {
    this.suit = suit;
    this.value = value;
    this.suitValue = SuitModel.mapSuitToId.get(this.suit);

    this.initUI();
    this.initListenner();
  }

  private void initUI() {
    String cardTextureName = this.suitValue + "" + this.value;

    this.shape = new Image(Main.asset().getTG(cardTextureName));
    this.closeShape = new Image(Main.asset().getTG("00"));

    this.setSize(this.shape.getWidth(), this.shape.getHeight());

    this.addActor(this.shape, 0, 0, AL.c);
    this.addActor(this.closeShape, 0, 0, AL.c);
  }

  private void initListenner(){
    this.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        if(isEnableClick){
          displayCard();
        }
      }
    });
  }

  public void displayCard(){
    this.closeShape.setVisible(false);
  }

  public void changeValue(SuitModel.Suit suit, int value) {
    this.suit = suit;
    this.value = value;

    this.updateCardUI();
  }

  private void updateCardUI() {
    String textureRegionCardName = SuitModel.mapSuitToId.get(this.suit) + "" + this.value;
    TextureRegionDrawable textureRegionDrawable = textureCardsMap.get(textureRegionCardName);
    this.shape.setDrawable(textureRegionDrawable);
  }

  public void setIsEnableClick(boolean isEnableClick){
    this.isEnableClick = isEnableClick;
  }

  @Override
  public void setSize(float width, float height) {
    super.setSize(width, height);

    this.shape.setSize(width, height);
    this.closeShape.setSize(width, height);
  }

  public void reset() {
    this.isFree = true;
    this.isEnableClick = false;

    this.clearListeners();
    this.clearActions();
    this.remove();
  }

  public void moveWithDelay(Vector2 startPoint, Vector2 endPoint, float duration, Interpolation interpolation, float delay) {
    this.addAction(Actions.sequence(
        Actions.delay(delay),
        Actions.run(() -> this.move(startPoint, endPoint, duration, interpolation))
    ));
  }

  public void moveWithDelay(Vector2 startPoint, Vector2 endPoint, float duration,
                            Interpolation interpolation, float delay, Runnable runnable) {
    this.addAction(Actions.sequence(
        Actions.delay(delay),
        Actions.run(() -> this.move(startPoint, endPoint, duration, interpolation, runnable))
    ));
  }

  public void move(Vector2 startPoint, Vector2 endPoint, float duration, Interpolation interpolation) {
    this.setPosition(startPoint.x, startPoint.y);

    this.addAction(
        Actions.sequence(
            Actions.moveTo(endPoint.x, endPoint.y, duration, interpolation)
        )
    );
  }

  public void move(Vector2 startPoint, Vector2 endPoint, float duration, Interpolation interpolation, Runnable runnable) {
    this.setPosition(startPoint.x, startPoint.y);

    this.addAction(
        Actions.sequence(
            Actions.moveTo(endPoint.x, endPoint.y, duration, interpolation),
            Actions.run(runnable)
        )
    );
  }

  private static HashMap<String, TextureRegionDrawable> textureCardsMap = new HashMap<>();

  static {
    for (int suitIndex = 0; suitIndex < 4; suitIndex++) {
      for (int cardIndex = 1; cardIndex <= 13; cardIndex++) {
        String textureRegionCardName = suitIndex + "" + cardIndex;
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(Main.asset().getTG(textureRegionCardName));
        textureCardsMap.put(textureRegionCardName, textureRegionDrawable);
      }
    }
  }
}
