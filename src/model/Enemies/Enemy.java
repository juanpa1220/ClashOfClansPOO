package model.Enemies;

import model.Army;

public class Enemy extends Army {
    private int Scope;
    private Object objectiveWarrior;

    public Enemy(String name, String dirImage, int appearanceLevel, int level, int life, int hits, int field, int scope, Object objectiveWarrior) {
        super(name, dirImage, appearanceLevel, level, life, hits, field);
        Scope = scope;
        this.objectiveWarrior = objectiveWarrior;
    }
}
