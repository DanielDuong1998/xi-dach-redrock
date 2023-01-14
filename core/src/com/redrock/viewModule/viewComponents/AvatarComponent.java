package com.redrock.viewModule.viewComponents;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;
import com.redrock.sdk.modules.generic.ModuleMessage;
import com.redrock.viewModule.messages.ShowViewCardsMessage;

public class AvatarComponent extends AlignGroup {
  public boolean isFree = true;
  public int playerIndex = -1;

  private Image avatar, avatarBack, avatarFrame, scoreFrame, nameFrame, scoreBack;
  private Label nameLabel, coinLabel, scoreLabel, resultLabel;
  private AlignGroup resultGroup;
  private AlignGroup showCardButton;

  public AvatarComponent() {
    this.setSize(136, 217);
    this.initUI();
  }

  private void initUI() {
    this.resultGroup = new AlignGroup();
    this.showCardButton = new AlignGroup();
    this.avatar = new Image(Main.asset().getTG("avatar_1"));
    this.avatarBack = new Image(Main.asset().getTG("bg_avatar"));
    this.avatarFrame = new Image(Main.asset().getTG("frame_avatar"));
    this.scoreFrame = new Image(Main.asset().getTG("frame_score"));
    this.nameFrame = new Image(Main.asset().getTG("frame_name"));
    this.scoreBack = new Image(Main.asset().getTG("result_1"));
    Image showCardShape = new Image(Main.asset().getTG("btn_action_2"));
    this.nameLabel = new Label("Boss", new Label.LabelStyle(Main.asset().getBMFont("Arial-boldMT-LightYellow"), null));
    this.coinLabel = new Label("2", new Label.LabelStyle(Main.asset().getBMFont("Arial-boldMT-LightYellow"), null));
    this.scoreLabel = new Label("3", new Label.LabelStyle(Main.asset().getBMFont("Arial-boldMT-Yellow"), null));
    this.resultLabel = new Label("XÌ DÁCH", new Label.LabelStyle(Main.asset().getBMFont("Arial-boldMT-White"), null));
    Label showCardLabel = new Label("Xét", new Label.LabelStyle(Main.asset().getBMFont("Arial-boldMT-White"), null));

    this.coinLabel.setOrigin(Align.center);
    showCardShape.setSize(showCardShape.getWidth()*0.35f, showCardShape.getHeight()*0.35f);

    this.resultGroup.setSize(139, 34);
    this.showCardButton.setSize(showCardShape.getWidth(), showCardShape.getHeight());

    this.showCardButton.addActor(showCardShape, 0, 0, AL.c);
    this.showCardButton.addActor(showCardLabel, 0, 3, AL.c);
    this.addActor(this.nameFrame, 0, 0, AL.c);
    this.addActor(this.avatarBack, 0, 6, AL.ct);
    this.addActor(this.avatar, 0, 6, AL.ct);
    this.addActor(this.avatarFrame, 0, 5, AL.ct);
    this.addActor(this.scoreFrame, 0, 0, AL.c);
    this.addActor(this.nameLabel, 0, 139, AL.ct);
    this.addActor(this.coinLabel, 0, 163, AL.ct);
    this.addActor(this.scoreLabel, 0, 163, AL.ct);
    this.addActor(this.resultGroup, 0, 0, AL.ct);
    this.resultGroup.addActor(scoreBack, resultLabel);

    this.initListener();
  }

  private void initListener(){
    this.showCardButton.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Main.moduleMessage().sendMsg(new ShowViewCardsMessage(playerIndex));

        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  public void setScoreFramePositionFromPoint(Vector2 position) {
    this.scoreFrame.setPosition(position.x, position.y);
    this.coinLabel.setPosition(this.scoreFrame.getX() + (this.scoreFrame.getWidth() - this.coinLabel.getPrefWidth()) / 2,
        this.scoreFrame.getY() + (this.scoreFrame.getHeight() - this.coinLabel.getPrefHeight()) / 2);
  }

  public void setResultFramePositionFromPoint(Vector2 position) {
    this.resultGroup.setPosition(position.x, position.y);
    this.resultLabel.setPosition((this.resultGroup.getWidth() - this.resultLabel.getPrefWidth()) / 2,
        (this.resultGroup.getHeight() - this.resultLabel.getPrefHeight()) / 2);
  }

  public void addGroupForShowCardButton(AlignGroup group){
    group.addActor(this.showCardButton);
  }

  public void updatePositionForShowCardButton(Vector2 position){
    this.showCardButton.setPosition(position.x, position.y);
  }

  public void updatePlayerIndex(int playerIndex){
    this.playerIndex = playerIndex;
  }

  public void reset() {
    this.isFree = true;
    this.playerIndex = -1;

    this.showCardButton.clearListeners();
    this.clearListeners();
    this.clearActions();
    this.remove();
  }
}
