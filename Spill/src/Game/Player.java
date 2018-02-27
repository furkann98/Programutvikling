package Game;

import java.awt.*;

public class Player {
    //DATAFELT
    private int x;
    private int y;
    private int r; //radius

    private int dx;
    private int dy;
    private int speed;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private boolean firing;
    private long firingTimer;
    private long firingDelay;

    private int lives;
    private Color color1; // Regular
    private Color color2; // When Hit


    //KONSTRUKTØR
    public Player(){
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
        r = 5;

        dx = 0;
        dy = 0;
        speed = 2;

        lives = 3;
        color1 = Color.WHITE;
        color2 = Color.RED;


        firing = false;
        firingTimer = System.nanoTime(); //Current time
        firingDelay = 200;  //Shots per second

    }

    //FUNKSJONER


    public void setLeft(boolean b) {left = b;}
    public void setRight(boolean b) {right = b;}
    public void setUp(boolean b) {up = b;}
    public void setDown(boolean b) {down = b;}



    public void setFiring(boolean b){
        firing = b;
    }


    public void update(){
        if(left){
            dx = -speed;
        }
        if(right){
            dx = speed;
        }
        if(up){
            dy = -speed;
        }
        if(down){
            dy = speed;
        }

        x += dx;
        y += dy;

        if(x < r) x = r;
        if(y < r) y = r;
        if(x > GamePanel.WIDTH - r) x = GamePanel.WIDTH - r;
        if(y > GamePanel.HEIGHT - r) y = GamePanel.HEIGHT - r;

        dx = 0;
        dy = 0;

        if(firing){
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if(elapsed > firingDelay){
                GamePanel.bullets.add(new Bullet(300, x, y));
                firingTimer = System.nanoTime();
            }
        }

    }




    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval(x-r,y-r, 2 * r,2 * r);

        g.setStroke(new BasicStroke(3));
        g.setColor(color1.darker());
        g.drawOval(x-r,y-r, 2*r,2*r);
        g.setStroke(new BasicStroke(1));

    }


}
