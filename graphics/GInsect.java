package graphics;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import listeners.InsectListener;
import model.Tecton;
import model.Line;

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
        place = to;
    }

    public void destroy() {
        //TODO
    }

    //InsectListenert implementáló metódusok

    public void moveStarted(Tecton from, Tecton to) {
        for(Line l : from.getConnections()){
            Tecton[] ends = l.getEnds();
            if(ends[0] == from && ends[1] == to || ends[0] == to && ends[1] == from){
                
            }
        }
    }
    
    public void moveFinished(Tecton to) {
        
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


}
