package Userinterface;

import Controller.Controller;
import Model.Insect;
import Model.Mushroom;
import Model.Tecton;
import Model.Line;
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

        /**
         * /create-insect <tecton>
         * Leírás: Rovar lehelyezése a paraméterként tekton azonosítóval rendelkezó tektonra.
         * Opciók: 
         * -i <azonosító> : Ha a rovart előre meghatározott azonosítóval szeretnénk létrehozni. Ezzel lesz a játékosokhoz rendelve.
         * -e <effekt típusa>:
         * slow: Lassított rovart hoz létre.
         * frozen: Fagyott  rovart hoz létre.
         * fast: Gyorsított  rovart hoz létre.
         * exhausting: Kimerült  rovart hoz létre.
         */
        commands.put("/create-insect", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String id = getOption(options, "-i", "i"+controller.getAllInsect().size());
                String tectonId = args[0];
                String effectType = getOption(options, "-e", "normal");
                

                Insect insect = new Insect();

                Tecton tecton = controller.getTectonById(tectonId);
                if (tecton == null) {
                    System.out.println("Tecton with ID " + tectonId + " not found.");
                    return;
                }else {
                    System.out.println("Tecton with ID " + tectonId + " found.");
                    insect.setTecton(tecton);
                    System.out.println("Insect moved to tecton with ID " + tectonId);
                }

                if (effectType.equalsIgnoreCase("slow")) {
                    insect.setSpeed(1);
                } else if (effectType.equalsIgnoreCase("frozen")) {
                    insect.setCanMove(false);
                    insect.setSpeed(0);
                } else if (effectType.equalsIgnoreCase("fast")) {
                    insect.setSpeed(3);
                } else if (effectType.equalsIgnoreCase("exhausting")) {
                    insect.setCanCut(false);
                } else if (effectType.equalsIgnoreCase("normal")) {
                    //nem szűkséges megadni, mert az insect konstruktora alaphelyzetbe állítva hozza létre a rovart
                }else {
                    System.out.println("Unknown effect type: " + effectType);
                }

                controller.addInsect(id, insect);
                tecton.addInsect(insect);

            }
        });


        /**
         * /add-effect <insect> <effect>
         * Leírás: A effekt adása a kiválasztott rovarnak. Paraméterként át kell adni a rovar azonosítóját, amelynek az effektet szeretnénk adni, és az effekt típusát.
         * slow: A rovar lassabban tud mozogni.
         * frozen: A rovar nem tud mozogni.
         * fast: A rovar gyorsabban tud mozogni.
         * exhausting: A rovar nem tud fonalat vágni.
        */
        commands.put("/add-effect", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String insectId = args[0];
                String effectType = args[1];

                Insect insect = controller.getInsectById(insectId);
                if (insect == null) {
                    System.out.println("Insect with ID " + insectId + " not found.");
                    return;
                } else {
                    System.out.println("Insect with ID " + insectId + " found.");
                }

                if (effectType.equalsIgnoreCase("slow")) {
                    insect.setSpeed(1);
                } else if (effectType.equalsIgnoreCase("frozen")) {
                    insect.setCanMove(false);
                    insect.setSpeed(0);
                } else if (effectType.equalsIgnoreCase("fast")) {
                    insect.setSpeed(3);
                } else if (effectType.equalsIgnoreCase("exhausting")) {
                    insect.setCanCut(false);
                } else {
                    System.out.println("Unknown effect type: " + effectType);
                }
            }
        });


        /**
         * /reset-effect <rovar>
         * Leírás: A paraméterként átadott rovarazonosítóval rendlekező rovarról eltávolítja az effekteket.
        */
        commands.put("/reset-effect", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String insectId = args[0];

                Insect insect = controller.getInsectById(insectId);
                if (insect == null) {
                    System.out.println("Insect with ID " + insectId + " not found.");
                    return;
                } else {
                    System.out.println("Insect with ID " + insectId + " found.");
                }

                insect.setCanCut(true);
                insect.setCanMove(true);
                insect.setSpeed(2);
            }
        });



        /**
         * /create-mushroom <tecton>
         * Leírás: Gombatest létrehozása egy adtott tektonon. Ehhez nem szükséges semmilyen előfeltétel megléte, ami a gombatest növesztéséhez kell. Paraméterként át kell adni a a tekton azonosítóját, amin a gombatest lesz.
         * Opciók: 
         * -i <azonosító> : Gombaazonosító megadása, alapértelmezetten 1-es azonosítóval jön létre.
         * -sp <spóra szám> : Hány spórával szeretnénk létrehozni a gombatestet. Alapértelmezetten 5 spórával rendelkezik.
        */
        commands.put("/create-mushroom", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
            String id = getOption(options, "-i", "1");
            String tectonId = args[0];
            int sporeCount = Integer.parseInt(getOption(options, "-sp", "5"));

            Tecton tecton = controller.getTectonById(tectonId);
            if (tecton == null) {
                System.out.println("Tecton with ID " + tectonId + " not found.");
                return;
            } else {
                System.out.println("Tecton with ID " + tectonId + " found.");
            }

            Mushroom mushroom = new Mushroom(Integer.parseInt(id), tecton);
            mushroom.setSporeCount(sporeCount); // Set the spore count based on the -sp option
            controller.addMushroom("m" + controller.getAllMushroom().size(), mushroom);
            tecton.setMyMushroom(mushroom);
            }
        });


        /**
         * /setlevel-mushroom <mushroom> <kor>
         * Leírás: Alkalmas arra, hogy egy meglévő gombatest életkorát beállítsa, 
         * azaz a spóraszámát határozza meg az adminisztrátor, ezzel feltételezve, 
         * hogy a paraméterként kapott gombatest dobott már el spórákat és “öregedni” kezdett. 
         * A “<kor>” maximális értéke 3 lehet, a minimális érték pedig 0, azaz a gombatest éppen meghal. 
         * Ezt az értéket 2-re állítva éri el a gombatest azt a kort, mikor képes a szomszédos tektonok 
         * szomszédaira is gombaspórát dobni
        */
        commands.put("/setlevel-mushroom", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String id = args[0];
                int level = Integer.parseInt(args[1]);

                Mushroom mushroom = controller.getMushroomById(id);
                if (mushroom == null) {
                    System.out.println("Mushroom with ID " + id + " not found.");
                    return;
                } else {
                    System.out.println("Mushroom with ID " + id + " found.");
                }

                if (level < 0 || level > 3) {
                    System.out.println("Invalid level. Level must be between 0 and 3.");
                    return;
                }

                mushroom.setLevel(level);
            }
        });



        /**
         * /create-line <tecton1> <tecton2>
         * Leírás: Gombafonal létrehozása két tekton között. Ehhez nem szükséges semmilyen előfeltétel megléte, ami a gombafonál növesztéséhez kell. Paraméterként át kell adni a két tekton azonosítóját, amik közé a fonal fog kerülni.
         * Opciók: 
         * -i <azonosító> : A gombafonal gombaazonosítójának megadásához.
        */
        commands.put("/create-line", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String id = getOption(options, "-i", "1");
                String tectonId1 = args[0];
                String tectonId2 = args[1];

                Tecton tecton1 = controller.getTectonById(tectonId1);
                Tecton tecton2 = controller.getTectonById(tectonId2);

                if (tecton1 == null || tecton2 == null) {
                    System.out.println("One or both tectons not found.");
                    return;
                }

                // Create the line and add it to both tectons
                Line line = new Line(tecton1, tecton2, Integer.parseInt(id));
                controller.addLine(id,line);
            }
        });


        

        /*
        commands.put("/load", new LoadCommand(controller));
        commands.put("/save", new SaveCommand(controller));
        commands.put("/compare", new CompareCommand(controller));
        commands.put("/help", new HelpCommand());
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
