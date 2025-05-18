package graphics;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import listeners.InsectListener;
import model.Insect;
import model.Tecton;

public class GInsect extends Image implements InsectListener{

    //attribútumok
    public Insect insect;
    private JLabel infoTag;
    private Map map;

    //publikus metódusok
    

    /**
     * Konstruktor
     * @param insect
     * @param map
     */
    public GInsect(Insect insect, Map map){
        super("graphics/images/Insect.png");
        this.insect = insect;
        this.map = map;
        //A layout-ot le kell venni, hogy jó helyen jelenjen meg az effekt felitara
        this.setLayout(null);


        //JLabel az effektek megjelenítésére
        infoTag = new JLabel("None"); // A spórák számát megjelenítő JLabel
        //TODO: a spóra darabszámát lehet feljebb is helyezni, hogy ne takarja el a gomba
        infoTag.setBounds(this.getX()+100,this.getY()+50, 100, 50); // A JLabel pozíciója és mérete
        infoTag.setForeground(java.awt.Color.RED); // A JLabel szövegének színe
        infoTag.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 10)); // A JLabel betűtípusa és mérete
        infoTag.setVisible(false); // A JLabel láthatósága
        this.add(infoTag); // A JLabel hozzáadása a GSporeContainerhez
        this.revalidate();
        this.repaint();
    }


    /**
     * Grafikus rovar eltávolítása a térképről
     */
    public void destroy() {
        map.removeInsect(insect);
    }

    //InsectListenert implementáló metódusok

    /**
     * Azon metódus, amely akkor hívodik meg ha egy rovar elkezd mozogni.
     * @param from a kiinduló tekton
     * @param to a cél tekton
     */
    public void moveStarted(Tecton from, Tecton to) {
        List<Tecton> tectons = new ArrayList<>();
        tectons.add(from);
        tectons.add(to);
        map.InsectMoved(this, tectons);
    }
    

    /**
     * Azon metódus, amely akkor hívódik meg ha egy rovar befejezte a mozgását.
     * @param to a cél tekton
     */
    public void moveFinished(Tecton to) {
        List<Tecton> tectons = new ArrayList<>();
        tectons.add(to);
        map.InsectMoved(this, tectons);
    }


    /**
     * Azon metódus, amely akkor hívódik meg ha egy rovar elfogyaszt egy spórát
     * @param effect az adott effekt
     */
    public void sporeEaten(String effect) {

        //JLabelt mehjelenítjük a felirattal
        infoTag.setText(effect);
        infoTag.setVisible(true);
        this.revalidate();
        this.repaint();
    }


    /**
     * Azon metódus, amely akkor hívódik meg ha egy spóra hatása lejár egy rovaron.
     */
    public void effectReseted() {
        //JLabelt eltüntetjük
        infoTag.setText(" ");
        infoTag.setVisible(false);
        this.revalidate();
        this.repaint();
    }


    /**
     * Azon metódus, amely akkor hívódik meg amikor egy rovar elpusztul.
     */
    public void insectDestroyed() {
        this.destroy();
    }

    public Insect getMyInsect() {
        return insect;
    }

}
