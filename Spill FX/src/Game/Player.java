package Game;



import Controller.GamePanelController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    private boolean recovering;
    private long recoveryTimer;

    private int lives;
    private Color color1; // Regular
    private Color color2; // When Hit

    private int score;

    private int powerLevel;
    private int power;
    private int[] requiredPower = {1, 2, 3, 4, 5};

    //KONSTRUKTØR
    public Player(){
        x = GamePanelController.WIDTH / 2;
        y = GamePanelController.HEIGHT / 2;
        r = 10;

        dx = 0;
        dy = 0;
        speed = 4;

        lives = 3;

        //MÅ BYTTE TIL PNG FIL/////
        color1 = Color.PURPLE; /// KOBLA TIL SISTE ENEMY???

        color2 = Color.BLUE;


        firing = false;
        firingTimer = System.nanoTime(); //Current time
        firingDelay = 120;  //Shots per second

        recovering = false;
        recoveryTimer =0;

        score = 0;

    }

    //FUNKSJONER

    public int getx() {return x;}
    public int gety() {return y;}
    public int getr() {return r;}

    public int getScore(){return score;}

    public int getLives() {return lives;}

    public boolean isDead(){return lives <= 0;}
    public boolean isRecovering(){ return recovering;}

    public void setLeft(boolean b) {left = b;}
    public void setRight(boolean b) {right = b;}
    public void setUp(boolean b) {up = b;}
    public void setDown(boolean b) {down = b;}

    public void setFiring(boolean b){
        firing = b;
    }

    public void addScore(int i){ score += i;}

    public void gainLife(){
        lives++;
    }

    public void loseLife(){
        lives--;
        recovering = true;
        recoveryTimer = System.nanoTime();

    }

    public void increasePower(int i){

        power += i;
        if(powerLevel == 4){
            if(power > requiredPower[powerLevel]){
                power = requiredPower[powerLevel];
            }
            return;
        }
        if(power >= requiredPower[powerLevel]){
            power -= requiredPower[powerLevel];
            powerLevel++;
        }

    }

    public int getPowerLevel() { return powerLevel;}
    public int getPower() {return power;}
    public int getRequiredPower() {return requiredPower[powerLevel];}

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
        if(x > GamePanelController.WIDTH - r) x = GamePanelController.WIDTH - r;
        if(y > GamePanelController.HEIGHT - r) y = GamePanelController.HEIGHT - r;

        dx = 0;
        dy = 0;

       //Firing
        if(firing){
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if(elapsed > firingDelay){
                firingTimer = System.nanoTime();
                
                if(powerLevel < 2) {
                    GamePanelController.bullets.add(new Bullet(0, x, y));
                }
                else if(powerLevel < 4) {
                    GamePanelController.bullets.add(new Bullet(0, x , y+5));
                    GamePanelController.bullets.add(new Bullet(0, x , y-5));
                }
                else{
                GamePanelController.bullets.add(new Bullet(0.2, x , y));
                GamePanelController.bullets.add(new Bullet(0, x , y));
                GamePanelController.bullets.add(new Bullet(-0.2, x  , y));
                }

            }
        }


        if(recovering) {
            long elapsed = (System.nanoTime() - recoveryTimer) / 1000000;
            if (elapsed > 2000) { //Invincible for 2 seconds when hit
                recovering = false;
                recoveryTimer = 0;
            }
        }


    }




    public void draw(GraphicsContext g){

        color1 = Color.RED; /// KOBLA TIL SISTE ENEMY???

        color2 = Color.BLUE;

        if(recovering){

            g.setFill(color1);
            g.fillOval(x-r,y-r, 2 * r,2 * r);

/*
            g.setStroke(color1);
            g.setLineWidth(2);
            g.stroke();
*/

        }else{
            g.setFill(color2);
            g.fillOval(x-r,y-r, 2 * r,2 * r);

/*
            g.setStroke(color2);
            g.setLineWidth(2);
            g.stroke();
*/
        }


    }


}
