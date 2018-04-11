
package View;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("/View/mainPage.fxml"));

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();
        }


    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN ||
                    event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                //your code for moving the ship
            }
            else if(event.getCode() == KeyCode.SPACE) {
                //your code for shooting the missile
            }
            event.consume();
        }
    };

}
