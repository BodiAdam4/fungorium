package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import listeners.JobListener;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Tecton;

public class MushroomPicker extends Player {
    /* - Privát attribútumok*/
    private int score = 0;

    
    // A gombász által végrehajtható akciók figyelésére szolgáló lista, ha egy akció már megtörtént, akkor nem hajtható végre újra az új körig
    // 0: growLine, 1: growBody, 2: throwSpores
    private Boolean[] actions = new Boolean[]{false, false, false, false}; 


    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public MushroomPicker(String displayName, Controller controller) {
        super(displayName, controller);
    }


    /* - Getter/Setter metódusok*/
    public List<Mushroom> getMushrooms() {
        //Get the whole mushroom list and search for it's own mushrooms
        HashMap<String, Mushroom> allMushroom = controller.getAllMushroom();
        List<Mushroom> myMushroomList = new ArrayList<>();
        for (Mushroom mushroom : allMushroom.values()) {
            if (mushroom.getMushroomId() == this.getPlayerId()) {
                myMushroomList.add(mushroom);
            }
        }
        return myMushroomList;
    }


    public List<Line> getLines() {
        //Get the whole line list and search for it's own lines
        HashMap<String, Line> allLine = controller.getAllLine();
        List<Line> myLineList = new ArrayList<>();
        for (Line line : allLine.values()) {
            if (line.getId() == this.getPlayerId()) {
                myLineList.add(line);
            }
        }
        return myLineList;
    }

    /* - Egyéb metódusok*/

    @Override
    public int calculateScore() {
        return score;
    }

    public void increaseScore() {
        this.score++;
    }

    /**
     * Visszaállítja a gombász akcióit.
     */
    @Override
    public void ResetInsectActions() {
        actions[0] = false;
        actions[1] = false;
        actions[2] = false;
        actions[3] = false;
    }

    //GrowLine metódus a gombafonal növesztésére
    public boolean growLine(Tecton from, Tecton to){
        if (actions[0]) {
            System.out.println("Mushroom has already grown a line this turn!");
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Mushroom has already grown a line this turn!");
            }
            return false;
        }

        if(from.hasBody(getPlayerId()) && from.getMyMushroom() != null) {
            from.getMyMushroom().growLine(to);
            actions[0] = true;
            return true;

        //Ha van a tektonon line, akkor a line növeszti a gombafonalat a Line osztály growLine() metódusával
        } else {
            for (Line line : from.getConnections()) {
                if (line.getId() == this.getPlayerId()) {
                    line.growLine(from,to);
                    actions[0] = true;
                    return true;
                }
            }
        }
        //Ha egyik sem teljesül, akkor nem tudunk gombafonalat növeszteni
        for (JobListener listener : jobListeners) {
            listener.jobFailed("Mushroom is not yours");
        }
        return false;
    }

    /**
     * Megpróbáljuk kinöveszteni a gombatestet a megadott tektonon.
     *
     * @param tecton a Tecton, ahol a gombatestet növeszteni szeretnénk
     * @return igaz, ha a növesztés sikeres volt, különben hamis      
     * */
    public boolean growBody(Tecton tecton) {
        
        if (actions[1]) {
            System.out.println("Mushroom has already grown in this turn!");
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Mushroom has already grown in this turn!");
            }
            return false;
        }

        List<Line> connections = tecton.getConnections();

        // Ellenőrizzük, hogy van-e a játékoshoz tartozó fonal a tektonon
        for (Line line : connections) {
            if (line.getId() == this.getPlayerId()) {
                // Megpróbáljuk növeszteni a gombatestet
                if (line.growMushroom(tecton)) {
                    System.out.println("Sikeres gombatest-növesztés a tektonon!");
                    score++;
                    actions[1] = true;
                    return true;
                } else {
                    System.out.println("Nem sikerült gombatestet növeszteni!");
                    return false;
                }
            }
        }
        System.out.println("Nincs a játékoshoz tartozó gombafonal ezen a tektonon!");
        for (JobListener listener : jobListeners) {
            listener.jobFailed("Can't grow mushroom to this tecton!");
        }
        return false;
    }

    /**
     * A metódus lehetővé teszi a spóra dobását a paraméterként kapott tektonra.
     * Ellenőrzi, hogy melyik gombatest képes gombaspórát dobni a tektonra, ha talál egy olyan gombatestet,
     * mely képes spórát dobni a kiszemelt tektonra, akkor meghívja az adott gombatest throwSpores függvényét.
     * @param Tectonto
     * @return false, ha nem sikerült spórát dobni, true ha sikerült.
     */
    public boolean ThrowSpore(Tecton tectonTo){

        if (actions[2]) {
            System.out.println("Spores has already thrown in this turn!");
            return false;
        }

        for(Mushroom m : getMushrooms()){
            for  (Tecton t : m.getMyTecton().getNeighbors()){
                if (m.getLevel() >= 2 && t.getNeighbors().contains(tectonTo)){
                    m.throwSpores(tectonTo);
                    actions[2] = true;
                    return true;
                } 
                
                if (t == tectonTo){
                    m.throwSpores(tectonTo);
                    actions[2] = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Megpróbálunk elfogyasztani egy fagyasztott állapotban lévő rovart.
     *
     * @param insect az Insect, amelyet el szeretnénk fogyasztani
     * @return igaz, ha a rovar sikeresen elfogyasztva, különben hamis
     */
    public boolean eatInsect(Insect insect) {
        if (actions[3]) {
            System.out.println("One insect has already eaten in this turn!");
            return false;
        }

        //Ellenőrizzük, hogy a rovar fagyasztott állapotban van-e
        if (insect.getCanMove()) {
            System.out.println("The insect is not frozen!");
            return false;
        }

        for (Line line : insect.getTecton().getConnections()) {
            if (line.getId() == this.getPlayerId()) {
                boolean success = line.eatInsect(insect);
                if (line.eatInsect(insect)) {
                    System.out.println("Insect eaten successfully!");
                    actions[3] = true;
                    return true;
                }
            }
        }

        System.out.println("The insect is not on your line!");
        return false;
    }

    
}
