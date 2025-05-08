package graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import listeners.InsectListener;
import model.Tecton;

public class GInsect extends Image implements InsectListener{

    //attribútumok
    public String id;
    public String playerId;
    private JPanel place;
    private JLabel infoTag;

    //publikus metódusok
    public GInsect(String id, String playerId, JPanel place){
        super("graphics/images/Insect.png");
        this.id = id;
        this.playerId = playerId;
    }

    public void move(JPanel to){
        //TODO
    }

    public void destroy() {
        //TODO
    }

    //InsectListenert implementáló metódusok

    public void moveStarted(Tecton from, Tecton to) {
        //TODO
    }
    public void moveFinished(Tecton to) {
        //TODO
    }
    public void sporeEaten(String effect) {
        //TODO
    }
    public void effectReseted() {
        //TODO
    }
    public void insectDestroyed() {
        //TODO
    }


}
