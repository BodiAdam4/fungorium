package graphics;

import listeners.TectonListener;
import model.Mushroom;
import model.Tecton;


/**
 * A tektonok megjelenítéséért felelős osztály. Tartalmazza a gombatestek és spóratárolók grafikus példányait is.
 */
public class GTecton extends Image implements TectonListener {
    
    /* - Publikus attribútumok*/
    public String id;                   //A tektonhoz tartozó kontrollerbeli azonosító
    

    /* - Privát attribútumok*/
    private GMushroom mushroom; //A tektonon lévő gombatestet megjelenítő osztály egy példánya.

    private GSporeContainer sporeContainer; //A tektonon lévő spórákat megjelenítő osztály egy példánya.

    private Map map; //A térkép ami tartalmazza a grafikus tektont

    /* - Konstruktor(ok)*/
    public GTecton(String id) {
        super("graphics/images/Spores.png"); // A Tecton képe
        this.id = id;
    }


    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/
    
    /**
    * Grafikus gombatest hozzáadása a tektonhoz. Paraméterként át kell adni a gombatest grafikus példányát.
    * @param mushroom A gombatest grafikus példánya
    */
    public void addMushroom(GMushroom mushroom) {
        this.mushroom = mushroom;
        this.add(mushroom);
    }
    
    /**
     * Grafikus gombatest eltüntetése a tektonról.
     */
    public void removeMushroom() {
        this.mushroom = null;
        this.remove(mushroom);
    }
    
    /**
     * A tektonon való gombatest növesztésének következtében lefutó metódus. Paraméterként megkapja a kinövesztett gombatestet.
     */
    @Override
    public void mushroomAdded(Mushroom mushroom) {
        
    }

    /**
     * A tektonon lévő gombatest megszűnésének következtében lefutó metódus.
     */ 
    @Override
    public void mushroomRemoved() {
        mushroom.destroy();
        removeMushroom();
    }

    /**
     *  A tekton eltörésekor lefutó metódus, mely paraméterként átadja a másik tektont, ami a törés során keletkezett.
     */
    @Override
    public void tectonBroken(Tecton tecton) {
        
    }

    
}

