package model.Warriors;

import model.BoardItem;
import model.Interfaces.IMove;

import java.util.ArrayList;

public class MediumRangeWarriors extends Warrior implements IMove {
    public MediumRangeWarriors(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
    }

    @Override
    public void attack() {

    }

    @Override
    public void move() {

    }
}
