package com.redrock.viewModule.hardViews;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;
import com.redrock.viewModule.viewComponents.AvatarComponent;

public class GameHardView extends AlignGroup {
  private static final GameHardView inst = new GameHardView();

  private GameHardView(){
    this.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());

    Image table = new Image(Main.asset().getTG("table"));

    table.setSize(this.getWidth(), this.getHeight());

    this.addActor(table, 0, 0, AL.c);
  }

  public static GameHardView inst(){return inst;}
}
