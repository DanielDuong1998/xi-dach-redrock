package com.redrock.viewModule.hardViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;


public class HeaderMainGameView extends AlignGroup {
  private static final HeaderMainGameView inst = new HeaderMainGameView();

  private AlignGroup backButton, settingButton;
  private Image backShape, backShapePress, settingShape, settingShapePress;

  private HeaderMainGameView(){
    this.initUI();
    this.initButtonListener();
  }

  private void initUI(){
    this.setSize(Main.getStage().getWidth(), 57);
    this.backButton = new AlignGroup();
    this.settingButton = new AlignGroup();

    backShape = new Image(Main.asset().getTG("btn_back"));
    backShapePress = new Image(Main.asset().getTG("btn_back_pressed"));
    settingShape = new Image(Main.asset().getTG("btn_menu"));
    settingShapePress = new Image(Main.asset().getTG("btn_menu_pressed"));

    this.backButton.setSize(backShape.getWidth(), backShape.getHeight());
    this.settingButton.setSize(settingShape.getWidth(), settingShape.getHeight());

    this.backButton.addActor(backShape, backShapePress);
    this.settingButton.addActor(settingShape, settingShapePress);

    this.addActor(this.backButton, 0, 0, AL.cl);
    this.addActor(this.settingButton, 0, 0, AL.cr);

    this.displayPressBackButton(false);
    this.displayPressSettingButton(false);
  }

  private void initButtonListener(){
    this.backButton.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        displayPressBackButton(true);

        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);

        displayPressBackButton(false);

        if(inTapSquare())
          handleBackClicked();
      }
    });

    this.settingButton.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        displayPressSettingButton(true);

        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);

        displayPressSettingButton(false);

        if(inTapSquare())
          handleSettingClicked();
      }
    });
  }

  private void handleBackClicked(){
    System.out.println("handle back btn clicked");
  }

  private void handleSettingClicked(){
    System.out.println("handle setting btn clicked");
  }

  private void displayPressBackButton(boolean isPress){
    this.backShape.setVisible(!isPress);
    this.backShapePress.setVisible(isPress);
  }

  private void displayPressSettingButton(boolean isPress){
    this.settingShape.setVisible(!isPress);
    this.settingShapePress.setVisible(isPress);
  }

  public static HeaderMainGameView inst(){return inst;}
}
