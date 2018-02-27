package Controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HowToPlayController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle rb){


    }

    public void buttonPlay(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/HowToPlay.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void buttonBack(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/mainPage.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }





}