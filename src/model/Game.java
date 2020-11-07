package model;

import control.BoardController;
import control.WarriorPicker;
import model.Enemies.Enemy;
import model.Warriors.Warrior;

import java.util.ArrayList;

public class Game {
    BoardController boardController;
    WarriorPicker warriorPicker;
    ArrayList<Warrior> warriors = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    int level;

    public Game(BoardController boardController, WarriorPicker warriorPicker) {
        this.boardController = boardController;
        this.warriorPicker = warriorPicker;
        this.level = 1;
    }

    public void start() {
        warriorPicker.setLblLevel(this.level);
        warriorPicker.showWarriors(this.level);
        boardController.initBoard();
    }
}
