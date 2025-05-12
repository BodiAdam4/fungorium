package graphics;

import java.util.HashMap;
import java.util.List;

import model.Insect;
import model.Tecton;
import controller.Controller;

import java.awt.Color;

import userinterface.CommandProcessor;

/**
 * Az GraphicController osztály felelős a játékmenet során a kontroller és a grafikus felület összeköttetéséért.
 * A modelbeli objektumok létrejöttekor itt történik a grafikus objektumok létrehozása és a különböző eseményfigyelők beállítása.
 */
public class GraphicController {
    /* - Publikus attribútumok*/
    /* - Privát attribútumok*/
    private Map map; //A térképet kezelő objektum.

    private List<Tecton> selected; //A kiválasztott tektonok listája

    private CommandProcessor cmd; //A parancsokat feldolgozó egység, amelyen keresztül a felhasználói tevékenységek végrehajtódnak.

    private HashMap<String, Color> playerColors; //A játékosokhoz tartozó színek listája. Kulcsként a játékos azonosítója, értékként pedig a hozzá tartozó szín van.

    /* - Konstruktor(ok)*/

    /**
     * Az osztály konstruktora, mely a játék kezdetén fut le.
     * Paraméterként átveszi a játékosok azonosítóit, amik szerint a színeket fogja kiosztani.
     * @param players A játékosok azonosítói
     */
    GraphicController(String[] players){

    }

    /* - Getter/Setter metódusok*/
    /* - Egyéb metódusok*/
    public void createInsect(Insect insect){
    }

    public void createMushroom(){

    }

    public void createLine(){

    }

    public void createTecton(){

    }

    public void addSelected(GTecton gtecton){
        selected.add(gtecton.getMyTecton());
    }

    public void sendCommand(String command){
        Tecton[] tectons = new Tecton[2];
        tectons[0] = selected.get(0);
        tectons[1] = selected.get(1);
        String commmand = Controller.translateCommand(command, tectons);
        cmd.ExecuteCommand(command);
    }
}
