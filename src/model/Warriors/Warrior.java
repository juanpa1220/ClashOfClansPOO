package model.Warriors;

import model.Army;
import model.BoardItem;

import java.util.ArrayList;
import java.util.Random;

public abstract class Warrior extends Army {
    private final String type;
    private boolean isEnemy;

    private int lastPosition;
    private ArrayList<Warrior> opponents;
    private Warrior opponent = null;
    private boolean running = true;
    private boolean paused = false;

    public Warrior(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
        this.type = type;
    }

    @Override
    public abstract void attack();

    public void killOpponent(Warrior warrior) {
        if (this.getOpponents().contains(warrior)) {
            warrior.setRunning(false);
            super.getRefBoard().get(warrior.getLastPosition()).removeTroop();
            super.getRefBoard().get(warrior.getCurrentPosition()).removeTroop();
            this.getOpponents().remove(warrior);
        }
    }

    public void setInitPosition() {
        boolean flag = true;
        while (flag) {
            int index = new Random().nextInt(399);
            if (this.getRefBoard().get(index).isAvailable()) {
                this.getRefBoard().get(index).setWarrior(this);
                this.setCurrentPosition(index);
                flag = false;
            }
        }
    }

    public void reduceLife(int hits) {
        super.setLife(super.getLife() - hits);
    }

    public void setIsEnemy(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }

    public void setOpponents(ArrayList<Warrior> opponents) {
        this.opponents = opponents;
    }

    public ArrayList<Warrior> getOpponents() {
        return opponents;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public Warrior getOpponent() {
        return opponent;
    }

    public void setOpponent(Warrior opponent) {
        this.opponent = opponent;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public String getType() {
        return type;
    }
}
