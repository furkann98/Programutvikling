package Logic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Dette er Eksplosjon klassen, her skjer animasjonen når en fiende dør.
 *
 *
 *
 *
 */
public class Explosion {

    //Datafelt
    private double x;
    private double y;
    private int r;
    private int maxRadius;


    //CONSTRUCTOR

    public Explosion(double x, double y, int r, int max){
        this.x = x;
        this.y = y;
        this.r = r;
        maxRadius = max;
    }

    /**
     * Denne metoden oppdaterer sirkelen(animasjonen) med lengde 2 for hver gang.
     * @return true/false returnerer verdien ettersom animasjonen starter/slutter
     */
    public boolean update(){
        r+=2;
        if(r >= maxRadius){
            return true;
        }
        return false;

    }

    /**
     * Tegner inn hvordan sirkelen skal bevege seg.
     *
     * @param g hvor det skal bli tegnet
     */
    public void draw(GraphicsContext g){
        g.setStroke(Color.WHITE);
        g.setGlobalAlpha(0.4);
        g.strokeOval((int) (x-r), (int)(y-r), 2*r, 2*r);
        g.setGlobalAlpha(1);

    }




}
