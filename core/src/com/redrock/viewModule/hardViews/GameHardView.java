package com.redrock.viewModule.hardViews;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;

public class GameHardView extends AlignGroup {
  private static final GameHardView inst = new GameHardView();

  private GameHardView(){
    this.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());

    Image bg = new Image(Main.asset().getTG("main_bg"));
    Image table = new Image(Main.asset().getTG("table"));

    bg.setSize(this.getWidth(), this.getHeight());

    this.addActor(bg, 0, 0, AL.c);
    this.addActor(PickCardView.inst(), 0, 0, AL.c);
  }

  public static GameHardView inst(){return inst;}
}
