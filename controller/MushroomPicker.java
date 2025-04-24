package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Insect;
import model.Line;
import model.Mushroom;
import model.Tecton;

public class MushroomPicker extends Player {
    /* - Privát attribútumok*/

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
        return getMushrooms().size();
        //TODO: implement this method
    }

    
    //GrowLine metódus a gombafonal növesztésére
    public boolean growLine(Tecton from, Tecton to){

        if(from.hasBody(getPlayerId())) {
            from.getMyMushroom().growLine(to);
            return true;

        //Ha van a tektonon line, akkor a line növeszti a gombafonalat a Line osztály growLine() metódusával
        } else {
            for (Line line : from.getConnections()) {
                if (line.getId() == this.getPlayerId()) {
                    line.growLine(from,to);
                    return true;
                }
            }
        }
        //Ha egyik sem teljesül, akkor nem tudunk gombafonalat növeszteni
        return false;
    }

    /**
     * Megpróbáljuk kinöveszteni a gombatestet a megadott tektonon.
     *
     * @param tecton a Tecton, ahol a gombatestet növeszteni szeretnénk
     * @return igaz, ha a növesztés sikeres volt, különben hamis      
     * */
    public boolean growBody(Tecton tecton) {
        
        List<Line> connections = tecton.getConnections();

        // Ellenőrizzük, hogy van-e a játékoshoz tartozó fonal a tektonon
        for (Line line : connections) {
            if (line.getId() == this.getPlayerId()) {
                // Megpróbáljuk növeszteni a gombatestet
                if (line.growMushroom(tecton)) {
                    System.out.println("Sikeres gombatest-növesztés a " + tecton + " tektonon!");
                    return true;
                } else {
                    System.out.println("Nem sikerült gombatestet növeszteni!");
                    return false;
                }
            }
        }
        System.out.println("Nincs a játékoshoz tartozó gombafonal ezen a tektonon!");
        return false;
    }


    /**
     * Megpróbálunk elfogyasztani egy fagyasztott állapotban lévő rovart.
     *
     * @param insect az Insect, amelyet el szeretnénk fogyasztani
     * @return igaz, ha a rovar sikeresen elfogyasztva, különben hamis
     */
    public boolean eatInsect(Insect insect) {
        //Ellenőrizzük, hogy a rovar fagyasztott állapotban van-e
        if (insect.getCanMove()) {
            System.out.println("A rovar nem fagyasztott állapotban van!");
            return false;
        }

        for (Line line : insect.getTecton().getConnections()) {
            if (line.getId() == this.getPlayerId()) {
                boolean success = line.eatInsect(insect);
                if (line.eatInsect(insect)) {
                    System.out.println("Rovar sikeresen elfogyasztva!");
                    return true;
                }
            }
        }

        System.out.println("Nincs fonal amivel elfogyasztható a rovar!");
        return false;
    }
}
