package com.redrock.viewModule.viewComponents;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;

public class AvatarComponent extends AlignGroup {
  public boolean isFree = true;

  private Image avatar, avatarBack, avatarFrame, scoreFrame, nameFrame;
  private Label nameLabel, coinLabel;

  public AvatarComponent(){
    this.setSize(136, 217);
    this.initUI();
  }

  private void initUI(){
    this.avatar = new Image(Main.asset().getTG("avatar_1"));
    this.avatarBack = new Image(Main.asset().getTG("bg_avatar"));
    this.avatarFrame = new Image(Main.asset().getTG("frame_avatar"));
    this.scoreFrame = new Image(Main.asset().getTG("frame_score"));
    this.nameFrame = new Image(Main.asset().getTG("frame_name"));
    this.nameLabel = new Label("1", new Label.LabelStyle(Main.asset().getBMFont("wheel_font"), null));
    this.coinLabel = new Label("2", new Label.LabelStyle(Main.asset().getBMFont("wheel_font"), null));

    this.addActor(this.nameFrame, 0, 0, AL.c);
    this.addActor(this.avatarBack, 0, 6, AL.ct);
    this.addActor(this.avatar, 0, 6, AL.ct);
    this.addActor(this.avatarFrame, 0, 5, AL.ct);
    this.addActor(this.nameLabel, 0, 139, AL.ct);
    this.addActor(this.coinLabel, 0, 163, AL.ct);
  }

  public void reset() {
    this.isFree = true;

    this.clearListeners();
    this.clearActions();
    this.remove();
  }
}
