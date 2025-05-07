package listeners;

import model.Insect;


/**
 * A LineListener interfész a gombafonalakon bekövetkező változások figyelését valósítja meg. 
 * Az interfész tartalmaz minden lehetséges eseményhez egy függvényt, amit a gombafonal lefuttat, ha bekövetkezik.
*/
public interface LineListener {

    /* - Azon metódus, amely egy rovar elfogyasztása után hívódik meg.*/
    public void insectEaten(Insect insect);

    
    /* - A gombafonal elpusztulásakor meghívódó metódus.*/
    public void lineDestroyed();


    /* - A gombafonal fázisainak változása esetén fut le a függvény. Három különböző fázis követése lehetséges,amit a paraméterként adott szám mutat meg. Ha a phase = 1, akkor a gombafonal kinőtt, ha 2 akkor a gombafonal elkezdett haldokolni, és ha 0 akkor még csak most kezdett el  kinőni.*/
    public void phaseChange(int phase);
}
