package model.Warriors;

import javafx.application.Platform;
import model.Army;
import model.BoardItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Warrior extends Army {
    private final String type;
    private final ArrayList<BoardItem> refBoard;
    private boolean isEnemy;
    private int lastPosition;
    private int nextPosition;
    private ArrayList<Warrior> opponents;
    private Warrior opponent = null;
    private int notMoveCounter = 0;

    public Warrior(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace);
        this.type = type;
        this.refBoard = refBoard;
    }

    @Override
    public void setInitPosition() {
        boolean flag = true;
        while (flag) {
            int index = new Random().nextInt(399);
            if (this.getRefBoard().get(index).isAvailable()) {
                this.getRefBoard().get(index).setWarrior(this);
                this.setCurrentPosition(index);
                this.setLastPosition(index);
                this.setNextPosition(index);
                flag = false;
            }
        }
    }

    public void setOpponent() {
        int n = this.getOpponents().size();
        if (n > 0) {
            Warrior opponent = this.getOpponents().get(new Random().nextInt(n));
            this.setOpponent(opponent);
        } else {
            this.setOpponent(null);
        }
    }

    public void defaultAttack() {
        Warrior opponent = this.getOpponent();
        opponent.reduceLife(this.getHits());
        if (opponent.getLife() <= 0) {
            this.killOpponent(opponent);
            this.setOpponent();
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

    public void defaultStart(boolean setOpponent) {
        int iterations = 0;
        while (this.isRunning()) {
            int finalIterations = iterations;
            Platform.runLater(() -> {
                if (finalIterations == 0) {
                    setInitPosition();
                    if (setOpponent) {
                        setOpponent();
                    }
                }
                this.onLoop();
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

    public abstract void onLoop();

    public void defaultMove(int scope) {
        this.refBoard.get(this.getCurrentPosition()).removeTroop();
        this.refBoard.get(this.getLastPosition()).removeTroop();
        this.refBoard.get(this.getNextPosition()).removeTroop();
        this.refBoard.get(this.getNextPosition()).setWarrior(this);
        this.setLastPosition(this.getCurrentPosition());
        this.setCurrentPosition(this.getNextPosition());

        if (this.getOpponent() != null) {
            Point opponentPosition = this.refBoard.get(this.getOpponent().getCurrentPosition()).getPosition();
            Point myPosition = this.refBoard.get(this.getCurrentPosition()).getPosition();
            Point newPosition = new Point(myPosition.x, myPosition.y);

            if (opponentPosition.x < myPosition.x) {
                int tem = myPosition.x - 1;
                if (tem >= 0 && tem < 20) {
                    newPosition.x = tem;
                }
            } else if (opponentPosition.x > myPosition.x) {
                int tem = myPosition.x + 1;
                if (tem >= 0 && tem < 20) {
                    newPosition.x = tem;
                }
            }
            if (opponentPosition.y < myPosition.y) {
                int tem = myPosition.y - 1;
                if (tem >= 0 && tem < 20) {
                    newPosition.y = tem;
                }
            } else if (opponentPosition.y > myPosition.y) {
                int tem = myPosition.y + 1;
                if (tem >= 0 && tem < 20) {
                    newPosition.y = tem;
                }
            }
            int index = newPosition.y * 20 + newPosition.x;

            ///////////////////////
            boolean isAttack = false;
            for (int i = -scope; i < scope; i++) {
                for (int j = scope; j > -scope; j--) {
                    if ((newPosition.x + i == opponentPosition.x && newPosition.y + i == opponentPosition.y) ||
                            (newPosition.x + j == opponentPosition.x && newPosition.y + j == opponentPosition.y) ||
                            (newPosition.x + i == opponentPosition.x && newPosition.y + j == opponentPosition.y) ||
                            (newPosition.x + j == opponentPosition.x && newPosition.y + i == opponentPosition.y)
                    ) {
                        isAttack = true;
                        break;
                    }
                }
            }
            //////////////////////

            if (isAttack) {
                this.attack();
            } else if (refBoard.get(index).isAvailable()) {
                this.setNextPosition(index);
                this.refBoard.get(index).setAvailable(false);
                notMoveCounter = 0;
            } else {
                if (notMoveCounter >= 3) {
                    this.setOpponent();
                } else {
                    newPosition.x = newPosition.x + new Random().nextInt((1 - -1) + 1) + -1;
                    newPosition.y = newPosition.y + new Random().nextInt((1 - -1) + 1) + -1;
                    index = newPosition.y * 20 + newPosition.x;
                    if (index >= 0 && index <= 255 && refBoard.get(index).isAvailable()) {
                        this.setNextPosition(index);
                        this.refBoard.get(index).setAvailable(false);
                        notMoveCounter = 0;
                    }
                    notMoveCounter++;
                }
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

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public String getType() {
        return type;
    }

    public int getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(int nextPosition) {
        this.nextPosition = nextPosition;
    }

}
