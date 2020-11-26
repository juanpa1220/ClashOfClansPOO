package model;

import control.BoardController;
import control.MainWindowController;
import control.WarriorPickerController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.FileManager.JsonManager;
import model.Warriors.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Game extends Thread {
    BoardController boardController;
    WarriorPickerController warriorPickerController;
    MainWindowController mainWindowController;
    ArrayList<Warrior> genericWarriors = new ArrayList<>();
    ArrayList<Warrior> warriors = new ArrayList<>();
    ArrayList<Warrior> enemies = new ArrayList<>();
    private int level;
    private boolean hasStared = false;
    //    private boolean isRunning = false;
    AtomicBoolean isRunning = new AtomicBoolean(true);
    AtomicBoolean isPaused = new AtomicBoolean(false);
    AtomicReference<Alert> alert = new AtomicReference<>();

    public Game(BoardController boardController, WarriorPickerController warriorPickerController, MainWindowController mainWindowController) {
        this.boardController = boardController;
        this.warriorPickerController = warriorPickerController;
        this.mainWindowController = mainWindowController;
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
//                        this.enemies.add(new ContactWarrior(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type));
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
        this.setRandomEnemies();
        System.out.println(this.enemies);
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

        if (!hasStared) {
            this.start();
            this.hasStared = true;
        }
        this.isPaused.set(false);
    }

    private void stopFight() {
        for (Warrior w : this.warriors) {
            w.setRunning(false);
        }
        for (Warrior w : this.enemies) {
            w.setRunning(false);
        }
    }

    public void newLevel(int level) {
        this.level = level;
        this.genericWarriors.clear();
        this.warriors.clear();
        this.enemies.clear();
        this.showWarriorsPick();
        this.mainWindowController.setChildRoot("warriorPicker");
        this.boardController.clearBoard();
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void run() {
        while (isRunning.get()) {
            Platform.runLater(() -> {
//                System.out.println("marcador:\tenemies: " + enemies.size() + "\twarriors:" + warriors.size());
                if (enemies.size() == 0) {
                    this.isPaused.set(true);
                    this.stopFight();
                    alert.set(new Alert(Alert.AlertType.INFORMATION));
                    alert.get().setTitle("Congrats");
                    alert.get().setHeaderText("You have won level " + this.level + ".");
                    alert.get().setContentText("Good lucky in the next level");
                    ButtonType btnOk = new ButtonType("Play Next Level");
                    alert.get().getButtonTypes().setAll(btnOk);
                    alert.get().showAndWait();
                    newLevel(++this.level);
                } else if (warriors.size() == 0) {
                    this.isPaused.set(true);
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
                        newLevel(this.level);
                    } else if (result.get() == btnNextLevel) {
                        newLevel(++this.level);
                    }
                }
            });
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (this.isPaused.get()) {
                try {
                    sleep(100L);
                } catch (InterruptedException ignored) {
                }
            }

        }
    }

    private void setRandomEnemies() {
        int remainingHousing = 3 + 3 * this.level;
        int count = 0;
        boolean flag = true;
        while (flag) {
            int index = new Random().nextInt(this.genericWarriors.size());
            Warrior w = this.genericWarriors.get(index);
            if (remainingHousing == 0) {
                flag = false;
            } else if ("Contact".equals(w.getType()) && remainingHousing - w.getHousingSpace() >= 0) {
                this.enemies.add(
                        new ContactWarrior(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                w.getType()));
                remainingHousing -= w.getHousingSpace();
            } else if ("Medium Range".equals(w.getType()) && remainingHousing - w.getHousingSpace() >= 0) {
                this.enemies.add(
                        new MediumRangeWarriors(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                w.getType()));
                remainingHousing -= w.getHousingSpace();
            } else if ("Aerial".equals(w.getType()) && remainingHousing - w.getHousingSpace() >= 0) {
                this.enemies.add(
                        new AerialWarrior(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                w.getType()));
                remainingHousing -= w.getHousingSpace();
            } else if ("Beast".equals(w.getType()) && remainingHousing - w.getHousingSpace() >= 0) {
                this.enemies.add(
                        new Beast(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                w.getType()));
                remainingHousing -= w.getHousingSpace();
            } else if ("Hero".equals(w.getType()) && remainingHousing - w.getHousingSpace() >= 0) {
                this.enemies.add(
                        new Hero(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
                                w.getAppearanceLevel(), w.getLevel(), w.getLife(), w.getHits(), w.getHousingSpace(),
                                w.getType()));
                remainingHousing -= w.getHousingSpace();
            } else {
                count++;
                if (count > 5) {
                    flag = false;
                }
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
