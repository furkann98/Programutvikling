package Game;

import com.sun.javafx.tk.Toolkit;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

public class GamePanelFX extends Application implements Runnable {


    //DATAFELT

    //DATA FELT MÅ BLI STØRRE FOR VERTIKAL SPILL
    public static int WIDTH = 1000;
    public static int HEIGHT = 600;


    private Thread thread;
    private boolean running;

    //private BufferedImage image;
    private GraphicsContext g;

    private int FPS = 30;
    private double averageFPS;

    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy>enemies;
    public static ArrayList<PowerUp>powerups;
    public static ArrayList<Explosion>explosions;
    public static ArrayList<Text>texts;

    private long waveStartTimer;
    private long waveStartTimerDiff;
    private long waveNumber;
    private boolean waveStart;
    private int waveDelay = 2000;


    private long slowDownTimer;
    private long slowDownTimerDiff;
    private int slowDownLength = 6000; // 6 sekunder


    //FUNKSJON
    //@Override
    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    public void run(){
        running = true;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (GraphicsContext) image.getGraphics();


        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        powerups = new ArrayList<PowerUp>();
        explosions = new ArrayList<Explosion>();
        texts = new ArrayList<Text>();

        waveStartTimer = 0;
        waveStartTimerDiff = 0;
        waveStart = true;
        waveNumber = 0;



        long startTime;
        long URDTimeMillis;
        long waitTime;
        long totalTime = 0;

        int frameCount = 0;
        int maxFrameCount = 30;

        long targetTime = 1000 / FPS; // Tiden for en loop-runde

        //GAME LOOP
        while(running)  {

            startTime = System.nanoTime();


            gameUpdate();      // Positioning
            gameRender();       // off-screen image  , double buffering
            gameDraw();         //  gamescreen

            URDTimeMillis = (System.nanoTime() - startTime)/10000;
            waitTime = targetTime - URDTimeMillis;

            try{
                Thread.sleep(waitTime);
            }
            catch (Exception e) {
            }

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if (frameCount == maxFrameCount){
                averageFPS = 100.0 / ((totalTime / frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;

            }

        }

        g.setColor(new Color(0,100,255));
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Century Gothic", Font.PLAIN, 16));

        String s = "G A M E   O V E R ! ";
        int length = (int)g.getFontMetrics().getStringBounds(s,g).getWidth();
        g.drawString(s, (WIDTH - length)/2, HEIGHT/2);
        s = "Final Score: " + player.getScore();
        int length1 = (int)g.getFontMetrics().getStringBounds(s,g).getWidth();
        g.drawString(s, (WIDTH - length1)/2, HEIGHT/2+30);
        gameDraw();

    }

    private void gameUpdate(){

        //new Waves
        if(waveStartTimer == 0 && enemies.size() == 0){
            waveNumber++;
            waveStart = false;
            waveStartTimer = System.nanoTime();
        }else{
            waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000; // millisekunder
            if(waveStartTimerDiff > waveDelay){
                waveStart = true;
                waveStartTimer = 0;
                waveStartTimerDiff = 0;
            }
        }

        //Create enemies
        if(waveStart && enemies.size() == 0){
            createNewEnemies();
        }


        //Player update
        player.update();


        //Bullet update
        for (int i = 0; i < bullets.size(); i++){
            boolean remove = bullets.get(i).update();
            if(remove){
                bullets.remove(i);
                i--;
            }
        }
        //Enemy Update
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).update();
        }

        //PowerUp Update
        for(int i = 0; i < powerups.size(); i++){
            boolean remove = powerups.get(i).update();
            if(remove){
                powerups.remove(i);
                i--;
            }
        }

        //explosion update
        for(int i = 0; i < explosions.size(); i++){
            boolean remove = explosions.get(i).update();
            if(remove){
                explosions.remove(i);
                i--;
            }
        }


        //Text Update
        for(int i = 0; i < texts.size(); i++){
            boolean remove = texts.get(i).update();
            if(remove){
                texts.remove(i);
                i--;
            }
        }

        //Bullet-Enemy Collision
        for(int i = 0; i < bullets.size(); i++){
            Bullet b = bullets.get(i);
            double bx = b.getx();
            double by = b.gety();
            double br = b.getr();

            for(int j = 0; j < enemies.size(); j++){
                Enemy e = enemies.get(j);
                double ex = e.getx();
                double ey = e.gety();
                double er = e.getr();

                double dx = bx - ex;
                double dy = by - ey;
                double dist = Math.sqrt( dx * dx + dy * dy);

                if(dist < br + er){
                    e.hit();
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }

        //CHECK  DEAD ENEMIES
        for(int i = 0; i < enemies.size(); i++){

            if(enemies.get(i).isDead()){
                Enemy e  = enemies.get(i);

                // ROll FOR POWER UP
                double random = Math.random();
                if(random < 0.001){
                    powerups.add(new PowerUp(1, e.getx(), e.gety()));
                }else if(random < 0.020){
                    powerups.add(new PowerUp(2, e.getx(), e.gety()));
                }else if(random < 0.120){
                    powerups.add(new PowerUp(3, e.getx(), e.gety()));
                }else if(random < 0.130){
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
        if(player.isDead()){
            running = false;
        }

        //Player Enemy-Collision
        if(!player.isRecovering()){
            int px = player.getx();
            int py = player.gety();
            int pr = player.getr();

            for(int i = 0; i < enemies.size(); i++){

                Enemy e = enemies.get(i);
                double ex = e.getx();
                double ey = e.gety();
                double er = e.getr();

                double dx = px - ex;
                double dy = py - ey;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if(dist < pr + er){
                    player.loseLife();
                }
            }
        }

        //Player powerup-collision

        int px = player.getx();
        int py = player.gety();
        int pr = player.getr();
        for(int i = 0; i < powerups.size(); i++){
            PowerUp p = powerups.get(i);
            double x = p.getx();
            double y = p.gety();
            double r = p.getr();
            double dx = px-x;
            double dy = py-y;
            double dist = Math.sqrt(dx*dx+dy*dy);

            //Collected powerup
            if(dist < pr+r) {
                int type = p.getType();

                if(type == 1){
                    player.gainLife();
                    texts.add(new Text(player.getx(), player.gety(), 2000, "Extra life"));
                }
                if(type == 2){
                    player.increasePower(1);
                    texts.add(new Text(player.getx(), player.gety(), 2000, "Power"));
                }
                if(type == 3){
                    player.increasePower(2);
                    texts.add(new Text(player.getx(), player.gety(), 2000, "Double Power"));
                }
                if(type == 4){
                    slowDownTimer = System.nanoTime();
                    for(int t = 0; t < enemies.size(); t++){
                        enemies.get(t).setSlow(true);
                    }
                    texts.add(new Text(player.getx(), player.gety(), 2000, "Slow Down"));
                }

                powerups.remove(i);
                i--;
            }

        }


        //SlowDown Update
        if(slowDownTimer != 0){
            slowDownTimerDiff = (System.nanoTime() - slowDownTimer)/1000000;
            if(slowDownTimerDiff > slowDownLength){
                slowDownTimer = 0;
                for(int t = 0; t < enemies.size(); t++){
                    enemies.get(t).setSlow(false);
                }
            }

        }

    }

    private void gameRender(){

        // Draw Background
        g.setFill(new Color(0, 100,255));
        g.fillRect(0,0, WIDTH, HEIGHT);
        g.setFill(Color.BLACK);

        //Dette må fjernes
        /*
        g.drawString("FPS: " + averageFPS, 10, 10);
        g.drawString("Number of bullets: " + bullets.size(), 10, 20);
        g.drawString("Enemies: " +  enemies.size(), 10, 30);
         */

        //Draw slowDown Screen
        if(slowDownTimer != 0){
            g.setFill(new Color(255,255,255,64));
            g.fillRect(0,0,WIDTH, HEIGHT);
        }



        //Draw Player
        player.draw(g);

        //Draw Player
        for (int i = 0; i < bullets.size(); i++){
            bullets.get(i).draw(g);
        }

        //Draw Enemy
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(g);
        }


        //Draw PowerUps
        for(int i = 0; i < powerups.size(); i++){
            powerups.get(i).draw(g);
        }

        //Draw explosions
        for(int i = 0; i < explosions.size(); i++){
            explosions.get(i).draw(g);
        }

        //Draw Text
        for(int i = 0; i < texts.size(); i++){
            texts.get(i).draw(g);
        }



        //Draw Wave Number
        if(waveStartTimer != 0){
            g.setFont(Font.font("Century Gothic", 18));
            String s = "- W A V E " + waveNumber + " -";
            int length = (int) g.getFont().getSize(); // FEIL?

            //ALPHA?
            int alpha = (int) (255 * Math.sin(3.14 * waveStartTimerDiff / waveDelay));
            if(alpha > 255) alpha = 255;
            g.setFill(new Color(255, 255, 255, alpha));
            g.strokeText(s, WIDTH / 2 - length / 2, HEIGHT / 2);
        }

        //draw player lives
        for(int i = 0; i < player.getLives(); i++){
            g.setFill(Color.WHITE);
            g.fillOval( 20 + (20 * i), 20, player.getr() * 2, player.getr()*2);
            //g.setStrokeWidth(3); // FEIL?
            g.setFill(Color.WHITE.darker());
            g.fillOval(20 + (20 * i), 20, player.getr() * 2, player.getr()*2); // FEIL?
            //g.setStroke(new BasicStroke(1)); FEIL?
        }

        //draw player power
        g.setFill(Color.YELLOW);
        g.fillRect(20,40,player.getPower()*8,8);
        g.setFill(Color.YELLOW.darker());
        g.setStroke(Color.BLACK);
        for(int i = 0; i < player.getRequiredPower(); i++){
            Rectangle g = new Rectangle(20 + 8 * i, 40, 8,8);
        }
        //g.setStroke(new BasicStroke(1)); //FEIL?

        //Draw player score
        g.setFill(Color.WHITE);
        g.setFont(new Font ("Century Gothic", 14));
        g.strokeText("Score: " + player.getScore(), WIDTH - 100, 30); //FEIL?


        //Draw slowDown meter
        if(slowDownTimer != 0){
            g.setFill(Color.WHITE);
            g.fillRect(20,60,100,8); //FEIL?
            g.fillRect(20,60,(int)(100 - 100.0 * slowDownTimerDiff/slowDownLength), 8);
        }



    }

    private void gameDraw(){
        GraphicsContext g2 = canvas.getGraphicsContext2D(); // FEIL?
        g2.drawImage(image, 0,0,null);
        g2.dispose();
    }

    private void createNewEnemies(){
        enemies.clear();
        Enemy e;
        if(waveNumber == 1){
            for(int i = 0; i < 4; i++){
                enemies.add(new Enemy(1,1));
            }
        }
        if(waveNumber == 2){
            for(int i = 0; i < 8; i++){
                enemies.add(new Enemy(1,1));
            }
        }

        if(waveNumber == 3){
            for(int i = 0; i < 4; i++){
                enemies.add(new Enemy(1,1));
            }
            enemies.add(new Enemy(1,2));
            enemies.add(new Enemy(1,2));
        }
        if(waveNumber == 4){
            enemies.add(new Enemy(1,3));
            enemies.add(new Enemy(1,4));
            for(int i = 0; i < 4; i++){
                enemies.add(new Enemy(2,1));
            }
        }

        if(waveNumber == 5){
            enemies.add(new Enemy(1,4));
            enemies.add(new Enemy(1,3));
            enemies.add(new Enemy(2,3));
        }
        if(waveNumber == 6){
            enemies.add(new Enemy(1,3));
            for(int i = 0; i < 4; i++){
                enemies.add(new Enemy(2,1));
                enemies.add(new Enemy(3,1));
            }
        }
        if(waveNumber == 7){
            enemies.add(new Enemy(1,3));
            enemies.add(new Enemy(2,3));
            enemies.add(new Enemy(3,3));
        }
        if(waveNumber == 8){
            enemies.add(new Enemy(1,4));
            enemies.add(new Enemy(2,4));
            enemies.add(new Enemy(3,4));
        }
        if(waveNumber == 9){
            running = false;
        }

    }


    public void keyTyped(KeyEvent key){}

    public void keyPressed(KeyEvent key){
        
        if (key.getCode() == KeyCode.LEFT){
            player.setLeft(true);
        }
        if (key.getCode() == KeyCode.RIGHT){
            player.setRight(true);
        }
        if (key.getCode() == KeyCode.UP){
            player.setUp(true);
        }
        if (key.getCode() == KeyCode.DOWN){
            player.setDown(true);
        }
        if(key.getCode() == KeyCode.SPACE){
            player.setFiring(true);
        }



    }
    public void keyReleased(KeyEvent key){

        if (key.getCode() == KeyCode.LEFT){
            player.setLeft(false);
        }
        if (key.getCode() == KeyCode.RIGHT){
            player.setRight(false);
        }
        if (key.getCode() == KeyCode.UP){
            player.setUp(false);
        }
        if (key.getCode() == KeyCode.DOWN){
            player.setDown(false);
        }
        if(key.getCode() == KeyCode.SPACE){
            player.setFiring(false);
        }

    }




}
