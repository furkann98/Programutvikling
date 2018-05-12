package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;




public class ChooseWaveController {

    private GamePanelController gpc = new GamePanelController();
    //File handling

    //private FileChooser filehandling = new FileChooser();


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

    public void buttonBack(javafx.event.ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/mainPage.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
