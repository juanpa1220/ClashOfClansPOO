package model.Warriors;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.BoardItem;
import model.Interfaces.IMakeNoise;
import model.Interfaces.IMove;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class ContactWarrior extends Warrior implements IMove, IMakeNoise {
    private final ArrayList<BoardItem> refBoard;
    private int notMoveCounter = 0;
    private final String musicFile = "src/asserts/sounds/swordraw.mp3";     // For example
    private final Media sound = new Media(new File(musicFile).toURI().toString());
    private final MediaPlayer mediaPlayer = new MediaPlayer(sound);

    public ContactWarrior(ArrayList<BoardItem> refBoard, String name, String dirImage, int appearanceLevel, int level, int life, int hits, int housingSpace, String type) {
        super(refBoard, name, dirImage, appearanceLevel, level, life, hits, housingSpace, type);
        this.refBoard = refBoard;
        this.setLastPosition(-1);
    }

    @Override
    public void run() {
        int iterations = 0;
        while (this.isRunning()) {
            int finalIterations = iterations;
            Platform.runLater(() -> {
                if (finalIterations == 0) {
                    super.setInitPosition();
                }
                if (getOpponent() == null) {
                    setOpponent();
                }
                move();
            });
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            iterations++;
        }
    }

    @Override
    public void makeNoise() {
        mediaPlayer.play();
        mediaPlayer.stop();
    }

    @Override
    public void attack() {
        this.makeNoise();
        Warrior opponent = this.getOpponent();
        opponent.reduceLife(this.getHits());
        if (opponent.getLife() <= 0) {
            this.killOpponent(opponent);
            this.setOpponent();
        }
    }

    @Override
    public void move() {
        if (this.getLastPosition() != -1) {
            this.refBoard.get(this.getLastPosition()).removeTroop();
        }

        this.refBoard.get(this.getCurrentPosition()).setWarrior(this);
        this.setLastPosition(this.getCurrentPosition());

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
            if (newPosition.x == opponentPosition.x && newPosition.y == opponentPosition.y) {
                this.attack();

            } else if (refBoard.get(index).isAvailable()) {
                this.setCurrentPosition(index);
                notMoveCounter = 0;
            } else {
                if (notMoveCounter >= 3) {
                    this.setOpponent();
                } else {
                    newPosition.x = newPosition.x + new Random().nextInt((1 - -1) + 1) + -1;
                    newPosition.y = newPosition.y + new Random().nextInt((1 - -1) + 1) + -1;
                    index = newPosition.y * 20 + newPosition.x;
                    this.setCurrentPosition(index);
                    notMoveCounter++;
                }
            }

        }
    }
}
