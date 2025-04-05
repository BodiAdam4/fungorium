package Userinterface;

import Controller.Controller;
import Model.Tecton;
import Model.TectonInfertile;
import Model.TectonOnlyLine;
import Model.TectonTime;
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

        commands.put("/create-tecton", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String id = getOption(options, "-i", "t"+controller.getAllTecton().size());
                int sporeCount = Integer.parseInt(getOption(options, "-sp", "0"));
                String type = getOption(options, "-t", "tecton");
                String neighborList = getOption(options, "-n", "");
                
                String[] neighbors = new String[0];

                if (!neighborList.equals(""))
                    neighbors = neighborList.split(";");

                Tecton newTecton = null;

                if (type.equalsIgnoreCase("time")){
                    newTecton = new TectonTime();
                }
                else if (type.equalsIgnoreCase("infertile")){
                    newTecton = new TectonInfertile();
                }
                else if (type.equalsIgnoreCase("onlyline")){
                    newTecton = new TectonOnlyLine();
                }
                else if (type.equalsIgnoreCase("keepalive")){
                    //newTecton = new ();
                    //TODO: Tectonkeep alive elkészítése
                }
                else {
                    newTecton = new Tecton();
                }

                if (neighbors.length > 0) {
                    for (String tectonId : neighbors) {
                        Tecton neighbor = controller.getTectonById(tectonId);
                        if (neighbor != null) {
                            newTecton.setNeighbors(neighbor);
                            neighbor.setNeighbors(newTecton); // Assuming bidirectional connection
                        } else {
                            System.out.println("Neighbor tecton with ID " + tectonId + " not found.");
                        }
                    }
                }

                //TODO: Spóraszám beállítása
                controller.addTecton(id, newTecton);
            }
        });

        
        commands.put("/list", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String id = getOption(options, "-i", "t"+controller.getAllTecton().size());
                int sporeCount = Integer.parseInt(getOption(options, "-sp", "0"));
                String type = getOption(options, "-t", "tecton");
                String neighbors = getOption(options, "-n", "");
                
                System.out.println(TestTools.GetStatus(controller));
            }
        });

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

    public void ExecuteCommand(String command) {
        String baseCommand = command.split(" ")[0];
        int optionFieldIndex = command.indexOf(" -");

        HashMap<String, String> options = new HashMap<>();

        if (optionFieldIndex != -1) {
            optionFieldIndex++;
            String[] optionsRaw = command.substring(optionFieldIndex, command.length()).split(" ");
            for (int i = 0; i<optionsRaw.length; i+=2){
                options.put(optionsRaw[i].strip(), optionsRaw[i+1].strip());
            }
        }
        else {
            optionFieldIndex = command.length();
        }

        String[] plainArgs = command.substring(command.indexOf(" ")+1, optionFieldIndex).split(" ");

        if (commands.containsKey(baseCommand)) {
            commands.get(baseCommand).execute(plainArgs, options);
        } else {
            System.out.println("Unknown command: " + command);
        }

 
    }

    public void getInput(){

    }
}
