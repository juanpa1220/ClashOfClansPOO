package model;

import control.BoardController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BoardItem {
    private Label label;
    private int row;
    private int col;
    private int index;
    private boolean isAvailable;

    public BoardItem(Label label, int row, int col, int index, boolean isAvailable) {
        this.label = label;
        this.row = row;
        this.col = col;
        this.index = index;
        this.isAvailable = isAvailable;
    }

    public void setTroop(Army army) {
        Image image = new Image(BoardController.class.getResourceAsStream(army.getDirImage()));
        this.label.setGraphic(new ImageView(image));
        this.isAvailable = true;
    }

    public void removeTroop() {
        this.label.setGraphic(null);
        this.isAvailable = false;
    }

    public Label getLabel() {
        return label;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
