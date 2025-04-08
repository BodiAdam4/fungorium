package Userinterface;

import Controller.Controller;
import Model.Insect;
import Model.Mushroom;
import Model.Tecton;
import Model.Line;
import Model.Spore;
import Model.TectonInfertile;
import Model.TectonOnlyLine;
import Model.TectonTime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
         * /load <elérési út>
         * Leírás: Játék betöltése mentett állapotból. Paraméterként el kell adni a mentést tartalmazó parancsfájl elérési útját. Sorra végrehajtódnak az itt tárolt parancsok.
        */
        commands.put("/load", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String filePath = args[0];
                try (
                BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(filePath));
                    String line;
                    while ((line = reader.readLine()) != null) {

                        ExecuteCommand(line);
                    }
                    reader.close();
                    
                } catch (Exception e) {
                    // TODO: handle exception
                }

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
         * /move <insect> <tecton>
         * Leírás: A rovarász elmozdíthatja a rovarját egy tektonon állva, egy vele gombafonalakkal összekapcsolt tektonra. 
         * Paraméterként meg kell adni a rovar azonosítóját, amivel mozogni szeretnénk és a tekton azonosítóját amire szeretnénk mozgatni.
         * 
        */
        commands.put("/move", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String insectId = args[0];
                String tectonId = args[1];

                Insect insect = controller.getInsectById(insectId);
                if (insect == null) {
                    System.out.println("Insect with ID " + insectId + " not found.");
                    return;
                } else {
                    System.out.println("Insect with ID " + insectId + " found.");
                }

                Tecton tecton = controller.getTectonById(tectonId);
                if (tecton == null) {
                    System.out.println("Tecton with ID " + tectonId + " not found.");
                    return;
                } else {
                    System.out.println("Tecton with ID " + tectonId + " found.");
                }

                insect.move(tecton);
                System.out.println("Insect moved form " + controller.getTectonId(insect.getTecton()) + " to " + tectonId);
                //TODO: valami miatt a rovar át tud menni olyan tectonra is, ami nincs összeköttetéssel a kiinduló tectonnal.
            }
        });


        /**
         * /cut-line <insect> <line>
         * Leírás: Gombafonal elvágása azon a tektonon, ahol a rovar tartózkodik. 
         * Paraméterként át kell adni, hogy melyik a tektonhoz tartozó gombafonalat szeretnénk elvágni és a rovar azonosítóját, 
         * amivel a műveletet végre szeretnénk hajtani.  Ha a rovar “exhausting” effekt hatása alatt áll akkor nem tudja ezt megtenni.elvágni.
        */
        commands.put("/cut-line", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String insectId = args[0];
                String lineId = args[1];

                Insect insect = controller.getInsectById(insectId);
                if (insect == null) {
                    System.out.println("Insect with ID " + insectId + " not found.");
                    return;
                } else {
                    System.out.println("Insect with ID " + insectId + " found.");
                }

                Line line = controller.getLineById(lineId);
                if (line == null) {
                    System.out.println("Line with ID " + lineId + " not found.");
                    return;
                } else {
                    System.out.println("Line with ID " + lineId + " found.");
                }

                if (insect.getCanCut()) {
                    line.Destroy();
                    System.out.println("Line cut successfully.");
                } else {
                    System.out.println("Insect cannot cut the line due to exhausting effect.");
                }
            }
        });

        //TODO: /eat-spore <insect> <spore>


        /**
         * /grow-line <source_tecton> <destination_tecton>
         * Leírás: Gombafonal növesztése a két tekton között.
         * Paraméterként át kell adni a két tekton azonosítóját, amik között a gombafonalat szeretnénk növeszteni.
        */
        //TODO: Ennek véres a torka
        commands.put("/grow-line", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String id = getOption(options, "-i", "m0");
                String tectonId1 = args[0];
                String tectonId2 = args[1];

                Tecton tecton1 = controller.getTectonById(tectonId1);
                Tecton tecton2 = controller.getTectonById(tectonId2);

                if (tecton1 == null || tecton2 == null) {
                    System.out.println("One or both tectons not found.");
                    return;
                }

                Mushroom mushroom1 = controller.getMushroomById(id);
                mushroom1.growLine(tecton2);
            }
        });


        /**
         * /add-effect <insect> <effect>
         * Leírás: A effekt adása a kiválasztott rovarnak. Paraméterként át kell adni a rovar azonosítóját, amelynek az effektet szeretnénk adni, és az effekt típusát.
         * slow: A rovar lassabban tud mozogni.
         * frozen: A rovar nem tud mozogni.
         * fast: A rovar gyorsabban tud mozogni.
         * exhausting: A rovar nem tud fonalat vágni.
         * //TODO: duplicate : Duplikáló hatással lesz a spóra a rovarra. Látrejön egy új rovar, mely független az előzőtől, és ugyan ahoz a rovarászhoz fog tartozni, azaz a playerId-ja megyegyezik azzal a rovarral, aki megette ezt a spórát
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


        /**
         * /add-spore <tecton>
         * Leírás: Spórák hozzáadása a tektonhoz.
         * Opciók: 
         * -sp <spóra darabszáma> : Hány darab spótát szeretnénk az adott tektonra elhelyezni.
         * -i <gombaazonosító> : Azon gombatest azonosítója, amely az adott spórát eldobja.
         * -t <típus> :
         * slow : Lassító hatással lesz a spóra a rovarra.
         * frozen : Fagyasztó hatással lesz a spóra a rovarra.
         * fast : Gyorsító hatással lesz a spóra a rovarra.
         * exhausting : Kimerült hatással lesz a spóra a rovarra.
         * duplicate : Duplikáló hatással lesz a spóra a rovarra. Látrejön egy új rovar, mely független az előzőtől, és ugyan ahoz a rovarászhoz fog tartozni, azaz a playerId-ja megyegyezik azzal a rovarral, aki megette ezt a spórát
         * ha nem adjuk meg a -t opciót, akkor alapértelmezetten a "normal" típusú spórát helyezi el.
        */
        //TODO: Ennek véres a torka
        commands.put("/add-spore", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String id = getOption(options, "-i", "1");
                String tectonId = args[0];
                int sporeCount = Integer.parseInt(getOption(options, "-sp", "1"));
                String type = getOption(options, "-t", "normal");

                int valueIsType;
                if (type.equalsIgnoreCase("normal")) {
                    valueIsType = 1;
                } else if (type.equalsIgnoreCase("frozen")) {
                    valueIsType = 6;
                } else if (type.equalsIgnoreCase("fast")) {
                    valueIsType = 7;
                } else if (type.equalsIgnoreCase("exhausting")) {
                    valueIsType = 8;
                } else if (type.equalsIgnoreCase("duplicate")) {
                    valueIsType = 9;
                } else {
                    if (Integer.parseInt(type) % 1 == 0) {
                        valueIsType = Integer.parseInt(type);
                        
                    }else {
                        System.out.println("Unknown type: " + type);
                        return;
                    }
                }
                Tecton tecton = controller.getTectonById(tectonId);
                if (tecton == null) {
                    System.out.println("Tecton with ID " + tectonId + " not found.");
                    return;
                } else {
                    System.out.println("Tecton with ID " + tectonId + " found.");
                }

                Spore spore = new Spore(Integer.parseInt(id),valueIsType);
                for (int i = 0; i < sporeCount; i++) {
                    tecton.getSporeContainer().addSpores(spore); // Add spores to the spore container of the tecton
                    Mushroom mushroom = controller.getMushroomById(id);
                    mushroom.setSporeCount(mushroom.getSporeCount() - sporeCount); // Increase the spore count of the mushroom
                }
                //spore.setType(type); // Set the type based on the -t option
                //spore.setType(type); // Set the type based on the -t option
            }
        });
        

        /*
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
