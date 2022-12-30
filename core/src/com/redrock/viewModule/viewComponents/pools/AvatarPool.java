package com.redrock.viewModule.viewComponents.pools;

import com.badlogic.gdx.utils.Array;
import com.redrock.viewModule.viewComponents.AvatarComponent;

public class AvatarPool {
  private static final AvatarPool inst = new AvatarPool();
  private final Array<AvatarComponent> pool = new Array<>();

  private AvatarPool(){

  }

  public AvatarComponent getInst(){
    for(AvatarComponent avatarComponent : this.pool){
      if(avatarComponent.isFree){
        avatarComponent.isFree = false;

        return avatarComponent;
      }
    }

    AvatarComponent avatarComponent = new AvatarComponent();
    avatarComponent.isFree = false;
    this.pool.add(avatarComponent);

    return avatarComponent;
  }

  public void reset(){
    for(AvatarComponent avatarComponent: this.pool){
      avatarComponent.reset();
    }
  }

  public static AvatarPool inst(){return inst;}
}
