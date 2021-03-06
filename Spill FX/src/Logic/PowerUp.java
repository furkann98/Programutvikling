package Logic;

import Controller.GamePanelController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * klasse for powerUP
 *
 * implementerer alle nødvendige variabler i datafeltet
 * lager konstruktør og setter opp alle metoder.
 *
 * @author Muhammed Furkan Ergin s325881 / Pedram Rahdeirjoo s325906  */

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
    private Image powerup1 = new Image("/View/img/Heart.png");
    private Image powerup4 = new Image("/View/img/slowtime.png");


    /**
     *
     * @param type   Hva slags type powerUp
     * @param x  x-koordinaten til powerUp
     * @param y  y-koordinaten til powerUp
     */
    //Constructor
    public PowerUp(int type, double x, double y){
        this.type = type;
        this.x = x;
        this.y = y;

        if(type == 1){
            img = powerup1;
            color1 = Color.TRANSPARENT;
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
            color1 = Color.TRANSPARENT;
            r = 3;
        }





    }


    //Functions
    public double getx(){ return x; }
    public double gety(){ return y; }
    public double getr(){ return r; }

    /**
     * getType metoden
     *
     * getType metoden blir brukt for å hente/finne ut hvilken type powerUp som
     * blir plukket opp av Player.
     *
     * @return type hva slags type powerUp som blir hentet/plukket opp
     */
    public int getType(){
        //powerUpPlayer.play();
        return type;
    }

    /**
     *
     *
     *
     * @return true/false Hvilken verdi som stemmer passer til PowerUp sin koordinater.
     *
     */
    public boolean update(){

        x -= 0.5;

        if(y > GamePanelController.HEIGHT + r){
            return true;
        }else{
            return false;
        }


    }

    /**
     *
     * Tegner powerUps på canvasen med graphicscontext i form av sirkel.
     * dersom powerup er av type 4 blir det satt inn et bilde og figuren blir av typen rektangel.
     *
     * @param g GraphicsContext verktøyet for å sette inn bilde på canvaset.
     *
     * @see Image et bildet av powerup, ettersom, hvilken type det er.
     */
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
