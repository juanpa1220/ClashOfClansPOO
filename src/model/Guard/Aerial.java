package model.Guard;

import javafx.application.Platform;
import model.BoardItem;

import java.util.ArrayList;

public class Aerial extends Guard {
    public Aerial(ArrayList<BoardItem> refBoard,
                  String name,
                  String dirImage,
                  int appearanceLevel,
                  int level,
                  int housingSpace,
                  Object[] objectiveWarrior,
                  String type
    ) {
        super(refBoard, name, dirImage, appearanceLevel, level, 1000, 1000, housingSpace, objectiveWarrior,type);
    }

    @Override
    public void run() {
        int iterations = 0;
        while (this.isRunning()) {
            //super.setInitPosition();
            int finalIterations = iterations;
            Platform.runLater(() -> {
                if (finalIterations == 0) {
                    super.setInitPosition();
                }
                if (getOpponent() == null) {
                    setOpponent();
                }
                attack();
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

}
