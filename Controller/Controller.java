package Controller;
import Model.Insect;
import Model.Line;
import Model.Mushroom;
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

    /* - Mushroom */
    public HashMap<String, Mushroom> getAllMushroom() {
        return allMushroom;
    }


    public Mushroom getMushroomById(String id) {
        if (!allMushroom.containsKey(id)) {
            return null;
        }

        return allMushroom.get(id);
    }


    public String getMushroomId(Mushroom mushroom) {
        for (String id : allMushroom.keySet()) {
            if (allMushroom.get(id).equals(mushroom)) {
                return id;
            }
        }
        return "Null";
    }


    /* - Line */
    public HashMap<String, Line> getAllLine() {
        return allLine;
    }


    public Line getLineById(String id) {
        if (!allLine.containsKey(id)) {
            return null;
        }

        return allLine.get(id);
    }


    public String getLineId(Line line) {
        for (String id : allLine.keySet()) {
            if (allLine.get(id).equals(line)) {
                return id;
            }
        }
        return "Null";
    }


    /* - Insect */
    public HashMap<String, Insect> getAllInsect() {
        return allInsect;
    }


    public Insect getInsectById(String id) {
        if (!allInsect.containsKey(id)) {
            return null;
        }

        return allInsect.get(id);
    }


    public String getInsectId(Insect insect) {
        for (String id : allInsect.keySet()) {
            if (allInsect.get(id).equals(insect)) {
                return id;
            }
        }
        return "Null";
    }


    /* - Tecton */
    public HashMap<String, Tecton> getAllTecton() {
        return allTecton;
    }


    public Tecton getTectonById(String id) {
        if (!allTecton.containsKey(id)) {
            return null;
        }

        return allTecton.get(id);
    }


    public String getTectonId(Tecton tecton) {
        for (String id : allTecton.keySet()) {
            if (allTecton.get(id).equals(tecton)) {
                return id;
            }
        }
        return "Null";
    }

}
