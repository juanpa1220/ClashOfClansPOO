package model.Warriors;

import model.BoardItem;
import model.Interfaces.IGrowUp;
import model.Interfaces.IMakeNoise;

import java.awt.*;
import java.util.ArrayList;

public class AerialWarrior extends Warrior implements IGrowUp, IMakeNoise {
    int scope;

    public AerialWarrior(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, type);
        this.scope = 5 + level / 2;
    }

    @Override
    public void onLoop() {
        this.exist();
    }

    @Override
    public void run() {
        this.defaultStart(false);
    }

    private void exist() {
        boolean isAttack = false;
        for (int i = -scope; i < scope; i++) {
            for (int j = scope; j > -scope; j--) {
                Point current = this.getRefBoard().get(this.getCurrentPosition()).getPosition();
                for (Warrior w : this.getOpponents()) {
                    Point wCurrent = this.getRefBoard().get(w.getCurrentPosition()).getPosition();
                    if ((current.x + i == wCurrent.x && current.y + i == wCurrent.y) ||
                            (current.x + j == wCurrent.x && current.y + j == wCurrent.y) ||
                            (current.x + i == wCurrent.x && current.y + j == wCurrent.y) ||
                            (current.x + j == wCurrent.x && current.y + i == wCurrent.y)
                    ) {
                        this.setOpponent(w);
                        isAttack = true;
                        break;
                    }
                }
            }
        }
        if (isAttack) {
            this.attack();
        }
    }


    @Override
    public void attack() {
//        this.makeNoise();
        this.defaultAttack();
    }

    @Override
    public void growUp() {

    }

    @Override
    public void makeNoise() {
        mediaPlayer.play();
        mediaPlayer.stop();
    }

}
