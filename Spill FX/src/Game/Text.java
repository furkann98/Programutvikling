package Game;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Text {


    //DATAFELT
    private double x;
    private double y;
    private long time;
    private String s;

    private long start;

    //CONSTRUCTOR
    public Text(double x, double y, long time, String s) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.s = s;
        start = System.nanoTime();
    }


    public boolean update() {
        long elapsed = (System.nanoTime() - start) / 1000000;
        if (elapsed > time) {
            return true;

        }
        return false;
    }

    public void draw(GraphicsContext g) {


        g.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12 ));
        long elapsed  = (System.nanoTime() - start) / 1000000;
        //int alpha= (int)(255 * Math.sin(3.14 * elapsed/time ));
        g.setGlobalAlpha(0.4);
        g.setFill(Color.WHITE);
       // int length = (int)g.getFontMetrics().getStringBounds(s,g).getWidth();
        g.fillText(s,(int)(x), (int)y);
        g.setGlobalAlpha(1);
    }

}


