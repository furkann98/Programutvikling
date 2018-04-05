package Game;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class Bullet {

    //DATAFELT
    private double x;
    private double y;
    private int r;

    private double dx;
    private double dy;
    private double rad;
    private double speed;

    private Color color1;

    //KONSTRUKTØR

    public Bullet(double angle, int x, int y){
        this.x = x;
        this.y = y;
        r = 2;

        rad = Math.toRadians(angle);
        speed = 4;
        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;



        color1 = Color.YELLOW;
    }

    //FUNKSJON

    public double getx() {return x;}
    public double gety() {return y;}
    public double getr() {return r;}

    public boolean update(){
        x += dx;
        y += dy;

        if(x < -r || x > GamePanel.WIDTH + r || y < -r || y > GamePanel.HEIGHT + r ){
            return true;
        }
        return false;
    }

    public void draw(GraphicsContext g){
        g.setColor(color1);
        g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r );

    }



}
