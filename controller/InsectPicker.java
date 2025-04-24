package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Insect;
import model.Tecton;

public class InsectPicker extends Player {
    /* - Privát attribútumok*/

    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public InsectPicker(String displayName, Controller controller) {
        super(displayName, controller);
    }

    
    /* - Getter/Setter metódusok*/

    public List<Insect> getInsect() {
        //Get the whole insect list and search for it's own insects
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
        int score = 0;
        for (Insect insect : getInsect()) {
            score += insect.getSporeCount();
        }
        return score;
    }


    //TODO: implement other methods
    
    public boolean move(Tecton to, Insect insect){
        if(insect.getInsectId() == this.getPlayerId()){
            return insect.move(to);
        }
        return false;
    }
    //TODO: cutLine
    //TODO: eatSpore
}
