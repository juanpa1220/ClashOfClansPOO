package control;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.FileManager.JsonManager;
import org.json.simple.JSONObject;


import java.io.File;

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
        this.txtName.setText("");
        this.txtImagePath.setText("/asserts/imgs/");
        this.comboBox.getItems().setAll(
                "Contact",
                "Medium Range",
                "Aerial",
                "Beast",
                "Hero"
        );
        this.comboBox.setPromptText("Choose your warrior type");
        this.lifeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt("0")));
        this.lifeSpinner.setEditable(true);
        this.hitsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt("0")));
        this.hitsSpinner.setEditable(true);
        this.appearLevelSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 15, Integer.parseInt("0")));
        this.hitsSpinner.setEditable(true);
        this.housingSpaceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt("0")));
    }

    public void onOkAction(ActionEvent actionEvent) throws Exception {
        String name = txtName.getText();
        String path = txtImagePath.getText();
        String type = comboBox.getValue();
        int appearLevel = appearLevelSpinner.getValue();
        int life = lifeSpinner.getValue();
        int hits = hitsSpinner.getValue();
        int housingSpace = housingSpaceSpinner.getValue();


        if (name.equals("") || path.equals("/asserts/imgs/") || path.equals("") ||
                type == null || appearLevel == 0 || life == 0 || hits == 0 || housingSpace == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid data");
            alert.setContentText("Make sure you field every space");
            alert.showAndWait();
        } else {
            File file = new File(System.getProperty("user.dir") + "/src" + path);
            if (file.exists()) {
                JSONObject newWarrior = new JSONObject();
                newWarrior.put("name", name);
                newWarrior.put("imagePath", ".." + path);
                newWarrior.put("type", type);
                newWarrior.put("appearanceLevel", String.valueOf(appearLevel));
                newWarrior.put("life", String.valueOf(life));
                newWarrior.put("hits", String.valueOf(hits));
                newWarrior.put("housing", String.valueOf(housingSpace));
                JSONObject newWarrior2 = new JSONObject();
                newWarrior2.put("warrior", newWarrior);

                boolean response = JsonManager.jsonWriter(newWarrior2, "src/asserts/docs/gameSettings/warriors.json");
                Alert alert;
                if (response) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Congrats");
                    alert.setHeaderText("Congrats");
                    alert.setContentText("Your new warrior have been saved successfully");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Something went wrong");
                    alert.setContentText("The new warrior could not be saved");
                }
                alert.showAndWait();
//                this.onCancelAction();
                this.mainWindowController.updateWarriorPick();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid image path");
                alert.setContentText("Image path: " + file.getPath() + " does not exists");
                alert.showAndWait();
            }
        }
    }

    public void onCancelAction() {
        this.setUpComponents();
        this.mainWindowController.setChildRoot("warriorPicker");
    }


    public void setParentRoot(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

}
