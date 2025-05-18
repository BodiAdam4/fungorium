package graphics;

import java.awt.Dimension;
import listeners.MushroomListener;
import model.Line;
import model.Mushroom;


/**
 * Az osztály a modellbeli gombatestek megjelenítéséért felelős. 
 * A játék során az osztály példányait a GTecton példányok fogják tartalmazni.
*/
public class GMushroom extends Image implements MushroomListener{
    
    /* - Publikus attribútumok*/

    /**
     * A gombatest, amelyhez a grafikus elem tartozik
     */
    private Mushroom myMushroom;        

    /* - Konstruktor(ok)*/
    /**
     * Konstruktor
     * @param myMushroom
     */
    public GMushroom(Mushroom myMushroom) {
        super("graphics/images/mushroom.png"); //A gombatest képe
        this.myMushroom = myMushroom; //A gombatest referenciájának beállítása
        this.setPreferredSize(new Dimension(Map.CELL_SIZE,Map.CELL_SIZE));
    }


    /* - Getter/Setter metódusok*/
    
    /**
     * Visszaadja a garafikus gombatesthez tartozó elemet.
     * @return a gombatest, amelyhez a grafikus elem tartozik
     */
    public Mushroom getMyMushroom() {
        return myMushroom;
    }

    /* - Egyéb metódusok*/

    /* - A grafikus gombatest megsemmisítésére alkalmas függvény.*/

    /* - MushroomListenert megvalósító metódusok*/
    
    /**
     * Gombaspóra dobásánál lefutó metódus, mely paraméterként megkapja az eldobott gombaspóra referenciáját.
     */
    public void mushroomUpgraded() {
        this.SetImage("graphics\\images\\mushroom_leaves.png");
        this.revalidate();
        this.repaint();
    }
}
