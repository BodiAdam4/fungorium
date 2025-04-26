package userinterface;

import controller.Controller;
import java.io.BufferedInputStream;
import java.util.Scanner;

public class ConsoleMain {

    /* - Belépési pont (main)*/
    public static void main(String[] args) {
        // Create an instance of the CommandProcessor and start the command loop

        System.out.println("Welcome to the Fungorium Game!\n" +
                "Type /help to see the game commands.\n" +
                "Type /exit to exit the game.");

        Controller controller = new Controller();
        CommandProcessor cmd = new CommandProcessor(controller);

        Scanner scanner = new Scanner(new BufferedInputStream(System.in));

        while(scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if(input.equals("/exit")) {
                System.out.println("Exiting the game.");
                break;
            }
            try {
                cmd.ExecuteCommand(input);
            } catch (Exception e) {
                System.out.println("Something went wrong :(\nInvalid command or invalid parameters.\n" +
                        "Please check the command and try again.\n" +
                        "Type /help to see the game commands."+e.getMessage());
            }
        }
        scanner.close();
    }
}
