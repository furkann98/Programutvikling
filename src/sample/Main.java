package sample;

import javafx.application.*;
import javafx.event.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
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

        StackPane v = new StackPane(btn2);
        StackPane h = new StackPane(btn);

        StackPane root = new StackPane(v, h);



        primaryStage.setTitle("SemesterOppgave");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
