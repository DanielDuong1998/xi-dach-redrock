package com.redrock.viewModule;

import com.redrock.comons.AlignGroup;
import com.redrock.sdk.component.GenericModule;
import com.redrock.viewModule.hardViews.HeaderMainGameView;

public class ViewCenterModule extends GenericModule {
  private static final ViewCenterModule inst = new ViewCenterModule();

  private BoardViewModule boardViewModule = BoardViewModule.inst();
  private AlignGroup logicGroup;

  private ViewCenterModule(){

  }

  public void init(AlignGroup logicGroup){
    this.logicGroup = logicGroup;
    this.boardViewModule.init(this.logicGroup);

    this.startGame();
  }

  public void startGame(){
    this.boardViewModule.displayDistributeCardsAnimations();
  }

  public static ViewCenterModule inst(){return inst;}
}
