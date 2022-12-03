package com.redrock.sdk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.*;
import com.redrock.Main;
import com.redrock.manager.SoundMgr;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "LibGDXUnsafeIterator"})
public class AssetLoader {
  private final String                            atlasPath       = "textureAtlas/";
  private final String                            texturePath     = "texture/";
  private final String                            animPath        = "animation/";
  private final String                            soundPath       = "sound/";
  private final String                            musicPath       = "music/";
  private final String                            bitmapFontPath  = "bitmapFont/";
  private final String                            shaderPath      = "shader/";
  private final String                            particlePath    = "particle/";

  private       AssetManager                      assetManager;

  private       Map<String, Array<String>>        mFilename;
  private       Map<String, TextureRegion>        tgCache;
  private       Map<String, Texture>              texCache;
  private       Map<String, TextureAtlas>         alCache;
  private       Map<String, BitmapFont>           bmfCache;
  private       Map<String, FileHandle>           particleCache;

  public AssetLoader() {
    mFilename       = new HashMap<>();
    assetManager    = new AssetManager();
    tgCache         = new HashMap<>();
    texCache        = new HashMap<>();
    alCache         = new HashMap<>();
    bmfCache        = new HashMap<>();
    particleCache   = new HashMap<>();

//    load(atlasPath, "texture-atlas", TextureAtlas.class);
//    load(texturePath, "texture", Texture.class);
//    load(soundPath, "sound", Sound.class);
//    load(musicPath, "music", Music.class);
//    loadBMFont();
    load();
  }

  public <T> void load(String folderPath, String filename, Class<T> type)  {
    Array<String> listFilesName = listFiles(folderPath, filename);
    mFilename.put(filename, listFilesName);
    for (String file : listFilesName)
      assetManager.load(folderPath + file, type);
  }

  public void loadBMFont() {
    Array<String> bmtFonts = listFiles(bitmapFontPath, "bitmap-font");
    mFilename.put("bitmap-fonts", bmtFonts);
    BitmapFontLoader.BitmapFontParameter bmParam = new BitmapFontLoader.BitmapFontParameter();
    bmParam.minFilter = Texture.TextureFilter.Linear;
    bmParam.magFilter = Texture.TextureFilter.Linear;

    for (String bmtFont : bmtFonts)
      assetManager.load(bitmapFontPath + bmtFont, BitmapFont.class, bmParam);
  }

  public void load() {
    Array<String> atlases = listFiles(atlasPath, "texture-atlas");
    mFilename.put("texture-atlas", atlases);
    for (String atlas : atlases)
      assetManager.load(atlasPath + atlas, TextureAtlas.class);

    Array<String> textures = listFiles(texturePath, "texture");
    mFilename.put("textures", textures);
    for (String tex : textures)
      assetManager.load(texturePath + tex, Texture.class);

    //sound
    Array<String> sounds  = listFiles(soundPath, "sound");
    mFilename.put("sounds", sounds);
    for (String sound : sounds) {
      if (Main.isWebGL()) {
        String md5 = "assets/" + Main.iPlat().getMD5PathFile("sound/" + sound);
        SoundMgr.loadSoundH5(sound, md5, false, false);
      }
      else
        assetManager.load(soundPath + sound, Sound.class);
    }

    //music
    Array<String> musics = listFiles(musicPath, "music");
    mFilename.put("musics", musics);
    for (String music : musics) {
      if (Main.isWebGL()) {
        String md5 = "assets/" + Main.iPlat().getMD5PathFile("music/" + music);
        SoundMgr.loadSoundH5(music, md5, true, true);
      }
      else
        assetManager.load(musicPath + music, Music.class);
    }

    Array<String> bmtFonts = listFiles(bitmapFontPath, "bitmap-font");
    mFilename.put("bitmap-fonts", bmtFonts);
    BitmapFontLoader.BitmapFontParameter bmParam = new BitmapFontLoader.BitmapFontParameter();
    bmParam.minFilter = Texture.TextureFilter.Linear;
    bmParam.magFilter = Texture.TextureFilter.Linear;

    for (String bmtFont : bmtFonts)
      assetManager.load(bitmapFontPath + bmtFont, BitmapFont.class, bmParam);
  }

  public void cache() {
    //atlas
    Array<String> atlases = mFilename.get("texture-atlas");
    for (String name : atlases) {
      TextureAtlas atlas = assetManager.get(atlasPath + name, TextureAtlas.class);
      alCache.put(name.replace(".atlas", ""), atlas);

      Array<TextureAtlas.AtlasRegion> regions =  atlas.getRegions();
      for (TextureAtlas.AtlasRegion region : regions) {
        TextureRegion tg = atlas.findRegion(region.name);
        tg.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tgCache.put(region.name, tg);
      }
    }

    //texture
    Array<String> textures = mFilename.get("textures");
    if (textures != null)
      for (String name : textures) {
        Texture tex = assetManager.get(texturePath + name, Texture.class);
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        texCache.put(name.replace(".png", ""), tex);
      }

    //bitmap font
    Array<String> bmtFonts = mFilename.get("bitmap-fonts");
    for (String name : bmtFonts) {
      BitmapFont font = assetManager.get(bitmapFontPath + name, BitmapFont.class);
      font.getData().markupEnabled = true;

      for (int i = 0; i < font.getRegions().size; i++) {
        font.getRegions().get(i).getTexture()
            .setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getRegions().get(i);
      }
      String[] a = name.split("\\.");
      bmfCache.put(a[0], font);
    }

    //particles
    Array<String> particles = mFilename.get("particles");
    if (particles != null)
      for (String filename : particles)
        particleCache.put(filename, assetManager.get(particlePath + filename, FileHandle.class));

  }

  private Array<String> listFiles(String path, String fileName) {
    Array<String> lsFiles   = new Array<>();
    String[]      fileNames = Gdx.files.internal(path + fileName + ".txt").readString().split("\n");

    for (String n : fileNames)
      if (!n.equals(""))
        lsFiles.add(n);

    return lsFiles;
  }

  public boolean update() {
    return assetManager.update(17);
  }

  public float getProgress() {
    return assetManager.getProgress();
  }

  public TextureAtlas getAl(String key) {
    if(alCache.get(key) == null)
      throw new NullPointerException("Can not find TextureAtlas with key: " + key);

    return alCache.get(key);
  }

  public Music getMusic(String key) {
    if(assetManager.get(musicPath + key + ".mp3") == null)
      throw new NullPointerException("Can not find Music with key: " + key);

    return assetManager.get(musicPath + key + ".mp3");
  }

  public Sound getSound(String key) {
    if(assetManager.get(soundPath + key + ".mp3") == null)
      throw new NullPointerException("Can not find Sound with key: " + key);

    return assetManager.get(soundPath + key + ".mp3");
  }

  public BitmapFont getBMFont(String key) {
    if(bmfCache.get(key) == null)
      throw new NullPointerException("Can not find BitmapFont with key: " + key);

    return bmfCache.get(key);
  }

//  public ParticleEffectPool getPEP(String key) {
//    return pCache.get(key);
//  }
//
//  public Animation<TextureRegion> getAnim(String key,
//                                          int frameCol, int frameRow, float frameDuration) {
//    Texture walkSheet = assetManager.get(animPath + key + ".png", Texture.class);
//    TextureRegion[][] tmp = TextureRegion.split(walkSheet,
//            walkSheet.getWidth() / frameCol,
//            walkSheet.getHeight() / frameRow);
//
//    TextureRegion[] walkFrames = new TextureRegion[frameCol * frameRow];
//    int index = 0;
//    for (int i = 0; i < frameRow; i++) {
//      for (int j = 0; j < frameCol; j++) {
//        walkFrames[index++] = tmp[i][j];
//      }
//    }
//    return new Animation<>(frameDuration, walkFrames);
//  }

  public TextureRegion getTG(String key) {
    if(tgCache.get(key) == null)
      throw new NullPointerException("Can not find TextureRegion with key: " + key);

    return tgCache.get(key);
  }

  public FileHandle getFilePar(String key) {
    if(particleCache.get(key) == null)
      throw new NullPointerException("Can not find FileHandle with key: " + key);

    return particleCache.get(key);
  }

  public Texture getTex(String key) {
    if(texCache.get(key) == null)
      throw new NullPointerException("Can not find Texture with key: " + key);

    return texCache.get(key);
  }

//  public NinePatch getPatch(String key) {
//    Array<String> atlases = listFiles(atlasPath);
//    NinePatch patch;
//    for (String name : atlases) {
//      TextureAtlas atlas = assetManager.get(atlasPath + name, TextureAtlas.class);
//      patch = atlas.createPatch(key);
//      if(patch != null)
//        return patch;
//    }
//    return null;
//  }

  public ShaderProgram getShaderProgram(String key) {
    return assetManager.get(shaderPath + key + "/", ShaderProgram.class);
  }

  public float progress() {
    return assetManager.getProgress();
  }

  /*********************************************************************************************************************
   LOADER PARAMETERS
   ********************************************************************************************************************/
//  public static class FreeTypeParam extends FreetypeFontLoader.FreeTypeFontLoaderParameter {
//    public String key;
//  }

  public static class ShaderParameter extends AssetLoaderParameters<ShaderProgram> {

  }

  public static class JsonParameter extends AssetLoaderParameters<JsonValue> {

  }
}