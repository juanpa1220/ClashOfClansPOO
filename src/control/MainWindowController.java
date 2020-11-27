package control;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.FileManager.JsonManager;
import model.Game;
import model.Warriors.Warrior;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


public class MainWindowController {
    public Button btnNewWarrior;
    public Button btnNewGame;
    public Button btnOpenGame;
    public Button btnSaveGame;
    public Button btnSkipLevel;
    public AnchorPane primaryStage;
    public AnchorPane rootPane;
    public AnchorPane boardPane;
    private AnchorPane newWarriorFormPane;
    public AnchorPane warriorPickerPane;
    public Game game;
    private NewWarriorFormController newWarriorFormController;


    public void onActionInitGame() throws IOException {
        this.boardPane = FXMLLoader.load(getClass().getResource("../view/board.fxml"));
        this.warriorPickerPane = FXMLLoader.load(getClass().getResource("../view/warriorPicker.fxml"));
        this.newWarriorFormPane = FXMLLoader.load(getClass().getResource("../view/newWarriorForm.fxml"));

        this.setChildRoot("warriorPicker");
        BoardController boardController = BoardController.getInstance();
        boardController.createBoard();

        WarriorPickerController warriorPickerController = WarriorPickerController.getInstance();
        warriorPickerController.setParentRoot(this);

        this.newWarriorFormController = NewWarriorFormController.getInstance();
        this.newWarriorFormController.setParentRoot(this);

        this.game = new Game(boardController, warriorPickerController, this);
        this.game.showWarriorsPick();


        btnNewWarrior.setDisable(false);
    }

    public void starGameLevel(Hashtable<String, Integer> selectedWarriors) {
        this.setChildRoot("board");
        this.game.setSelectedWarriors(selectedWarriors);
        this.game.startLevel();
    }

    public void onActionNewWarrior() {
        int response = this.loginDialog();
        if (response == 1) {
            this.newWarriorFormController.setUpComponents();
            this.rootPane.getChildren().setAll(this.newWarriorFormPane);
        } else if (response == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unauthenticated");
            alert.setContentText("Username or password invalid");
            alert.showAndWait();
        }
    }

    public void setChildRoot(String childRoot) {
        if (childRoot.equals("warriorPicker")) {
            this.rootPane.getChildren().setAll(this.warriorPickerPane);
            this.btnNewWarrior.setDisable(false);
            this.btnSkipLevel.setDisable(true);
            this.btnSaveGame.setDisable(true);
            this.btnOpenGame.setDisable(false);

        }
        if (childRoot.equals("board")) {
            this.rootPane.getChildren().setAll(this.boardPane);
            this.btnNewWarrior.setDisable(true);
            this.btnSkipLevel.setDisable(false);
            this.btnSaveGame.setDisable(false);
            this.btnOpenGame.setDisable(true);
        }
    }

    public void updateWarriorPick() {
//        this.game.showWarriorsPick();
        this.game.newLevel(this.game.getLevel());
    }

    private int loginDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Please enter your credentials");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("../asserts/imgsGUI/login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        AtomicInteger response = new AtomicInteger(-1);
        result.ifPresent(usernamePassword -> {
            if (usernamePassword.getKey().equals("admin") && usernamePassword.getValue().equals("admin")) {
                response.set(1);
            } else {
                response.set(0);
            }
        });
        return response.get();
    }

    public void onNewGameAction() {
        this.game.newLevel(1);
    }

    public void onOpenGameAction() {
        JSONArray game = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Your Game");
        fileChooser.setInitialDirectory(new File("src/asserts/docs/savedGames"));
        File file = fileChooser.showOpenDialog(this.primaryStage.getScene().getWindow());
        if (file != null) {
            game = JsonManager.jsonReader(file.getAbsolutePath());
        }
        ArrayList<JSONObject> game2 = new ArrayList<>();
        if (game != null) {
            game.forEach(g -> game2.add((JSONObject) g));
        }
        if (game2.size() > 0) {
            JSONObject temGame1 = game2.get(0);
            JSONObject temGame = (JSONObject) temGame1.get("game");

            int level = Integer.parseInt((String) temGame.get("level"));
            ArrayList<String> temWarriors = (ArrayList<String>) temGame.get("warriors");   // convertir a lista
            Hashtable<String, Integer> selectedWarriors = new Hashtable<>();
            this.game.newLevel(level);
            ArrayList<Warrior> generic = this.game.getGenericWarriors();
            for (Warrior w : generic) {
                selectedWarriors.put(w.getTroopName(), 0);
            }
            for (String w : temWarriors) {
                selectedWarriors.put(w, selectedWarriors.get(w) + 1);
            }
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congrats");
            alert.setHeaderText("Your game has been opened successfully.");
            alert.setContentText("You are going to resume your game in level " + level + ".");
            alert.showAndWait();
            this.starGameLevel(selectedWarriors);
        }
    }

    public void onSaveGameAction() {
        this.game.pauseGame();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Your Game");
        fileChooser.setInitialDirectory(new File("src/asserts/docs/savedGames"));
        File file = fileChooser.showSaveDialog(this.primaryStage.getScene().getWindow());
        if (file != null) {
            JSONObject gameTosave = new JSONObject();
            gameTosave.put("level", String.valueOf(this.game.getLevel()));
            ArrayList<String> warriors = new ArrayList<>();
            for (Warrior w : this.game.warriors) {
                warriors.add(w.getTroopName());
            }
            gameTosave.put("warriors", warriors);
            JSONObject gameTosave2 = new JSONObject();
            gameTosave2.put("game", gameTosave);

            JsonManager.jsonWriteGame(gameTosave2, file.getAbsolutePath() + ".json");
        }
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congrats");
        alert.setHeaderText("Your game has been saved successfully.");
        alert.setContentText("You can resume your game in this level whenever you want.");
        alert.showAndWait();
        this.game.resumeGame();
    }

    public void onSkipLevelAction() {
        this.setChildRoot("warriorPicker");
        this.game.newLevel(this.game.getLevel() + 1);
    }
}
