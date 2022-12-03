package com.redrock.scenes;

import com.badlogic.gdx.ScreenAdapter;
import com.redrock.Main;
import com.redrock.comons.AL;
import com.redrock.comons.AlignGroup;
import com.redrock.logics.LogicCenterModule;
import com.redrock.manager.SceneMgr;
import com.redrock.viewModule.ViewCenterModule;
import com.redrock.viewModule.hardViews.GameHardView;

public class GamePlayScene extends ScreenAdapter {
  private AlignGroup gParent = new AlignGroup();
  private AlignGroup logicGroup = new AlignGroup();

  public GamePlayScene() {
    Main.layers().init(SceneMgr.GAMEPLAY_SCENE, Main.getStage().getWidth(), Main.getStage().getHeight());

    this.gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());
    this.logicGroup.setSize(1280, 720);

    this.gParent.addActor(GameHardView.inst());
    this.gParent.addActor(logicGroup, 0, 0, AL.c);

    LogicCenterModule.inst().startGame();
    ViewCenterModule.inst().init(this.logicGroup);
  }

  @Override
  public void show() {
    super.show();

    Main.layers().activeLayersBy(SceneMgr.GAMEPLAY_SCENE);
    Main.getStage().addActor(this.gParent);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

  }

  @Override
  public void resize(int width, int height) {

  }
}
