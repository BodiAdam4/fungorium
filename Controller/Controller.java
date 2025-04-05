package Controller;
import Model.Mushroom;
import Model.Line;
import Model.Insect;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    /* - Privát attribútumok*/
    private HashMap<String, Mushroom> allMushroom;
    private HashMap<String, Line> allLine;
    private HashMap<String, Insect> allInsect;
    private List<Player> players;
    private List<MushroomPicker> mushroomPickers;
    private List<InsectPicker> insectPickers;
    private Time time;
    private int actualPlayerIdx;

    /* - Publikus attribútumok*/

    /* - Konstruktorok*/

    //Konstruktor
    public Controller(){
        this.allMushroom = new HashMap<>();
        this.allLine = new HashMap<>();
        this.allInsect = new HashMap<>();
        this.players = new ArrayList<>();
        this.mushroomPickers = new ArrayList<>();
        this.insectPickers = new ArrayList<>();

    }
}
