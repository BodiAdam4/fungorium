package Controller;
import Model.Mushroom;
import Model.Line;
import Model.Insect;
import Model.Tecton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    /* - Privát attribútumok*/
    private HashMap<String, Mushroom> allMushroom;
    private HashMap<String, Line> allLine;
    private HashMap<String, Insect> allInsect;
    private HashMap<String, Tecton> allTecton;
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
        this.allTecton = new HashMap<>();
        this.players = new ArrayList<>();
        this.mushroomPickers = new ArrayList<>();
        this.insectPickers = new ArrayList<>();

    }


    /* - Getter/Setter metódusok*/
    public HashMap<String, Mushroom> getAllMushroom() {
        return allMushroom;
    }

    public void setAllMushroom(HashMap<String, Mushroom> allMushroom) {
        this.allMushroom = allMushroom;
    }

    public HashMap<String, Line> getAllLine() {
        return allLine;
    }

    public void setAllLine(HashMap<String, Line> allLine) {
        this.allLine = allLine;
    }

    public HashMap<String, Insect> getAllInsect() {
        return allInsect;
    }

    public void setAllInsect(HashMap<String, Insect> allInsect) {
        this.allInsect = allInsect;
    }

    public HashMap<String, Tecton> getAllTecton() {
        return allTecton;
    }

    public void setAllTecton(HashMap<String, Tecton> allTecton) {
        this.allTecton = allTecton;
    }

}
