package graphics;

import java.util.List;

import javax.swing.JLabel;

import listeners.SporeContainerListener;
import model.Spore;
import model.SporeContainer;
/**
 * A tektonon elhelyezkedő spórák kirajzolásáért felelős osztály.
 * A játék során a példányait a GTecton osztály példányai fogják tartalmazni, mivel egy tektonhoz egy spóratároló tartozik.
 */
public class GSporeContainer extends Image implements SporeContainerListener {
    
    /* - Privát attribútumok*/
    /**
     * A spórák számát megjelenítő JLabel
     */
    private JLabel sporecountLabel; 

    /**
     * A grafikus spóra tárolóhoz tartozó SporeContainer
     */
    private SporeContainer mySporeContainer;

    /**
     * A grafikus spóra tárolóhoz tartozó grafikus tekton
     */
    private GTecton myGTecton;

    /* - Konstruktor(ok)*/
    /**
     * Konstruktor
     * @param mySporeContainer
     * @param myGTecton
     */
    public GSporeContainer(SporeContainer mySporeContainer, GTecton myGTecton) {
        super("graphics/images/Spores.png"); // A SporeContainer képe
        this.mySporeContainer = mySporeContainer;
        this.myGTecton=myGTecton;
        this.setLayout(null);

        //JLabel a spórák számának megjelenítésére
        sporecountLabel = new JLabel("0"); // A spórák számát megjelenítő JLabel
        //TODO: a spóra darabszámát lehet feljebb is helyezni, hogy ne takarja el a gomba
        sporecountLabel.setBounds(this.getX()+70, this.getY()-10, 50, 50); // A JLabel pozíciója és mérete
        sporecountLabel.setForeground(java.awt.Color.RED); // A JLabel szövegének színe
        sporecountLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20)); // A JLabel betűtípusa és mérete
        sporecountLabel.setVisible(true); // A JLabel láthatósága
        this.add(sporecountLabel); // A JLabel hozzáadása a GSporeContainerhez
        this.revalidate();
        this.repaint();
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

        List<Spore> spores = mySporeContainer.getSpores();
        boolean sameId = true;
        for(int i = 1; i < spores.size(); i++){
            if (spores.get(i).getSporeId()!= spores.get(i-1).getSporeId()) {
                sameId = false;
                break;
            }
        }

        if (sameId) {
            int id = spores.get(0).getSporeId();
            this.SetImage("graphics\\images\\Spores.png");
            this.TintImage(myGTecton.getMap().getGraphicController().getMushroomColor(id));
        } else {
            this.ResetTint();
            this.SetImage("graphics\\images\\SporesMulticlolor.png");
        }

        //A spórák számát megjelenítő JLabel
        sporecountLabel.setText(String.valueOf(sporeCount)); // A spórák számát megjelenítő JLabel frissítése
        this.revalidate();
        this.repaint();
    }

    /**
     * A spóratárolóból gombaspóra elvétele esetén lefutó függvény, paraméterként átadja a spóratárolóban lévő spórák számát.
     */
    @Override
    public void sporeRemoved(int sporeCount) {
        sporecountLabel.setText(String.valueOf(sporeCount)); // A spórák számát megjelenítő JLabel frissítése
        sporecountLabel.setVisible(true); // A JLabel láthatósága
        this.revalidate();
        this.repaint();

        if(sporeCount == 0) {
            this.setVisible(false);
            this.getParent().repaint();

            //A spórák számát megjelenítő JLabel eltüntetése
            sporecountLabel.setVisible(false);
            this.revalidate();
            this.repaint();
        }
    }
}
