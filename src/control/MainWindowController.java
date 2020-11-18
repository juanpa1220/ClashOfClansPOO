package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.FileManager.JsonManager;
import model.Game;

import java.io.IOException;


public class MainWindowController {
    public AnchorPane primaryStage;
    public AnchorPane rootPane;
    public AnchorPane boardPane;
    private AnchorPane newWarriorFormPane;
    public AnchorPane warriorPickerPane;
    public Button btnNewWarrior;
    public Game game;
    private NewWarriorFormController newWarriorFormController;


    public void onActionInitGame(ActionEvent actionEvent) throws IOException {
//        this.primaryStage.getStyleClass().add("anchor");
//        this.primaryStage.setStyle("-fx-background-color: #386264");
        this.boardPane = FXMLLoader.load(getClass().getResource("../view/board.fxml"));
        this.warriorPickerPane = FXMLLoader.load(getClass().getResource("../view/warriorPicker.fxml"));
        this.newWarriorFormPane = FXMLLoader.load(getClass().getResource("../view/newWarriorForm.fxml"));

        this.setChildRoot("warriorPicker");
        BoardController boardController = BoardController.getInstance();
        boardController.createBoard();

        WarriorPickerController warriorPickerController = WarriorPickerController.getInstance();

        this.newWarriorFormController = NewWarriorFormController.getInstance();
        this.newWarriorFormController.setParentRoot(this);

        this.game = new Game(boardController, warriorPickerController);
        this.game.start();

        btnNewWarrior.setDisable(false);
    }

    public void onActionNewWarrior(ActionEvent actionEvent) {
        this.newWarriorFormController.setUpComponents();
        this.rootPane.getChildren().setAll(this.newWarriorFormPane);
        game.addWarrior();
    }

    public void setChildRoot(String childRoot) {
        if (childRoot.equals("warriorPicker")) {
            this.rootPane.getChildren().setAll(this.boardPane);
        }


    }
}
