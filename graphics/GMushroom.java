package graphics;

import listeners.MushroomListener;
import model.Spore;
import model.Line;


/**
 * Az osztály a modellbeli gombatestek megjelenítéséért felelős. 
 * A játék során az osztály példányait a GTecton példányok fogják tartalmazni.
*/
public class GMushroom extends Image implements MushroomListener{
    
    /* - Publikus attribútumok*/
    public String id;                   //A gombatesthez tartozó azonosító, amely alapján meg lehet találni a kontrollerben.
    public String playerId;             //Annak a játékosnak az azonosítója akihez a gombatest tartozik.

    /* - Konstruktor(ok)*/
    public GMushroom(String id, String playerId) {
        super("graphics/images/mushroom.png"); //A gombatest képe
        this.id = id;
        this.playerId = playerId;
    }


    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/

    /* - A grafikus gombatest megsemmisítésére alkalmas függvény.*/
    public void destroy() {
        //TODO: implementálni kell a gombatest megsemmisítését a grafikus felületen.
    }


    //MushroomListenert megvalósító metódusok


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
    }

}
