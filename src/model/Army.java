package model;

import java.util.ArrayList;

public abstract class Army extends Thread {
    private final ArrayList<BoardItem> refBoard;
    private final String name;
    private final String dirImage;
    private final int appearanceLevel;
    private final int level;
    private int life;
    private int hits;
    private int housingSpace;
    private int currentPosition;
    private boolean running = true;
    private boolean paused = false;

    public Army(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace) {
        this.refBoard = refBoard;
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
                "name='" + this.name + '\'' +
                '}';
    }

    public abstract void setInitPosition();

    public abstract void attack();


    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }


    public String getTroopName() {
        return this.name;
    }

    public ArrayList<BoardItem> getRefBoard() {
        return refBoard;
    }

    public String getDirImage() {
        return dirImage;
    }

    public int getAppearanceLevel() {
        return appearanceLevel;
    }

    public int getLevel() {
        return level;
    }

    public int getHousingSpace() {
        return housingSpace;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setHousingSpace(int housingSpace) {
        this.housingSpace = housingSpace;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
