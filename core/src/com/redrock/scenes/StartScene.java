package com.redrock.scenes;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.redrock.Main;
import com.redrock.manager.SceneMgr;

public class StartScene extends ScreenAdapter {

  public static float CENTER_X = Main.getStage().getWidth()/2;
  public static float CENTER_Y = Main.getStage().getHeight()/2;

  private final Group gParent;

  public StartScene() {
    this.gParent = new Group();
    this.gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());

    Main.layers().init(SceneMgr.START_SCENE, Main.getStage().getWidth(), Main.getStage().getHeight());
  }

  @Override
  public void show() {
    super.show();

    Main.layers().activeLayersBy(SceneMgr.START_SCENE);
    Main.getStage().addActor(gParent);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);

    CENTER_X = Main.getStage().getWidth() / 2;
    CENTER_Y = Main.getStage().getHeight() / 2;

    gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());
  }
}
