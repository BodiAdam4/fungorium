package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    //TODO: growBody
    //public boolean growBody(Tecton tecton){
      //  return true;
    //}


    /**
     * A metódus lehetővé teszi a spóra dobását a paraméterként kapott tektonra.
     * Ellenőrzi, hogy melyik gombatest képes gombaspórát dobni a tektonra, ha talál egy olyan gombatestet,
     * mely képes spórát dobni a kiszemelt tektonra, akkor meghívja az adott gombatest throwSpores függvényét.
     * @param Tectonto
     * @return false, ha nem sikerült spórát dobni, true ha sikerült.
     */
    public boolean ThrowSpore(Tecton tectonTo){
        for(Mushroom m : getMushrooms()){
            for  (Tecton t : m.getMyTecton().getNeighbors()){
                if (m.getLevel() >= 2 && t.getNeighbors().contains(tectonTo)){
                    m.throwSpores(tectonTo);
                    return true;
                } 
                
                if (t == tectonTo){
                    m.throwSpores(tectonTo);
                    return true;
                }
            }
        }
        return false;
    }

    //TODO: eatInsect
}
