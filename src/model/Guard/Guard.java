package model.Guard;
import model.Warriors.Warrior;
import model.Army;
import model.BoardItem;

import java.util.ArrayList;
import java.util.Random;

public abstract class Guard extends Army {
    //private Object objectiveWarrior;
    //private Warrior objectiveWarrior = null;
    private ArrayList<Warrior> warriors;
    private final int scope;
    private ArrayList<Warrior> objectives;
    private Warrior opponent = null;

    private boolean running = true;
    private boolean paused = false;
    private final String type;

    public Guard(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, int scope, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
        this.scope = scope;
        this.type = type;

    }

    @Override
    public void setInitPosition() {
        boolean flag = true;
        while (flag) {
            int index = new Random().nextInt(399);
            if (this.getRefBoard().get(index).isAvailable()) {
                this.getRefBoard().get(index).setGuard(this);
                this.setCurrentPosition(index);
                flag = false;
            }
        }
    }

    public void reduceLife(int hits) {
        super.setLife(super.getLife() - hits);
    }


    @Override
    public void attack() {}

    public abstract void killObjective();

    public int getScope() {
       return scope;
    }

    public void setScope(int scope) {
        scope = scope;
    }

    public ArrayList<Warrior> getObjectives() {
        return objectives;
    }

    public void setObjectives(ArrayList<Warrior> objectives) {
        this.objectives = objectives;
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

    public Warrior getOpponent() {
        return opponent;
    }

    public void setOpponent(Warrior opponent) {
        this.opponent = opponent;
    }

    public String getType() {
        return type;
    }

}