package control;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class NewWarriorFormController {
    public ComboBox<String> comboBox;
    public TextField txtName;
    public TextField txtImagePath;
    public Spinner<Integer> lifeSpinner;
    public Spinner<Integer> appearLevelSpinner;
    public Spinner<Integer> hitsSpinner;
    public Spinner<Integer> housingSpaceSpinner;
    public static NewWarriorFormController newWarriorFormController;
    private MainWindowController mainWindowController;


    public static NewWarriorFormController getInstance() {
        return newWarriorFormController;
    }

    public NewWarriorFormController() {
        newWarriorFormController = this;
    }

    public void setUpComponents() {
        this.comboBox.getItems().setAll(
                "Contact",
                "Medium Range",
                "Aerial",
                "Beast",
                "Hero"
        );

        this.lifeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt("0")));
        this.hitsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt("0")));
        this.appearLevelSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 15, Integer.parseInt("0")));
        this.housingSpaceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt("0")));

    }

    public void onOkAction(ActionEvent actionEvent) {
    }

    public void onCancelAction(ActionEvent actionEvent) {
        this.mainWindowController.setChildRoot("warriorPicker");
    }


    public void setParentRoot(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
