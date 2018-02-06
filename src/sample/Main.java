package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button btn = new Button();
        Button btn2 = new Button();

        btn.setMinHeight(200.0);
        btn.setMinWidth(300.0);
        btn2.setMinHeight(200.0);
        btn2.setMinWidth(300.0);
        btn.setText("Say 'Hello World'");
        btn2.setText("Say 'Helloooo'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hellooooo");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(btn,btn2);

        primaryStage.setTitle("SemesterOppgave");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
