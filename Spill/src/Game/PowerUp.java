package Game;

import java.awt.*;

public class PowerUp {

    //Datafelt
    private double x;
    private double y;
    private int r;

    private int type;
    //type 1 = +1 life
    //type 2 = +1 power
    //type 3 = +2 power

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





    }


    //Functions
    public double getx(){ return x; }
    public double gety(){ return y; }
    public double getr(){ return r; }

    public int getType(){return type;}

    public boolean update(){

        //denne må byttes til x
        y += 0.5;

        if(y > GamePanel.HEIGHT + r){
            return true;
        }else{
            return false;
        }


    }

    public void draw(Graphics2D g){

    g.setColor(color1);
    g.fillRect((int)(x-r),(int) (y-r), 2*r, 2*r);

    //borderg
    g.setStroke(new BasicStroke(3));
    g.setColor(color1.darker());
    g.drawRect((int)(x-r),(int) (y-r), 2*r, 2*r);
    g.setStroke(new BasicStroke(1));

    }


}
