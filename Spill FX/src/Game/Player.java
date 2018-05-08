package Game;



import Controller.GamePanelController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.File;

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
    private boolean firingSound = false;

    private boolean recovering;
    private long recoveryTimer;

    private int lives;
    private boolean livesSound = false;
    public boolean getLivesSound(){return livesSound;}
    public void setLivesSound(boolean b){livesSound = b;}
    private boolean gamoverSound = false;
    private Color color;

    private int score;

    private int powerLevel;
    private int power;
    private int[] requiredPower = {1, 2, 3, 4, 5};

    private Image img = new Image("View/img/spaceShip.png");
    private Image imgRecovering = new Image("View/img/spaceShip2.png");




    //KONSTRUKTØR
    public Player(){
        x = GamePanelController.WIDTH / 2;
        y = GamePanelController.HEIGHT / 2;
        r = 30;

        dx = 0;
        dy = 0;
        speed = 6;

        lives = 3;

        color = Color.TRANSPARENT;

        firing = false;
        firingTimer = System.nanoTime(); //Current time
        firingDelay = 200;  //Shots per second

        recovering = false;
        recoveryTimer =0;

        score = 0;

    }

    //FUNKSJONER

    public int getx() {return x;}
    public int gety() {return y;}
    public int getr() {return r;}

    public int getScore(){return score;}
    public void setScore(int score){this.score = score;}

    public int getLives() {return lives;}

    public boolean isDead(){

        return lives <= 0;
    }
    public boolean gameoverSound(){ return gamoverSound; }
    public void setGameoverSound(boolean b){ gamoverSound = b;}
    public boolean isRecovering(){ return recovering;}

    public void setLeft(boolean b) {left = b;}
    public void setRight(boolean b) {right = b;}
    public void setUp(boolean b) {up = b;}
    public void setDown(boolean b) {down = b;}

    public void setLives(int lives){ this.lives = lives;}
    public void kill(){lives = 0;}

    public void setFiring(boolean b){ firing = b; }
    public boolean getFiring(){ return firing;}

    public void setFiringSound(boolean b){ firingSound = b; }
    public boolean getFiringSound(){ return firingSound;}


    public void addScore(int i){ score += i;}

    public void gainLife(){
        lives++;
    }

    public void loseLife(){
        lives--;
        recovering = true;
        recoveryTimer = System.nanoTime();
        livesSound = true;

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
    public void setPower(int power){powerLevel = power;}

    //Super power
    private boolean checkNuke = false;
    private boolean nuke = false;
    public boolean getNuke(){ return checkNuke;}
    public void setNukeTrue(){nuke = true;}



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
                else if(powerLevel < 3) {
                    GamePanelController.bullets.add(new Bullet(0, x , y+5));
                    GamePanelController.bullets.add(new Bullet(0, x , y-5));
                }
                else {
                        GamePanelController.bullets.add(new Bullet(0.2, x , y));
                        GamePanelController.bullets.add(new Bullet(0, x , y));
                        GamePanelController.bullets.add(new Bullet(-0.2, x  , y));

                }

                setFiringSound(true);

            }


        }


        //NUKE
        if(powerLevel > 3){
            checkNuke = true;
        }
        if(nuke == true){
            for (double i = 0; i < 360; i += 0.05){
                GamePanelController.bullets.add(new Bullet(i, x , y));

            }
            powerLevel--;
            checkNuke = false;
            nuke = false;
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
        if(recovering){

            g.setFill(color);
            g.fillOval(x-r,y-r, 2 * r,2 * r);
            g.drawImage(imgRecovering, x-r,y-r, 2 * r,2 * r);


        }else{
            g.setFill(color);
            g.fillOval(x-r,y-r, 2 * r,2 * r);
            g.drawImage(img, x-r,y-r, 2 * r,2 * r);
        }


        //Draw nuke Ready
        if( powerLevel == 4){
            g.setFill(Color.YELLOW);
            g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 22 ));
            g.fillText(" - NUKE READY -  ", 150, 50);
        }
    }


}
