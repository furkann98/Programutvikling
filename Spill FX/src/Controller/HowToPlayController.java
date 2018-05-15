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


/**
 * Controller-klassen for HowToPlay.fxml
 *
 * her er knappen som er i HowToPlay klassen.
 *
 *
 * @author Muhammed Furkan Ergin s325881 / Pedram Rahdeirjoo s325906 */


public class HowToPlayController implements Initializable {


    /**
     *Sender brukeren tilbake til hovedMenyen.
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonBack(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/mainPage.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }





}