package Game;

import java.awt.*;


public class Enemy {
    //DATAFELT
    private double x;
    private double y;
    private int r;

    private double dx;
    private double dy;
    private double rad; // angle/radiens
    private double speed;

    private int health;
    private int type;
    private int rank;

    private Color color1;

    private boolean ready; //inside gamestring
    private boolean dead;


    //CONSTRUCTOR
    public Enemy(int type, int rank){
        this.type = type;
        this.rank = rank;


        //DEFAULT ENEMY TYPE
        if(type == 1){
            color1 = Color.BLACK;
            if(rank == 1){
                speed = 1;
                r = 5;
                health = 1;
            }
        }

        //Stronger, faster default
        if(type == 2){
            color1 = Color.RED;
            if (rank == 1) {
                speed = 3;
                r = 5;
                health = 2;
            }
        }
        //Slow, but hard to kill enemy
        if(type == 3){
            color1 = Color.GREEN;
            if(rank == 1){
                speed = 1.5;
                r = 5;
                health = 5;
            }
        }




        x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
        y = -r;


        // MÅ BYTTE ANGLE
        double angle =  Math.random() * 140 + 20;

        rad = Math.toRadians(angle);

        // MÅ KANSKJE BYTTE HER OGSÅ

        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;

        ready = false;
        dead = false;

    }


        //FUNCTIONS
    public double getx() {return x;}
    public double gety() {return y;}
    public double getr() {return r;}

    public int getType() {return type;}
    public int getRank() {return rank;}

    public boolean isDead(){ return dead; }

    public void hit(){
        health--;
        if(health <= 0){
            dead = true;
        }

    }


    public void update(){
        x += dx;
        y += dy;
        if(!ready){
            if(x > r && x < GamePanel.WIDTH - r &&
                    y > r && y < GamePanel.HEIGHT - r){
                ready = true;
            }

        }

        if(x < r && dx < 0) dx = -dx;
        if(y < r && dy < 0) dy = -dy;
        if(x > GamePanel.WIDTH - r && dx > 0) dx = -dx;
        if(y > GamePanel.HEIGHT - r && dy > 0) dy = -dy;
    }

    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval((int)(x - r), (int)(y - r) , 2 * r, 2 * r);
        g.setStroke(new BasicStroke(3));


        g.setColor(color1.darker());
        g.drawOval((int)(x - r), (int)(y - r) , 2 * r, 2 * r);
        g.setStroke(new BasicStroke(1));
    }


}
