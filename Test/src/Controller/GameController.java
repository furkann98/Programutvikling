package Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import Controller.GameController.*;
import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.shape.*;

public class GameController implements Initializable {

    int imageX = 0;
    int imageY = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        Avatar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if (key.getCode().equals(KeyCode.RIGHT)) {
                    imageX += 5;
                } else if (key.getCode().equals(KeyCode.LEFT)) {
                    imageX -= 5;
                }
                updateImageView();
            }

            private void updateImageView() {
                Avatar.setX(imageX);
                Avatar.setY(imageY);
            }
        });
        Avatar.setFocusTraversable(true);

    }

}



