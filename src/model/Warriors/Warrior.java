package model.Warriors;

import model.Army;
import model.BoardItem;

import java.util.ArrayList;

public abstract class Warrior extends Army {
    private boolean isEnemy;
    private int currentPosition;
    private int lastPosition;
    private ArrayList<Warrior> opponents;
    private Warrior opponent = null;
    private boolean running = true;
    private boolean paused = false;

    public Warrior(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
    }

    @Override
    public abstract void attack();

    public void killOpponent(Warrior warrior) {
        super.getRefBoard().get(warrior.currentPosition).removeTroop();
        this.getOpponents().remove(warrior);
        warrior.setRunning(false);
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

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }
}
