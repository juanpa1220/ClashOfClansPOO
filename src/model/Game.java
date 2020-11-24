package model;

import control.BoardController;
import control.WarriorPickerController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import model.FileManager.JsonManager;
import model.Warriors.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Game extends Thread {
    BoardController boardController;
    WarriorPickerController warriorPickerController;
    ArrayList<Warrior> genericWarriors = new ArrayList<>();
    ArrayList<Warrior> warriors = new ArrayList<>();
    ArrayList<Warrior> enemies = new ArrayList<>();
    private int level;

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
                        ContactWarrior newWarrior = new ContactWarrior(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type);
                        this.genericWarriors.add(newWarrior);
//                        this.warriors.add(new ContactWarrior(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type));
                        this.enemies.add(new ContactWarrior(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type));
                        break;
                    case "Medium Range":
                        this.genericWarriors.add(new MediumRangeWarriors(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type));
                        break;
                    case "Aerial":
                        this.genericWarriors.add(new AerialWarrior(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type));
                        break;
                    case "Beast":
                        this.genericWarriors.add(new Beast(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type));
                        break;
                    case "Hero":
                        this.genericWarriors.add(new Hero(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type));
                        break;
                }
            }
        }
    }


    public void showWarriorsPick() {
        this.setUpWarriors(this.level);
        warriorPickerController.showWarriors(this.level, this.genericWarriors);
    }

    public void startLevel() {
        warriorPickerController.setLblLevel(this.level);
        for (Warrior w : this.warriors) {
            w.setIsEnemy(false);
            w.setOpponents(this.enemies);
            w.start();
        }
        for (Warrior w : this.enemies) {
            w.setIsEnemy(true);
            w.setOpponents(this.warriors);
            w.start();
        }
        this.start();
    }

    private void stopFight() {
        for (Warrior w : this.warriors) {
            w.setRunning(false);
        }
        for (Warrior w : this.enemies) {
            w.setRunning(false);
        }
    }

    @Override
    public void run() {
        AtomicBoolean levelRunning = new AtomicBoolean(true);
        AtomicReference<Alert> alert = new AtomicReference<>();
        while (levelRunning.get()) {
            Platform.runLater(() -> {
                System.out.println("marcador:\tenemies: " + enemies.size() + "\twarriors:" + warriors.size());
                if (enemies.size() == 0) {
                    levelRunning.set(false);
                    this.stopFight();
                    alert.set(new Alert(Alert.AlertType.INFORMATION));
                    alert.get().setTitle("Congrats");
                    alert.get().setHeaderText("You have won level" + this.level + ".");
                    alert.get().setContentText("Good lucky in the next level");
                    alert.get().showAndWait();
                }
                if (warriors.size() == 0) {
                    levelRunning.set(false);
                    this.stopFight();
                    alert.set(new Alert(Alert.AlertType.CONFIRMATION));
                    alert.get().setTitle("Sorry");
                    alert.get().setHeaderText("You have lost level " + this.level + ".");
                    alert.get().setContentText("Do you want to play again this level?");
                    ButtonType btnPlayAgain = new ButtonType("Play Again");
                    ButtonType btnNextLevel = new ButtonType("Next Level");
                    alert.get().getButtonTypes().setAll(btnPlayAgain, btnNextLevel);
                    Optional<ButtonType> result = alert.get().showAndWait();
                    if (result.get() == btnPlayAgain) {
                        System.out.println("play again");
                    } else if (result.get() == btnNextLevel) {
                        System.out.println("next level");
                    }
                }
            });

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void setSelectedWarriors(Hashtable<String, Integer> selectedWarriors) {
        for (Warrior w : this.genericWarriors) {
            int q = selectedWarriors.get(w.getTroopName());
            for (int i = 0; i < q; i++) {
                switch (w.getType()) {
                    case "Contact":
                        this.warriors.add(
                                new ContactWarrior(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                        w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                        w.getType()));
                        break;
                    case "Medium Range":
                        this.warriors.add(
                                new MediumRangeWarriors(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                        w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                        w.getType()));
                        break;
                    case "Aerial":
                        this.warriors.add(
                                new AerialWarrior(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                        w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                        w.getType()));
                        break;
                    case "Beast":
                        this.warriors.add(
                                new Beast(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                        w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                        w.getType()));
                        break;
                    case "Hero":
                        this.warriors.add(
                                new Hero(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                        w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                        w.getType()));
                        break;
                }
            }
        }

    }

}
