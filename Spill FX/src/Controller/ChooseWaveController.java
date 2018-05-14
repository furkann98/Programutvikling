package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Denne klassen/Controller er for fxml-filen/siden til "Choose wave"
 * Her er alle knappene og deres funksjoner til alle knappene som synes på FXML-en.
 *
 *
 */


public class ChooseWaveController {

    private GamePanelController gpc = new GamePanelController();
    //File handling

    //private FileChooser filehandling = new FileChooser();


    /**
     * Metode som kjører spillet i Wave 1
     *
     * Metoden kjører spillet, med verdier som sender Player, med en bestemt verdi av health, til et bestemt wave,
     * en bestemt verdi av Score, og med en bestemt nivå av PowerLevel.
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonWave1(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
        Scene tableViewScene = new Scene(tableViewParent);

        gpc = (GamePanelController) loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        gpc.load(3, 0, 1, 1);

        gpc.gameLoop.start();
    }


    /**
     * Metode som kjører spillet i Wave 2
     *
     * Metoden kjører spillet, med verdier som sender Player, med en bestemt verdi av health, til et bestemt wave,
     * en bestemt verdi av Score, og med en bestemt nivå av PowerLevel.
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonWave2(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
        Scene tableViewScene = new Scene(tableViewParent);

        gpc = (GamePanelController) loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        gpc.load(3, 0, 2, 0);

        gpc.gameLoop.start();
    }

    /**
     * Metode som kjører spillet i Wave 3
     *
     * Metoden kjører spillet, med verdier som sender Player, med en bestemt verdi av health, til et bestemt wave,
     * en bestemt verdi av Score, og med en bestemt nivå av PowerLevel.
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonWave3(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
        Scene tableViewScene = new Scene(tableViewParent);

        gpc = (GamePanelController) loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        gpc.load(3, 0, 3, 2);

        gpc.gameLoop.start();
    }

    /**
     * Metode som kjører spillet i Wave 4
     *
     * Metoden kjører spillet, med verdier som sender Player, med en bestemt verdi av health, til et bestemt wave,
     * en bestemt verdi av Score, og med en bestemt nivå av PowerLevel.
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonWave4(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
        Scene tableViewScene = new Scene(tableViewParent);

        gpc = (GamePanelController) loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        gpc.load(3, 0, 4, 2);

        gpc.gameLoop.start();
    }

    /**
     * Metode som kjører spillet i Wave 5
     *
     * Metoden kjører spillet, med verdier som sender Player, med en bestemt verdi av health, til et bestemt wave,
     * en bestemt verdi av Score, og med en bestemt nivå av PowerLevel.
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonWave5(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
        Scene tableViewScene = new Scene(tableViewParent);

        gpc = (GamePanelController) loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        gpc.load(3, 0, 5, 3);

        gpc.gameLoop.start();
    }

    /**
     * Metode som kjører spillet i Wave 6
     *
     * Metoden kjører spillet, med verdier som sender Player, med en bestemt verdi av health, til et bestemt wave,
     * en bestemt verdi av Score, og med en bestemt nivå av PowerLevel.
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonWave6(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
        Scene tableViewScene = new Scene(tableViewParent);

        gpc = (GamePanelController) loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        gpc.load(3, 0, 6, 3);

        gpc.gameLoop.start();
    }

    /**
     * Metode som kjører spillet i Wave 7
     *
     * Metoden kjører spillet, med verdier som sender Player, med en bestemt verdi av health, til et bestemt wave,
     * en bestemt verdi av Score, og med en bestemt nivå av PowerLevel.
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonWave7(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
        Scene tableViewScene = new Scene(tableViewParent);

        gpc = (GamePanelController) loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        gpc.load(4, 0, 7, 4);

        gpc.gameLoop.start();
    }

    /**
     * Metode som kjører spillet i Wave 8
     *
     * Metoden kjører spillet, med verdier som sender Player, med en bestemt verdi av health, til et bestemt wave,
     * en bestemt verdi av Score, og med en bestemt nivå av PowerLevel.
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonWave8(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
        Scene tableViewScene = new Scene(tableViewParent);

        gpc = (GamePanelController) loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        gpc.load(4, 0, 8, 4);

        gpc.gameLoop.start();
    }


    /**
     * Metode som kjører spillet i Bonus Wave
     *
     * Metoden kjører spillet, med verdier som sender Player, med en bestemt verdi av health, til et bestemt wave,
     * en bestemt verdi av Score, og med en bestemt nivå av PowerLevel.
     *
     * Bonus Wave er en ekstra bane for de som kanskje tenker at de første rundene blir litt for enkle.
     *
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void buttonBonus(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("/View/GamePanel.fxml").openStream());
        Scene tableViewScene = new Scene(tableViewParent);

        gpc = (GamePanelController) loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        gpc.load(3, 0, 10, 4);

        gpc.gameLoop.start();
    }


    /**
     *Metode som sender brukeren tilbake til hovedMenyen.
     *
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
