package Logic;


import Controller.GamePanelController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;



/**
 * Dette er Enemy-klassen, her blir fiendene tegnet opp med alle sine ulike verdier,
 * blant annet, posisjon, fart, liv, rank, type, om de er døde, og størrelse.
 *
 *
 * @author Muhammed Furkan Ergin s325881 / Pedram Rahdeirjoo s325906
 * */


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

    private int i = 0;

    private Image img;
    private Image asteroid1 = new Image("View/img/Asteroid.png");
    private Image asteroid2 = new Image("View/img/Asteroid2.png");
    private Image asteroid3 = new Image("View/img/Asteroid3.png");
    //private Image asteroid1 = new Image("View/img/Asteroid.png");


    //CONSTRUCTOR
    public Enemy(int type, int rank){
        this.type = type;
        this.rank = rank;


        //DEFAULT ENEMY TYPE
        if(type == 1){
            color1 = Color.TRANSPARENT;
            img = asteroid1;
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
            color1 = Color.TRANSPARENT;
            img = asteroid3;
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
            color1 = Color.TRANSPARENT;
            img = asteroid2;

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

        r= r*2;
        speed = speed * 0.6;

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



    /**
     *  Enemy Hit/Collisjon
     *  Her er Hit-metoden som ser om fienden treffer Player eller ikke, og hva som skjer
     *  om de blir truffet.
     *
     *  Hvis Fienden blir truffet vil Livet(Health) til Player minke med en for hver gang,
     *  hvis livet er null eller mindre vil variabelen "dead" bli true og Player blir død,
     *  deretter får du hit = true.
     *
     *
     * */
    public void hit(){
        health--;
        if(health <= 0){
            dead = true;
        }
        hit = true;
        hitTimer = System.nanoTime();

    }

    /**
     * Explode-metoden
     *Denne metoden får fiender til å splitte seg til mindre fiender.
     *
     *
     * Når en Fiende dør går den gjennom en if-test, for å se hvilken type den er i,
     * dersom den er i typen 1 til 3 vil de større fiendene spre seg til mindre fiender,
     * alt ettersom hvilken type de er i.
     *
     *
     *
     *
     * */

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

    /**
     *
     *
     * Oppdaterer fiendens posisjon.
     *
     * Kjører en if-test for å se om "slow" variabelen er aktivert, dersom den er aktivert beveger
     * fiendene seg saktere.
     *
     *
     * Får fiendene til å bevege på seg og har to if-tester som sørger for at fiendene
     * ikke går ut av banen. Dette har vi gjort ved å gruppe selve canvaset sin lengde og bredde.
     *  Dersom fiendene treffer banen vil deres x- og y-koordinater forandre seg til motsatt.
     *
     *
     *
     *
     *
     * */

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

    /**
     * Tegner Fiender
     *
     * Tegner inn fienden med bestemt farge og størrelse, deretter setter inn et bilde
     * over med samme størrelse og posisjon som sirkelen under.
     * */

    public void draw(GraphicsContext g){

        g.setFill(color1);
        g.fillOval(x-r,y-r, 2 * r,2 * r);
        g.drawImage(img, x-r,y-r, 2 * r,2 * r);

    }


}
