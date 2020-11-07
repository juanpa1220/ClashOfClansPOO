package control;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class WarriorPicker {
    public Label lblRemainingFields;
    public Label lblLevel;
    public static WarriorPicker warriorPicker;

    public WarriorPicker() {
        warriorPicker = this;
    }

    public static WarriorPicker getInstance() {
        return warriorPicker;
    }

    public void setLblRemainingFields(int remainingFields) {
        this.lblRemainingFields.setText(String.valueOf(remainingFields));
    }

    public void setLblLevel(int level) {
        this.lblLevel.setText(String.valueOf(level));
    }

    public void showWarriors(int level) {

    }

    public void onStartGameAction(ActionEvent actionEvent) {

    }
}
