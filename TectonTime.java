import javax.sound.sampled.Line;

/**
 * A Tecton osztály leszármazottja, mely rendelkezik egy olyan különleges hatással, ami miatt csak a kapcsolódott gombafonalak egy idő után megszűnnek
 */
public class TectonTime extends Tecton{
    /**
     * Gombafonál csatlakoztatása a tektonhoz.
     */
    boolean addLine(Line line){
        Logger.FunctionStart(this, "addLine", new Object[]{line});
        connections.add(line);
        Logger.FunctionEnd();
        return true;
    }
}
