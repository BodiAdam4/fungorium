package graphics;

import java.awt.Color;
import listeners.TectonListener;
import model.Tecton;


/**
 * A tektonok megjelenítéséért felelős osztály. Tartalmazza a gombatestek és spóratárolók grafikus példányait is.
 */
public class GTecton extends Image implements TectonListener {
    
    /* - Publikus attribútumok*/
    /**
     * A tektonhoz tartozó kontrollerbeli azonosító
     */
    public Tecton myTecton;                  
    

    /* - Privát attribútumok*/
    /**
     * A tektonon lévő gombatestet megjelenítő osztály egy példánya.
     */
    private GMushroom mushroom; 

    /**
     * Fiatal gombatest képe.
     */
    private Image youngMushroom;

    /**
     * A tektonon lévő spórákat megjelenítő osztály egy példánya.
     */
    private GSporeContainer sporeContainer; 

    /**
     * A térkép ami tartalmazza a grafikus tektont
     */
    private Map map; 

    /* - Konstruktor(ok)*/
    /**
     * Konstruktor
     * @param myTecton
     * @param map
     */
    public GTecton(Tecton myTecton, Map map) {
        super("graphics/images/tecton"+myTecton.toString()+".png"); // A Tecton képe
        this.myTecton = myTecton; //A tektonhoz tartozó kontrollerbeli azonosító beállítása
        this.map = map;
        this.setLayout(null);
        this.addMouseListener(this);
        sporeContainer = new GSporeContainer(myTecton.getSporeContainer(), this);
        myTecton.getSporeContainer().addSporeContainerListener(sporeContainer);
        this.add(sporeContainer);
        sporeContainer.setBounds(0, 0, Map.CELL_SIZE, Map.CELL_SIZE);
        sporeContainer.setVisible(false);
        this.revalidate();
        this.repaint();
    }


    /* - Getter/Setter metódusok*/

    public Map getMap(){
        return map;
    }

    //TODO: Új függvény
    public Tecton getMyTecton() {
        return myTecton;
    }

    /* - Egyéb metódusok*/
    
    /**
    * Grafikus gombatest hozzáadása a tektonhoz. Paraméterként át kell adni a gombatest grafikus példányát.
    * @param mushroom A gombatest grafikus példánya
    */
    public void addMushroom(GMushroom mushroom) {
         if (youngMushroom != null) {
            this.remove(youngMushroom);
            youngMushroom = null;
        }

        this.mushroom = mushroom;
        this.add(mushroom);
        mushroom.setBounds(0, 0, Map.CELL_SIZE, Map.CELL_SIZE);
        this.repaint();
    }
    
    /**
     * Grafikus gombatest eltüntetése a tektonról.
     */
    public void removeMushroom() {
        this.remove(mushroom);
        this.mushroom = null;
    }
    
    /**
     * A tektonon való gombatest növesztésének következtében lefutó metódus. Paraméterként megkapja a kinövesztett gombatestet.
     */
    @Override
    public void mushroomGrowStarted(int id) {
        youngMushroom = new Image("graphics\\images\\mushroomYoung.png");
        youngMushroom.setBounds(0, 0, Map.CELL_SIZE, Map.CELL_SIZE);
        Color mColor = map.getGraphicController().getMushroomColor(id);
        youngMushroom.TintImage(mColor);
        this.add(youngMushroom);
        this.revalidate();
        this.repaint();
    }

    /**
     * A tektonon lévő gombatest megszűnésének következtében lefutó metódus.
     */ 
    @Override
    public void mushroomRemoved() {
        removeMushroom();
    }

    /**
     *  A tekton eltörésekor lefutó metódus, mely paraméterként átadja a másik tektont, ami a törés során keletkezett.
     */
    @Override
    public void tectonBroken(Tecton tecton) {
        
    }
}

