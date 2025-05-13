package graphics;

import controller.Controller;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Tecton;
import userinterface.CommandProcessor;

/**
 * Az GraphicController osztály felelős a játékmenet során a kontroller és a grafikus felület összeköttetéséért.
 * A modelbeli objektumok létrejöttekor itt történik a grafikus objektumok létrehozása és a különböző eseményfigyelők beállítása.
 */
public class GraphicController {
    /* - Publikus attribútumok*/
    /* - Privát attribútumok*/
    private Map map; //A térképet kezelő objektum.

    private List<GTecton> selected; //A kiválasztott tektonok listája

    private CommandProcessor cmd; //A parancsokat feldolgozó egység, amelyen keresztül a felhasználói tevékenységek végrehajtódnak.

    private HashMap<String, Color> playerColors; //A játékosokhoz tartozó színek listája. Kulcsként a játékos azonosítója, értékként pedig a hozzá tartozó szín van.

    /* - Konstruktor(ok)*/

    /**
     * Az osztály konstruktora, mely a játék kezdetén fut le.
     * Paraméterként átveszi a játékosok azonosítóit, amik szerint a színeket fogja kiosztani.
     * @param players A játékosok azonosítói //TODO: Rá kell nézni
     */
    GraphicController(){

    }

    //TODO: Új függvény
    public void setMap(Map map) {
        this.map = map;
    }

    /* - Getter/Setter metódusok*/
    /* - Egyéb metódusok*/
    public void createInsect(Insect insect){
        GInsect gInsect = new GInsect(insect);
        map.addInsect(gInsect);
        insect.addInsectListener(gInsect);
    }

    public void createMushroom(Mushroom mushroom){
        GMushroom gMushroom = new GMushroom(mushroom);
        map.addMushroom(gMushroom);
        mushroom.addMushroomListener(gMushroom);
    }

    public void createLine(Line line){
        GLine gLine = new GLine(map.getTecton(line.getEnds()[0]), map.getTecton(line.getEnds()[1]));
        map.addLine(gLine);
        line.addLineListener(gLine);
    }

    public void createTecton(Tecton tecton){
        GTecton gTecton = new GTecton(tecton);
        map.addTecton(gTecton);
        tecton.addTectonListener(gTecton);
    }

    public void addSelected(GTecton gtecton){
        if(selected.size() == 2){
            selected.clear();
        }
        selected.add(gtecton);
    }

    public void sendCommand(String command){
        Tecton[] tectons = new Tecton[2];
        tectons[0] = selected.get(0).getMyTecton();
        tectons[1] = selected.get(1).getMyTecton();
        String commmand = Controller.translateCommand(command, tectons);
        cmd.ExecuteCommand(command);
    }
}
