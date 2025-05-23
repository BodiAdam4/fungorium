package controller;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import listeners.ControlListener;

/**
 * Játékosokatt kezelő osztály
 */
public class PlayerHandler {
    /* - Privát attribútumok*/
    private List<MushroomPicker> mushroomPickers;
    private List<InsectPicker> insectPickers;
    private Controller controller;
    private int actualPlayerIdx = 0;
    private int round = 1;
    
    private String askName(String msg){
        System.out.print(msg);
        
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        return scanner.nextLine();
    }


    /**
     * Konstruktor
     * @param mushroomPickerCount
     * @param insectPickerCount
     * @param ctrl
     * @param askName
     */
    public PlayerHandler(int mushroomPickerCount, int insectPickerCount, Controller ctrl, boolean askName) {
        this.mushroomPickers = new ArrayList<>();
        this.insectPickers = new ArrayList<>();
        this.controller = ctrl;

        if (askName) {
            System.out.println("#### Player selection ####");
            for (int i = 0; i < mushroomPickerCount; i++) {
                String name = askName("Enter the name of mushroom picker #" + (i + 1) + ": ");
                mushroomPickers.add(new MushroomPicker(name, controller));
            }
            Player.playerIdCounter = 0;
            for (int i = 0; i < insectPickerCount; i++) {
                String name = askName("Enter the name of insect picker #" + (i + 1) + ": ");
                insectPickers.add(new InsectPicker(name, controller));
            }
        }
        else {
            for (int i = 0; i < mushroomPickerCount; i++) {
                mushroomPickers.add(new MushroomPicker("MushroomPicker #" + (i + 1), controller));
            }
            Player.playerIdCounter = 0;
            for (int i = 0; i < insectPickerCount; i++) {
                insectPickers.add(new InsectPicker("InsectPicker #" + (i + 1), controller));
            }
        }
        

        controller.setGameRunning(true);
    }

    /**
     * Konstruktor
     * @param mushroomPickerCount
     * @param insectPickerCount
     * @param ctrl
     * @param playerNames
     */
    public PlayerHandler(int mushroomPickerCount, int insectPickerCount, Controller ctrl, List<String> playerNames) {
        this.mushroomPickers = new ArrayList<>();
        this.insectPickers = new ArrayList<>();
        this.controller = ctrl;

        for (int i = 0; i < mushroomPickerCount; i++) {
            String name = playerNames.get(i);
            MushroomPicker picker = new MushroomPicker(name, controller);
            picker.setJobListeners(controller.getJobListeners());
            mushroomPickers.add(picker);
        }

        Player.playerIdCounter = 0;
        for (int i = 0; i < insectPickerCount; i++) {
            String name = playerNames.get(mushroomPickerCount+i);
            InsectPicker picker = new InsectPicker(name, controller);
            picker.setJobListeners(controller.getJobListeners());
            insectPickers.add(picker);
        }
        
        controller.setGameRunning(true);

        HashMap<String, Integer> scores = new HashMap<>();

        for (Player player : getAllPlayer()) {
            scores.put(player.getDisplayName(), player.calculateScore());
        }

        for (ControlListener listener : controller.getControlListeners()) {
            listener.onNextRound(getActualPlayer().getDisplayName(), !actualPlayerIsMushroomPicker(), round, scores);
        }
    }


    /**
     * Lépteti a soron következő játékosindexet és lekezeli az új körök kezdetét, amiről szól a Controller osztálynak is.
    */
    public void nextPlayer() {
        actualPlayerIdx++;


        if (actualPlayerIdx >= mushroomPickers.size() + insectPickers.size()) {
            actualPlayerIdx = 0;
            insectPickers.forEach(InsectPicker::ResetInsectActions);
            mushroomPickers.forEach(MushroomPicker::ResetInsectActions);
            controller.nextRound();
            round++;
        }

        
        if (!getActualPlayer().canPlay()) {
            nextPlayer();
        }
        
        HashMap<String, Integer> scores = new HashMap<>();

        for (Player player : getAllPlayer()) {
            scores.put(player.getDisplayName(), player.calculateScore());
        }

        for (ControlListener listener : controller.getControlListeners()) {
            listener.onNextRound(getActualPlayer().getDisplayName(), !actualPlayerIsMushroomPicker(), round, scores);
        }
    }
    
    public List<InsectPicker> getInsectPickers() {
        return insectPickers;
    }

    public List<MushroomPicker> getMushroomPickers() {
        return  mushroomPickers;
    }

    /**
     * Megnézi, hogy milyen típusú az aktuális játékos
     * @return Ha a visszatérési érték true, akkor a játékos gombász, ha false, akkor rovarász.
     */
    public boolean actualPlayerIsMushroomPicker() {
        return actualPlayerIdx < mushroomPickers.size();
    }

    public Player getActualPlayer() {
        if (actualPlayerIdx < mushroomPickers.size()){
            return mushroomPickers.get(actualPlayerIdx);
        }
        else {
            return insectPickers.get(actualPlayerIdx - mushroomPickers.size());
        }
    }

    public String getWinner() {
        MushroomPicker winnerMushroom = mushroomPickers.get(0);

        for (MushroomPicker player : mushroomPickers) {
            if (player.calculateScore() > winnerMushroom.calculateScore()) {
                winnerMushroom = player;
            }
        }

        InsectPicker winnerInsect = insectPickers.get(0);
        for (InsectPicker player : insectPickers) {
            if (player.calculateScore() > winnerInsect.calculateScore()) {
                winnerInsect = player;
            }
        }

        return "Winner mushroompicker: "+winnerMushroom.getDisplayName() + " with score: " + winnerMushroom.calculateScore() + "\n" +
                "Winner insectpicker: "+winnerInsect.getDisplayName() + " with score: " + winnerInsect.calculateScore();
    }

    public List<Player> getAllPlayer() {
        List<Player> allPlayers = new ArrayList<>();


        for(Player player : mushroomPickers) {
            allPlayers.add(player);
        }
        for(Player player : insectPickers) {
            allPlayers.add(player);
        }
        return allPlayers;
    }
    
}
