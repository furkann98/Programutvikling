package Game;

import Controller.GamePanelController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



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


    //Constructor
    public PowerUp(int type, double x, double y){
        this.type = type;
        this.x = x;
        this.y = y;

        if(type == 1){
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
            color1 = Color.BLUE;
            r = 3;
        }





    }


    //Functions
    public double getx(){ return x; }
    public double gety(){ return y; }
    public double getr(){ return r; }

    public int getType(){return type;}

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
        g.fillRect((int)(x-r),(int) (y-r), 2*r, 2*r);

        g.setStroke(color1.darker());
        g.setLineWidth(2);
        g.stroke();



    }


}
