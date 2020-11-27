package model.Guard;

import model.BoardItem;

import java.util.ArrayList;

public class Aerial extends Guard {
    public Aerial(ArrayList<BoardItem> refBoard,
                  String name,
                  String dirImage,
                  int appearanceLevel,
                  int level,
                  int housingSpace,
                  Object objectiveWarrior,
                  String type
    ) {
        super(refBoard, name, dirImage, appearanceLevel, level, 1000, 1000, housingSpace, objectiveWarrior,type);
    }

}