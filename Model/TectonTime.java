package Model;
/**
 * A Tecton osztály leszármazottja, mely rendelkezik egy olyan különleges hatással, ami miatt csak a kapcsolódott gombafonalak egy idő után megszűnnek
 */
public class TectonTime extends Tecton{
    
    //Konstruktor
    public TectonTime(){
        super();
    }

    
    /**
     * Gombafonál csatlakoztatása a tektonhoz.
     * @param line A hozzáadandó gombafonal
     * @return Visszatér egy boolean-el annak függvényében, hogy sikerült-e a fonalat hozzáadni.
     */
    public boolean addLine(Line line){
        connections.add(line);
        line.ttl = 3;
        return true;
    }
}
