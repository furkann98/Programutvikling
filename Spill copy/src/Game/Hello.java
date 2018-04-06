package Game;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Hello extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Hello World!");
        StackPane root = new StackPane();
        final Canvas canvas = new Canvas( 250, 250);

        Ellipse ellipse = new Ellipse(30, 30, 30, 100);
        Color color = Color.BLUE;
        ellipse.setStroke(color);
        ellipse.setStrokeWidth(3);
        ellipse.setFill(Color.GREEN);
        primaryStage.setScene(new Scene(root, 400, 250));
        GraphicsContext g = canvas.getGraphicsContext2D();

        g.setFill(Color.RED);
        g.fillOval(50, 50, 70, 30);

        g.setLineWidth(3);

        root.getChildren().add(canvas);
        g.setLineWidth(1);

        primaryStage.show();


    }


}
