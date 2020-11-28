package model.Warriors;

import model.BoardItem;
import model.Interfaces.IMakeNoise;

import java.util.ArrayList;

public class Hero extends ContactWarrior implements IMakeNoise {
    public Hero(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, type);
    }

    @Override
    public void onLoop() {
        super.onLoop();
    }

    @Override
    public void run() {
        super.defaultStart(true);
    }

    @Override
    public void makeNoise() {
        super.makeNoise();
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

