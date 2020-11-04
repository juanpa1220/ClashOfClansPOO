package control;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Controller {
    public Button test;
    public Label labelTest;
    public Button test3;
    Image image;

    public Controller() {
    }

    public void test(ActionEvent actionEvent) {
        System.out.println("test");
        Button button = (Button) actionEvent.getSource();
        button.setGraphic(new ImageView(image));
    }

    public void onAction(ActionEvent actionEvent) {
        Image image = new Image(getClass().getResourceAsStream("Sprite_de_Minerva_FEW.gif"));
        ImageView view = new ImageView(image);
//        labelTest.setGraphic(new ImageView(image));
        test3.setGraphic(view);
    }
}
