package model.Guard;

import model.Army;
import model.BoardItem;
import model.Warriors.Warrior;

import java.util.ArrayList;
import java.util.Random;

public class Guard extends Army {
    private Object objectiveWarrior;
    private final String type;
    private ArrayList<Warrior> opponents;
    private Warrior opponent = null;

    public Guard(ArrayList<BoardItem> refBoard,
                 String name,
                 String dirImage,
                 int appearanceLevel,
                 int level,
                 int life,
                 int hits,
                 int housingSpace,
                 Object objectiveWarrior,
                 String type
    ) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
        this.type = type;
        this.objectiveWarrior = objectiveWarrior;

    }


    public void setInitPosition() {
            boolean flag = true;
            while (flag) {
                int index = new Random().nextInt(399);
                if (this.getRefBoard().get(index).isAvailable()) {
                    this.getRefBoard().get(index).setGuard(this);
                    //this.setCurrentPosition(index);
                    flag = false;
                }
            }
        }


    @Override
    public void attack(

    ) {}

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


    public String getType() {
        return type;
    }

    public Object getObjectiveWarrior() {
        return objectiveWarrior;
    }

    public void setObjectiveWarrior(Object objectiveWarrior) {
        this.objectiveWarrior = objectiveWarrior;
    }
    public ArrayList<Warrior> getOpponents() {
        return opponents;
    }

}
