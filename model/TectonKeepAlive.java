package model;

import java.util.List;
import listeners.ObjectChangeListener.ObjectChangeEvent;

/**
 * A Tecton osztály leszármazottja, mely rendelkezik egy olyan különleges hatással, hogy életben tartja azokat a fonalakat, amelyek nincsenek közvetve vagy közvetlenül gombatesthez kötve.
 */
public class TectonKeepAlive extends Tecton {

    /* - Privát attribútumok*/

    //Konstruktor
    public TectonKeepAlive() {
        super();
    }

    /**
     * Gombafonál csatlakoztatása a tektonhoz.
     * @param line A hozzáadandó gombafonal
     * @return Visszatér egy boolean-el annak függvényében, hogy sikerült-e a fonalat hozzáadni.
     */
    @Override
    public boolean addLine(Line line) {
        connections.add(line);
        return true;
    }


    @Override
    public boolean hasBody(int mushroomId){
        return true;
    }


    /**
     * A tekton két tektonra törése
     */
    public void breakTecton(){
        List<Tecton> ng = getNeighbors();
        TectonKeepAlive t3 = new TectonKeepAlive();
        changeListener.tectonChanged(ObjectChangeEvent.OBJECT_ADDED, t3);
        for (Tecton t : ng) {
            t3.setNeighbors(t);
            t.setNeighbors(t3);
        }
        t3.setNeighbors(this);
        this.setNeighbors(t3);
        int size = connections.size();
        for (int i = 0; i < size; i++) {
            connections.get(0).Destroy();
        }
    }
}
