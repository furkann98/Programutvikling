package Game;

import Controller.GamePanelController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;


public class PowerUp {

    //Datafelt
    private double x;
    private double y;
    private int r;

    private int type;
    //type 1 = +1 life
    //type 2 = +1 power
    //type 3 = +2 power
    //type 4 = slows down time

    private Color color1;

    private Image img;
    private Image powerup1 = new Image("View/img/Heart.png");
    private Image powerup4 = new Image("View/img/slowtime.png");

/*

    private String powerUpFile = "src/View/sound/power.mp3";
    private Media powerUpSound = new Media(new File(powerUpFile).toURI().toString());
    private MediaPlayer powerUpPlayer = new MediaPlayer(powerUpSound);
*/
    //Constructor
    public PowerUp(int type, double x, double y){
        this.type = type;
        this.x = x;
        this.y = y;

        if(type == 1){
            img = powerup1;
            color1 = Color.GREEN;
            r = 3;
        }

        if(type == 2){
            color1 = Color.YELLOW;
            r = 3;
        }
        if(type == 3 ){
            color1 = Color.YELLOW;
            r = 5;
        }
        if(type == 4){
            img = powerup4;
            color1 = Color.BLUE;
            r = 3;
        }





    }


    //Functions
    public double getx(){ return x; }
    public double gety(){ return y; }
    public double getr(){ return r; }

    public int getType(){
        //powerUpPlayer.play();
        return type;
    }

    public boolean update(){

        //denne må byttes til x
        x -= 0.5;

        if(y > GamePanelController.HEIGHT + r){
            return true;
        }else{
            return false;
        }


    }

    public void draw(GraphicsContext g){

        g.setFill(color1);
        if (type == 4){
            g.fillRect((int)(x-r),(int) (y-r), 4*r, 4*r);
            g.drawImage(img, x-1.4*r, y-1.4*r,5*r, 5*r);
        } else{
            g.fillOval((int)(x-r),(int) (y-r), 4*r, 4*r);
            g.drawImage(img, x-1.5*r, y-1.5*r,5*r, 5*r);
        }


    }


}
