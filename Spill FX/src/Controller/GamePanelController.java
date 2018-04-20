package Controller;


import Game.*;
import View.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//ubrukte
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;




public class GamePanelController implements Initializable {


    //DATAFELT

    //Node
    @FXML private Canvas canvas;
    @FXML private Pane pane;

    private Image imgLife = new Image("View/img/Heart.png");

    //Map Size
    public static int WIDTH = 1000;
    public static int HEIGHT = 600;

    //GrapichsContext
    private GraphicsContext g;


    //Objects and arrays
    public static Player player = new Player();
    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();;
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();;
    public static ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();;
    public static ArrayList<Explosion> explosions = new ArrayList<Explosion>();;
    public static ArrayList<Text> texts = new ArrayList<Text>();;

    //Wave Start
    private long waveStartTimer;
    private long waveStartTimerDiff;
    private long waveNumber;
    private boolean waveStart;
    private int waveDelay = 3000; // 3 Sekunder

    //Slowdown power-up
    private long slowDownTimer;
    private long slowDownTimerDiff;
    private int slowDownLength = 6000; // 6 sekunder


    //Pause and gamover
    private boolean gameOver = false;
    private boolean pause = false;

    //Animation timer - Gameloop
    AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long now) {
            gameUpdate();      //Positioning
            gameRender();       //Image update
        }
    };



    @Override
    public void initialize(URL url, ResourceBundle rb) {


        //Keylistener
        canvas.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case UP:
                    player.setUp(true);
                    break;
                case DOWN:
                    player.setDown(true);
                    break;
                case LEFT:
                    player.setLeft(true);
                    break;
                case RIGHT:
                    player.setRight(true);
                    break;
                case SPACE:
                    player.setFiring(true);
                    break;
                case P:
                    if(gameOver == true){
                        System.out.println("Du har tapt, kan ikke trykke på pause");
                    }
                    else if(pause == true) {
                        pause = false;
                        gameLoop.start();
                    } else {
                        pause = true;
                        gameLoop.stop();
                    }
                    break;
                case Q:
                    gameOver = true;
                    gameLoop.stop();
                    gameOver();
                    break;
                case B:
                    if (player.getNuke()) {
                        player.setNukeTrue();
                    }
                    break;


            }
        });
        canvas.setOnKeyReleased(key -> {
            switch (key.getCode()) {
                case UP:
                    player.setUp(false);
                    break;
                case DOWN:
                    player.setDown(false);
                    break;
                case LEFT:
                    player.setLeft(false);
                    break;
                case RIGHT:
                    player.setRight(false);
                    break;
                case SPACE:
                    player.setFiring(false);
                    break;

            }
        });


        //Grapichscontext
        g = canvas.getGraphicsContext2D();

        //Startverdier
        waveStartTimer = 0;
        waveStartTimerDiff = 0;
        waveStart = true;
        waveNumber = 0;

        //Starts gameloop
        gameLoop.start();
    }

//METODER

    // GameOver
    private void gameOver(){

        //Background
        g.setFill(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);

        //Gameover Text
        g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 22 ));
        String s = "Y O U R    S C O R E :  " + player.getScore();
        g.setFill(Color.WHITE);
        g.fillText(s, WIDTH / 2 - textWidth(s), HEIGHT / 2);
        //Restart knapp

    }


        private void gameUpdate(){

            //new Waves
            if (waveStartTimer == 0 && enemies.size() == 0) {
                waveNumber++;
                waveStart = false;
                 waveStartTimer = System.nanoTime();
            } else {
                waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000; // millisekunder
                if (waveStartTimerDiff > waveDelay) {
                    waveStart = true;
                    waveStartTimer = 0;
                    waveStartTimerDiff = 0;
                }
            }

            //Create enemies
            if (waveStart && enemies.size() == 0) {
                createNewEnemies();
            }


            //Player update
            player.update();


            //Bullet update
            for (int i = 0; i < bullets.size(); i++) {
                boolean remove = bullets.get(i).update();
                if (remove) {
                    bullets.remove(i);
                    i--;
                }
            }
            //Enemy Update
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();
            }

            //PowerUp Update
            for (int i = 0; i < powerups.size(); i++) {
                boolean remove = powerups.get(i).update();
                if (remove) {
                    powerups.remove(i);
                    i--;
                }
            }

            //explosion update
            for (int i = 0; i < explosions.size(); i++) {
                boolean remove = explosions.get(i).update();
                if (remove) {
                    explosions.remove(i);
                    i--;
                }
            }


            //Text Update
            for (int i = 0; i < texts.size(); i++) {
                boolean remove = texts.get(i).update();
                if (remove) {
                    texts.remove(i);
                    i--;
                }
            }

            //Bullet-Enemy Collision
            for (int i = 0; i < bullets.size(); i++) {
                Bullet b = bullets.get(i);
                double bx = b.getx();
                double by = b.gety();
                double br = b.getr();

                for (int j = 0; j < enemies.size(); j++) {
                    Enemy e = enemies.get(j);
                    double ex = e.getx();
                    double ey = e.gety();
                    double er = e.getr();

                    double dx = bx - ex;
                    double dy = by - ey;
                    double dist = Math.sqrt(dx * dx + dy * dy);

                    if (dist < br + er) {
                        e.hit();
                        bullets.remove(i);
                        i--;
                        break;
                    }
                }
            }

            //CHECK  DEAD ENEMIES
            for (int i = 0; i < enemies.size(); i++) {

                if (enemies.get(i).isDead()) {
                    Enemy e = enemies.get(i);

                    // POWER UP - Sannsynlighet
                    double random = Math.random();
                    if (random < 0.021) {
                        powerups.add(new PowerUp(1, e.getx(), e.gety()));
                    } else if (random < 0.020) {
                        powerups.add(new PowerUp(2, e.getx(), e.gety()));
                    } else if (random < 1) {
                        powerups.add(new PowerUp(3, e.getx(), e.gety()));
                    } else if (random < 0.010) {
                        powerups.add(new PowerUp(4, e.getx(), e.gety()));
                    }


                    player.addScore(e.getType() + e.getRank());
                    enemies.remove(i);
                    i--;

                    e.explode();
                    explosions.add(new Explosion(e.getx(), e.gety(), e.getr(), e.getr() + 30));
                }
            }

            // Check dead Player
            if (player.isDead()) {
                gameOver = true;
            }

            //Player Enemy-Collision
            if (!player.isRecovering()) {
                int px = player.getx();
                int py = player.gety();
                int pr = player.getr();

                for (int i = 0; i < enemies.size(); i++) {

                    Enemy e = enemies.get(i);
                    double ex = e.getx();
                    double ey = e.gety();
                    double er = e.getr();

                    double dx = px - ex;
                    double dy = py - ey;
                    double dist = Math.sqrt(dx * dx + dy * dy);

                    if (dist < pr + er) {
                        player.loseLife();
                    }
                }
            }

            //Player powerup-collision

            int px = player.getx();
            int py = player.gety();
            int pr = player.getr();
            for (int i = 0; i < powerups.size(); i++) {
                PowerUp p = powerups.get(i);
                double x = p.getx();
                double y = p.gety();
                double r = p.getr();
                double dx = px - x;
                double dy = py - y;
                double dist = Math.sqrt(dx * dx + dy * dy);

                //Collected powerup
                if (dist < pr + r) {
                    int type = p.getType();

                    if (type == 1) {
                        player.gainLife();
                        texts.add(new Text(player.getx(), player.gety(), 2000, "Extra life"));
                    }
                    if (type == 2) {
                        player.increasePower(1);
                        texts.add(new Text(player.getx(), player.gety(), 2000, "Power"));
                    }
                    if (type == 3) {
                        player.increasePower(2);
                        texts.add(new Text(player.getx(), player.gety(), 2000, "Double Power"));
                    }
                    if (type == 4) {
                        slowDownTimer = System.nanoTime();
                        for (int t = 0; t < enemies.size(); t++) {
                            enemies.get(t).setSlow(true);
                        }
                        texts.add(new Text(player.getx(), player.gety(), 2000, "Slow Down"));
                    }

                    powerups.remove(i);
                    i--;
                }

            }


            //SlowDown Update
            if (slowDownTimer != 0) {
                slowDownTimerDiff = (System.nanoTime() - slowDownTimer) / 1000000;
                if (slowDownTimerDiff > slowDownLength) {
                    slowDownTimer = 0;
                    for (int t = 0; t < enemies.size(); t++) {
                        enemies.get(t).setSlow(false);
                    }
                }

            }

        }


        private void gameRender(){

            //Clear the last drawn objects
            g.clearRect(0,0, WIDTH, HEIGHT);

            //Draw Player
            player.draw(g);


            //Draw Bullets
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).draw(g);
            }

            //Draw Enemy
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g);
            }


            //Draw PowerUps
            for (int i = 0; i < powerups.size(); i++) {
                powerups.get(i).draw(g);
            }

            //Draw explosions
            for (int i = 0; i < explosions.size(); i++) {
                explosions.get(i).draw(g);
            }

            //Draw Text
            for (int i = 0; i < texts.size(); i++) {
                texts.get(i).draw(g);
            }


            //Draw Wave Number
            if(waveStartTimer != 0 && waveNumber == 1) {
                //Wave Number
                g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 22 ));
                String s = "- W A V E " + waveNumber + " -";
                g.setFill(Color.RED);
                g.fillText(s, WIDTH / 2 - textWidth(s) , HEIGHT / 2 - 60);

                //How to play
                g.setFont(Font.font("Verdana", FontPosture.REGULAR, 25 ));
                String s1 = "Move with ARROWS";
                g.setFill(Color.WHITE);
                g.fillText(s1, WIDTH / 2 - textWidth(s) / .7, HEIGHT / 2 + 90);

                g.setFont(Font.font("Verdana", FontPosture.REGULAR, 25 ));
                String s2 = "Shoot with SPACE";
                g.setFill(Color.WHITE);
                g.fillText(s2, WIDTH / 2 - textWidth(s) / .7, HEIGHT / 2 + 130);

                g.setFont(Font.font("Verdana", FontPosture.REGULAR, 25 ));
                String s3 = "Pause with P";
                g.setFill(Color.WHITE);
                g.fillText(s3, WIDTH / 2 - textWidth(s) / .7, HEIGHT / 2 + 170);


            }
            else if (waveStartTimer != 0) {
                //Wave Number
                g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 22 ));
                String s = "- W A V E " + waveNumber + " -";
                g.setFill(Color.RED);
                g.fillText(s, WIDTH / 2 - textWidth(s), HEIGHT / 2);
            }

              //draw player lives
            g.setFill(Color.WHITE);
            g.setFont(new Font("Century Gothic", 14));
            g.fillText("Life: ", 10, 25);
            for (int i = 0; i < player.getLives(); i++) {
                g.drawImage(imgLife, 60 + (20 * i),10, 20,20);


            }

            //draw player power
                 g.setFill(Color.WHITE);
                 g.setFont(new Font("Century Gothic", 14));
                 g.fillText("Power: ", 10, 50);
            for(int i = 0; i <player.getPowerLevel(); i++){
                g.setFill(Color.YELLOW);
                g.fillRect( 60 + (20 * i) , 40, 12, 12);
            }

            //Draw player score
            g.setFill(Color.WHITE);
            g.setFont(new Font("Century Gothic", 14));
            g.fillText("Score: " + player.getScore(), WIDTH - 100, 30);


            //Draw slowDown meter
            if (slowDownTimer != 0) {
                g.setFill(Color.WHITE);
                g.setGlobalAlpha(0.4);
                g.fillRect(20, 60, (int) (100 - 100.0 * slowDownTimerDiff / slowDownLength), 8);
            }else{
                g.setGlobalAlpha(1);
            }

        }

        //Calculate Length of text
        public double textWidth(String s){
            javafx.scene.text.Text text = new javafx.scene.text.Text(s);
            double width = text.getBoundsInLocal().getWidth();
            return width;
        }


    private void createNewEnemies(){
            enemies.clear();
            Enemy e;
            if (waveNumber == 1) {
                for (int i = 0; i < 4; i++) {
                    enemies.add(new Enemy(1, 1));
                }
            }
            if (waveNumber == 2) {
                for (int i = 0; i < 8; i++) {
                    enemies.add(new Enemy(1, 1));
                }
            }

            if (waveNumber == 3) {
                for (int i = 0; i < 4; i++) {
                    enemies.add(new Enemy(1, 1));
                }
                enemies.add(new Enemy(1, 2));
                enemies.add(new Enemy(1, 2));
            }
            if (waveNumber == 4) {
                enemies.add(new Enemy(1, 3));
                enemies.add(new Enemy(1, 4));
                for (int i = 0; i < 4; i++) {
                    enemies.add(new Enemy(2, 1));
                }
            }

            if (waveNumber == 5) {
                enemies.add(new Enemy(1, 4));
                enemies.add(new Enemy(1, 3));
                enemies.add(new Enemy(2, 3));
            }
            if (waveNumber == 6) {
                enemies.add(new Enemy(1, 3));
                for (int i = 0; i < 4; i++) {
                    enemies.add(new Enemy(2, 1));
                    enemies.add(new Enemy(3, 1));
                }
            }
            if (waveNumber == 7) {
                enemies.add(new Enemy(1, 3));
                enemies.add(new Enemy(2, 3));
                enemies.add(new Enemy(3, 3));
            }
            if (waveNumber == 8) {
                enemies.add(new Enemy(1, 4));
                enemies.add(new Enemy(2, 4));
                enemies.add(new Enemy(3, 4));
            }
            if (waveNumber == 9) {
                gameOver = true;
                gameLoop.stop();
            }

        }

}
