package model;
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


    //TODO: ezt átnézni, mert csak rögtönzött megoldás
    /**
     * Gombafonál eltávolítása a tektonról, de a fonal életben marad.
     * @param line A hozzáadandó gombafonal
     * @return Visszatér egy boolean-el annak függvényében, hogy sikerült-e a fonalat eltávolítani.
     */
    public boolean removeLineKeepAlive(Line line) {
        if (connections.contains(line)) {
            connections.remove(line);
            return true;
        } else {
            return false;
        }
    }
    
}
