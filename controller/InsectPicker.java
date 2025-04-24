package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Insect;
import model.Line;

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

    /**
     * Megpróbálja elvágni a megadott fonalat egy adott rovarral.
     * Csak akkor történik meg a vágás, ha a rovar a rovarászhoz tartozik.
     *
     * @param toCut a {@link Line} Az elvágandó fonál
     * @param insect a {@link Insect}, a rovar
     * @return {@code true}, ha a vágás sikeres volt, különben {@code false}
    */
    public boolean cutLine(Line toCut, Insect insect){
        if(getInsect().contains(insect)){
            return insect.cutLine(toCut);
        }
        return false;
    }

    /**
     * Egy rovar megpróbál megenni egy spórát.
     * Csak akkor hajtódik végre, ha az adott insect a rovarászhoz tartozik.
     *
     * @param insect az {@link Insect}, amely megpróbál spórát enni
     * @return {@code true}, ha sikerült egy spórát megenni, különben {@code false}
    */
    public boolean eatSpore(Insect insect){
        int spores = insect.getTecton().getSporeContainer().getSporeCount();
        if(getInsect().contains(insect)){
            insect.eatSpores(1);
        }
        return spores - insect.getTecton().getSporeContainer().getSporeCount() == 1;
    }
    
}
