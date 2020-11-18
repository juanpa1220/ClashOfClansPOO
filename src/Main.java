import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage refPrimaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainWindow.fxml"));
        primaryStage.setTitle("Herencia de Clanes");
        primaryStage.setScene(new Scene(root, 640, 780));
        primaryStage.setResizable(false);
        primaryStage.show();
        refPrimaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
