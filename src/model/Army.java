package model;

import control.BoardController;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Army extends Thread {
    private final ArrayList<BoardItem> refBoad;
    private String name;
    private String dirImage;
    private int appearanceLevel;
    private int level;
    private int life;
    private int hits;
    private int housingSpace;


    public Army(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace) {
        this.refBoad = refBoard;
        this.name = name;
        this.dirImage = dirImage;
        this.appearanceLevel = appearanceLevel;
        this.level = level;
        this.life = life;
        this.hits = hits;
        this.housingSpace = housingSpace;
    }


    @Override
    public String toString() {
        return "Army{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getDirImage() {
        return dirImage;
    }
}
