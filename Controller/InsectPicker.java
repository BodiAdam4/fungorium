package Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Insect;

public class InsectPicker extends Player {
    /* - Privát attribútumok*/

    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public InsectPicker(int playerId, String displayName, Controller controller) {
        super(playerId, displayName, controller);
        // TODO - implement InsectPicker.InsectPicker
    }

    
    /* - Getter/Setter metódusok*/

    public List<Insect> getInsect() {
        //TODO: get the whole insect list and search for it's own insects
        HashMap<String, Insect> allInsect = controller.getAllInsect();
        List<Insect> myinsectList = new ArrayList<>();
        for (Insect insect : allInsect.values()) {
            if (insect.getInsectId() == this.getPlayerId()) {
                myinsectList.add(insect);
            }
        }
        return myinsectList;
    }



    /* - Egyéb metódusok*/

    @Override
    public int calculateScore() {
        // TODO - implement Player.calculateScore
        return 0;
    }
}
