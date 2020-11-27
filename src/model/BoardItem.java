package model;

import control.BoardController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Warriors.Warrior;

import java.awt.*;

public class BoardItem  {
    private final Label label;
    private final int row;
    private final int col;
    private int index;
    private boolean isAvailable;
    String backgroundDefaultColor = "#c2cdd4";

    public BoardItem(Label label, int row, int col, int index, boolean isAvailable) {
        this.label = label;
        this.row = row;  // y
        this.col = col;  // x
        this.index = index;
        this.isAvailable = isAvailable;
    }

    public void setWarrior(Warrior warrior) {
        Image image = new Image(BoardController.class.getResourceAsStream(warrior.getDirImage()));
        this.label.setGraphic(new ImageView(image));
        if (warrior.isEnemy()) {
            this.label.setStyle("-fx-background-color: #c64c4c; -fx-border-color: #4a4f51");
        } else {
            this.label.setStyle("-fx-background-color: #2f76c3; -fx-border-color: #4a4f51");
        }
        this.isAvailable = false;
    }

    public void removeTroop() {
        this.label.setGraphic(null);
        String tem = "-fx-background-color: " + this.backgroundDefaultColor;
        this.label.setStyle(tem);
        this.label.setStyle("-fx-border-color: #0e0e0e");
        this.isAvailable = true;
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
        return this.isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Point getPosition() {
        return new Point(col, row);
    }

}
