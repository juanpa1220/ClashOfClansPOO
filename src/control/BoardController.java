package control;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.BoardItem;

import java.util.ArrayList;

public class BoardController {
    public GridPane gridPane;
    public static BoardController boardController;
    private final ArrayList<BoardItem> board = new ArrayList<>();

    public BoardController() {
        boardController = this;
    }

    public static BoardController getInstance() {
        return boardController;
    }

    public void createBoard() {
        int index = 0;
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 20; col++) {
                Label temLabel = new Label();
                temLabel.setMaxHeight(30);
                temLabel.setMinHeight(30);
                temLabel.setPrefHeight(30);
                temLabel.setMaxWidth(30);
                temLabel.setMinWidth(30);
                temLabel.setPrefWidth(30);
                temLabel.setStyle("-fx-border-color: #4a4f51");
//                temLabel.setId(String.valueOf(index));
//                temLabel.setText(String.valueOf(col) + "," + String.valueOf(row));
                this.gridPane.add(temLabel, col, row);
                this.board.add(new BoardItem(temLabel, row, col, index, true));
                index++;
            }
        }
    }

    public ArrayList<BoardItem> getBoard() {
        return board;
    }

    public void clearBoard() {
        for (BoardItem item : this.board) {
            if (!item.isAvailable()) {
                item.removeTroop();
            }
        }
    }
}
