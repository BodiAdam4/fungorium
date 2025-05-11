package graphics;

import java.util.HashMap;
import java.util.List;
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

    private List<GTecton> selected; //A kiválasztott tektonok listája

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
    public void createInsect(){

    }
    public void createMushroom(){

    }
    public void createLine(){

    }
    public void createTecton(){

    }
    public void addSelected(GTecton tecton){
        selected.add(tecton);
    }
    public void sendCommand(String command){
        
    }
}
