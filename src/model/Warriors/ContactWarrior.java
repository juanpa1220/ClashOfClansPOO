package model.Warriors;

import javafx.application.Platform;
import model.BoardItem;
import model.Interfaces.IMakeNoise;
import model.Interfaces.IMove;

import java.util.ArrayList;
import java.util.Random;

public class ContactWarrior extends Warrior implements IMove, IMakeNoise {
    private boolean runnig = true;
    private boolean paused = false;
    private ArrayList<BoardItem> refBoard;
    private int currentPosition;
    private int lastPosition;

    public ContactWarrior(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
        this.refBoard = refBoard;
        this.currentPosition = new Random().nextInt(256);
        this.lastPosition = -1;
    }

    @Override
    public void run() {
        while (runnig) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    move();
                }
            });
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void makeNoise() {

    }

    @Override
    public void move() {
        if (!this.refBoard.get(this.currentPosition).isAvailable()) {
            if (lastPosition != -1) {
                this.refBoard.get(this.lastPosition).removeTroop();
            }
            this.refBoard.get(currentPosition).setTroop(this);
            this.lastPosition = this.currentPosition;
            this.currentPosition += 1;
        }
    }
}
