package control;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import model.Warriors.Warrior;

import java.util.ArrayList;

public class WarriorPickerController {
    public Label lblRemainingFields;
    public Label lblLevel;
    public static WarriorPickerController warriorPickerController;
    private MainWindowController mainWindowController;
    public ListView<String> listView;
    private int remainingFields;

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

    public void showWarriors(int level, ArrayList<Warrior> warriors) {
        this.listView.getItems().add("test1");
        this.listView.getItems().add("test2");
        this.listView.getItems().add("test3");

        /* creating horizontal box to add item objects */
        HBox hbox = new HBox(listView);
    }

    public void onStartGameAction(ActionEvent actionEvent) {
        mainWindowController.starGameLevel();
    }

    public void setParentRoot(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

}
