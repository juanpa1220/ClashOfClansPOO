package model;

public class Army {
    private String name;
    private String dirImage;
    private int appearanceLevel;
    private int level;
    private int life;
    private int hits;
    private int field;


    public Army(String name, String dirImage, int appearanceLevel, int level, int life, int hits, int field) {
        this.name = name;
        this.dirImage = dirImage;
        this.appearanceLevel = appearanceLevel;
        this.level = level;
        this.life = life;
        this.hits = hits;
        this.field = field;
    }
}
