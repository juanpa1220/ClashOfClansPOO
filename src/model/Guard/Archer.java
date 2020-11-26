package model.Guard;

import model.BoardItem;
import model.Warriors.Warrior;

import java.util.ArrayList;

public abstract class Archer extends Guard {


    public Archer(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, int scope, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, scope, type);
    }

    public void killObjective(Warrior objective){
        if (this.getObjectives().contains(objective)){
            objective.setRunning(false);
            super.getRefBoard().get(objective.getLastPosition()).removeTroop();
            super.getRefBoard().get(objective.getCurrentPosition()).removeTroop();
            this.getObjectives().remove(objective);
        }
}
        }