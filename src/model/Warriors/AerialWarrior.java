package model.Warriors;

import model.BoardItem;

import java.util.ArrayList;

public class AerialWarrior extends Warrior{
    public AerialWarrior(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
    }

    @Override
    public void attack() {

    }
}
