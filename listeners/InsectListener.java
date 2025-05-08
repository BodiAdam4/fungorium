package listeners;
import model.Tecton;


/**
 * Az InsectListener interfész a rovarokon bekövetkező változások figyelését valósítja meg. 
 * Az interfész tartalmaz minden lehetséges eseményhez egy függvényt, amit a rovar lefuttat, ha bekövetkezik.
*/
public interface InsectListener {

    /* - Azon metódus, amely akkor hívodik meg ha egy rovar elkezd mozogni.*/
    public void moveStarted(Tecton from, Tecton to);

    /* - Azon metódus, amely akkor hívódik meg ha egy rovar befejezte a mozgását */
    public void moveFinished(Tecton to);

    /* - Azon metódus, amely akkor hívódik meg ha egy rovar elfogyaszt egy spórát */
    public void sporeEaten(String effect);

    /* - Azon metódus, amely akkor hívódik meg ha egy spóra hatása lejár egy rovaron */
    public void effectReseted();

    /* - Azon metódus, amely akkor hívódik meg amikor egy rovar elpusztul */
    public void insectDestroyed();
}
