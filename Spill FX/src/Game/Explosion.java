package Game;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;

public class Explosion {

    //Datafelt
    private double x;
    private double y;
    private int r;
    private int maxRadius;

    private String musicFile = "src/View/sound/gameover?.wav";     // For example
    private Media sound = new Media(new File(musicFile).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);

    //CONSTRUCTOR

    public Explosion(double x, double y, int r, int max){
        this.x = x;
        this.y = y;
        this.r = r;
        maxRadius = max;
    }

    public boolean update(){
        r+=2;
        if(r >= maxRadius){
            return true;
        }
        return false;

    }

    public void draw(GraphicsContext g){
        g.setStroke(Color.WHITE);
        g.setGlobalAlpha(0.4);
        g.strokeOval((int) (x-r), (int)(y-r), 2*r, 2*r);
        g.setGlobalAlpha(1);

        mediaPlayer.play();


    }




}
