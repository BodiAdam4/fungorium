package Userinterface;

import Controller.Controller;
import Controller.MushroomPicker;
import Model.Insect;
import Model.Line;
import Model.Mushroom;
import Model.Spore;
import Model.SporeExhausting;
import Model.SporeFast;
import Model.SporeFrozen;
import Model.SporeSlow;
import Model.Tecton;
import Model.TectonInfertile;
import Model.TectonKeepAlive;
import Model.TectonOnlyLine;
import Model.TectonTime;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandProccessor {
    /* - Privát attribútumok*/
    private HashMap<String, Command> commands;

    private List<String> commandHistory;

    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public CommandProccessor(Controller controller) {
        this.commands = new HashMap<>();
        this.commandHistory = new ArrayList<>();

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
                    newTecton = new TectonKeepAlive();
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

        /* 
        commands.put("/list", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String id = getOption(options, "-i", "t"+controller.getAllTecton().size());
                int sporeCount = Integer.parseInt(getOption(options, "-sp", "0"));
                String type = getOption(options, "-t", "tecton");
                String neighbors = getOption(options, "-n", "");
                
                System.out.println(TestTools.GetStatus(controller));
            }
        });
        */

        /**
         * /list
         * Leírás: A játék objektumainak kilistázása.
         * Opciók: 
         * -tecton : Ha csak a tektonokat szeretnénk kilistázni.
         * -line : Ha csak a gombafonalakat szeretnénk kilistázni.
         * -insect : Ha csak a rovarokat szeretnénk kilistázni.
         * -mushroom : Ha csak a gombatesteket szeretnénk kilistázni.
         * -s <tecton> : Ha egy adott tektonon lévő spórákat szeretnénk kilistázni, ehhez paraméterként át kell adni a tekton azonosítóját.
        */

        /*
         * boolean logFile = getOption(options, "-log", "false").equalsIgnoreCase("true");
            boolean commandFile = getOption(options, "-cmd", "false").equalsIgnoreCase("true");


            System.out.println("Saving game to " + savePath);
            System.out.println("Log file: " + logFile);
            System.out.println("Command file: " + commandFile);

            if (logFile) {
                TestTools.writeLogToFile(savePath, controller);
            }
         */
         /**
         * /list Kilistázza a játékban található összes objektumot
         * Opciók: 
         *-tecton : Ha csak a tektonokat szeretnénk kilistázni.
         *-line : Ha csak a gombafonalakat szeretnénk kilistázni.
         *-insect : Ha csak a rovarokat szeretnénk kilistázni.
         *-mushroom : Ha csak a gombatesteket szeretnénk kilistázni.
         *-s <tecton> : Ha egy adott tektonon lévő spórákat szeretnénk kilistázni, ehhez paraméterként át kell adni a tekton azonosítóját.

         * Leírás: Játék betöltése mentett állapotból. Paraméterként el kell adni a mentést tartalmazó parancsfájl elérési útját. Sorra végrehajtódnak az itt tárolt parancsok.
        */
        commands.put("/list", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String tectonId = getOption(options, "-s", null);
                boolean tectons = getOption(options, "-tecton", "false").equalsIgnoreCase("true");
                boolean line = getOption(options, "-line", "false").equalsIgnoreCase("true");
                boolean insect = getOption(options, "-insect", "false").equalsIgnoreCase("true");
                boolean mushroom = getOption(options, "-mushroom", "false").equalsIgnoreCase("true");
                //boolean tectons = getOption(options, "-tecton", "false").equalsIgnoreCase("true");

                if (tectons) {
                    for (Tecton tecton : controller.getAllTecton().values()) {
                        String tectonIds= controller.getTectonId(tecton);
                        System.out.println(TestTools.GetTectonStatus(controller, tectonIds));
                    }
                    
                } else if (line) {
                    for (Line linet : controller.getAllLine().values()) {
                        String lineIds= controller.getLineId(linet);
                        System.out.println(TestTools.GetLineStatus(controller, lineIds));
                    }
                } else if (insect) {
                    for (Insect insectt : controller.getAllInsect().values()) {
                        String insectIds= controller.getInsectId(insectt);
                        System.out.println(TestTools.GetInsectStatus(controller, insectIds));
                    }
                } else if (mushroom) {
                    for (Mushroom mush : controller.getAllMushroom().values()) {
                        String mushroomIds= controller.getMushroomId(mush);
                        System.out.println(TestTools.GetMushroomStatus(controller, mushroomIds));
                    }
                    //TODO: Spóra kiírását meg kell csinálni, mert ez a megoldás fals
                }else if (args[0].equals("-s") && tectonId != null) {
                    Tecton tecton = controller.getTectonById(tectonId);
                    if (tecton != null) {
                        for (Tecton tectont : controller.getAllTecton().values()) {
                            TestTools.getSporesOnTecton(tectont);
                            System.out.println("Spores on tecton processed successfully.");
                        }
                    } else {
                        System.out.println("Tecton with ID " + tectonId + " not found.");
                    }
                }else {
                    System.out.println(TestTools.GetStatus(controller));
                }
            }
            public String toString() {
                return "Lists all the object in the game.\n\tUsing: /list";
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
            public String toString() {
                return "Loads a game from a previous save.\n\tUsing: /load <file-path>";
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
                
                String id = getOption(options, "-i", "t"+controller.getAllTecton().size());
                int insectId = Integer.parseInt(getOption(options, "-iid", "1"));
                String tectonId = args[0];
                String effectType = getOption(options, "-e", "normal");
                

                Insect insect = new Insect();
                insect.setInsectId(insectId);
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
            public String toString() {
                return "Creates an insect on a specified tecton with optional effects (slow, frozen, fast, exhausting) and id.\n\tUsing: /create-insect <TectonID> <insectID> <effect>";
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
            }
            @Override
            public String toString() {
                return "Moves an Insect from a tecton to another if a line exists between the tectons.\n\tUsing: /move <insectID> <TectonID>";
            }
        });


        /**
         * /set-neigbors <tectonFrom> <tectonToList>
         * Leírás: 
         * A paratéterként kapott "tectonFrom" tektonnak beállítja a szomszédait, azaz olyan tektonokat, 
         * melyek közvetlenül mellette helyezkednek el. A második paraméterben egy lista adható meg, 
         * hogy egyszerre több szomszédos tektont is be lehessen állítani. Ide ";"-vel elválasztva sorolhatjuk fel a szomszédokat.
        */
        commands.put("/set-neigbors", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String tectonId = args[0];
                String[] neighbors = args[1].split(";");

                Tecton tecton = controller.getTectonById(tectonId);
                if (tecton == null) {
                    System.out.println("Tecton with ID " + tectonId + " not found.");
                    return;
                } else {
                    System.out.println("Tecton with ID " + tectonId + " found.");
                }

                for (String neighborId : neighbors) {
                    Tecton neighbor = controller.getTectonById(neighborId);
                    if (neighbor != null) {
                        tecton.setNeighbors(neighbor);
                        neighbor.setNeighbors(tecton);
                    } else {
                        System.out.println("Neighbor tecton with ID " + neighborId + " not found.");
                    }
                }
            }
            @Override
            public String toString() {
                return "Sets neighbors for the specified tecton.\n\tUsing: /set-neigbors <tectonFrom> <tectonTo1;tectonTo2;...tectonToN>";
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
            @Override
            public String toString() {
                return "Cuts line on the tecton that the given insect is on. You also have to specify which line you want to be cut.\n\tUsing: /cut-line <insect> <line>";
            }
        });

        //TODO: /eat-spore <insect> <spore>


        /**
         * /grow-line <source_tecton> <destination_tecton>
         * Leírás: Gombafonal növesztése a két tekton között.
         * Paraméterként át kell adni a két tekton azonosítóját, amik között a gombafonalat szeretnénk növeszteni.
        */
        commands.put("/grow-line", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                int id = Integer.parseInt(getOption(options, "-mid", "1"));
                String sourceTectonId = args[0];
                String destinationTectonId = args[1];

                Tecton from = controller.getTectonById(sourceTectonId);
                Tecton to = controller.getTectonById(destinationTectonId);

                boolean running = controller.isGameRunning();
                
                if (running) {
                    MushroomPicker mpicker = (MushroomPicker)(controller.getPlayerHandler().getActualPlayer());
                    mpicker.growLine(from, to);
                    
                    
                } else{

                    if(from.hasBody(id)) {
                        from.getMyMushroom().growLine(to);

                    //Ha van a tektonon line, akkor a line növeszti a gombafonalat a Line osztály growLine() metódusával
                    } else {
                        for (Line line : from.getConnections()) {
                            if (line.getId() == id) {
                                line.growLine(from,to);
                                return;
                            }
                        }
                    }
                }
            }

            @Override
            public String toString() {
                return "Grows line between two given tectons. \n\tUsing: /grow-line <source_tecton> <destination_tecton>";
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
            @Override
            public String toString() {
                return "Add a specific effect to a given insect. You can select from the following list: {slow, frozen, fast, exhausting, duplicate} \n\tUsing: /add-effect <insect> <effect>";
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
            @Override
            public String toString() {
                return "Remove any acting effect froma given insect.\n\tUsing: /reset-effect <insect>";
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
            String mushroomId = getOption(options, "-mid", "1");
            String id = getOption(options, "-i", "m"+controller.getAllMushroom().size());
            String tectonId = args[0];
            int sporeCount = Integer.parseInt(getOption(options, "-sp", "5"));

            Tecton tecton = controller.getTectonById(tectonId);
            if (tecton == null) {
                System.out.println("Tecton with ID " + tectonId + " not found.");
                return;
            } else {
                System.out.println("Tecton with ID " + tectonId + " found.");
            }

            Mushroom mushroom = new Mushroom(Integer.parseInt(mushroomId), tecton);
            mushroom.setSporeCount(sporeCount); // Set the spore count based on the -sp option
            controller.addMushroom(id, mushroom);
            tecton.setMyMushroom(mushroom);
            }
            @Override
            public String toString() {
                return "You can create a mushroom on a given tecton. Also you have the option to set the mushroomId and how many spore you want within the mushroom. Without given value, the mushroom has 5 spores.\n\tUsing: /create-mushroom <tecton> -mid <mushroomId> -sp <sporeCount>";
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
            @Override
            public String toString() {
                return "You can set the age of a given mushroom. The age must be between 0 and 3.(Inclusive)\n\tUsing: /setlevel-mushroom <mushroom> <age>";
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
                String id = getOption(options, "-i", "l"+controller.getAllLine().size());
                String mushroomId = getOption(options, "-mid", "1");
                String tectonId1 = args[0];
                String tectonId2 = args[1];

                Tecton tecton1 = controller.getTectonById(tectonId1);
                Tecton tecton2 = controller.getTectonById(tectonId2);

                if (tecton1 == null || tecton2 == null) {
                    System.out.println("One or both tectons not found.");
                    return;
                }

                // Create the line and add it to both tectons
                Line line = new Line(tecton1, tecton2, Integer.parseInt(mushroomId));
                controller.addLine(id,line);
            }
            @Override
            public String toString() {
                return "Creates a line between two given tecton. Also you have to option to give the mushroomId for the line.\n\tUsing: /create-line <tecton1> <tecton2> -mid <msuhroomId>";
            }
        });

        /**
         * TODO:TECTON TÖRÉS
         */
        commands.put("/tecton-break", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String tectonId = args[0];
                Tecton tecton = controller.getTectonById(tectonId);

                
                tecton.breakTecton();
            }

            @Override
            public String toString() {
                return "Breaks the tecton, removing all lines from it.\n\tUsing: /tecton-break <tectonId>";
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
        commands.put("/add-spore", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String tectonId = args[0];
                int id = Integer.parseInt(getOption(options, "-mid", "1"));
                int sporeCount = Integer.parseInt(getOption(options, "-sp", "1"));
                String type = getOption(options, "-t", "normal");

                Spore spore = null;

                if (type.equalsIgnoreCase("frozen")) {
                    spore = new SporeFrozen(id,2);
                } else if (type.equalsIgnoreCase("fast")) {
                    spore = new SporeFast(id, 2);
                } else if (type.equalsIgnoreCase("slow")) {
                    spore = new SporeSlow(id, 2);
                }else if (type.equalsIgnoreCase("exhausting")) {
                    spore = new SporeExhausting(id, 2);
                } else if (type.equalsIgnoreCase("duplicate")) {
                    //TODO: Duplikáló hatású spóra létrehozása
                } else {
                    spore = new Spore(id, 2);
                }

                Tecton tecton = controller.getTectonById(tectonId);
                if (tecton == null) {
                    System.out.println("Tecton with ID " + tectonId + " not found.");
                    return;
                } else {
                    System.out.println("Tecton with ID " + tectonId + " found.");
                }

                for (int i = 0; i < sporeCount; i++) {
                    tecton.getSporeContainer().addSpores(spore);
                }
            }
            @Override
            public String toString() {
                return "Adds a spore to the given tecton. Also have the option, to choose how many spores you want to add, with what mushroomId aswell. You can also choose the type of the spore from the following list:{slow, frozen, fast, exhausting, duplicate}\n\tUsing: /add-spore <tecton> -sp <sporeCount> -mid <mushroomId> -t <type>";
            }
        });

        //TODO: Leírás
        commands.put("/eat-spore", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String insectId = args[0];
                Insect insect = controller.getInsectById(insectId);

                insect.eatSpores(1);
            }

            @Override
            public String toString() {
                return "Eats a spore from the tecton.\n\tUsing: /eat-spore <insectId>";
            }
        });

        //TODO: Leírás
        commands.put("/throw-spore", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String mushroomId = args[0];
                String tectonId = args[1];
                Mushroom mushroom = controller.getMushroomById(mushroomId);
                Tecton tecton = controller.getTectonById(tectonId);

                mushroom.throwSpores(tecton, 1);
            }

            @Override
            public String toString() {
                return "Throws a spore from the given mushroom to the given tecton. \n\tUsing: /throw-spore <mushroom> <tecton>";
            }
        });

        //TODO: Leírás
        commands.put("/build-mushroom", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String tectonId = args[0];
                int id = Integer.parseInt(getOption(options, "-mid", "1"));
                Tecton tecton = controller.getTectonById(tectonId);

                if (controller.isGameRunning()) {
                    MushroomPicker mpicker = (MushroomPicker)(controller.getPlayerHandler().getActualPlayer());
                    //mpicker.buildMushroom(tecton);
                    //TODO: Játékmenet alatti építés
                } else {
                    Line selected = null;

                    for (Line line : tecton.getConnections()) {
                        if (line.getId() == id) {
                            selected = line;
                            break;
                        }
                    }

                    if (selected != null) {
                        selected.growMushroom(tecton);
                    } else {
                        System.out.println("Line with ID " + id + " not found on tecton " + tectonId + ".");
                    }
                }
            }

            @Override
            public String toString() {
                return "Builds a mushroom on the tecton.\n\tUsing: /build-mushroom <tectonId> -mid <mushroomId>";
            }
        });


        commands.put("/save", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String savePath = args[0];
                boolean logFile = getOption(options, "-log", "false").equalsIgnoreCase("true");
                boolean commandFile = getOption(options, "-cmd", "false").equalsIgnoreCase("true");


                System.out.println("Saving game to " + savePath);
                System.out.println("Log file: " + logFile);
                System.out.println("Command file: " + commandFile);

                if (logFile) {
                    TestTools.writeLogToFile(savePath, controller);
                }
                else if (commandFile) {
                    String content = commandHistory.stream()
                                        .reduce((s1, s2) -> s1 + "\n" + s2)
                                        .orElse("");
                    try (FileWriter writer = new FileWriter(savePath)) {
                        writer.write(content);
                        System.out.println("Sikeresen kiírva a fájlba: " + savePath);
                    } catch (IOException e) {
                        System.err.println("Hiba történt a fájl írása közben: " + e.getMessage());
                    }
                }
            }
            @Override
            public String toString() {
                return "Save command or log file to the given place. Requires a path to the file and an option -cmd or -log to save the command history or the log file.\n\tUsing: /save <path> -cmd";
            }
        });

        
        commands.put("/help", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                System.out.println("Available commands: ");
                System.out.println("=========================================");
                List<String> sorted = new ArrayList<>(commands.keySet());
                sorted.sort((k1, k2) -> k1.compareTo(k2));
                for (String name : sorted) {
                    System.out.println(name+" : \n\t" + commands.get(name).toString()+"\n");
                }
                System.out.println("=========================================");
            }

            @Override
            public String toString() {
                return "Lists all avaible commands.\n\tUsing: /help";
            }
        });
        
        commands.put("/start", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                int mCount = Integer.parseInt(getOption(options, "-m", "2"));
                int iCount = Integer.parseInt(getOption(options, "-i", "2"));

                controller.StartGame(mCount, iCount);
            }

            @Override
            public String toString() {
                return "Start game with 2 MushroomPicker and 2 InsectPicker and 10 round. Also have the option to change the number of mushroom or insectpicker, the number of the rounds and whether you want a map to be generated or not\n\tUsing: /start -m <mushroomPickerCount> -i <insectPickerCount -k <roundCount> -nomap";
            }
        });
        

        //TODO: Make the "/compare" command with one parameter, and with two parameters
        commands.put("/compare", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String filePath = args[0];
                String filePath2 = getOption(options, "-f", null);
                if(filePath2 == null) {
                    TestTools.compare(filePath, controller);
                }else {
                    TestTools.compare(filePath, filePath2);
                }
            }
        });
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

                if (i+1 >= optionsRaw.length) {
                    options.put(optionsRaw[i].strip(), "true");
                }
                else if (optionsRaw[i+1].charAt(0) == '-') {
                    options.put(optionsRaw[i].strip(), "true");
                    i--;
                }
                else {
                    options.put(optionsRaw[i].strip(), optionsRaw[i+1].strip());
                }
            }
        }
        else {
            optionFieldIndex = command.length();
        }

        String[] plainArgs = command.substring(command.indexOf(" ")+1, optionFieldIndex).split(" ");

        if (commands.containsKey(baseCommand)) {
            commands.get(baseCommand).execute(plainArgs, options);
            if (!baseCommand.equalsIgnoreCase("/save")){
                commandHistory.add(command);
            }
        } else {
            System.out.println("Unknown command: " + command);
        }

    }

}
