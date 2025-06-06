package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import listeners.ControlListener;
import listeners.JobListener;
import listeners.ObjectChangeListener;
import listeners.ResultListener;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Schedule;
import model.Tecton;
import model.Timer;
import userinterface.RandTools;


/**
 * A parancsok kiértékeléséért felelős osztály.
 */
public class Controller {
    /* - Privát attribútumok*/
    private static HashMap<String, Mushroom> allMushroom;
    private static HashMap<String, Line> allLine;
    private static HashMap<String, Insect> allInsect;
    private static HashMap<String, Tecton> allTecton;

    public static int mushroomIndex = 100;
    public static int lineIndex = 100;
    public static int insectIndex = 100;
    public static int tectonIndex = 100;

    private static PlayerHandler playerHandler;
    private boolean isGameRunning = false;
    private int round = 0;
    private int maxRound = 5;

    private ObjectChangeListener objectChangeListener;
    private List<ObjectChangeListener> objectListeners = new ArrayList<>();
    private static List<JobListener> jobListeners = new ArrayList<>();
    private List<ControlListener> controlListeners = new ArrayList<>();
    private List<ResultListener> resultListeners = new ArrayList<>();

    /* - Publikus attribútumok*/

    /* - Konstruktorok*/

    /**
     * Konstruktor
     */
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
                    addInsect(("i"+(insectIndex++)), insect);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allInsect.remove(getInsectId(insect));
                }
            }

            @Override
            public void lineChanged(ObjectChangeEvent event, Line line) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addLine(("l"+(lineIndex++)), line);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allLine.remove(getLineId(line));
                }
            }

            @Override
            public void tectonChanged(ObjectChangeEvent event, Tecton tecton) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addTecton(("t"+(tectonIndex++)), tecton);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allTecton.remove(getTectonId(tecton));
                }
            }

            @Override
            public void mushroomChanged(ObjectChangeEvent event, Mushroom mushroom) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addMushroom(("m"+(mushroomIndex)), mushroom);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allMushroom.remove(getMushroomId(mushroom));
                }
            }
        };

        setTiming();
    }


    /**
     * Időzítő beállítása
     */
    public void setTiming() {
        
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

                int breakChance = RandTools.random(10);
                if(breakChance < 4){
                    int which = RandTools.random(allTecton.size());
                    Tecton toBreak = null;
                    for (String key : allTecton.keySet()) {
                        if (which == 0) {
                            toBreak = allTecton.get(key);
                            break;
                        }
                        which--;
                    }

                    if (toBreak != null) {
                        toBreak.breakTecton();
                    }
                }
            }
        }, 1);
    }

    /**
     * Játék elkezdése
     * @param mushroomPickerCount gombászok száma
     * @param insectPickerCount rovarászok száma
     * @param askName kell-e neveket lekérdezni
     */
    public void StartGame(int mushroomPickerCount, int insectPickerCount, boolean askName) {
        playerHandler = new PlayerHandler(mushroomPickerCount, insectPickerCount, this, askName);
    }


    /**
     * Játék elkezdése
     * @param mushroomPickerCount gombászok száma
     * @param insectPickerCount rovarászok száma
     * @param playerNames Játékosok nevei
     */
    public void StartGame(int mushroomPickerCount, int insectPickerCount, List<String> playerNames) {
        playerHandler = new PlayerHandler(mushroomPickerCount, insectPickerCount, this, playerNames);
    }

    /* - Getter/Setter metódusok*/

    public void addObjectListener(ObjectChangeListener listener) {
        objectListeners.add(listener);
    }

    public void addJobListener(JobListener listener) {
        jobListeners.add(listener);
    }
    
    public List<JobListener> getJobListeners() {
        return jobListeners;
    }

    public void addControlListener(ControlListener listener) {
        controlListeners.add(listener);
    }

    public List<ControlListener> getControlListeners() {
        return controlListeners;
    }

    public void addResultListeners(ResultListener listener) {
        resultListeners.add(listener);
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


    /**
     * Gombatest hozzáadása a játékhoz
     * @param id gombatetst azonosítója
     * @param mushroom az adott gomba
     */
    public void addMushroom(String id, Mushroom mushroom) {
        if (allMushroom.containsKey(id)) {
            System.out.println("Mushroom with this ID already exists.");
            return;
        }

        
        for (ObjectChangeListener listener : objectListeners) {
            listener.mushroomChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, mushroom);
        }

        for (JobListener listener : jobListeners) {
            mushroom.addJobListener(listener);
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


    /**
     * Gombafonal hozzáadása a játékhoz
     * @param id gombafonal azonosítója
     * @param mushroom az adott gomba
     */
    public void addLine(String id, Line line) {
        if (allLine.containsKey(id)) {
            System.out.println("Line with this ID already exists.");
            return;
        }

        
        for (ObjectChangeListener listener : objectListeners) {
            listener.lineChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, line);
        }

        
        for (JobListener listener : jobListeners) {
            line.addJobListener(listener);
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


    /**
     * Körök számának beállítása
     * @param maxRound körök száma
     */
    public void setMaxRound(int maxRound) {
        this.maxRound = maxRound;
    }


    /**
     * Áttérés a következő körre
     */
    public void nextRound() {
        round++;
        Timer.forwardTime();
        System.out.println("Round " + (round+1) + " started.");
        if (round == maxRound) {
            endGame();
        }
    }


    /**
     * Játék végeés a pontszámok kiértékelése
     */
    public void endGame() {
        System.out.println("Game ended!");
        System.out.println(playerHandler.getWinner());

        for (ResultListener resultListener : resultListeners) {

            List<InsectPicker> insects = playerHandler.getInsectPickers();
            List<MushroomPicker> mushrooms = playerHandler.getMushroomPickers();

            // Sort players by points in descending order
            insects.sort((p1, p2) -> Integer.compare(p2.calculateScore(), p1.calculateScore()));
            mushrooms.sort((p1, p2) -> Integer.compare(p2.calculateScore(), p1.calculateScore()));

            // Concatenate the data in the required format
            StringBuilder dataBuilder = new StringBuilder();
            for (int i = 0; i<mushrooms.size(); i++) {
                dataBuilder.append(mushrooms.get(i).getDisplayName()+";"+mushrooms.get(i).calculateScore());

                if(i != mushrooms.size()-1) {
                    dataBuilder.append(";");
                }
            }
            dataBuilder.append(" ");

            for (int i = 0; i<insects.size(); i++) {
                dataBuilder.append(insects.get(i).getDisplayName()+";"+insects.get(i).calculateScore());

                if(i != insects.size()-1) {
                    dataBuilder.append(";");
                }
            }

            String data = dataBuilder.toString();
            resultListener.showResults(data);
            System.out.println(data);
        }
        
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


    /**
     * Rovar hozzáadása a játékhoz
     * @param id rovar azonosítója
     * @param mushroom az adott gomba
     */
    public void addInsect(String id, Insect insect) {
        if (allInsect.containsKey(id)) {
            System.out.println("Insect with this ID already exists.");
            return;
        }
        
        for (ObjectChangeListener listener : objectListeners) {
            listener.insectChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, insect);
        }

        
        for (JobListener listener : jobListeners) {
            insect.addJobListener(listener);
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


    /**
     * Tekton hozzáadása a játékhoz
     * @param id tekton azonosítója
     * @param mushroom az adott gomba
     */
    public void addTecton(String id, Tecton tecton) {
        if (allTecton.containsKey(id)) {
            System.out.println("Tecton with this ID already exists.");
            return;
        }
        
        for (ObjectChangeListener listener : objectListeners) {
            listener.tectonChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, tecton);
        }

        
        for (JobListener listener : jobListeners) {
            tecton.addJobListener(listener);
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


    /**
     * A játék resetelése.
     * Törlődik minden játékelem.
     */
    public void HardReset() {
        allMushroom.clear();
        allLine.clear();
        allInsect.clear();
        allTecton.clear();
        playerHandler = null;
        isGameRunning = false;
        round = 0;
        Timer.ResetTimer();
        setTiming();

        RandTools.resetFix();
    }


    /**
     * Parancsok lefordítása a CommandProcessor számára.
     * A GrapicController által használt "gombnyomásokat" és kijelölt tektonokar a CommandProcessor számára érthető formátumra alakítja.
     * @param cmd a parancs
     * @param tectons a tektonok, amikből a paraméterreket kapjuk
     * @return a parancs teljes szövege
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
                for(JobListener jobListener : jobListeners){
                    jobListener.jobFailed("There is no insect on the selected tecton!");
                }
                return "Hiba";
            case "/eat-spore":
                //Megkeressük a kivalasztott tektonban az aktuális játékos rovarját
                for (Insect insect : from.getInsects()) {
                    if(insect.getInsectId() == player.getPlayerId()){
                        return "/eat-spore " + getInsectId(insect);
                    }
                }
                for(JobListener jobListener : jobListeners){
                    jobListener.jobFailed("There is no insect on the selected tecton!");
                }
                return "Hiba";
            case "/cut-line":
                for (Insect insect : from.getInsects()) {
                    if(insect.getInsectId() == player.getPlayerId()){
                        insectId = getInsectId(insect);
                    }
                }
                if(insectId == null){
                    for(JobListener jobListener : jobListeners){
                        jobListener.jobFailed("There is no insect on the selected tecton!");
                    }
                    return "Hiba";
                }
                for(Line line : from.getConnections()){
                    if((line.getEnds()[0] == from && line.getEnds()[1] == to) || (line.getEnds()[0] == to && line.getEnds()[1] == from)){
                        return "/cut-line " + insectId + " " + getLineId(line);
                    }
                }
                for(JobListener jobListener : jobListeners){
                    jobListener.jobFailed("There is no line on the selected tecton!");
                }     
                return "Hiba";
            case "/grow-line":
                return "/grow-line " + fromId + " " + toId;
            case "/build-mushroom":
                return "/build-mushroom " + fromId;
            case "/throw-spore":
                if(from.getMyMushroom() == null){
                    for(JobListener jobListener : jobListeners){
                        jobListener.jobFailed("There is no mushroom on the selected tecton!");
                    }
                    return "Hiba";
                }
                return "/throw-spore " + getMushroomId(from.getMyMushroom())+ " " + toId;    
            case "/eat-insect":
                for (Insect insect : from.getInsects()) {
                    if(insect.getCanMove() == false){
                        insectId = getInsectId(insect);
                    }
                }
                if(insectId == null){
                    for(JobListener jobListener : jobListeners){
                        jobListener.jobFailed("There is no insect on the selected tecton!");
                    }
                    return "Hiba";
                }
                for(Line line : from.getConnections()){
                    if(line.getId() == player.getPlayerId()){
                        lineId = getLineId(line);
                    }
                }
                if(lineId == null){
                    for(JobListener jobListener : jobListeners){
                        jobListener.jobFailed("There is no line on the selected tecton!");
                    }
                    return "Hiba";
                }
                return "/eat-insect " + insectId + " " + lineId;    
            case "/next":
                return "/next";
            default:
                return "";
        }
    }
}
