package graphics;

import java.util.List;

import javax.swing.JPanel;

import listeners.LineListener;
import model.Insect;

/**
 * Az osztály a modellbeli gombafonal megjelenítéséért felelős. 
 * A játék során az osztály példányait a Map példánya fogja tartalmazni.
*/
public class GLine extends JPanel implements LineListener   //Image?
{
    /* - Publikus attribútumok*/
    public String id;                   //A gombatesthez tartozó azonosító, amely alapján meg lehet találni a kontrollerben.
    //nem kéne? public String playerId;             //Annak a játékosnak az azonosítója akihez a gombatest tartozik.

    /* - Privát attribútumok*/
    //private BufferedImage line;
    //private BufferedImage endcap;
    private List<GTecton> ends;

    /* - Konstruktor(ok)*/
    public GLine(String _id){
        id = _id;
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


    //protected void paintComponent(Graphics g){}


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
}
