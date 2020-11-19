package model.Warriors;

import model.Army;
import model.BoardItem;

import java.util.ArrayList;
import java.util.Random;

public class Warrior extends Army {

    public Warrior(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
    }


    public void appear(ArrayList<BoardItem> board) {
        int possition = new Random().nextInt(256);
        board.get(possition).setTroop(this);
    }
}
