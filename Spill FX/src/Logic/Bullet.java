package Logic;

import Controller.GamePanelController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Dette er Bullet klassen, her blir den tegnet opp og har med kulens,
 * radius, fart og posisjon.
 *
 * Setter inn all informasjon i datafeltet, x-posisjon, y-posisjon, farten.
 * Setter alt gjennom bullet-konstruktøren, oppdaterer kulenes posisjon og setter dem gjennom
 * to if-tester som førger for at de ikke går ut av banens område(Width, og Height)
 *  Deretter tegner vi kulene som sirkler(oval) og setter inn farge.
 *
 *
 * @author Rjoo
 * */

public class Bullet {

    //DATAFELT
    private double x;
    private double y;
    private int r;

    private double dx;
    private double dy;
    private double rad;
    private double speed;

    private Color color;


    //KONSTRUKTØR

    public Bullet(double angle, int x, int y){
        this.x = x;
        this.y = y;
        r = 4;

        rad = Math.toRadians(angle);
        speed = 8;
        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;



        color = Color.LIGHTGREEN;
    }

    //FUNKSJON

    public double getx() {return x;}
    public double gety() {return y;}
    public double getr() {return r;}

    /**
     * Oppdaterer kulens posisjon.
     *  Dersom kulen går utenfor banens Lengde eller Bredde vil den ikke eksistere lenger.
     *
     *  @return  true/false   ettersom hva kulens posisjon er.
     *
     *
     *
     * */
    public boolean update(){
        x += dx;
        y += dy;

        if(x < -r || x > GamePanelController.WIDTH + r || y < -r || y > GamePanelController.HEIGHT + r ){
            return true;
        }
        return false;
    }

    /**
     * Her blir kullene tegnet
     *
     * Gir kulene fargen "color" som er fargen lysegrønn
     * og tegner dem som en sirket(oval) med en lengde og bredde.
     *
     * @author
     * */
    public void draw(GraphicsContext g){
        g.setFill(color);
        g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r );
    }




}
