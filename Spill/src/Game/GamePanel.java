package Game;

import  javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.util.*;


public class GamePanel extends JPanel implements Runnable, KeyListener {

    //DATAFELT

    //DATA FELT MÅ BLI STØRRE FOR VERTIKAL SPILL
    public static int WIDTH = 400;
    public static int HEIGHT = 400;


    private Thread thread;
    private boolean running; 

    private BufferedImage image;
    private Graphics2D g;
    
    private int FPS = 30;
    private double averageFPS;

    public static Player player;
    public static ArrayList<Bullet>bullets;
    public static ArrayList<Enemy>enemies;
    public static ArrayList<PowerUp>powerups;

    private long waveStartTimer;
    private long waveStartTimerDiff;
    private long waveNumber;
    private boolean waveStart;
    private int waveDelay = 2000;


    //Konstruktør
    public GamePanel(){
        super();
        setPreferredSize(new Dimension( WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

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
        g = (Graphics2D) image.getGraphics();

        //Graphics
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Texts
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        /*for(int i = 0; i < 5; i++){
            enemies.add(new Enemy(1,1));
        }
        */
        powerups = new ArrayList<PowerUp>();


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
                if(random < 0.5){
                    powerups.add(new PowerUp(1, e.getx(), e.gety()));
                }else if(random < 0.6){
                    powerups.add(new PowerUp(2, e.getx(), e.gety()));
                    
                }else if(random < 0.9){
                    powerups.add(new PowerUp(3, e.getx(), e.gety()));
                }

                //else powerups.add(new PowerUp(2, e.getx(), e.gety()));

                 
                player.addScore(e.getType() + e.getRank());
                enemies.remove(i);
                i--;
            }
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
                }
                if(type == 2){
                    player.increasePower(1);
                }
                if(type == 3){
                    player.increasePower(2);
                }

                powerups.remove(i);
                i--;
            }

        }



    }

    private void gameRender(){

        // Draw Background
        g.setColor(new Color(102, 178,255));
        g.fillRect(0,0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);

        //Dette må fjernes
        /*
        g.drawString("FPS: " + averageFPS, 10, 10);
        g.drawString("Number of bullets: " + bullets.size(), 10, 20);
        g.drawString("Enemies: " +  enemies.size(), 10, 30);
         */

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



        //Draw Wave Number
        if(waveStartTimer != 0){
            g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
            String s = "- W A V E " + waveNumber + " -";
            int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            //ALPHA?
            int alpha = (int) (255 * Math.sin(3.14 * waveStartTimerDiff / waveDelay));
            if(alpha > 255) alpha = 255;
            g.setColor(new Color(255, 255, 255, alpha));
            g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2);
        }

        //draw player lives
        for(int i = 0; i < player.getLives(); i++){
            g.setColor(Color.WHITE);
            g.fillOval( 20 + (20 * i), 20, player.getr() * 2, player.getr()*2);
            g.setStroke(new BasicStroke(3));
            g.setColor(Color.WHITE.darker());
            g.drawOval(20 + (20 * i), 20, player.getr() * 2, player.getr()*2);
            g.setStroke(new BasicStroke(1));
        }

        //draw player power
        g.setColor(Color.YELLOW);
        g.fillRect(20,40,player.getPower()*8,8);
        g.setColor(Color.YELLOW.darker());
        g.setStroke(new BasicStroke(2));
        for(int i = 0; i < player.getRequiredPower(); i++){
            g.drawRect(20 + 8 * i, 40, 8,8);
        }
        g.setStroke(new BasicStroke(1));

        //Draw player score
        g.setColor(Color.WHITE);
        g.setFont(new Font ("Century Gothic", Font.PLAIN, 14));
        g.drawString("Score: " + player.getScore(), WIDTH - 100, 30);

        

    }
    
    private void gameDraw(){
         Graphics g2 = this.getGraphics();
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
                enemies.add(new Enemy(2,1));
            }
            for(int i = 0; i < 4; i++){
                enemies.add(new Enemy(3,1));
            }

        }
    }


    public void keyTyped(KeyEvent key){}

    public void keyPressed(KeyEvent key){
        int keyCode = key.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT){
            player.setLeft(true);
        }
        if (keyCode == KeyEvent.VK_RIGHT){
            player.setRight(true);
        }
        if (keyCode == KeyEvent.VK_UP){
            player.setUp(true);
        }
        if (keyCode == KeyEvent.VK_DOWN){
            player.setDown(true);
        }
        if(keyCode == KeyEvent.VK_SPACE){
            player.setFiring(true);
        }



    }
    public void keyReleased(KeyEvent key){
         int keyCode = key.getKeyCode();

         if (keyCode == KeyEvent.VK_LEFT){
             player.setLeft(false);
         }                                 
         if (keyCode == KeyEvent.VK_RIGHT){
             player.setRight(false);
         }                                 
         if (keyCode == KeyEvent.VK_UP){   
             player.setUp(false);
         }                                 
         if (keyCode == KeyEvent.VK_DOWN){ 
             player.setDown(false);
         }
        if(keyCode == KeyEvent.VK_SPACE){
            player.setFiring(false);
        }

    }


}
