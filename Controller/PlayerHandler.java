package Controller;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerHandler {
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
    
}
