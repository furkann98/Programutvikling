package Logic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Tekst klassen
 *
 * Dette er tekst-klassen for powerups.
 *  Når powerUps blir plukket opp så skal det komme en tekst.
 *
 */

public class Text {


    //DATAFELT
    private double x;
    private double y;
    private long time;
    private String s;

    private long start;

    //CONSTRUCTOR

    /**
     * Konstruktør for teksten
     *
     * Setter tekst i riktig posisjon og varighet.
     *
     *
     * @param x posisjon
     * @param y posisjon
     * @param time hvor lang tid teksten skal vare.
     * @param s String tekst
     */
    public Text(double x, double y, long time, String s) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.s = s;
        start = System.nanoTime();
    }

    /**
     * Oppdaterer vargigheten til teksten.
     *
     * @return true/false  returnerer true når teksten er i spillet og false når den skal vekk.
     */
    public boolean update() {
        long elapsed = (System.nanoTime() - start) / 1000000;
        if (elapsed > time) {
            return true;
        }
        return false;
    }


    /**
     * Tegner inn teksten.
     * Tegner teksten i det punktet hvor powerupen blir plukket opp
     *
     * @param g GraphicsContext
     * @see Text kommer opp en tekst til samme posisjon hvor powerUps blir plukket opp.
     *
     */
    public void draw(GraphicsContext g) {
        g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12 ));
        g.setGlobalAlpha(0.4);
        g.setFill(Color.WHITE);
        g.fillText(s,(int)(x), (int)y);
        g.setGlobalAlpha(1);
    }
}


