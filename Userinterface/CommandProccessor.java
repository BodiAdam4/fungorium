package Userinterface;

import java.lang.ModuleLayer.Controller;
import java.util.HashMap;

public class CommandProccessor {
    /* - Privát attribútumok*/
    private HashMap<String, Command> commands;
    private Controller controller;

    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public CommandProccessor(Controller controller) {
        this.controller = controller;
        this.commands = new HashMap<>();


        /*
        commands.put("/load", new LoadCommand(controller));
        commands.put("/save", new SaveCommand(controller));
        commands.put("/compare", new CompareCommand(controller));
        commands.put("/help", new HelpCommand());
        commands.put("/create-tecton", new CreateTectonCommand(controller));
        */

    }


    /* - Getter/Setter metódusok*/
    /* - Egyéb metódusok*/

    /*
    public void inputCommand(String inputcommand){
        String[] command = inputcommand.split(" ");
        if (commands.containsKey(command[0])) {
            commands.get(command[0]).execute(command);
        } else {
            System.out.println("Unknown command: " + command[0]);
        }
    }
    */

    public void getInput(){

    }
}
