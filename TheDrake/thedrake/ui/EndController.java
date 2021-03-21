package thedrake.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EndController implements Initializable {

    @FXML
    private Button mainMenuButton;
    @FXML
    private Button newGameButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onNewGame() {
        Stage stage = (Stage) newGameButton.getScene().getWindow();
        stage.close();
        TheDrakeApp game=new TheDrakeApp();
        try {
            game.start(new Stage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMainMenu(){
        Stage stage = (Stage) mainMenuButton.getScene().getWindow();
        stage.close();
        MainMenu newScreen=new MainMenu();
        try {
            newScreen.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
