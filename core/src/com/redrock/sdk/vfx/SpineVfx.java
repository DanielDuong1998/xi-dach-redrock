//package com.redrock.csdk.vfx;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//
//public class SpineVfx extends Actor {
//  Skeleton              skeleton;
//  AnimationState        state;
//  SkeletonRenderer      renderer;
//  SkeletonRendererDebug debugRenderer;
//  TwoColorPolygonBatch  tcpBatch;
//
//  public SpineVfx(TextureAtlas atlas, String strJson) {
//    renderer = new SkeletonRenderer();
//    renderer.setPremultipliedAlpha(false); // PMA results in correct blending without outlines.
//
////    atlas = new TextureAtlas(Gdx.files.internal(strAtlas));
//    SkeletonJson json = new SkeletonJson(atlas);
//
////    json.setScale(0.5f); // Load the skeleton at 60% the size it was in Spine.
//    SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(strJson));
//
//    skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
////    skeleton.setPosition(GStage.getWorldWidth()/2 - skeleton.getData().getWidth()/2, GStage.getWorldHeight()/2 - skeleton.getData().getHeight()/2);
////    skeleton.setPosition(GStage.getWorldWidth()/3, GStage.getWorldHeight()/2);
////    skeleton.setScale(1.5f,-1.5f);
//    //skeleton.set
//
//    AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
//    //stateData.setMix("run", "jump", 0.2f);
//    //stateData.setMix("jump", "run", 0.2f);
//    state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
//    state.setTimeScale(0.5f); // Slow all animations down to 50% speed.
//    // Queue animations on track 0.
//
//    tcpBatch = new TwoColorPolygonBatch();
//    tcpBatch.setPremultipliedAlpha(true);
//
//    setSize(skeleton.getData().getWidth(), skeleton.getData().getHeight());
//  }
//
//  @Override
//  public void act(float delta) {
//    super.act(delta);
//    state.update(delta);
//    state.apply(skeleton);
//    skeleton.updateWorldTransform();
//  }
//
//  @Override
//  public void draw(Batch batch, float parentAlpha) {
//    super.draw(batch, parentAlpha);
//
//    batch.end();
//    tcpBatch.setProjectionMatrix(batch.getProjectionMatrix());
//    tcpBatch.setTransformMatrix(batch.getTransformMatrix());
//    tcpBatch.begin();
//
//    renderer.draw(tcpBatch, skeleton);
//    tcpBatch.end();
//
//    batch.begin();
//  }
//
//  public void startAnim(int trackIndex, String anim, boolean loop) {
//    state.setAnimation(trackIndex, anim, loop);
//  }
//
//  public void startAnim(boolean loop) {
//    state.setAnimation(0, "animation", loop);
//  }
//
//  public void startAnim(String anim, boolean loop) {
//    state.setAnimation(0, anim, loop);
//  }
//
//  public void setPositionSpine(float x, float y) {
//    skeleton.setPosition(x, y);
//  }
//
//  public void setScaleSpine(float sclX, float sclY) {
////    skeleton.setScale(sclX, sclY);
//    skeleton.getRootBone().setScale(sclX, sclY);
//  }
//
//  public float getWidthSpine() {
//    return skeleton.getData().getWidth();
//  }
//
//  public float getHeightSpine() {
//    return skeleton.getData().getHeight();
//  }
//
//  public void flipYSpine() {
//    skeleton.setScaleY(-1);
//  }
//}
