package control;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.BoardItem;

import java.util.ArrayList;

public class BoardController {
    public GridPane gridPane;
    private final ArrayList<BoardItem> board = new ArrayList<>();

    public void createBoard(GridPane gridPane) {
        this.gridPane = gridPane;

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
                temLabel.setText(String.valueOf(index));
                temLabel.setId(String.valueOf(index));
                gridPane.add(temLabel, col, row);
                this.board.add(new BoardItem(temLabel, row, col, index, false));
                index++;
            }
        }
    }

    public void initBoard() {
        Image image = new Image(getClass().getResourceAsStream("../asserts/imgs/dragon.gif"));

        for (BoardItem item : this.board) {
            item.getLabel().setStyle("-fx-border-color: #0e0e0e");
            item.getLabel().setText("");
            item.getLabel().setGraphic(new ImageView(image));
        }
    }
}
