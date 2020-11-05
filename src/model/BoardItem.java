package model;

import javafx.scene.control.Label;

public class BoardItem {
    private Label label;
    private int row;
    private int col;
    private int index;
    private boolean isBusy;

    public BoardItem(Label label, int row, int col, int index, boolean isBusy) {
        this.label = label;
        this.row = row;
        this.col = col;
        this.index = index;
        this.isBusy = isBusy;
    }

    public Label getLabel() {
        return label;
    }
}
