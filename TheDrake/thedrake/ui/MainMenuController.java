package thedrake.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private Button end;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void onPlay() {
        onEnd();
        TheDrakeApp game=new TheDrakeApp();
        try {
            game.start(new Stage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onEnd(){
        Stage stage = (Stage) end.getScene().getWindow();
        stage.close();
    }
}
