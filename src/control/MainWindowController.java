package control;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import model.Game;

import java.io.IOException;
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

        }
        if (childRoot.equals("board")) {
            this.rootPane.getChildren().setAll(this.boardPane);
            btnNewWarrior.setDisable(true);
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
}
