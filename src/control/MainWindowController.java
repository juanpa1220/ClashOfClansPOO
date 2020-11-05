package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;


public class MainWindowController {
    public AnchorPane rootPane;

    public void btnInitGame(ActionEvent actionEvent) throws IOException {
        AnchorPane board = FXMLLoader.load(getClass().getResource("../view/board.fxml"));
        rootPane.getChildren().setAll(board);
        GridPane gridPane = (GridPane) board.getChildren().get(0);

        BoardController boardController = new BoardController();
        boardController.createBoard(gridPane);
        boardController.initBoard();
    }
}
