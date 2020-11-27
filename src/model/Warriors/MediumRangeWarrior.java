package model.Warriors;

import model.BoardItem;
import model.Interfaces.IGrowUp;
import model.Interfaces.IMove;

import java.util.ArrayList;

public class MediumRangeWarrior extends Warrior implements IMove, IGrowUp {
    int scope;

    public MediumRangeWarrior(ArrayList<BoardItem> refBoard, String name, String dirImage,
                              int appearanceLevel, int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, type);
        this.scope = 2 + level / 3;
    }

    @Override
    public void onLoop() {
        this.move();
    }

    @Override
    public void run() {
        defaultStart();
    }

    @Override
    public void growUp() {
        this.scope = 1 + this.getLevel() / 3;

        this.setLife(this.getLife() + (this.getLevel() / 2));
        this.setHits(this.getHits() + (this.getLevel() / 2));
        this.setHousingSpace(this.getHousingSpace() + (this.getLevel() / 2));
    }

    @Override
    public void attack() {
        super.defaultAttack();
    }

    @Override
    public void move() {
        super.defaultMove(this.scope);
    }

}
