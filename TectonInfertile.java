/**
 * A Tecton osztály leszármazottja, mely rendelkezik egy olyan különleges hatással, ami azt eredményezi, hogy nem képes rajta gombatest nőni.
 */
public class TectonInfertile {
    /**
     * Mivel ezen a típusú tektonon nem tud gombatest nőni, ezért mindig false értékkel tér vissza, ezzel meggátolva a növesztést.
     * @param id
     * @return
     */
    boolean addMushroom(int id){
        Logger.FunctionStart(this, "addMushroom", new Object[]{id});
        //TODO
        Logger.FunctionEnd();
        return true;
    }
    /**
     * Mivel a tektonon nem képes gombatest nőni, ezért számolás nélkül false értékkel tér vissza.
     * @return
     */
    boolean hasBody(){
        Logger.FunctionStart(this, "hasBody");
        //TODO
        Logger.FunctionEnd();
        return true;
    }
}
