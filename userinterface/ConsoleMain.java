package userinterface;

import java.io.BufferedInputStream;
import java.util.Scanner;

import controller.Controller;

public class ConsoleMain {

    /* - Belépési pont (main)*/
    public static void main(String[] args) {
        // Create an instance of the CommandProcessor and start the command loop
        Controller controller = new Controller();
        CommandProcessor cmd = new CommandProcessor(controller);

        Scanner scanner = new Scanner(new BufferedInputStream(System.in));

        while(scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if(input.equals("/exit")) {
                System.out.println("Exiting the game.");
                break;
            }
            cmd.ExecuteCommand(input);
        }
        scanner.close();
    }
}
