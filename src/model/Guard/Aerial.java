package model.Guard;

import model.BoardItem;

import java.util.ArrayList;

public class Aerial extends Guard {
    public Aerial(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, int scope, Object objectiveWarrior) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, scope, objectiveWarrior);
    }

}