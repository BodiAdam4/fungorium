package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import listeners.LineListener;
import model.Line;

/**
 * Az osztály a modellbeli gombafonal megjelenítéséért felelős. 
 * A játék során az osztály példányait a Map példánya fogja tartalmazni.
 */
public class GLine extends JPanel implements LineListener
{
    /* - Publikus attribútumok*/

    /**
     * A gombatesthez tartozó azonosító, amely alapján meg lehet találni a kontrollerben.
     */
    public String id;                   

    public Point startPoint;
    public Point endPoint;
    public int curveHeight = 50;

    /* - Privát attribútumok*/

    private Line myLine;
    private List<GTecton> ends;
    private Map map;
    private double darkenFactor = 0.0;

    private Point controlPoint;

    /* - Konstruktor(ok)*/
    
    /**
     * Konstruktor
     * @param start
     * @param end
     * @param line
     * @param map
     */
    public GLine(GTecton start, GTecton end, Line line, Map map) {
        ends = new ArrayList<>();
        ends.add(start);
        ends.add(end);
        myLine = line;
        this.map = map;

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setBackground(Color.WHITE);
    }


    /* - Getter/Setter metódusok*/
    public String getId(){
        return id;
    }


    public List<GTecton> getEnds(){
        return ends;
    }

    public Line getMyLine() {
        return myLine;
    }


    public void setEndPoints(Point startPoint, Point endPoint) {
        this.endPoint = endPoint;
        this.startPoint = startPoint;
    }


    /*Egyéb metódusok */

    /**
     * A grafikus gombafonal megsemmisítésére alkalmas függvény.
     */
    public void destroy() {
        map.removeLine(myLine);
    }


    /* - LineListenert megvalósító metódusok */

    /**
     * A gombafonal elpusztulásakor lefutó függvény
     */
    public void lineDestroyed(){
        destroy();
    }


    /** 
     * A gombafonal fázisainak változása esetén fut le a függvény. 
     * Három különböző fázis követése lehetséges,amit a paraméterként adott szám mutat meg. 
     * Ha a phase = 1, akkor a gombafonal kinőtt, és ha 2 akkor a gombafonal elkezdett haldokolni.
     * @param phase a fázist mutató szám
     */
    public void phaseChange(int phase){
        switch (phase) {
            case 1:
                darkenFactor = 0.0;
                break;
            case 2:
                darkenFactor = 0.6;
                break;
            default:
                break;
        }
        this.repaint();
    }


    /**
     * Kontrolpont előállítása két pont között.
     * @param p1 az első pont
     * @param p2 a második pont
     * @param offset eltolás mennyisége
     * @return a kapott kontrolpont
     */
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


    /**
     * Szín sötétítése
     * @param color a sötétítendő szín
     * @param factor a sötétítés erőssége
     * @return a kapott szín
     */
    public static Color darken(Color color, double factor) {
        factor = Math.max(0.0, Math.min(1.0, factor)); // biztos, hogy 0..1 között van
        int r = (int)(color.getRed() * factor);
        int g = (int)(color.getGreen() * factor);
        int b = (int)(color.getBlue() * factor);
        return new Color(r, g, b);
    }


    /**
     * Segédfüggvény görbe beállításához
     */
    public void stepCurve() {
        curveHeight += 20;
    }


    /**
     * Kontrolpont előállítása a gombafonalhoz
     * @return a kontrolpont
     */
    public Point getMiddlePoint() {
        Point start = startPoint;
        Point control = controlPoint;
        Point end = endPoint;

        double midX = 0.25 * start.getX() + 0.5 * control.getX() + 0.25 * end.getX();
        double midY = 0.25 * start.getY() + 0.5 * control.getY() + 0.25 * end.getY();

        return new Point((int)midX, (int)midY);
    }


    @Override
    protected void paintComponent(Graphics g) {

        if (myLine.ttl != -1) {
            darkenFactor = 0.6;
        }
        else {
            darkenFactor = 0.0;
        }

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        controlPoint = generateControlPoint(startPoint, endPoint, curveHeight);
        
        Color color = this.getBackground();

        // Sötétített vonal
        g2d.setStroke(new BasicStroke(6));
        g2d.setColor(darken(color, 0.7 - darkenFactor));

        QuadCurve2D q = new QuadCurve2D.Float();
        q.setCurve(startPoint, controlPoint, endPoint);
        g2d.draw(q);

        // Világosabb vonal
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(darken(color, 1.0 - darkenFactor));

        q = new QuadCurve2D.Float();
        q.setCurve(startPoint, controlPoint, endPoint);
        g2d.draw(q);
    }
}
