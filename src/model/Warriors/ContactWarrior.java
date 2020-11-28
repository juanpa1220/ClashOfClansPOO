package model.Warriors;

import model.BoardItem;
import model.Interfaces.IGrowUp;
import model.Interfaces.IMakeNoise;
import model.Interfaces.IMove;

import java.util.ArrayList;

public class ContactWarrior extends Warrior implements IMove, IMakeNoise, IGrowUp {

    public ContactWarrior(ArrayList<BoardItem> refBoard, String name, String dirImage,
                          int appearanceLevel, int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, type);
    }

    @Override
    public void onLoop() {
        this.move();
    }

    @Override
    public void run() {
        super.defaultStart(true);
    }

    @Override
    public void makeNoise() {
        mediaPlayer.play();
        mediaPlayer.stop();
    }

    @Override
    public void growUp() {
        this.setLife(this.getLife() + (this.getLevel() / 2));
        this.setHits(this.getHits() + (this.getLevel() / 2));
        this.setHousingSpace(this.getHousingSpace() + (this.getLevel() / 2));
    }

    @Override
    public void attack() {
        this.makeNoise();
        super.defaultAttack();
    }

    @Override
    public void move() {
        super.defaultMove(1);
    }

}
