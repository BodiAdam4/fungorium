package Controller;
import Model.Line;
import Model.Mushroom;
import Model.Tecton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MushroomPicker extends Player {
    /* - Privát attribútumok*/

    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public MushroomPicker(String displayName, Controller controller) {
        super(displayName, controller);
        // TODO - implement MushroomPicker.MushroomPicker
    }


    /* - Getter/Setter metódusok*/
    public List<Mushroom> getMushrooms() {
        //TODO: get the whole mushroom list and search for it's own mushrooms
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
        //TODO: get the whole line list and search for it's own lines
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
}
