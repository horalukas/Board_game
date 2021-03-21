package thedrake.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent game = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setTitle("The Drake");
        stage.setScene(new Scene(game));
        stage.show();

    }
}
