package Logic;



import Controller.GamePanelController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Player klassen er avatarens klasse.
 *
 * Her blir alt av verdier implementert på et Datafelt.
 * posisjons-variabler, bevegelse-boolean, skyte-boolean, lyd-boolean og en liste for skuddstyrke.
 *
 * @author Muhammed Furkan Ergin s325881 / Pedram Rahdeirjoo s325906  */




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

    private Image img = new Image("/View/img/spaceShip.png");
    private Image imgRecovering = new Image("/View/img/spaceShip2.png");




    //KONSTRUKTØR

    /**
     * Konstruktør Player
     * Setter inn alle verdier for Player.
     * Setter inn posisjonen for spilleren, radius, farten, farge, og
     * skyte-variabler
     *
     */
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

    /**
     * En boolean metode som sender spillerens leve-status.
     * Blir aktivert dersom spilleren er død.
     * @return lives  returnerer at Player sine liv er null
     */
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

    /**
     * Metode som sørger for at spilleren mister et liv for hver kollisjon.
     *
     *Dersom en spiller blir truffet av en fiende blir denne metoden aktivert og fjerner et av
     * spillerens liv, setter spiller i en "recovery" mode.
     *
     *
     */
    public void loseLife(){
        lives--;
        recovering = true;
        recoveryTimer = System.nanoTime();
        livesSound = true;

    }

    /**
     *increasePower er en metode som øker powerLevel hver gang den en spiller plukker en opp.
     *
     * Sørger for at hver gang en spiller plukker opp en powerUp så øker den powerLevel til
     * Spilleren helt til den når maks PowerLevel som er 4.
     *
     * @param i integer som sørger for å øke med i
     */
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


    /**
     *Oppdaterer Spillerens posisjon og spillerens skudd
     *
     *
     * Har Booleans som sørger for at spillerens posisjon tilsvarer en retning og fart.
     * Den øker/minker gradvis hele tiden
     *
     * Har Laget if-tester som sørger for at den er alltid innenfor spillets lengde og bredde,
     * Kan aldri gå ut av banen.
     *
     *
     * Har fikset firing med en delay så det blir jevne mellomrom for hvert skudd.
     *
     * Har også gjort at dersom Player sin PowerLevel øker, økes også skuddene, og de vil skyte
     * flere skudd i flere vinkler, alt ettersom Player sin powerLevel
     *
     */
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


    /**
     *Metode som tegner Player
     *
     *
     * Denne metoden tegner player to ganger, den ene er når Player er på banen,
     * og den andre er når Player har blitt truffet av en fiende.
     *
     * Dersom en fiende treffer Player, vil Player gå i en "Recovering" mode som får player til å
     * bytte farge som indikerer at han har blitt truffet.
     *
     *
     *  Dersom PowerLevel er på sitt maks nivå, vil det komme opp en tekst på skjermen som sier at
     *  En "Nuke" er klar til å brukes.
     *
     *
     * @param g GraphicsContext
     */
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
