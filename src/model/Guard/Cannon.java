package model.Guard;

import model.BoardItem;
import model.Warriors.Warrior;

import java.util.ArrayList;

public abstract class Cannon extends Guard {


    public Cannon(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, int scope, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, scope, type);
    }
}
}
