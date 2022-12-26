package com.redrock.viewModule.hardViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;
import com.redrock.viewModule.messages.PickACardForPlayerMessage;

import java.util.HashMap;

public class PickCardView extends AlignGroup {
  private static final PickCardView inst = new PickCardView();

  private AlignGroup pickButton, confirmButton;
  private HashMap<ButtonType, HashMap<ButtonState, Array<Image>>> buttonComponentsMap;

  private PickCardView() {
    this.setSize(720, 92);
    this.initUI();
    this.initButtonListener();
  }

  private void initUI() {
    this.pickButton = new AlignGroup();
    this.confirmButton = new AlignGroup();
    this.buttonComponentsMap = new HashMap<>();
    HashMap<ButtonState, Array<Image>> pickButtonComponentsMap = new HashMap<>();
    HashMap<ButtonState, Array<Image>> confirmButtonComponentsMap = new HashMap<>();
    Array<Image> normalPickButtonComponents = new Array<>();
    Array<Image> holdPickButtonComponents = new Array<>();
    Array<Image> normalConfirmButtonComponents = new Array<>();
    Array<Image> holdConfirmButtonComponents = new Array<>();

    pickButtonComponentsMap.put(ButtonState.NORMAL, normalPickButtonComponents);
    pickButtonComponentsMap.put(ButtonState.HOLD, holdPickButtonComponents);
    confirmButtonComponentsMap.put(ButtonState.NORMAL, normalConfirmButtonComponents);
    confirmButtonComponentsMap.put(ButtonState.HOLD, holdConfirmButtonComponents);
    this.buttonComponentsMap.put(ButtonType.PICK_BUTTON, pickButtonComponentsMap);
    this.buttonComponentsMap.put(ButtonType.CONFIRM_BUTTON, confirmButtonComponentsMap);

    Image pickButtonShape = new Image(Main.asset().getTG("btn_rut"));
    Image pickButtonHoldShape = new Image(Main.asset().getTG("btn_rut_pressed"));
    Image pickButtonLabelShape = new Image(Main.asset().getTG("text_rut"));
    Image pickButtonLabelHoldShape = new Image(Main.asset().getTG("text_rut_pressed"));

    Image confirmButtonShape = new Image(Main.asset().getTG("btn_dan"));
    Image confirmButtonHoldShape = new Image(Main.asset().getTG("btn_dan_pressed"));
    Image confirmButtonLabelShape = new Image(Main.asset().getTG("text_dan"));
    Image confirmButtonLabelHoldShape = new Image(Main.asset().getTG("text_dan_pressed"));

    normalPickButtonComponents.add(pickButtonShape, pickButtonLabelShape);
    holdPickButtonComponents.add(pickButtonHoldShape, pickButtonLabelHoldShape);
    normalConfirmButtonComponents.add(confirmButtonShape, confirmButtonLabelShape);
    holdConfirmButtonComponents.add(confirmButtonHoldShape, confirmButtonLabelHoldShape);

    this.pickButton.setSize(pickButtonShape.getWidth(), pickButtonShape.getHeight());
    this.confirmButton.setSize(confirmButtonShape.getWidth(), confirmButtonShape.getHeight());

    this.pickButton.addActor(pickButtonShape, pickButtonHoldShape);
    this.pickButton.addActor(pickButtonLabelShape, 49, 12, AL.tl);
    this.pickButton.addActor(pickButtonLabelHoldShape, 53, 14, AL.tl);
    this.confirmButton.addActor(confirmButtonShape, confirmButtonHoldShape);
    this.confirmButton.addActor(confirmButtonLabelShape, 49, 12, AL.tl);
    this.confirmButton.addActor(confirmButtonLabelHoldShape, 53, 14, AL.tl);

    this.addActor(this.pickButton, 0, 0, AL.cl);
    this.addActor(this.confirmButton, 0, 0, AL.cr);

    this.setButtonState(ButtonType.PICK_BUTTON, ButtonState.HOLD);
    this.setButtonState(ButtonType.CONFIRM_BUTTON, ButtonState.HOLD);
  }

  private void setButtonState(ButtonType buttonType, ButtonState buttonState) {
    HashMap<ButtonState, Array<Image>> buttonMap = this.buttonComponentsMap.get(buttonType);

    for (ButtonState keyState : buttonMap.keySet()) {
      boolean shouldVisible = (keyState == buttonState);

      for (Image image : buttonMap.get(keyState)) {
        image.setVisible(shouldVisible);
      }
    }
  }

  private void initButtonListener() {
    this.pickButton.addListener(new ClickListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        setButtonState(ButtonType.PICK_BUTTON, ButtonState.HOLD);

        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);

        setButtonState(ButtonType.PICK_BUTTON, ButtonState.NORMAL);
        System.out.println("intapSquare: " + inTapSquare());
        if(inTapSquare())
          Main.moduleMessage().sendMsg(new PickACardForPlayerMessage());
      }
    });

    this.confirmButton.addListener(new ClickListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        setButtonState(ButtonType.CONFIRM_BUTTON, ButtonState.HOLD);

        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);

        setButtonState(ButtonType.CONFIRM_BUTTON, ButtonState.NORMAL);
      }
    });
  }

  public static PickCardView inst() {
    return inst;
  }

  private static enum ButtonType {
    CONFIRM_BUTTON, PICK_BUTTON
  }

  private static enum ButtonState {
    NORMAL, HOLD
  }
}
