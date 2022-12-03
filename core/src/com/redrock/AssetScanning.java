package com.redrock;

import java.io.*;
import java.util.Scanner;

public class AssetScanning {

  public static void main(String[] args) throws IOException {

    String dir = System.getProperty("user.dir") + "/assets";

    writeFile(dir, "/textureAtlas/texture-atlas.txt", listFileName(dir + "/textureAtlas", ".atlas"));
    writeFile(dir, "/texture/texture.txt", listFileName(dir + "/texture", ".png"));
    writeFile(dir, "/bitmapFont/bitmap-font.txt", listFileName(dir + "/bitmapFont", ".fnt"));
    writeFile(dir, "/sound/sound.txt", listFileName(dir + "/sound", ".mp3"));
    writeFile(dir, "/music/music.txt", listFileName(dir + "/music", ".mp3"));
    writeFile(dir, "/particle/particle.txt", listFileName(dir + "/particle", ""));
  }

  private static void writeFile(String dir, String fileName, String contents) {
    try {
      File myObj = new File(dir + "/" + fileName);
      if (myObj.createNewFile())
        System.out.println("File created: " + myObj.getName());
      else
        System.out.println("File already exists.".toUpperCase());
    }
    catch (IOException e) {
      e.printStackTrace();
      return;
    }

    try {
      FileWriter myWriter = new FileWriter(dir + "/" + fileName);
      myWriter.write(contents);
      myWriter.close();

      System.out.println("Successfully wrote to the file.".toUpperCase());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String listFileName(String dir, String ext) {
    File      dirPath   = new File(dir);
    String[]  contents  = dirPath.list();

    if (contents == null)
      return "";

    StringBuilder sBuilder = new StringBuilder();
    for (String content : contents) {
      if (content.contains(ext))
        sBuilder.append(content).append("\n");

      System.out.println(content);
    }

    return sBuilder.toString();
  }

}
