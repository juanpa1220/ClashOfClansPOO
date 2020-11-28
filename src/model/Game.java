package model;

import control.BoardController;
import control.MainWindowController;
import control.WarriorPickerController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.FileManager.JsonManager;
import model.Guard.*;
import model.Interfaces.IGrowUp;
import model.Warriors.*;
import model.Guard.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Game extends Thread {

    private final BoardController boardController;
    private final WarriorPickerController warriorPickerController;
    private final MainWindowController mainWindowController;
    private final ArrayList<Warrior> genericWarriors = new ArrayList<>();
    private final ArrayList<Guard> genericGuards = new ArrayList<>();
    private final ArrayList<Guard> guards = new ArrayList<>();
    private final ArrayList<IGrowUp> growingWarriors = new ArrayList<>();
    public final ArrayList<Warrior> warriors = new ArrayList<>();
    private final ArrayList<Warrior> enemies = new ArrayList<>();

    private int level;
    private boolean hasStared = false;
    private final AtomicBoolean isRunning = new AtomicBoolean(true);
    private final AtomicBoolean isPaused = new AtomicBoolean(false);
    private final AtomicReference<Alert> alert = new AtomicReference<>();

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
                        ContactWarrior contactWarrior = new ContactWarrior(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type);
                        this.genericWarriors.add(contactWarrior);
                        this.growingWarriors.add(contactWarrior);
                        break;
                    case "Medium Range":
                        MediumRangeWarrior mediumRangeWarrior = new MediumRangeWarrior(boardController.getBoard(), name, path, appearanceLevel, level, life, hits, housing, type);
                        this.genericWarriors.add(mediumRangeWarrior);
                        this.growingWarriors.add(mediumRangeWarrior);
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


    private void setUpGuards(int level) {

        ArrayList<JSONObject> guards = new ArrayList<>();


        // Add Archer to Generic Guards

        String pathArcher = "../asserts/imgs/archerTower.gif";
        String[] archerObjectiveWarrior = {"AerialWarrior", "Beast",
                "Contact", "Hero", "MediumRangeWarrior"};

        Archer newGuardArcher = new Archer(boardController.getBoard(), "Archer", pathArcher,
                1, this.level, 2, archerObjectiveWarrior, "Archer");
        this.genericGuards.add(newGuardArcher);


        //Add Aerial to Generic Guards

        String pathAerial = "../asserts/imgs/aerial.gif";
        String[] aerialObjectiveWarrior = {"AerialWarrior"};

        Aerial newGuardAerial = new Aerial(boardController.getBoard(), "Aerial", pathAerial,
                2, this.level, 2, aerialObjectiveWarrior, "Aerial");
        this.genericGuards.add(newGuardAerial);

        //Add Bomb to Generic Guards

        String pathBomb = "../asserts/imgs/bomb.gif";
        String[] bombObjectiveWarrior = {"Beast",
                "ContactWarrior", "Hero", "MediumRangeWarrior"};

        Bomb newGuardBomb = new Bomb(boardController.getBoard(), "Bomb", pathBomb,
                3, this.level, 2, bombObjectiveWarrior, "Bomb");
        this.genericGuards.add(newGuardBomb);

        //Add Cannon to Generic Guards

        String pathCannon = "../asserts/imgs/cannonshot.gif";
        String[] cannonObjectiveWarrior = {"Beast",
                "ContactWarrior", "Hero", "MediumRangeWarrior"};

        Cannon newGuardCannon = new Cannon(boardController.getBoard(), "Cannon", pathCannon,
                4, this.level, 2, cannonObjectiveWarrior, "Cannon");
        this.genericGuards.add(newGuardCannon);


        //Add Mortar to Generic Guards

        String pathMortar = "../asserts/imgs/mortar.gif";
        String[] mortarObjectiveWarrior = {"Beast",
                "ContactWarrior", "Hero", "MediumRangeWarrior"};

        Mortar newGuardMortar = new Mortar(boardController.getBoard(), "Mortar", pathMortar,
                5, this.level, 2, mortarObjectiveWarrior, "Mortar");
        this.genericGuards.add(newGuardMortar);

        //Add Wall to Generic Guards

        String pathWall = "../asserts/imgs/wall.gif";
        String[] wallObjectiveWarrior = {""};

        Wall newGuardWall = new Wall(boardController.getBoard(), "Wall", pathWall,
                1, this.level, 2, wallObjectiveWarrior, "Wall");
        this.genericGuards.add(newGuardWall);


        System.out.println(this.genericGuards);

    }

    public void showWarriorsPick() {
        this.setUpWarriors(this.level);
        this.setUpGuards(this.level);

        for (IGrowUp w : this.growingWarriors) {
            w.growUp();
        }
        warriorPickerController.showWarriors(this.level, this.genericWarriors);
        this.setRandomEnemies();
        this.setRandomGuards();

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

        for (Guard g : this.guards) {
            g.setOpponents(this.warriors);
            g.start();
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

        for (Guard g : this.guards) {
            g.setRunning(false);
        }

    }

    public void newLevel(int level) {
        this.isPaused.set(true);
        this.stopFight();


        this.level = level;
        this.genericWarriors.clear();
        this.warriors.clear();
        this.enemies.clear();
        this.guards.clear();
        this.showWarriorsPick();
        this.mainWindowController.setChildRoot("warriorPicker");
        this.boardController.clearBoard();
    }

    public int getLevel() {
        return level;
    }

    public void pauseGame() {
        this.isPaused.set(true);
        for (Warrior w : this.warriors) {
            w.setPaused(true);
        }
        for (Warrior w : this.enemies) {
            w.setPaused(true);
        }
        for (Guard g : this.guards) {
            g.setPaused(true);
        }

    }

    public void resumeGame() {
        this.isPaused.set(false);
        for (Warrior w : this.warriors) {
            w.setPaused(false);
        }
        for (Warrior w : this.enemies) {
            w.setPaused(false);
        }
        for (Guard g : this.guards) {
            g.setPaused(false);
        }

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
                        new MediumRangeWarrior(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
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

    private void setRandomGuards() {
        int remainingHousing = 3 + 3 * this.level;
        int count = 0;
        boolean flag = true;
        while (flag) {
            int index = new Random().nextInt(this.genericGuards.size());
            Guard g = this.genericGuards.get(index);

            if(g.getAppearanceLevel()>this.level){

            }else{
                System.out.println(g.getType());
            if (remainingHousing == 0) {
                flag = false;
            } else if ("Aerial".equals(g.getType()) && remainingHousing - g.getHousingSpace() >= 0) {
                this.guards.add(new Aerial(boardController.getBoard(), g.getTroopName(), g.getDirImage(),
                        g.getAppearanceLevel(), g.getLevel(), g.getHousingSpace(),
                        g.getObjectiveWarrior(), g.getType()));
                remainingHousing -= g.getHousingSpace();
            } else if ("Archer".equals(g.getType()) && remainingHousing - g.getHousingSpace() >= 0) {
                this.guards.add(
                        new Archer(boardController.getBoard(), g.getTroopName(), g.getDirImage(),
                                g.getAppearanceLevel(), g.getLevel(), g.getHousingSpace(),
                                g.getObjectiveWarrior(), g.getType()));
                remainingHousing -= g.getHousingSpace();
            }  else if ("Bomb".equals(g.getType()) && remainingHousing - g.getHousingSpace() >= 0) {
                this.guards.add(
                        new Bomb(boardController.getBoard(), g.getTroopName(), g.getDirImage(),
                                g.getAppearanceLevel(), g.getLevel(), g.getHousingSpace(),
                                g.getObjectiveWarrior(), g.getType()));
                remainingHousing -= g.getHousingSpace();
            } else if ("Cannon".equals(g.getType()) && remainingHousing - g.getHousingSpace() >= 0) {
                this.guards.add(
                        new Cannon(boardController.getBoard(), g.getTroopName(), g.getDirImage(),
                                g.getAppearanceLevel(), g.getLevel(), g.getHousingSpace(),
                                g.getObjectiveWarrior(), g.getType()));
                remainingHousing -= g.getHousingSpace();

            }else if ("Mortar".equals(g.getType()) && remainingHousing - g.getHousingSpace() >= 0) {
                this.guards.add(
                        new Mortar(boardController.getBoard(), g.getTroopName(), g.getDirImage(),
                                g.getAppearanceLevel(), g.getLevel(), g.getHousingSpace(),
                                g.getObjectiveWarrior(), g.getType()));
                remainingHousing -= g.getHousingSpace();

            }
            else if ("Wall".equals(g.getType()) && remainingHousing - g.getHousingSpace() >= 0) {
                this.guards.add(
                        new Wall(boardController.getBoard(), g.getTroopName(), g.getDirImage(),
                                g.getAppearanceLevel(), g.getLevel(), g.getHousingSpace(),
                                g.getObjectiveWarrior(), g.getType()));
                remainingHousing -= g.getHousingSpace();

            }
            else {
                count++;
                if (count > 6) {
                    flag = false;
                }
            }}
        }
    }


    public ArrayList<Warrior> getGenericWarriors() {
        return genericWarriors;
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

                                new MediumRangeWarrior(boardController.getBoard(), w.getTroopName(), w.getDirImage(),
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
