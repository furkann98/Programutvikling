package Controller;

import Logic.GameSave;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Denne Controller-klassen er for FXML-filen MainPage
 * Dette er forsiden til spillet, her kan du velge mellom fire knapper som sender deg videre til andre filer.
 * Play-Knappen, How to Play-knappen, load-knappen og Choose wave-knappen
 *
 */


public class MainPageController implements Initializable {

    private GamePanelController gpc = new GamePanelController();
    //File handling

    private FileChooser filehandling = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    /**
     * Kjører spillet,
     * bytter til GamePanel.FXML
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonPlay(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/GamePanel.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }


    /**
     *  HowToPlay metoden sender brukeren til fxml filen HowToPlay,
     *  der er det informasjon om hvordan spilleren skal styre avataren.
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonHowToPlay(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/HowToPlay.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }



    /**
     * Load knappen gir brukeren en sjanse til å få frem og åpne andre/fremtidige lagrede filer.

     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */

    public void buttonLoad(javafx.event.ActionEvent event) throws IOException {


        //filehandling.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = filehandling.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int lives = 0;
                int score = 0;
                int wave = 0;
                int power = 0;
                int i = 0;
                String line;
                while ((line = reader.readLine()) != null && line.matches("^[0-9]*$")) {
                    i++;
                    if (i == 1) {
                        lives = Integer.parseInt(line);
                    }
                    if (i == 2) {
                        score = Integer.parseInt(line);
                    }
                    if (i == 3) {
                        wave = Integer.parseInt(line);
                    }
                    if (i == 4) {
                        power = Integer.parseInt(line);
                    }
                }
                if (i != 4){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The file is corrupt, please try another file", ButtonType.OK);
                    alert.showAndWait();
                }
                else{
                    FXMLLoader loader = new FXMLLoader();
                    Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
                    Scene tableViewScene = new Scene(tableViewParent);

                    gpc = (GamePanelController) loader.getController();

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    window.setScene(tableViewScene);
                    window.show();
                    gpc.load(lives, score, wave, power);

                    gpc.gameLoop.start();

                }
            } catch (IOException e) {
                //e.printStackTrace();  prints out the stack trace.
            }
        }

    }


    /**
     *
     *
     * ChooseWave sender deg til ChooseWave.fxml
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */

    public void buttonChooseWave(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/ChooseWave.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }



}