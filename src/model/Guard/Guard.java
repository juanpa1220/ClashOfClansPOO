package model.Guard;

import javafx.application.Platform;
import model.Army;
import model.BoardItem;
import model.Warriors.Warrior;
import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;
import java.util.Random;

public class Guard extends Army {
    private Object[] objectiveWarrior;
    private  String type;
    private ArrayList<Warrior> opponents;
    private Warrior opponent = null;
    private int scope;

    public String getType() {
        return type;
    }

    public ArrayList<Warrior> getOpponents() {
        return opponents;
    }

    public Guard(ArrayList<BoardItem> refBoard,
                 String name,
                 String dirImage,
                 int appearanceLevel,
                 int level,
                 int life,
                 int hits,
                 int housingSpace,
                 Object[] objectiveWarrior,
                 String type,
                 int scope
    ) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
        this.scope = scope;
        this.type = type;
        this.objectiveWarrior = objectiveWarrior;

    }


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


    @Override
    public void attack() {
        // how?
        Warrior opponent = this.getOpponent();
        Object[] ow = this.objectiveWarrior;
        // Cast a trsing de object porque
        ArrayList<String> array = new ArrayList<>();
        // For each warrior in array object,
        for (Object a : ow
        ) {

            array.add((String) a);
        }
        if(opponent!=null){
            //Before attack check if is in the Objective Warriors of the Guard
            boolean isAllowed = array.contains(opponent.getType());

            if(isAllowed){
                opponent.reduceLife(this.getHits());
                if (opponent.getLife() <= 0) {
                    this.killOpponent(opponent);
                    this.setOpponent();
                }
            }
        }
    }


    public void killOpponent(Warrior warrior) {
        if (this.getOpponents().contains(warrior)) {
            super.getRefBoard().get(warrior.getLastPosition()).removeTroop();
            super.getRefBoard().get(warrior.getCurrentPosition()).removeTroop();
            super.getRefBoard().get(warrior.getNextPosition()).removeTroop();
            this.getOpponents().remove(warrior);
            warrior.setRunning(false);
        }
    }


    public void defaultStart() {
        int iterations = 0;
        while (this.isRunning()) {
            int finalIterations = iterations;
            Platform.runLater(() -> {
                if (finalIterations == 0) {
                    setInitPosition();
                    setOpponent();
                }

            });
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            iterations++;

            while (this.isPaused()) {
                try {
                    sleep(100L);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }


    public void setOpponent(Warrior opponent) {
        this.opponent = opponent;
    }


    public void setOpponent() {
        if (this.getOpponents().size() > 0) {
            Warrior opponent = this.getOpponents().get(new Random().nextInt(this.getOpponents().size()));
            this.setOpponent(opponent);
        } else {
            this.setOpponent(null);
        }
    }

    public void setOpponents(ArrayList<Warrior> opponents) {
        this.opponents = opponents;
    }

    public Warrior getOpponent() {
        return opponent;
    }



    public Object[] getObjectiveWarrior() {
        return objectiveWarrior;
    }

    public void setObjectiveWarrior(Object[] objectiveWarrior) {
        this.objectiveWarrior = objectiveWarrior;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
