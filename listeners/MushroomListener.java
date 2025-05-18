package listeners;

import model.Line;


/**
 * A MushroomListener interfész a gombatesteken bekövetkező változások figyelését valósítja meg.
 * Az interfész tartalmaz minden lehetséges eseményhez egy függvényt, amit a gombatest lefuttat, ha bekövetkezik.
*/
public interface MushroomListener {

    /**
     * Gombafonal növesztésekor meghívandó függvény.
     * @param line az adott gombafonal
     */
    public void lineGrew(Line line);


    /**
     * Gombatest fejlődésekor meghívandó függvény.
     */
    public void mushroomUpgraded();

    
    /**
     * Gombatetst megsemmisülésekor meghívandó függvény.
     */
    public void mushroomDestroyed();

}
