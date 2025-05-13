package graphics;

import controller.Controller;
import java.awt.Color;
import java.util.ArrayList;
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

    private List<GTecton> selected = new ArrayList<>(); //A kiválasztott tektonok listája

    private CommandProcessor cmd; //A parancsokat feldolgozó egység, amelyen keresztül a felhasználói tevékenységek végrehajtódnak.

    private List<Color> playerColors; //A játékosokhoz tartozó színek listája. Kulcsként a játékos azonosítója, értékként pedig a hozzá tartozó szín van.
    private int mushroomPickerCount;

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
        gInsect.TintImage(getInsectColor(gInsect.getMyInsect().getInsectId()));
        map.addInsect(gInsect);
        insect.addInsectListener(gInsect);
    }

    public void createMushroom(Mushroom mushroom){
        GMushroom gMushroom = new GMushroom(mushroom);
        gMushroom.TintImage(getMushroomColor(gMushroom.getMyMushroom().getMushroomId()));
        map.addMushroom(gMushroom);
        mushroom.addMushroomListener(gMushroom);
    }

    public void createLine(Line line){
        GLine gLine = new GLine(map.getTecton(line.getEnds()[0]), map.getTecton(line.getEnds()[1]), line);
        gLine.setBackground(getMushroomColor(gLine.getMyLine().getId()));
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
            //Kijelölések eltűntetése
            for (GTecton tecton : selected) {
                tecton.setSelected(false);
            }
            selected.clear();
        }
        selected.add(gtecton);
        //Kijelőlés hozzáadás
        gtecton.setSelected(true);
    }

    public void sendCommand(String command){
        Tecton[] tectons = new Tecton[2];
        if(selected.size() == 0){
            tectons[0] = null;
            tectons[1] = null;
        }
        if(selected.size() == 1){
            tectons[0] = selected.get(0).getMyTecton();
            tectons[1] = null;
        }
        if(selected.size() == 2){
            tectons[0] = selected.get(0).getMyTecton();
            tectons[1] = selected.get(1).getMyTecton();
        }
        String commmand = Controller.translateCommand(command, tectons);
        GraphicMain.cmdProcessor.ExecuteCommand(command);
    }

    public void setPlayers(List<Color> players, int mushroomPickCount) {
        playerColors = players;
        mushroomPickerCount = mushroomPickCount;
    }

    public Color getMushroomColor(int index) {
        return playerColors.get(index);
    }

    public Color getInsectColor(int index) {
        return playerColors.get(index+mushroomPickerCount);
    }
}
