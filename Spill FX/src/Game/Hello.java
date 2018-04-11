package Game;


import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import javafx.scene.input.KeyEvent;


public class Hello extends Application  {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Hello World!");
        StackPane root = new StackPane();
        final Canvas canvas = new Canvas( 250, 250);

        //Make Canvas
        Ellipse ellipse = new Ellipse(30, 30, 30, 100);
        Color color = Color.BLUE;
        ellipse.setStroke(color);
        ellipse.setStrokeWidth(3);
        ellipse.setFill(Color.GREEN);

        //Scene
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        GraphicsContext g = canvas.getGraphicsContext2D();

        g.setFill(Color.RED);
        g.fillOval(50, 50, 70, 30);

        g.setLineWidth(3);

        root.getChildren().add(canvas);
        g.setLineWidth(1);


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                if (event.getCode() == KeyCode.UP){
                    ellipse.setCenterX(20);
                }
                if (event.getCode() == KeyCode.DOWN){
                    ellipse.setCenterX(100);

                }
            }
        });


        primaryStage.show();

        /*  Slik skal vi ha bevegelser og skudd.

       .setOnkeyPressed(event -> {
            Switch(event.getCode()){
                case UP: player.setUP(true);
                break;
                case DOWN: player.setDOWN(true);
                    break;
                case LEFT: player.setLEFT(true);
                    break;
                case RIGHT: player.setRIGHT(true);
                    break;
                case SPACE: player.setFiring(true);
                    break;

            }

        });

       .setOnkeyReleased(event -> {
            Switch(event.getCode()){
                case UP: player.setUP(false);
                    break;
                case DOWN: player.setDOWN(false);
                    break;
                case LEFT: player.setLEFT(false);
                    break;
                case RIGHT: player.setRIGHT(false);
                    break;
                case SPACE: player.setFiring(false);
                    break;

            }

        });

        */





    }



}
