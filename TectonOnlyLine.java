import javax.sound.sampled.Line;

/**
 * A Tecton osztály leszármazottja, mely rendelkezik egy olyan különleges hatással, ami miatt csak egyfajta gombafonal tud kapcsolódni hozzá.
 */
public class TectonOnlyLine extends Tecton{

    public TectonOnlyLine(String objectName){
        super(objectName);
    }
    /**
     * Gombafonál csatlakoztatása a tektonhoz, viszont ellenőrzi, 
     * hogy már létezik kapcsolata bármilyen gombafonallal.
     * Ha igen akkor ellenőrzi, hogy az új kapcsolódó fonal 
     * megegyező azonosítóval rendelkezik, és csak akkor engedélyezi.
     * @param line A hozzáadandó gombafonal
     * @return Visszatér egy boolean-el, hogy sikerült-e hozzáadni a fonalat.
     */
    boolean addLine(Line line){
        Logger.FunctionStart(this, "addLine", new Object[]{line});
        connections.add(line);
        Logger.FunctionEnd();
        return true;
    }
}
