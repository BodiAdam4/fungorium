package Model;
/**
 * A Tecton osztály leszármazottja, mely rendelkezik egy olyan különleges hatással, ami azt eredményezi, hogy nem képes rajta gombatest nőni.
 */
public class TectonInfertile extends Tecton{

    /* - Privát attribútumok*/
    
    
    //Konstruktor
    public TectonInfertile(){
        super();
    }


    /** - Egyéb metódusok*/
    
    /**
     * Mivel ezen a típusú tektonon nem tud gombatest nőni, ezért mindig false értékkel tér vissza, ezzel meggátolva a növesztést.
     * @param id A hozzáadandó gombatest id-je.
     * @return Mindig false
     */
    public boolean addMushroom(int id){
        Logger.Log("You can't grow mushroom on this tecton!");
        return false;
    }


    /**
     * Mivel a tektonon nem képes gombatest nőni, ezért számolás nélkül false értékkel tér vissza.
     * @return Mindig false
     */
    boolean hasBody(){
        return false;
    }
}
