package model.Warriors;

import model.BoardItem;
import model.Interfaces.IMove;

import java.util.ArrayList;

public class Beast extends MediumRangeWarrior implements IMove {
    int scope;

    public Beast(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel,
                 int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, type);
        this.scope = 4;
    }

    @Override
    public void onLoop() {
        super.onLoop();
    }

    @Override
    public void run() {
        this.defaultStart(true);
    }

    @Override
    public void attack() {
        super.attack();
    }

    @Override
    public void move() {
        super.move();
    }

}
