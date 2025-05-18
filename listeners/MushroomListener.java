package listeners;

import model.Line;


/**
 * A MushroomListener interfész a gombatesteken bekövetkező változások figyelését valósítja meg.
 * Az interfész tartalmaz minden lehetséges eseményhez egy függvényt, amit a gombatest lefuttat, ha bekövetkezik.
*/
public interface MushroomListener {

    /**
     * Gombatest fejlődésekor meghívandó függvény.
     */
    public void mushroomUpgraded();
}
