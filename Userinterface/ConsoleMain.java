package Userinterface;

import Controller.Controller;
import java.io.BufferedInputStream;
import java.util.Scanner;

public class ConsoleMain {
    /* - Privát attribútumok*/
    private Controller controller;
    private CommandProccessor cmd;

    /* - Belépési pont (main)*/
    public static void main(String[] args) {
        // Create an instance of the CommandProcessor and start the command loop
        Controller controller = new Controller();
        CommandProccessor cmd = new CommandProccessor(controller);

        Scanner scanner = new Scanner(new BufferedInputStream(System.in));

        while(true) {
            cmd.ExecuteCommand(scanner.nextLine());
        }
    }
}
