package model.Warriors;

import model.Army;

public class Warrior extends Army {
    private int xp;

    public Warrior(String name, String dirImage, int appearanceLevel, int level, int life, int hits, int field, int xp) {
        super(name, dirImage, appearanceLevel, level, life, hits, field);
        this.xp = xp;
    }
}
