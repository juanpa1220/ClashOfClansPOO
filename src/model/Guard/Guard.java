package model.Guard;

import javafx.application.Platform;
import model.Army;
import model.BoardItem;
import model.Warriors.Warrior;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;
import java.util.Random;

public class Guard extends Army {
    private Object[] objectiveWarrior;
    private String type;
    private ArrayList<Warrior> opponents;
    private Warrior opponent = null;
    private int scope;

    public String getType() {
        return type;
    }

    public ArrayList<Warrior> getOpponents() {
        return opponents;
    }
    private final ArrayList<BoardItem> refBoard;

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
        this.refBoard = refBoard;
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

        Warrior opponent = this.getOpponent();

        Point opponentPosition = this.refBoard.get(this.getOpponent().getCurrentPosition()).getPosition();
        Point myPosition = this.refBoard.get(this.getCurrentPosition()).getPosition();
        ///////////////////////
        boolean isAttack = false;

        //Search for enemy in the scope range
        for (int i = -this.scope; i < this.scope; i++) {
            for (int j = this.scope; j > -this.scope; j--) {
                if ((myPosition.x + i == opponentPosition.x && myPosition.y + i == opponentPosition.y) ||
                        (myPosition.x + j == opponentPosition.x && myPosition.y + j == opponentPosition.y) ||
                        (myPosition.x + i == opponentPosition.x && myPosition.y + j == opponentPosition.y) ||
                        (myPosition.x + j == opponentPosition.x && myPosition.y + i == opponentPosition.y)
                ) {
                    isAttack = true;
                    break;
                }
            }
        }
        //////////////////////
        //If enemy founded , attack!!
        if (isAttack) {

            if (opponent != null) {
                //Before attack check if is in the Objective Warriors of the Guard
                Object[] objectiveWarriors = this.objectiveWarrior;

                ArrayList<String> array = new ArrayList<>();

                for (Object warrior : objectiveWarriors
                ) {
                    //Include every warrior in the String Array!
                    array.add((String) warrior);
                }
                boolean isAllowed = array.contains(opponent.getType());

                if (isAllowed) {

                    opponent.reduceLife(this.getHits());
                    if (opponent.getLife() <= 0) {
                        this.killOpponent(opponent);
                        this.setOpponent();
                    }
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
