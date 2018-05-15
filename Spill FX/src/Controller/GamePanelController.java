package Controller;


import Logic.*;
import Logic.GameSave;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

//ubrukte
import javafx.scene.control.Button;

/**
 *
 * GamePanelController er controlleren til Gamepanel.fxml og implementerer Initializable
 *
 * Importerer canvas, HBox, VBox og Buttons fra gamepanel.fxml
 * Setter bane Størelsen som int verdier ( WIDTH og HEIGHT )
 * Implementerer GrapichsContext, Player og bullets, enemies, powerups, explosions og text i en ArrayList.
 * Implementerer wavestart, slowdown, pause, gameover, Image, Sound variabler som er nødvendig for spillets funskjoner.
 * Implementerer en ny Thread for bedre effektivitet og bedre ytelse, hvor alt av lydfiler blir satt inn
 * Setter inn verdier på variabler og starter funksjoner inn i initialize funskjonen, slik at spillet starter.
 * Implementerer hovedmetodene GameUpdate, GameRender, createNewEnemies, gameover, restart, filehandling, save og load.
 * Implementerer on-click funskjoner på alle knapper i gamePanel.fxml
 *
 * @author Muhammed Furkan Ergin s325881 / Pedram Rahdeirjoo s325906
 */
public class GamePanelController implements Initializable {


    //DATAFELT

    //FXML
    @FXML private Canvas canvas;
    @FXML private VBox pauseMenu;
    @FXML private HBox gameOverMenu;
    @FXML private HBox victoryMenu;
    @FXML private Button picPause;

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
    public void setPause(boolean b){
        this.pause = b;
    }

    //Lives Image
    private Image imgLife = new Image("/View/img/Heart.png");

    //File handling
    private GameSave save = new GameSave();
    private FileChooser filehandling = new FileChooser();

    //power-up collision
    private boolean powerUpCollect = false;

    //Sound files
    AudioClip gameoverSound = new AudioClip(getClass().getResource("/View/sound/gameover.mp3").toString());
    AudioClip powerUpSound = new AudioClip(getClass().getResource("/View/sound/power.mp3").toString());
    AudioClip shootSound = new AudioClip(getClass().getResource("/View/sound/pew.mp3").toString());
    AudioClip playerHitSound = new AudioClip(getClass().getResource("/View/sound/playerHit.mp3").toString());
    AudioClip backgroundSound = new AudioClip(getClass().getResource("/View/sound/background2.mp3").toString());


    //New thread for sound
    Thread soundThread = new Thread(new Runnable() {

        /**
         * Run metoden starter med thread`en og kjører Soundloopen i form av AnimationTimer
         *
         * Her blir alt av lydfiler spilt , når det skjer kolisjoner i en egen thread og loop.
         * Det gir "spill-logikken" mindre belastning da lydfilene kjøres i en egen thread.
         * Dette brukes for å unngå lag og skape bedre effektivitet og ytelse.
         */
        @Override
        public void run(){

            //Animation timer - SoundLoop
            AnimationTimer soundLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {

                    if (player.getFiringSound()) {
                        shootSound.play();
                        player.setFiringSound(false);
                    }
                    if (player.gameoverSound()){
                        gameoverSound.play();
                        player.setGameoverSound(false);
                    }
                    if(powerUpCollect){
                        powerUpSound.play();
                        powerUpCollect = false;
                    }
                    if(player.getLivesSound()){
                        playerHitSound.play();
                        player.setLivesSound(false);
                    }
                }
            };
            soundLoop.start();
        }
    });


        //Animation timer - Gameloop
        AnimationTimer gameLoop = new AnimationTimer() {

            /**
             * Handle skaper en timer som blir kjørt hver frame så lenge den er aktiv.
             *
             * Kjører (GameUpdate() og GameRender()) som setter sammen Logikken og kontrolleren i gameLoop.
             * gameUpdate(): er hvor alle objektenes siste posisjon, funksjoner, kolisjoner beregnes.
             * GameRender(): er hvor alle objekter tegnes i canvaset etter at de har blitt oppdatert.
             * if-test på om player har flere liv og om player er i siste wave.
             *
             *
             * @param now blir kalt inn i hver frame. Lar loopen få mulighet til å Stoppe og Starte.
             */

            @Override
            public void handle(long now) {
                gameUpdate();      //Positioning
                gameRender();       //Image update
                if (player.isDead()){
                    player.setGameoverSound(true);
                    gameOver();
                }
                if (waveNumber == 9) victory();
                if (waveNumber == 11) victoryBonus();
            }
        };


    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // backgroundSound.play();

        //Keylistener
        canvas.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case UP: case W:
                    player.setUp(true);
                    break;
                case DOWN: case S:
                    player.setDown(true);
                    break;
                case LEFT: case A:
                    player.setLeft(true);
                    break;
                case RIGHT: case D:
                    player.setRight(true);
                    break;
                case SPACE:
                    player.setFiring(true);
                    break;
                case B:
                    if (player.getNuke()) {
                        player.setNukeTrue();
                    }
                    break;
                case P:
                    if(gameOver == true){
                        System.out.println("Du har tapt, kan ikke trykke på pause");
                    }
                    else if(pause == true) {
                        pause = false;
                        gameLoop.start();
                    } else {
                        pauseMenu.setVisible(true);
                        drawPause();
                        picPause.setVisible(false);
                    }
                    break;
                case R:
                    restart();
                    break;
                case Q:
                    player.kill();
                    break;
                case T:
                }
            });

        canvas.setOnKeyReleased(key -> {
            switch (key.getCode()) {
                case UP: case W:
                    player.setUp(false);
                    break;
                case DOWN: case S:
                    player.setDown(false);
                    break;
                case LEFT: case A:
                    player.setLeft(false);
                    break;
                case RIGHT: case D:
                    player.setRight(false);
                    break;
                case SPACE:
                    player.setFiring(false);
                    break;

            }
        });

        //Background Music
        backgroundSound.stop();
        backgroundSound.play();
        backgroundSound.setCycleCount(1);


        //Grapichscontext
        g = canvas.getGraphicsContext2D();

        //Startverdier
        waveStartTimer = 0;
        waveStartTimerDiff = 0;
        waveStart = true;
        waveNumber = 0;

        //Starts gameloop
        gameLoop.start();

        //Starts thread for soundLoop
        soundThread.start();
    }



        //METODER


    /**
     * gameUpdate() metoden kjører i en AnimationTimer (60fps) "gameLoop",
     * og beregner hele tiden objektenes nye posisjoner og bevegelser.
     * Her blir Player, bullet, powerups, explosion, enemy og text oppdatert for hver "frame".
     *
     */
    private void gameUpdate(){

            //new Waves - with Wavedelay
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
                    if (waveNumber > 5 ){
                        if (random < 0.030 && player.getLives() < 5) {
                            powerups.add(new PowerUp(1, e.getx(), e.gety()));
                        } else if (random < 0.100) {
                            powerups.add(new PowerUp(2, e.getx(), e.gety()));
                        } else if (random < 0.130) {
                            powerups.add(new PowerUp(3, e.getx(), e.gety()));
                        } else if (random <0.145 ){
                            powerups.add(new PowerUp(4, e.getx(), e.gety()));
                        }
                    }else{
                        if (random < 0.030 && player.getLives() < 5) {
                            powerups.add(new PowerUp(1, e.getx(), e.gety()));
                        } else if (random < 0.150) {
                            powerups.add(new PowerUp(2, e.getx(), e.gety()));
                        } else if (random < 0.200) {
                            powerups.add(new PowerUp(3, e.getx(), e.gety()));
                        } else if (random <0.230 ){
                            powerups.add(new PowerUp(4, e.getx(), e.gety()));
                        }
                    }

                    player.addScore(e.getType() + e.getRank());
                    enemies.remove(i);
                    i--;

                    e.explode();
                    explosions.add(new Explosion(e.getx(), e.gety(), e.getr(), e.getr() + 30));
                }
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

                    powerUpCollect = true;
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

            //pause
            if(pause == false) {
                pauseMenu.setVisible(false);
                picPause.setVisible(true);
            }
            if(pause == true) {
                picPause.setVisible(false);
            }

        }


    /**
     * Dette er gameRender Metoden,
     * Dette er metoden hvor alt blir tegnet inn i canvaset på nytt, etter å ha blitt oppdatert i gameUpdate().
     * Først tommer den canvasen, og legger til alle nye objekter ved å bruke grapichscontext.
     * Her Tegner den Player, Bullets, Enemy, PowerUps, Explosions, Og all form av Text.
     *
     */

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
                String s2 = "Shoot with SPACE & B";
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
            for(int i = 0; i <player.getPowerLevel() -  1; i++){
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
                g.setGlobalAlpha(0.08);
                g.fillRect(0,0,WIDTH,HEIGHT);
                g.setGlobalAlpha(1);
                g.fillRect(20, 80, (int) (100 - 100.0 * slowDownTimerDiff / slowDownLength), 8);
            }

        }


    /**
     * Metoden som regner ut bredden til en String variabel.
     * Brukes til å midtstille Stringen på skjermen når det er nødvendig.
     *
     * @param s er en String variabel
     * @return lengden til s i double
     */
    //Calculate Length of text
        public double textWidth(String s){
            javafx.scene.text.Text text = new javafx.scene.text.Text(s);
            double width = text.getBoundsInLocal().getWidth();
            return width;
        }


    /**
     * Metoden som lager nye waves
     * metoden sjekker nåværende waveNumber og legger inn ferdigstilte fiender.
     * fiendene blir laget i enemies objektet og importert til canvasen.
     */
    //Create enemies
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
                }
                if(waveNumber == 10){
                    enemies.add(new Enemy(1, 3));
                    for (int i = 0; i < 15; i++) {
                        enemies.add(new Enemy(1, 1));
                    }
                    for (int i = 0; i < 8; i++) {
                        enemies.add(new Enemy(2, 1));
                        enemies.add(new Enemy(3, 1));
                    }
                    enemies.add(new Enemy(1, 4));
                    enemies.add(new Enemy(2, 4));
                    enemies.add(new Enemy(3, 4));
                    enemies.add(new Enemy(1, 3));
                    enemies.add(new Enemy(2, 3));
                    enemies.add(new Enemy(3, 3));
                }

            }


    /**
     * Metoden restart starter spillet på nytt.
     * metoden nullstiller alle variabler og tømmer alle lister, slik at man starter der man først begynte.
     */
    //Restart
    private void restart(){
            //Objects and arrays
            player = new Player();
            bullets = new ArrayList<Bullet>();;
            enemies = new ArrayList<Enemy>();;
            powerups = new ArrayList<PowerUp>();;
            explosions = new ArrayList<Explosion>();;
            texts = new ArrayList<Text>();;

            //Pause and gamover
            gameOver = false;
            pause = false;
            gameOverMenu.setVisible(false);
            victoryMenu.setVisible(false);

            //Startverdier
            waveStartTimer = 0;
            waveStartTimerDiff = 0;
            waveStart = true;
            waveNumber = 0;
        }

    /**
     * metoden gameover ender spillet.
     * metoden gameover kjøres når spillerens liv er lik null.
     * når livet er lik null, blir skjermen svart, samtidig som den gjør gameover-menyen synlig
     */

    // GameOver
        private void gameOver(){
            player.kill();

            picPause.setVisible(false);

            //Background
            g.setFill(Color.BLACK);
            g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

            //Gameover Text
            g.setFill(Color.WHITE);
            g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 30 ));
            String s = "G A M E  O V E R ";
            g.fillText(s, canvas.getWidth() / 2 - textWidth(s) - 30, canvas.getHeight() / 3 );
            g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 22 ));
            String s1 = "Y O U R    S C O R E :  " + player.getScore();
            g.fillText(s1, canvas.getWidth() / 2 - textWidth(s) - 30, canvas.getHeight() / 2);

            //Stops the loop
            gameOver = true;
            gameLoop.stop();
            //Sound


            gameOverMenu.setVisible(true);
        }


    /**
     * Metoden victory kjøres når man vinner.
     * metoden tegner en svart skjerm og gir en gratulasjonstekst til brukeren, med antall poeng.
     * gjør victory menyen synlig.
     */
    // Victory
        private void victory(){

            //Background
            g.setFill(Color.BLACK);
            g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

            //Victory Text
            g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25 ));
            String s = "V I C T O R Y !  Y o u r  s c o r e : " + player.getScore();
            g.setFill(Color.WHITE);
            g.fillText(s, canvas.getWidth() / 2 - textWidth(s), canvas.getHeight() / 2);

            //Stops the loop
            gameOver = true;
            gameLoop.stop();


            victoryMenu.setVisible(true);
        }

    /**
     * Metoden victoryBonus kjøres når man vinner bonusspillet.
     * metoden tegner en svart skjerm og gir en gratulasjonstekst til brukeren, med antall poeng.
     * gjør victory menyen synlig.
     */
    // Victory Bonus game
        private void victoryBonus(){

            //Background
            g.setFill(Color.BLACK);
            g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

            //Victory Text
            g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25 ));
            String s = "CONGRATULATIONS!! You completed the bonus wave!";
            g.setFill(Color.WHITE);
            g.fillText(s, canvas.getWidth() / 2 - textWidth(s), canvas.getHeight() / 2);

            //Stops the loop
            gameOver = true;
            gameLoop.stop();


            victoryMenu.setVisible(true);
        }


    /**
     * Metoden load laster inn lagrede spill.
     * metoden tar inn variabler som man får fra en textfil, og får spillet til å starte der man slapp.
     * bytter variablene i spillet med de tilsvarende parameterne
     *
     * @param lives int variabel som tilsvarer livet til avataren
     * @param score int variabel som tilsvarer poengscore til avatar
     * @param wave int varibale som tilsvarer wavenumber i spillet
     * @param power int variabel som tilsvarer power til avatar
     */

        //File handling
        public void load(int lives, int score, int wave, int power){
            //Objects and arrays
            player = new Player();
            bullets = new ArrayList<Bullet>();;
            enemies = new ArrayList<Enemy>();;
            powerups = new ArrayList<PowerUp>();;
            explosions = new ArrayList<Explosion>();;
            texts = new ArrayList<Text>();;

            player.setLives(lives);
            player.setScore(score);
            player.setPower(power);

            //Pause and gamover
            gameOver = false;
            pause = false;

            //Startverdier
            waveStartTimer = 0;
            waveStartTimerDiff = 0;
            waveStart = true;
            waveNumber = wave - 1;

            pause = false;
        }

    /**
     * metoden pause stopper spillet og alle objekter.
     * Stopper gameloopen som få bevegelser i gang og får opp en pausemeny.
     * starter spillet igjen når boolean variabelen: pause = false.
     */
        //Draw pause
        public void drawPause(){
            pauseMenu.setVisible(true);
            gameLoop.stop();
            pause = true;


            g.setGlobalAlpha(0.76);
            g.setFill(Color.BLACK);
            g.fillRect(0.0, 0.0,WIDTH,HEIGHT);

            g.setGlobalAlpha(1);
            g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40 ));
            String s = "P A U S E";
            g.setFill(Color.WHITE);
            g.fillText(s, canvas.getWidth() / 2 - textWidth(s)*2 + 10, canvas.getHeight() / 6);

        }

    /**
     * Metode som fortsetter spiller når den er satt i pause.
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    //Pause Buttons
        public void continueBtn(javafx.event.ActionEvent event) throws IOException {
            pause = false;
            gameLoop.start();
        }


    /**
     * Metode som restarter spiller når den er satt i pause
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void restartBtn(javafx.event.ActionEvent event) throws IOException {
            restart();
            gameLoop.start();
        }

    /**
     * metode som saver spillet når den er satt i pause
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void saveBtn(javafx.event.ActionEvent event) throws IOException {

        File file = filehandling.showSaveDialog(null);
        if (file!= null) {
            try {
                save.makeFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            save.save(player, (int) waveNumber);
        }

    }

    /**
     * Metode som loader spillet når den er satt i pause
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */


    public void loadBtn(javafx.event.ActionEvent event) throws IOException {

        //filehandling.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = filehandling.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int lives = 0;
                int score = 0;
                int wave = 0;
                int power = 0;
                int i = 0;
                String line;
                while ((line = reader.readLine()) != null && line.matches("^[0-9]*$")) {
                    i++;
                    if (i == 1) {
                        lives = Integer.parseInt(line);
                    }
                    if (i == 2) {
                        score = Integer.parseInt(line);
                    }
                    if (i == 3) {
                        wave = Integer.parseInt(line);
                    }
                    if (i == 4) {
                        power = Integer.parseInt(line);
                    }
                }
                if (i != 4){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The file is corrupt, please try another file", ButtonType.OK);
                    alert.showAndWait();
                } else{
                    load(lives, score, wave, power);
                    pause = false;
                    gameLoop.start();
                }

            } catch (IOException e) {
                //  e.printStackTrace();
            }

        }
    }


    /**
     * metode som går tilbake til startsiden, når spillet er i pause
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */
    public void mainBtn(javafx.event.ActionEvent event) throws IOException {
                restart();
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/mainPage.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            }


    /**
     * metode som setter spillet i pause
     *
     * @param event bruker onClick event på knappen
     * @throws IOException kaster IOExeption ved input og output
     */

        public void pauseBtn(javafx.event.ActionEvent event) throws IOException {
                drawPause();
                picPause.setVisible(false);
        }


}
