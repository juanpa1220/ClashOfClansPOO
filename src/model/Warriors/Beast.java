package model.Warriors;

import model.BoardItem;

import java.util.ArrayList;

public class Beast extends MediumRangeWarrior {
    public Beast(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, type);
    }
}
