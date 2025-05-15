package listeners;

import model.Line;


/**
 * A MushroomListener interfész a gombatesteken bekövetkező változások figyelését valósítja meg.
 * Az interfész tartalmaz minden lehetséges eseményhez egy függvényt, amit a gombatest lefuttat, ha bekövetkezik.
*/
public interface MushroomListener {

    /* - Gombafonál növesztésekor lefutó függvény, paraméterként átadja a kinövesztett gombafonalat.*/
    public void lineGrew(Line line);


    /* - Gombaspóra dobásánál lefutó metódus, mely paraméterként megkapja az eldobott gombaspóra referenciáját.*/
    public void mushroomUpgraded();

    
    /* - A gombatest elpusztulásakor lefutó metódus.*/
    public void mushroomDestroyed();

}
