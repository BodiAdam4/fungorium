package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import listeners.JobListener;
import model.Insect;
import model.Line;
import model.Tecton;

public class InsectPicker extends Player {
    /* - Privát attribútumok*/
    
    // A rovarokhoz tartozó akciók figyelésére szolgáló lista, ha egy akció már megtörtént, akkor nem hajtható végre újra az új körig
    // 0: move, 1: cutLine, 2: eatSpore, 3: alreadyCut
    private HashMap<Insect, Boolean[]> insectActions = new HashMap<>();

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
        return score;
    }

    /**
     * Hozzáadja a hiányzó rovart az eseménykövető listához.
     * @param insect A hozzáadandó rovar
     */
    public void RefreshInsectActions(Insect insect) {
        insectActions.put(insect, new Boolean[]{false, false, false});
    }

    /**
     * Visszaállítja a rovarok akcióit.
     */
    @Override
    public void ResetInsectActions() {
        for (Insect insect : insectActions.keySet()) {
            insectActions.get(insect)[0] = false;
            insectActions.get(insect)[1] = false;
            insectActions.get(insect)[2] = false;
        }
    }

    /**
     * Megpróbálja elvágni a megadott fonalat egy adott rovarral.
     * Csak akkor történik meg a vágás, ha a rovar a rovarászhoz tartozik.
     *
     * @param toCut Az elvágandó fonál
     * @param insect A rovar
     * @return igaz, ha a vágás sikeres volt, különben hamis
    */
    public boolean cutLine(Line toCut, Insect insect){
        if(getInsect().contains(insect)){
            if(!insectActions.containsKey(insect)){
                RefreshInsectActions(insect);
            } 
            else if(insectActions.get(insect)[2]){
                System.out.println("Insect already cut a line in this turn!");
                for (JobListener listener : jobListeners) {
                    listener.jobFailed("Insect already cut a line in this turn!");
                }
                return false;
            }
            
            insectActions.get(insect)[2] = true;
            return insect.cutLine(toCut);
        }
        
        System.out.println("Insect is not yours!");
        for (JobListener listener : jobListeners) {
            listener.jobFailed("Insect is not yours!");
        }
        return false;
    }

    /**
     * Egy rovar megpróbál megenni egy spórát.
     * Csak akkor hajtódik végre, ha az adott insect a rovarászhoz tartozik.
     *
     * @param insect az a rovar, amely megpróbál spórát enni
     * @return igaz, ha sikerült egy spórát megenni, különben hamis
    */
    public boolean eatSpore(Insect insect){
        int spores = insect.getTecton().getSporeContainer().getSporeCount();
        if(getInsect().contains(insect)){
            if(!insectActions.containsKey(insect)){
                RefreshInsectActions(insect);
            } 
            else if(insectActions.get(insect)[1]){
                System.out.println("Insect already eat spore in this turn!");
                for (JobListener listener : jobListeners) {
                    listener.jobFailed("Insect already eat spore in this turn!");
                }
                return false;
            }
            insectActions.get(insect)[1] = true;
            score += insect.eatSpores(1);
        }
        else {
            System.out.println("Insect is not yours!");
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Insect is not yours!");
            }
        }
        return spores - insect.getTecton().getSporeContainer().getSporeCount() == 1;
    }
    
     
    /**
     * Megpróbálja mozgatni a rovart egy másik Tectonra.
     * @param to A cél Tecton
     * @param insect A rovar, amelyet mozgatni szeretnénk
     * @return igaz, ha a mozgás sikeres volt, különben hamis
     */
    public boolean move(Tecton to, Insect insect){
        if(insect.getInsectId() == this.getPlayerId()){
            if(!insectActions.containsKey(insect)){
                RefreshInsectActions(insect);
            } 
            else if(insectActions.get(insect)[0]){
                System.out.println("Insect already moved in this turn!");
                for (JobListener listener : jobListeners) {
                    listener.jobFailed("Insect already moved in this turn!");
                }
                return false;
            }
            insectActions.get(insect)[0] = true;
            return insect.move(to);
        }
        System.out.println("Insect is not yours!");
        for (JobListener listener : jobListeners) {
            listener.jobFailed("Insect is not yours!");
        }
        return false;
    }

}
