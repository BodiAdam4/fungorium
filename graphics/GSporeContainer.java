package graphics;

import listeners.SporeContainerListener;
/**
 * A tektonon elhelyezkedő spórák kirajzolásáért felelős osztály.
 * A játék során a példányait a GTecton osztály példányai fogják tartalmazni, mivel egy tektonhoz egy spóratároló tartozik.
 */
public class GSporeContainer extends Image implements SporeContainerListener {
    
    /* - Publikus attribútumok*/

    /* - Konstruktor(ok)*/
    public GSporeContainer() {
        super("graphics/images/Spores.png"); // A SporeContainer képe
    }

    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/

    /**
     * A grafikus spóratároló megsemmisítésére szolgáló függvény.
     */
    public void destroy(){

    }

    /**
     * A spóratárolóhoz gombaspóra hozzáadása esetén lefutó függvény, paraméterként átadja a spóratárolóban lévő spórák számát
     */
    @Override
    public void sporeAdded(int sporeCount) {
        this.setVisible(true);
        this.getParent().repaint();
    }

    /**
     * A spóratárolóból gombaspóra elvétele esetén lefutó függvény, paraméterként átadja a spóratárolóban lévő spórák számát.
     */
    @Override
    public void sporeRemoved(int sporeCount) {
        if(sporeCount == 0) {
            this.setVisible(false);
            this.getParent().repaint();
        }
    }

}
