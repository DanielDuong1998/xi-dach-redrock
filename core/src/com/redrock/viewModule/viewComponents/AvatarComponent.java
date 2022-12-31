package com.redrock.viewModule.viewComponents;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;

public class AvatarComponent extends AlignGroup {
  public boolean isFree = true;

  private Image avatar, avatarBack, avatarFrame, scoreFrame, nameFrame, scoreBack;
  private Label nameLabel, coinLabel, scoreLabel, scoreText;
  private AlignGroup scoreGroup;

  public AvatarComponent() {
    this.setSize(136, 217);
    this.initUI();
  }

  private void initUI() {
    this.scoreGroup = new AlignGroup();
    this.avatar = new Image(Main.asset().getTG("avatar_1"));
    this.avatarBack = new Image(Main.asset().getTG("bg_avatar"));
    this.avatarFrame = new Image(Main.asset().getTG("frame_avatar"));
    this.scoreFrame = new Image(Main.asset().getTG("frame_score"));
    this.nameFrame = new Image(Main.asset().getTG("frame_name"));
    this.nameLabel = new Label("1", new Label.LabelStyle(Main.asset().getBMFont("wheel_font"), null));
    this.coinLabel = new Label("2", new Label.LabelStyle(Main.asset().getBMFont("wheel_font"), null));
    this.scoreLabel = new Label("3", new Label.LabelStyle(Main.asset().getBMFont("wheel_font"), null));
    this.scoreText = new Label("4", new Label.LabelStyle(Main.asset().getBMFont("wheel_font"), null));

    this.coinLabel.setOrigin(Align.center);

    this.scoreGroup.setSize(139, 34);

    this.addActor(this.nameFrame, 0, 0, AL.c);
    this.addActor(this.avatarBack, 0, 6, AL.ct);
    this.addActor(this.avatar, 0, 6, AL.ct);
    this.addActor(this.avatarFrame, 0, 5, AL.ct);
    this.addActor(this.scoreFrame, 0, 0, AL.c);
    this.addActor(this.nameLabel, 0, 139, AL.ct);
    this.addActor(this.coinLabel, 0, 163, AL.ct);
    this.addActor(this.scoreLabel, 0, 163, AL.ct);
    this.addActor(this.scoreBack, 0, 0, AL.ct);
    this.scoreGroup.addActor(scoreBack, scoreText);
  }

  public void setScoreFramePositionFromPoint(Vector2 position) {
    this.scoreFrame.setPosition(position.x, position.y);
    this.coinLabel.setPosition(this.scoreFrame.getX() + (this.scoreFrame.getWidth() - this.coinLabel.getPrefWidth()) / 2,
        this.scoreFrame.getY() + (this.scoreFrame.getHeight() - this.coinLabel.getPrefHeight()) / 2);
  }

  public void reset() {
    this.isFree = true;

    this.clearListeners();
    this.clearActions();
    this.remove();
  }
}
