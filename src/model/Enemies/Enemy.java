package model.Enemies;

import model.Army;
import model.BoardItem;

import java.util.ArrayList;

public class Enemy extends Army {
    private int Scope;
    private Object objectiveWarrior;

    public Enemy(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, int scope, Object objectiveWarrior) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
        Scope = scope;
        this.objectiveWarrior = objectiveWarrior;
    }
}
