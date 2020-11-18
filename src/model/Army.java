package model;

public class Army extends Thread{
    private String name;
    private String dirImage;
    private int appearanceLevel;
    private int level;
    private int life;
    private int hits;
    private int housingSpace;


    public Army(String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace) {
        this.name = name;
        this.dirImage = dirImage;
        this.appearanceLevel = appearanceLevel;
        this.level = level;
        this.life = life;
        this.hits = hits;
        this.housingSpace = housingSpace;
    }
}
