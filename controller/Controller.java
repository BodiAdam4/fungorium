package controller;
import graphics.GraphicController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import listeners.ObjectChangeListener;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Schedule;
import model.Tecton;
import model.Timer;
import userinterface.RandTools;

public class Controller {
    /* - Privát attribútumok*/
    private static HashMap<String, Mushroom> allMushroom;
    private static HashMap<String, Line> allLine;
    private static HashMap<String, Insect> allInsect;
    private static HashMap<String, Tecton> allTecton;
    private static PlayerHandler playerHandler;
    private boolean isGameRunning = false;
    private int round = 0;
    private int maxRound = 5;

    private ObjectChangeListener objectChangeListener;
    private List<ObjectChangeListener> objectListeners = new ArrayList<>();

    /* - Publikus attribútumok*/

    /* - Konstruktorok*/

    //Konstruktor
    public Controller(){
        this.allMushroom = new HashMap<>();
        this.allLine = new HashMap<>();
        this.allInsect = new HashMap<>();
        this.allTecton = new HashMap<>();
        //this.mushroomPickers = new ArrayList<>();
        //this.insectPickers = new ArrayList<>();

        objectChangeListener = new ObjectChangeListener() {
            @Override
            public void insectChanged(ObjectChangeEvent event, Insect insect) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addInsect(("i"+(allInsect.size()+1)), insect);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allInsect.remove(getInsectId(insect));
                }
            }

            @Override
            public void lineChanged(ObjectChangeEvent event, Line line) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addLine(("l"+(allLine.size()+1)), line);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allLine.remove(getLineId(line));
                }
            }

            @Override
            public void tectonChanged(ObjectChangeEvent event, Tecton tecton) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addTecton(("t"+(allTecton.size()+1)), tecton);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allTecton.remove(getTectonId(tecton));
                }
            }

            @Override
            public void mushroomChanged(ObjectChangeEvent event, Mushroom mushroom) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addMushroom(("m"+(allMushroom.size()+1)), mushroom);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allMushroom.remove(getMushroomId(mushroom));
                }
            }
        };


        Timer.addRepeatSchedule(new Schedule() {
            @Override
            public void onTime() {
                List<Line> deadLines = new ArrayList<>();
                
                for (String id : allLine.keySet()) {
                    Line line = allLine.get(id);
                    if (line.ttl != -1) {
                        line.ttl--;
                        if (line.ttl == 0) {
                            deadLines.add(line);
                            System.out.println("Line destroyed: " + id);
                        }
                    }
                }

                for (Line line : deadLines) {
                    line.Destroy();
                }

                for (String id : allInsect.keySet()) {
                    allInsect.get(id).resetEffect();
                }
            }
        }, 1);

        Timer.addRepeatSchedule(new Schedule() {
            @Override
            public void onTime() {
                for (String id : allInsect.keySet()) {
                    allInsect.get(id).resetEffect();
                    System.out.println("Insect effect reset: " + id);
                }
            }
        }, 2);
    }

    public void StartGame(int mushroomPickerCount, int insectPickerCount, boolean askName) {
        playerHandler = new PlayerHandler(mushroomPickerCount, insectPickerCount, this, askName);
    }

    /* - Getter/Setter metódusok*/

    public void addObjectListener(ObjectChangeListener listener) {
        objectListeners.add(listener);
    }

    /* - Propertyk*/
    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean isGameRunning) {
        this.isGameRunning = isGameRunning;
    }

    /* - Mushroom */
    public HashMap<String, Mushroom> getAllMushroom() {
        return allMushroom;
    }


    public void addMushroom(String id, Mushroom mushroom) {
        if (allMushroom.containsKey(id)) {
            System.out.println("Mushroom with this ID already exists.");
            return;
        }

        
        for (ObjectChangeListener listener : objectListeners) {
            listener.mushroomChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, mushroom);
        }

        mushroom.changeListener = objectChangeListener;
        allMushroom.put(id, mushroom);
    }


    public Mushroom getMushroomById(String id) {
        if (!allMushroom.containsKey(id)) {
            return null;
        }

        return allMushroom.get(id);
    }


    public static String getMushroomId(Mushroom mushroom) {
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


    public void addLine(String id, Line line) {
        if (allLine.containsKey(id)) {
            System.out.println("Line with this ID already exists.");
            return;
        }

        
        for (ObjectChangeListener listener : objectListeners) {
            listener.lineChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, line);
        }

        line.changeListener = objectChangeListener;
        allLine.put(id, line);
    }


    public Line getLineById(String id) {
        if (!allLine.containsKey(id)) {
            return null;
        }

        return allLine.get(id);
    }

    public void setMaxRound(int maxRound) {
        this.maxRound = maxRound;
    }

    public void nextRound() {
        round++;
        Timer.forwardTime();
        System.out.println("Round " + (round+1) + " started.");
        if (round == maxRound) {
            endGame();
        }
    }

    public void endGame() {
        System.out.println("Game ended!");
        System.out.println(playerHandler.getWinner());
    }

    public static String getLineId(Line line) {
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


    public void addInsect(String id, Insect insect) {
        if (allInsect.containsKey(id)) {
            System.out.println("Insect with this ID already exists.");
            return;
        }
        
        for (ObjectChangeListener listener : objectListeners) {
            listener.insectChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, insect);
        }

        insect.changeListener = objectChangeListener;
        allInsect.put(id, insect);
    }


    public Insect getInsectById(String id) {
        if (!allInsect.containsKey(id)) {
            return null;
        }

        return allInsect.get(id);
    }


    public static String getInsectId(Insect insect) {
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


    public void addTecton(String id, Tecton tecton) {
        if (allTecton.containsKey(id)) {
            System.out.println("Tecton with this ID already exists.");
            return;
        }
        
        for (ObjectChangeListener listener : objectListeners) {
            listener.tectonChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, tecton);
        }

        tecton.changeListener = objectChangeListener;
        allTecton.put(id, tecton);
    }


    public Tecton getTectonById(String id) {
        if (!allTecton.containsKey(id)) {
            return null;
        }

        return allTecton.get(id);
    }


    public static String getTectonId(Tecton tecton) {
        for (String id : allTecton.keySet()) {
            if (allTecton.get(id).equals(tecton)) {
                return id;
            }
        }
        return "Null";
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public void HardReset() {
        allMushroom.clear();
        allLine.clear();
        allInsect.clear();
        allTecton.clear();
        playerHandler = null;
        isGameRunning = false;
        round = 0;
        Timer.ResetTimer();

        Timer.addRepeatSchedule(new Schedule() {
            @Override
            public void onTime() {
                List<Line> deadLines = new ArrayList<>();
                
                for (String id : allLine.keySet()) {
                    Line line = allLine.get(id);
                    if (line.ttl != -1) {
                        line.ttl--;
                        if (line.ttl == 0) {
                            deadLines.add(line);
                            System.out.println("Line destroyed: " + id);
                        }
                    }
                }

                for (Line line : deadLines) {
                    line.Destroy();
                }

                for (String id : allInsect.keySet()) {
                    allInsect.get(id).resetEffect();
                }
            }
        }, 1);

        Timer.addRepeatSchedule(new Schedule() {
            @Override
            public void onTime() {
                for (String id : allInsect.keySet()) {
                    allInsect.get(id).resetEffect();
                    System.out.println("Insect effect reset: " + id);
                }
            }
        }, 2);

        RandTools.resetFix();
    }

    /**
     * Parancsok lefordítása a CommandProcessor számára.
     * A GrapicController által használt "gombnyomásokat" és kijelölt tektonokar a CommandProcessor számára érthető formátumra alakítja.
     */
    public static String translateCommand(String cmd, Tecton[] tectons){
        //A kivalasztott tektonok
        Tecton from = tectons[0];
        Tecton to = null;
        
        //A kivalasztott tektonok ID-jainak megkeresese
        String fromId = getTectonId(from);
        String toId = null;

        //Ha csak egy tekton van kivalasztva, akkor a toId null marad
        if(tectons.length == 2){
            to = tectons[1];
            toId = getTectonId(to);
        }

        //Az aktuális játékos
        Player player = playerHandler.getActualPlayer();
        

        String insectId = null;

        String lineId = null;
                
        switch (cmd) {
            case "/move":
                //Megkeressük a kivalasztott tektonban az aktuális játékos rovarját
                for (Insect insect : from.getInsects()) {
                    if(insect.getInsectId() == player.getPlayerId()){
                        return "/move " + getInsectId(insect) + " " + toId;
                    }
                }
                break;
            case "/eat-spore":
                //Megkeressük a kivalasztott tektonban az aktuális játékos rovarját
                for (Insect insect : from.getInsects()) {
                    if(insect.getInsectId() == player.getPlayerId()){
                        return "/eat-spore " + getInsectId(insect);
                    }
                }
                break;
            case "/cut-line":
                for (Insect insect : from.getInsects()) {
                    if(insect.getInsectId() == player.getPlayerId()){
                        insectId = getInsectId(insect);
                    }
                }
                for(Line line : from.getConnections()){
                    if(line.getEnds()[0] == from && line.getEnds()[1] == to){
                        return "/cut-line " + insectId + " " + getLineId(line);
                    }
                }     
                break;
            case "/grow-line":
                return "/grow-line " + fromId + " " + toId;
            case "/build-mushroom":
                return "/build-mushroom " + fromId;
            case "/throw-spore":
                if(from.getMyMushroom() == null){
                    return "";
                }
                return "/throw-spore " + getMushroomId(from.getMyMushroom())+ " " + toId;    
            case "/eat-insect":
                for (Insect insect : from.getInsects()) {
                    if(insect.getCanMove() == false){
                        insectId = getInsectId(insect);
                    }
                }
                for(Line line : from.getConnections()){
                    if(line.getId() == player.getPlayerId()){
                        lineId = getLineId(line);
                    }
                }
                return "/eat-insect " + insectId + " " + lineId;    
            case "/next":
                return "/next";
            default:
                return "";
        }
        return "";
    }
}
