package control;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import model.Warriors.Warrior;

import java.util.ArrayList;
import java.util.Hashtable;

public class WarriorPickerController {
    public Label lblRemainingFields;
    public Label lblLevel;
    public TilePane tilePane;
    public static WarriorPickerController warriorPickerController;
    private Hashtable<String, Integer> selectedWarriors;
    private MainWindowController mainWindowController;
    private int remainingFields;
    private ObservableList tilePaneList;

    public WarriorPickerController() {
        warriorPickerController = this;
    }

    public static WarriorPickerController getInstance() {
        return warriorPickerController;
    }

    public void setLblRemainingFields() {
        this.lblRemainingFields.setText(String.valueOf(this.remainingFields));
    }

    public void setLblLevel(int level) {
        this.lblLevel.setText(String.valueOf(level));
    }

    public void showWarriors(int level, ArrayList<Warrior> warriors) {
        this.setLblLevel(level);
        this.selectedWarriors = new Hashtable<>();
        this.remainingFields = 3 + 3 * level;
        this.setLblRemainingFields();

        for (Warrior warrior : warriors) {
            selectedWarriors.put(warrior.getTroopName(), 0);
        }

        tilePaneList = this.tilePane.getChildren();
        tilePaneList.clear();
        tilePane.setHgap(600);
        tilePane.setVgap(20);

        warriors.forEach(warrior -> {
            Label lblName = new Label(warrior.getTroopName());
            Label lblHousing = new Label(String.valueOf(warrior.getHousingSpace()));
            lblName.setMinWidth(120);
            lblHousing.setMinWidth(80);
            lblName.setFont(new Font("Ayuthaya", 12));
            lblHousing.setFont(new Font("Ayuthaya", 12));

            Spinner spinner = new Spinner();
            spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                    0, 10000, Integer.parseInt("0")));
            spinner.setPrefWidth(100);
            spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                int newTemValue = Integer.parseInt(newValue.toString());
                int lastValue = this.selectedWarriors.get(warrior.getTroopName());
                int housing = warrior.getHousingSpace();
                if (this.remainingFields > 0) {
                    if (newTemValue > lastValue) {
                        int tem = this.remainingFields - housing;
                        if (tem >= 0) {
                            this.remainingFields = tem;
                            this.setLblRemainingFields();
                            this.selectedWarriors.put(warrior.getTroopName(), this.selectedWarriors.get(warrior.getTroopName()) + 1);
                        } else {
                            spinner.getValueFactory().setValue(lastValue);
                        }
                    } else if (newTemValue < lastValue) {
                        this.remainingFields += warrior.getHousingSpace();
                        this.setLblRemainingFields();
                        this.selectedWarriors.put(warrior.getTroopName(), this.selectedWarriors.get(warrior.getTroopName()) - 1);
                    }
                } else if (this.remainingFields == 0) {
                    if (newTemValue < lastValue) {
                        this.remainingFields += warrior.getHousingSpace();
                        this.setLblRemainingFields();
                        this.selectedWarriors.put(warrior.getTroopName(), this.selectedWarriors.get(warrior.getTroopName()) - 1);
                    } else {
                        spinner.getValueFactory().setValue(lastValue);
                    }
                }
            });
            HBox temBox = new HBox(lblName, lblHousing, spinner);
            HBox.setMargin(lblName, new Insets(0, 0, 0, 50));
            temBox.setSpacing(90);
            tilePaneList.add(temBox);
        });
    }

    public void onStartGameAction(ActionEvent actionEvent) {
        this.mainWindowController.starGameLevel(this.selectedWarriors);
        this.selectedWarriors.clear();
    }

    public void setParentRoot(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

}
