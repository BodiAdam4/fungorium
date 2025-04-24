package model;

import java.util.List;

import listeners.ObjectChangeListener.ObjectChangeEvent;

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
        System.out.println("You can't grow mushroom on this tecton!");
        return false;
    }


    /**
     * Mivel a tektonon nem képes gombatest nőni, ezért számolás nélkül false értékkel tér vissza.
     * @return Mindig false
     */
    boolean hasBody(){
        return false;
    }


    /**
     * A tekton két tektonra törése
     */
    public void breakTecton(){
        List<Tecton> ng = getNeighbors();
        TectonInfertile t3 = new TectonInfertile();
        changeListener.tectonChanged(ObjectChangeEvent.OBJECT_ADDED, t3);
        for (Tecton t : ng) {
            t3.setNeighbors(t);
            t.setNeighbors(t3);
        }
        int size = connections.size();
        for (int i = 0; i < size; i++) {
            connections.get(0).Destroy();
        }
    }
}
