package model.Warriors;

import model.BoardItem;

import java.util.ArrayList;

public class Hero extends ContactWarrior {
    public Hero(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, type);
    }
}
