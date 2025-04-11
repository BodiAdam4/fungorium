package Controller;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerHandler {
    /* - Privát attribútumok*/
    private List<MushroomPicker> mushroomPickers;
    private List<InsectPicker> insectPickers;
    private Controller controller;
    private int actualPlayerIdx = 0;
    

    private String askName(String msg){
        System.out.print(msg);
        
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        return scanner.nextLine();
    }

    public PlayerHandler(int mushroomPickerCount, int insectPickerCount, Controller ctrl) {
        this.mushroomPickers = new ArrayList<>();
        this.insectPickers = new ArrayList<>();
        this.controller = ctrl;

        System.out.println("#### Player selection ####");
        for (int i = 0; i < mushroomPickerCount; i++) {
            String name = askName("Enter the name of mushroom picker #" + (i + 1) + ": ");
            mushroomPickers.add(new MushroomPicker(name, controller));
        }

        for (int i = 0; i < insectPickerCount; i++) {
            String name = askName("Enter the name of insect picker #" + (i + 1) + ": ");
            insectPickers.add(new InsectPicker(name, controller));
        }

        controller.setGameRunning(true);
    }

    /**
     * NextPlayer():
     * Lépteti a soron következő játékosindexet és lekezeli az új körök kezdetét, amiről szól a Controller osztálynak is.
    */
    /* 
    public void nextPlayer() {
        controller.actualPlayerIdx = (controller.actualPlayerIdx + 1) % (mushroomPickers.size() + insectPickers.size());
        if(controller.actualPlayerIdx < mushroomPickers.size()){
            mushroomPickers.get(controller.actualPlayerIdx).startTurn();
        } else {
            insectPickers.get(controller.actualPlayerIdx - mushroomPickers.size()).startTurn();
        }
    }
    */
    //TODO: implementáljuk a startTurn() metódust a MushroomPicker és InsectPicker osztályokban, hogy a játékosok tudjanak lépni


    /**
     * Ha a visszatérési érték true, akkor a játékos gombász, ha false, akkor rovarász.
     * @return
     */
    public boolean actualPlayerIsMushroomPicker() {
        return controller.actualPlayerIdx < mushroomPickers.size();
    }

    public Player getActualPlayer() {
        for (int i = 0; i<mushroomPickers.size(); i++) {
            if (i == controller.actualPlayerIdx) {
                return mushroomPickers.get(i);
            }
        }
        for (int i = 0; i<insectPickers.size(); i++) {
            if (mushroomPickers.size()-i == controller.actualPlayerIdx) {
                return insectPickers.get(i);
            }
        }

        return null; // Ha nem található játékos, akkor null-t ad vissza
    }

    public String getWinner() {
        return "[Ird at meg nem mukodik, ha nem nyer akkor 'No winner yet']";
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
