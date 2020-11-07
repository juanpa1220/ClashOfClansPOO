package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Game;

import java.io.IOException;


public class MainWindowController {
    public AnchorPane rootPane;
    public AnchorPane boardPane;
    public AnchorPane warriorPickerPane;
    Game game;

    public void onActionInitGame(ActionEvent actionEvent) throws IOException {
        this.boardPane = FXMLLoader.load(getClass().getResource("../view/board.fxml"));
        this.warriorPickerPane = FXMLLoader.load(getClass().getResource("../view/warriorPicker.fxml"));

        rootPane.getChildren().setAll(this.warriorPickerPane);
        BoardController boardController = BoardController.getInstance();
        boardController.createBoard();

        WarriorPicker warriorPicker = WarriorPicker.getInstance();

        this.game = new Game(boardController, warriorPicker);
        this.game.start();
    }
}
