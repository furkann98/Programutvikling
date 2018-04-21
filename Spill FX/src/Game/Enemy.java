package Game;


import Controller.GamePanelController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;




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

    private boolean hit;
    private long hitTimer;

    private boolean slow;


    //CONSTRUCTOR
    public Enemy(int type, int rank){
        this.type = type;
        this.rank = rank;


        //DEFAULT ENEMY TYPE
        if(type == 1){
            color1 = Color.RED;
            //color1 = new Color(0,0,255,128);
            if(rank == 1){
                speed = 1;
                r = 7;
                health = 1;
            }
            if(rank == 2){
                speed = 2;
                r = 14;
                health = 2;
            }
            if(rank == 3){
                speed = 1.5;
                r = 28;
                health = 3;
            }
            if(rank == 4){
                speed = 1.5;
                r = 56;
                health = 4;
            }
        }

        //Stronger, faster default
        if(type == 2){
            color1 = Color.RED.darker();
            //color1 = new Color(255,0,0,128);
            if (rank == 1) {
                speed = 3;
                r = 5;
                health = 2;
            }
            if (rank == 2) {
                speed = 3;
                r = 10;
                health = 3;
            }
            if (rank == 3) {
                speed = 2.5;
                r = 20;
                health = 3;
            }
            if (rank == 4) {
                speed = 2.5;
                r = 30;
                health = 4;
            }
        }
        //Slow, but hard to kill enemy
        if(type == 3){
            color1 = Color.RED;
            //color1 = new Color(0,255,0,128);

            if(rank == 1){
                speed = 1.5;
                r = 5;
                health = 3;
            }
            if(rank == 2){
                speed = 1.5;
                r = 10;
                health = 4;
            }
            if(rank == 3){
                speed = 1.5;
                r = 25;
                health = 5;
            }
            if(rank == 4){
                speed = 1.5;
                r = 45;
                health = 5;
            }
        }


        x = -r + GamePanelController.WIDTH;
        y = Math.random() * (GamePanelController.HEIGHT-GamePanelController.HEIGHT/8)+GamePanelController.HEIGHT / 8;



        // MÅ BYTTE ANGLE til et tall mellom 120-240
        //double angle =  Math.random() * 340 + 200;
        double angle =  Math.random() * 240 + 120;


        rad = Math.toRadians(angle);

        // MÅ KANSKJE BYTTE HER OGSÅ

        dx = Math.sin(rad) * speed;
        dy = Math.cos(rad) * speed;

        ready = false;
        dead = false;

        hit = false;
        hitTimer = 0;

    }


        //FUNCTIONS
    public double getx() {return x;}
    public double gety() {return y;}
    public int getr() {return r;}

    public int getType() {return type;}
    public int getRank() {return rank;}

    public Color getColor() { return color1; }

    public void setSlow(boolean b){slow = b;}

    public boolean isDead(){ return dead; }

    public void hit(){
        health--;
        if(health <= 0){
            dead = true;
        }
        hit = true;
        hitTimer = System.nanoTime();

    }

    public void explode(){

        if(rank > 1){
            int amount = 0;
            if(type == 1){
                amount = 3;
            }
            if(type == 2){
                amount = 3;
            }
            if(type == 3){
                amount = 4;
            }


            for(int i = 0; i < amount; i++){

                Enemy e = new Enemy(getType(), getRank() - 1);
                e.setSlow(slow);
                e.x = this.x;
                e.y = this.y;
                double angle = 0;

                if(!ready){
                    angle = Math.random() * 140+20;
                }else{
                    angle =  Math.random() * 360;
                }
                e.rad = Math.toRadians(angle);

                GamePanelController.enemies.add(e);
            }

        }

    }


    public void update(){

        if(slow){
            x += dx * 0.3;
            y += dy * 0.3;
        }else{
            x += 2 * dx;
            y += 2* dy;
        }



        if(!ready){
            if(x > r && x < GamePanelController.WIDTH - r &&
                    y > r && y < GamePanelController.HEIGHT - r){
                ready = false;
            }
        }

        if(x < r && dx < 0) dx = -dx;
        if(y < r && dy < 0) dy = -dy;
        if(x > GamePanelController.WIDTH - r && dx > 0) dx = -dx;
        if(y > GamePanelController.HEIGHT - r && dy > 0) dy = -dy;

        if(hit){
            long elapsed = (System.nanoTime() - hitTimer) / 1000000;
            if(elapsed > 50){
                hit = false;
                hitTimer = 0;
            }
        }

    }

    public void draw(GraphicsContext g){

        if(hit){
            g.setFill(color1.darker());
            g.fillOval(x-r,y-r, 2 * r,2 * r);

/*
            g.fillOval(x-r,y-r, 2 * r,2 * r);
            g.setFill(color1.darker());
            g.setStroke(color1.darker());
            g.setLineWidth(2);
            g.stroke();

*/


        }else{
            g.setFill(color1);
            g.fillOval(x-r,y-r, 2 * r,2 * r);

/*
            g.setStroke(color1);
            g.setLineWidth(2);
            g.stroke();
*/
        }

    }


}
