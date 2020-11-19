package model;

import control.BoardController;
import control.WarriorPickerController;
import model.Enemies.Enemy;
import model.FileManager.JsonManager;
import model.Interfaces.IMove;
import model.Warriors.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Game {
    BoardController boardController;
    WarriorPickerController warriorPickerController;
    ArrayList<Warrior> genericWarriors = new ArrayList<>();
    ArrayList<Enemy> genericEnemies = new ArrayList<>();
    ArrayList<Warrior> warriors = new ArrayList<>();
    ArrayList<Warrior> enemies = new ArrayList<>();
    ArrayList<IMove> movedWarriors = new ArrayList<>();
    int level;

    public Game(BoardController boardController, WarriorPickerController warriorPickerController) {
        this.boardController = boardController;
        this.warriorPickerController = warriorPickerController;
        this.level = 1;
    }

    private void setUpWarriors(int level) {
        JSONArray temWarriors = JsonManager.jsonReader("src/asserts/docs/gameSettings/warriors.json");
        ArrayList<JSONObject> warriors = new ArrayList<>();
        if (temWarriors != null) {
            temWarriors.forEach(w -> warriors.add((JSONObject) w));
        }
        for (JSONObject warriorObject : warriors) {
            JSONObject warrior = (JSONObject) warriorObject.get("warrior");
            String temAppearanceLevel = (String) warrior.get("appearanceLevel");
            int appearanceLevel = Integer.parseInt(temAppearanceLevel);
            if (level >= appearanceLevel) {
                String name = (String) warrior.get("name");
                String path = (String) warrior.get("imagePath");
                String type = (String) warrior.get("type");
                int life = Integer.parseInt((String) warrior.get("life"));
                int hits = Integer.parseInt((String) warrior.get("hits"));
                int housing = Integer.parseInt((String) warrior.get("housing"));
                switch (type) {
                    case "Contact":
                        ContactWarrior newWarrior = new ContactWarrior(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing);
                        this.genericWarriors.add(newWarrior);
                        this.movedWarriors.add(newWarrior);
                        break;
                    case "Medium Range":
                        this.genericWarriors.add(new MediumRangeWarriors(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing));
                        break;
                    case "Aerial":
                        this.genericWarriors.add(new AerialWarrior(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing));
                        break;
                    case "Beast":
                        this.genericWarriors.add(new Beast(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing));
                        break;
                    case "Hero":
                        this.genericWarriors.add(new Hero(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing));
                        break;
                }
            }
        }
    }

    public void start() {
        warriorPickerController.setLblLevel(this.level);
        this.setUpWarriors(this.level);
        warriorPickerController.showWarriors(this.level, this.genericWarriors);
//        for (IMove warrior : movedWarriors) {
//            warrior.start();
//        }
        for (Warrior w : this.genericWarriors) {
//            w.appear(boardController.getBoard());
            w.start();
        }

//        boardController.initBoard();
    }

    public void addWarrior() {
    }
}
