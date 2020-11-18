package model;

import control.BoardController;
import control.WarriorPickerController;
import model.Enemies.Enemy;
import model.Warriors.Warrior;

import java.util.ArrayList;

public class Game {
    BoardController boardController;
    WarriorPickerController warriorPickerController;
    ArrayList<Warrior> warriors = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    int level;

    public Game(BoardController boardController, WarriorPickerController warriorPickerController) {
        this.boardController = boardController;
        this.warriorPickerController = warriorPickerController;
        this.level = 1;
    }

    public void start() {
        warriorPickerController.setLblLevel(this.level);
        warriorPickerController.showWarriors(this.level);
        boardController.initBoard();
    }

    public void addWarrior() {
    }
}
