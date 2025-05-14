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

    public GInsect(Insect insect, Map map){
        super("graphics/images/Insect.png");
        this.insect = insect;
        this.map = map;
    }

    public void destroy() {
        //TODO
    }

    //InsectListenert implementáló metódusok

    public void moveStarted(Tecton from, Tecton to) {
        List<Tecton> tectons = new ArrayList<>();
        tectons.add(from);
        tectons.add(to);
        map.InsectMoved(this, tectons);
    }
    
    public void moveFinished(Tecton to) {
        List<Tecton> tectons = new ArrayList<>();
        tectons.add(to);
        map.InsectMoved(this, tectons);
    }

    public void sporeEaten(String effect) {
        infoTag.setText(effect);
    }

    public void effectReseted() {
        infoTag.setText("");
    }

    public void insectDestroyed() {
        this.destroy();
    }

    public Insect getMyInsect() {
        return insect;
    }

}
