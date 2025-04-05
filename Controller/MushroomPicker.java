package Controller;
import Model.Mushroom;
import Model.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MushroomPicker extends Player {
    /* - Privát attribútumok*/

    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public MushroomPicker(int playerId, String displayName, Controller controller) {
        super(playerId, displayName, controller);
        // TODO - implement MushroomPicker.MushroomPicker
    }


    /* - Getter/Setter metódusok*/
    public List<Mushroom> getMushrooms() {
        //TODO: get the whole mushroom list and search for it's own mushrooms
        HashMap<String, Mushroom> allMushroom = controller.getAllMushroom();
        List<Mushroom> myMushroomList = new ArrayList<>();
        for (Mushroom mushroom : allMushroom.values()) {
            if (mushroom.getId() == this.getPlayerId()) {
                myMushroomList.add(mushroom);
            }
        }
        return myMushroomList;
    }


    public List<Line> getLines() {
        // TODO - implement MushroomPicker.getLines
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

    public int calculateScore() {
        // TODO - implement MushroomPicker.calculateScore
        return 0;
    }
}
