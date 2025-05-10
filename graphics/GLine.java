package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import listeners.LineListener;
import model.Insect;

/**
 * Az osztály a modellbeli gombafonal megjelenítéséért felelős. 
 * A játék során az osztály példányait a Map példánya fogja tartalmazni.
 */
public class GLine extends JPanel implements LineListener
{
    /* - Publikus attribútumok*/
    public String id;                   //A gombatesthez tartozó azonosító, amely alapján meg lehet találni a kontrollerben.

    /* - Privát attribútumok*/
    
    public BufferedImage lineMidle;
    public BufferedImage endCapStart;
    public BufferedImage endCapEnd;

    public Point startPoint;
    public Point endPoint;
    public int curveHeight = 50;

    private List<GTecton> ends;

    /* - Konstruktor(ok)*/
    public GLine(String _id){
        id = _id;
    }

    
    public GLine(GTecton start, GTecton end) {
        ends = new ArrayList<>();
        ends.add(start);
        ends.add(end);

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setBackground(Color.WHITE);
        try {
            lineMidle = ImageIO.read(new File("graphics\\images\\LineMiddle.png"));
            endCapStart = ImageIO.read(new File("graphics\\images\\LineLeft.png"));
            endCapEnd = ImageIO.read(new File("graphics\\images\\LineRight.png"));
        } catch (IOException e) {
            System.err.println("Line image not found");
        }
    }


    public GLine(String _id, List<GTecton> _ends){
        id = _id;
        setEnds(_ends);
    }


    /* - Getter/Setter metódusok*/
    public String getId(){
        return id;
    }


    public List<GTecton> getEnds(){
        return ends;
    }


    public void setEnds(List<GTecton> _ends){
        if(_ends.size() == 2)
            ends = _ends;
    }


    /*Egyéb metódusok */

    /**
     * A grafikus gombafonal megsemmisítésére alkalmas függvény.
     */
    public void destroy() {
        //TODO: GLine.destroy()
    }



    /* - LineListenert megvalósító metódusok */

    /** 
     * Rovar elfogyasztásakor lefutó függvény
     * @param insect a megevett rovar
     */
    public void insectEaten(Insect insect){
        //TODO: insectEaten(Insect insect)
    }


    /**
     * A gombafonal elpusztulásakor lefutó függvény
     */
    public void lineDestroyed(){
        //TODO: lineDestroyed()
    }


    /** 
     * A gombafonal fázisainak változása esetén fut le a függvény. 
     * Három különböző fázis követése lehetséges,amit a paraméterként adott szám mutat meg. 
     * Ha a phase = 1, akkor a gombafonal kinőtt, ha 2 akkor a gombafonal elkezdett haldokolni, és ha 0 akkor még csak most kezdett el kinőni.
     * @param phase a fázist mutató szám
     */
    public void phaseChange(int phase){
        //TODO: phaseChanged(int phase)
    }

    //TODO: Új függvény
    public void setEndPoints(Point startPoint, Point endPoint) {
        this.endPoint = endPoint;
        this.startPoint = startPoint;
    }

    //TODO: Új függvény
    public Point generateControlPoint(Point p1, Point p2, double offset) {
        // Középpont
        int midX = (p1.x + p2.x) / 2;
        int midY = (p1.y + p2.y) / 2;

        // Vektor a két pont között
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;

        // Normálvektor (merőleges vektor), egységesítve
        double length = Math.sqrt(dx*dx + dy*dy);
        double nx = -dy / length;
        double ny = dx / length;

        // Kontrollpont eltolása a merőleges irányban
        int ctrlX = (int)(midX + nx * offset);
        int ctrlY = (int)(midY + ny * offset);

        return new Point(ctrlX, ctrlY);
    }

    //TODO: Új függvény
    public static Color darken(Color color, double factor) {
        factor = Math.max(0.0, Math.min(1.0, factor)); // biztos, hogy 0..1 között van
        int r = (int)(color.getRed() * factor);
        int g = (int)(color.getGreen() * factor);
        int b = (int)(color.getBlue() * factor);
        return new Color(r, g, b);
    }

    //TODO: Új függvény
    public void stepCurve() {
        curveHeight += 20;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point controlPoint = generateControlPoint(startPoint, endPoint, curveHeight);
        
        Color color = this.getBackground();

        // Sötétített vonal
        g2d.setStroke(new BasicStroke(6));
        g2d.setColor(darken(color, 0.7));

        QuadCurve2D q = new QuadCurve2D.Float();
        q.setCurve(startPoint, controlPoint, endPoint);
        g2d.draw(q);

        // Világosabb vonal
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(color);

        q = new QuadCurve2D.Float();
        q.setCurve(startPoint, controlPoint, endPoint);
        g2d.draw(q);

    }
}
