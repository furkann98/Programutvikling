package Controller;

import Logic.GameSave;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    private GamePanelController gpc = new GamePanelController();
    //File handling

    private FileChooser filehandling = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void buttonPlay(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/GamePanel.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }



    public void buttonHowToPlay(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/HowToPlay.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void buttonLoad(javafx.event.ActionEvent event) throws IOException {



       // gpc.gameLoop.stop();  //STOP
        //gpc.setPause(true);
       // gpc.drawPause();   //Pause

        filehandling.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = filehandling.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int lives = 0;
                int score = 0;
                int wave = 0;
                int power = 0;
                int i = 0;
                String line;
                while ((line = reader.readLine()) != null) {
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


                FXMLLoader loader = new FXMLLoader();
                Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
                Scene tableViewScene = new Scene(tableViewParent);

                gpc = (GamePanelController) loader.getController();

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
                gpc.load(lives, score, wave, power);

                gpc.gameLoop.start();

            } catch (IOException e) {
                //System.out.println("Closed the  filechooser ");
                //e.printStackTrace();  prints out the stack trace.
            }
        }

    }

    public void buttonChooseWave(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/ChooseWave.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }



}