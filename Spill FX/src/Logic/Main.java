
package Logic;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Main klassen som "extendes Application
 *
 * Dette starter hele spillet med en start og main metode.
 * Spillet starter først og fremst i mainPage.fxml
 *
 * @author Muhammed Furkan Ergin s325881 / Pedram Rahdeirjoo s325906  */


public class Main extends Application {

    /**
     * Start punktet til alle JavaFX applikasjoner, hvor start metoden kalles på, der fxml filen sendes til primarystage.
     *
     * Dette skjer i "JavaFX Applicaion Thread".
     *
     * @param primaryStage er hovedscenen for denne applikasjonen hvor scener kan bli lagt til og byttet mellom seg.
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainPage.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main metoden som er grunnmuren til alle program, starter programmet.
     * @param args komando linjene som blir gitt til applikasjonen.
     */

    public static void main(String[] args) {
        launch(args);
    }

}
