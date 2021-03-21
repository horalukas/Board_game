package thedrake.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import thedrake.PlayingSide;

public class End extends Application {
    String LabelText ="REMÍZA";
    String Text="#ffffff";
    String Background="#000000";

    void setEndStyle(PlayingSide side){
        if(side==PlayingSide.ORANGE){
            LabelText ="ORANŽOVÝ VYHRÁL";
            Text="#ffa500";

        }else {
            LabelText = "MODRÝ VYHRÁL";
            Text="#0000ff";
        }

    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("End.fxml"));
        Label label = (Label) root.lookup("#labelEnd");
        label.setText(LabelText);
        label.setStyle("-fx-text-fill:"+Text);  //css pro label
        Button ng = (Button) root.lookup("#newGameButton");     //css pro tlacitka
        ng.setStyle("-fx-background-color:"+Text);
        Button mm = (Button) root.lookup("#mainMenuButton");
        mm.setStyle("-fx-background-color:"+Text);
        stage.setTitle("The Drake");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
