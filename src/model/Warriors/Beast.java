package model.Warriors;

import model.BoardItem;

import java.util.ArrayList;

public class Beast extends MediumRangeWarriors{
    public Beast(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
    }
}
