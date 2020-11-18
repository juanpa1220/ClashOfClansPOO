package control;

import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class WarriorPickerController {
    public Label lblRemainingFields;
    public Label lblLevel;
    public static WarriorPickerController warriorPickerController;
    public ListView listView;

    public WarriorPickerController() {
        warriorPickerController = this;
    }

    public static WarriorPickerController getInstance() {
        return warriorPickerController;
    }

    public void setLblRemainingFields(int remainingFields) {
        this.lblRemainingFields.setText(String.valueOf(remainingFields));
    }

    public void setLblLevel(int level) {
        this.lblLevel.setText(String.valueOf(level));
    }

    public void showWarriors(int level) {

        this.listView.getItems().add("test1");
        this.listView.getItems().add("test2");
        this.listView.getItems().add("test3");

        /* creating horizontal box to add item objects */
        HBox hbox = new HBox(listView);

    }

    public void onStartGameAction(ActionEvent actionEvent) {

    }
}
