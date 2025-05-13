package graphics;

import java.awt.Dimension;
import listeners.MushroomListener;
import model.Line;
import model.Mushroom;
import model.Spore;


/**
 * Az osztály a modellbeli gombatestek megjelenítéséért felelős. 
 * A játék során az osztály példányait a GTecton példányok fogják tartalmazni.
*/
public class GMushroom extends Image implements MushroomListener{
    
    /* - Publikus attribútumok*/
    private Mushroom myMushroom;        //A gombatest, amelyhez a grafikus elem tartozik

    /* - Konstruktor(ok)*/
    public GMushroom(Mushroom myMushroom) {
        super("graphics/images/mushroom.png"); //A gombatest képe
        this.myMushroom = myMushroom; //A gombatest referenciájának beállítása
        this.setPreferredSize(new Dimension(Map.CELL_SIZE,Map.CELL_SIZE));
    }


    /* - Getter/Setter metódusok*/
    

    /**
     * Visszaadja a garafikus gombatesthez tartozó elemet.
     *
     * @return a gombatest, amelyhez a grafikus elem tartozik
     */
    public Mushroom getMyMushroom() {
        return myMushroom;
    }

    /* - Egyéb metódusok*/

    /* - A grafikus gombatest megsemmisítésére alkalmas függvény.*/
    public void destroy() {
        //TODO: implementálni kell a gombatest megsemmisítését a grafikus felületen.
        
    }


    /* - MushroomListenert megvalósító metódusok*/


    /* - Gombafonál növesztésekor lefutó függvény, paraméterként átadja a kinövesztett gombafonalat.*/
    public void lineGrew(Line line) {
        //TODO: implementálni kell a gombafonal növesztéséért a grafikus felületen.

    }


    /* - Gombaspóra dobásánál lefutó metódus, mely paraméterként megkapja az eldobott gombaspóra referenciáját.*/
    public void sporeThrowed(Spore spore) {
        //TODO: implementálni kell a gombaspóra dobását a grafikus felületen.
    }


    /* - A gombatest elpusztulásakor lefutó metódus.*/
    public void mushroomDestroyed() {
        //TODO: implementálni kell a gombatest elpusztulását a grafikus felületen.
        this.destroy(); //A gombatest megsemmisítése a grafikus felületen.
    }

}
