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

    private String askName(String msg){
        System.out.print(msg);
        
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        return scanner.nextLine();
    }

    public PlayerHandler(int mushroomPickerCount, int insectPickerCount) {
        this.mushroomPickers = new ArrayList<>();
        this.insectPickers = new ArrayList<>();
        System.out.println("#### Player selection ####");
        for (int i = 0; i < mushroomPickerCount; i++) {
            String name = askName("Enter the name of mushroom picker #" + (i + 1) + ": ");
            mushroomPickers.add(new MushroomPicker(name, controller));
        }

        for (int i = 0; i < insectPickerCount; i++) {
            String name = askName("Enter the name of insect picker #" + (i + 1) + ": ");
            insectPickers.add(new InsectPicker(name, controller));
        }
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
        return actualPlayerIsMushroomPicker() ? mushroomPickers.get(controller.actualPlayerIdx) : insectPickers.get(controller.actualPlayerIdx - mushroomPickers.size());
    }

    
    
}
