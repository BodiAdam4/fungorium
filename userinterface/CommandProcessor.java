package userinterface;

import controller.Controller;
import controller.InsectPicker;
import controller.MushroomPicker;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Spore;
import model.SporeContainer;
import model.SporeDuplicate;
import model.SporeExhausting;
import model.SporeFast;
import model.SporeFrozen;
import model.SporeSlow;
import model.Tecton;
import model.TectonInfertile;
import model.TectonKeepAlive;
import model.TectonOnlyLine;
import model.TectonTime;
import model.Timer;

public class CommandProcessor {
    /* - Privát attribútumok*/
    private HashMap<String, Command> commands;

    private List<String> commandHistory;

    private List<String> mushroomPickerCommands;
    private List<String> insectPickerCommands;

    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public CommandProcessor(Controller controller) {
        this.commands = new HashMap<>();
        this.commandHistory = new ArrayList<>();

        this.mushroomPickerCommands = new ArrayList<>();
        mushroomPickerCommands.add("/build-mushroom");
        mushroomPickerCommands.add("/eat-insect");
        mushroomPickerCommands.add("/grow-line");
        mushroomPickerCommands.add("/throw-spore");

        this.insectPickerCommands = new ArrayList<>();
        insectPickerCommands.add("/cut-line");
        insectPickerCommands.add("/eat-spore");
        insectPickerCommands.add("/move");


        /**
         * Leírás: Tekton létrehozása.
         * Opciók:
         * -i <azonosító> : Ha a tektont adott azonosítóval szeretnénk létrehozni.
         * -sp <spóraszám> : Ha a tektont már spórákkal akarjuk létrehozni.
         * -t <típus> : Ha különleges típusú tektont szeretnénk létrehozni.
         *      time : Ezen a tektonon egy idő után elhalnak a gombafonalak
         *      infertile : Ezen a tektonon nem képes gombatest nőni.
         *      onlyline : Ezen a tektonon csupán egyfajta gombafonál nőlhet.
         *      keepalive : Ezen a tektonon akkor is életben maradnak a fonalak, ha azok már nincsenek kapcsolatban gombatesttel.
         * -n <tecton1>;<tecton2>;...<tectonX> : Ha a létrehozott tektonhoz be szeretnénk állítani a szomszédokat, 
         *  a kapcsoló után fel kell sorolni pontosvesszővel (;) elválasztva a már létező szomszédos tektonok azonosítóit.
        */
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


                // Lekérjük az adott tecton sporeContainer-jét és hozzáadjuk a spórákat
                if (sporeCount > 0) {
                    Spore[] sporesToAdd = SporeContainer.generateSpores(sporeCount, 1);
                    for (int i = 0; i < sporeCount; i++) {
                        newTecton.getSporeContainer().addSpores(sporesToAdd[i]);
                    }
                }

                controller.addTecton(id, newTecton);
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Creates a new tecton (game tile).
                       \tUsing: /create-tecton [options]
                       \tOptions:
                       \t-i <id>: Set the ID of the new tecton.
                       \t-sp <spore count>: Set the initial spore count on the tecton.
                       \t-t <type>: Set the type of the tecton.
                       \tAvailable types: {time, infertile, onlyline, keepalive}
                       \t-n <tecton1;tecton2;...>: Set neighboring tectons by listing their IDs, separated by semicolons.
                       """;
            }
        });

        
         /**
         * Leírás: Játék betöltése mentett állapotból. Paraméterként el kell adni a mentést tartalmazó parancsfájl elérési útját. Sorra végrehajtódnak az itt tárolt parancsok.
         * Opciók: 
         * -tecton : Ha csak a tektonokat szeretnénk kilistázni.
         * -line : Ha csak a gombafonalakat szeretnénk kilistázni.
         * -insect : Ha csak a rovarokat szeretnénk kilistázni.
         * -mushroom : Ha csak a gombatesteket szeretnénk kilistázni.
         * -s <tecton> : Ha egy adott tektonon lévő spórákat szeretnénk kilistázni, 
         * ehhez paraméterként át kell adni a tekton azonosítóját.
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


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Lists game objects.
                       \tUsing: /list [options]
                       \tOptions:
                       \t\t-tecton: Lists only tectons.
                       \t\t-line: Lists only mushroom lines.
                       \t\t-insect: Lists only insects.
                       \t\t-mushroom: Lists only mushrooms.
                       \t\t-s <tecton>: Lists spores on the given tecton.
                       """;
            }
        });


        /**
         * /load <elérési út>
         * Leírás: Játék betöltése mentett állapotból. 
         * Paraméterként el kell adni a mentést tartalmazó parancsfájl elérési útját. 
         * Sorra végrehajtódnak az itt tárolt parancsok.
        */
        commands.put("/load", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String filePath = args[0];
                List<String> lines = new ArrayList<>();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(filePath));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                    reader.close();
                    
                } catch (Exception e) {

                }
                controller.HardReset();
                for(String line : lines) {
                    System.out.println(line);
                    ExecuteCommand(line);
                }
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: Loads the game object's states from a previously saved state.\n" +
                       "\tUsing: /load <path_to_saved_command_file>\n" +
                       "\tNote: The specified path must point to a valid saved command file.";
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


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            public String toString() {
                return "Description: Creates an insect on the specified tecton.\n" +
                        "\tUsing: /create-insect <TectonID>\n" +
                        "\tOptions:\n" +
                        "\t\t-iid <insectId>: Specifies the insect's identifier. This integer determines to which player the object belongs (e.g., 1 means Player 1).\n" +
                        "\t\t-i <identifier>: Controller identifier.\n" +
                        "\t\t-e <effect type>: Defines the effect type for the insect. Accepted values are: {slow, frozen, fast, exhausting}";
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

                if(controller.isGameRunning()){
                    InsectPicker ipicker = (InsectPicker)(controller.getPlayerHandler().getActualPlayer());
                    ipicker.move(tecton, insect);
                }else{
                    insect.move(tecton);
                }

            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: Moves the specified insect to a new tecton.\n" +
                       "\tUsing: /move <insect> <tecton>\n" +
                       "\tParameters:\n" +
                       "\t\t<insect>: ID of the insect to be moved.\n" +
                       "\t\t<tecton>: ID of the tecton to which the insect will be moved.";
            }
        });


        /**
         * Leírás: A paratéterként kapott "tectonFrom" tektonnak beállítja a szomszédait, 
         * azaz olyan tektonokat, amelyek közvetlenül mellette helyezkednek el. 
         * A második paraméterben egy lista adható meg, hogy egyszerre több szomszédos tektont is be lehessen állítani. 
         * Ide ";"-vel elválasztva sorolhatjuk fel a szomszédokat. Amennyiben csak egy darab szomszédot szeretnénk megadni, 
         * a pontosvessző elhagyandó.
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


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: Sets the neighbors of the specified tecton.\n" +
                       "\tUsing: /set-neighbors <TectonFrom> <TectonTo1;TectonTo2;...;TectonToN>\n" +
                       "\tOptions: None.\n" +
                       "\tNote: The second parameter is a semicolon-separated list of tecton IDs. " +
                       "These will be assigned as direct neighbors of <TectonFrom>. If only one neighbor is specified, the semicolon can be omitted.";
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

                if(controller.isGameRunning()){
                    InsectPicker ipicker = (InsectPicker)(controller.getPlayerHandler().getActualPlayer());
                    ipicker.cutLine(line, insect);
                }else{
                    if (insect.getCanCut()) {
                        line.Destroy();
                        System.out.println("Line cut successfully.");
                    } else {
                        System.out.println("Insect cannot cut the line due to exhausting effect.");
                    }
                }
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: Cuts a mushroom line on the tecton where the specified insect is located.\n" +
                       "\tUsing: /cut-line <insect> <line>\n" +
                       "\tParameters:\n" +
                       "\t\t<insect>: ID of the insect performing the cut.\n" +
                       "\t\t<line>: ID of the mushroom line to be cut.\n" +
                       "\tNote: If the insect is under the 'exhausting' effect, it cannot cut the line.";
            }
        });


        /**
         * Leírás: Gombafonal növesztése két tekton közt. Ehhez szükséges, 
         * hogy az egyik tektonon legyen már a gombászhoz tartozó gombafonal. 
         * Paraméterként meg kell adni a forrás- és a céltektont.
         * Opciók:
         * -mid <mushroomId>: Gombaazonosító. Ennek segítségével adható meg, hogy melyik gombatest fogja 
         *  a fonálnövesztést végrehajtani. Megadja, hogy az adott objektum melyik játékoshoz tartozik. 
         *  Értéke egy int-típusú érték, amely, ha például 1, akkor az adott objektum az 1-es játékoshoz fog tartozni.
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


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: Grows a line between two tectons.\n" +
                       "\tUsing: /grow-line <SourceTecton> <DestinationTecton>\n" +
                       "\tOptions:\n" +
                       "\t\t-mid <mushroomId>: Specifies which mushroom body executes the line-growth. This integer determines the owner player (e.g., 1 means Player 1).\n" +
                       "\tNote: One of the tectons must already have a line belonging to the specified mushroom.";
            }
        });


        /**
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


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Adds an effect to the specified insect.
                       \tUsing: /add-effect <insect> <effect>
                       \tEffect types:
                       \t\tslow: The insect moves slower.
                       \t\tfrozen: The insect cannot move.
                       \t\tfast: The insect moves faster.
                       \t\texhausting: The insect cannot cut lines.
                       """;
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


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Removes all effects from the given insect.
                       \tUsing: /reset-effect <insect>
                       \t<insect>: The ID of the insect whose effects will be removed.
                       """;
            }
        });



        /**
         * Leírás: Gombatest létrehozása egy adtott tektonon. Ehhez nem szükséges semmilyen előfeltétel megléte, 
         * ami a gombatest növesztéséhez kell. Paraméterként át kell adni a a tekton azonosítóját, amin a gombatest lesz.
         * Opciók: 
         * -i <azonosító> : Megadja, hogy a Controllerben milyen néven lehet az adott objektumra hivatkozni. 
         *  Ez egy String-típusú érték kell, hogy legyen!
         * -mid <mushroomId>: Gombaazonosító. Ennek segítségével adható meg, hogy az újonnan létrehozott gombatest 
         *  milyen azonosítóval fog létrejönni. Megadja, hogy az adott objektum melyik játékoshoz tartozik. 
         *  Értéke egy int-típusú érték, amely, ha például 1, akkor az adott objektum az 1-es játékoshoz fog tartozni.
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


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                        Description: Creates a mushroom body on the specified tecton.
                        \tUsing: /create-mushroom <TectonID>
                        \tOptions:
                        \t\t-i <identifier>: Controller identifier.
                        \t\t-mid <mushroomId>: Mushroom ID. An integer that defines which player the object belongs to (e.g., 1 means Player 1).
                        \t\t-sp <spore count>: Number of spores the mushroom should start with. (Defaults to 5.)
                """;
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


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Sets the age of the specified mushroom body.
                       \tUsing: /setlevel-mushroom <mushroom> <age>
                       \tParameters:
                       \t\t<mushroom>: ID of the mushroom body.
                       \t\t<age>: Age of the mushroom body (0-3).
                       """;
            }
        });



        /**
         * Leírás: Gombafonal létrehozása két tekton között. Ehhez nem szükséges semmilyen előfeltétel megléte, 
         * ami a gombafonál növesztéséhez kell. Paraméterként át kell adni a két tekton azonosítóját, 
         * amik közé a fonal fog kerülni.
         * Opciók: 
         * -i <azonosító> :Megadja, hogy a Controllerben milyen néven lehet az adott objektumra hivatkozni. 
         *  Ez egy String-típusú érték kell, hogy legyen!
         * -mid <mushroomId>: Gombaazonosító. Ennek segítségével adható meg, hogy melyik gombatest hajtotta végre 
         *  a fonalnövesztést. Megadja, hogy az adott objektum melyik játékoshoz tartozik. Értéke egy int-típusú érték, 
         *  amely, ha például 1, akkor az adott objektum az 1-es játékoshoz fog tartozni.
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


                //Create the line and add it to both tectons
                Line line = new Line(tecton1, tecton2, Integer.parseInt(mushroomId));
                controller.addLine(id,line);
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Creates a mushroom line between two tectons.
                       \tUsing: /create-line <TectonID1> <TectonID2>
                       \tOptions:
                       \t\t-i <identifier>: Controller identifier. This is a string value used to refer to the line in the Controller.
                       \t\t-mid <mushroomId>: Mushroom identifier. Specifies which mushroom body performed the line creation. 
                       \t\tThis integer determines to which player the object belongs (e.g., 1 means Player 1).
                       """;
            }
        });


        /**
         * Leírás: A paraméterként kapott tekton el fog törni. Ekkor az eredeti tekton felületén a gombatest 
         * megmarad, azonban a gombafonalak elvesznek a tectonról. Az újonnan létrejött tekton pedig szomszédos 
         * lesz azon tektonokkal, amelyekkel az eredeti is szomszédos volt, illetve magával 
         * az eredeti tektonnal is szomszédos lesz.
         */
        commands.put("/tecton-break", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String tectonId = args[0];
                Tecton tecton = controller.getTectonById(tectonId);

                tecton.breakTecton();
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: Breaks the specified tecton.\n" +
                       "\tUsing: /tecton-break <TectonID>\n" +
                       "\tOptions: None.\n" +
                       "\tNote: The mushroom body remains on the original tecton, but all lines are lost. " +
                       "The new tecton will become a neighbor to all tectons that were neighbors of the original, and it will also be adjacent to the original tecton.";
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


                Tecton tecton = controller.getTectonById(tectonId);
                if (tecton == null) {
                    System.out.println("Tecton with ID " + tectonId + " not found.");
                    return;
                } else {
                    System.out.println("Tecton with ID " + tectonId + " found.");
                }


                Spore spore = null;
                for (int i = 0; i<sporeCount; i++){
                    if (type.equalsIgnoreCase("frozen")) {
                        spore = new SporeFrozen(id,2);
                    } else if (type.equalsIgnoreCase("fast")) {
                        spore = new SporeFast(id, 2);
                    } else if (type.equalsIgnoreCase("slow")) {
                        spore = new SporeSlow(id, 2);
                    }else if (type.equalsIgnoreCase("exhausting")) {
                        spore = new SporeExhausting(id, 2);
                    } else if (type.equalsIgnoreCase("duplicate")) {
                        spore = new SporeDuplicate(id, 2);
                    } else {
                        spore = new Spore(id, 2);
                    }

                    tecton.getSporeContainer().addSpores(spore);
                }
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Adds spores to the specified tecton.
                       \tUsing: /add-spore <TectonID>
                       \tOptions:
                       \t\t-sp <sporeCount>: Specifies how many spores to add to the given tecton.
                       \t\t-i <identifier>: Controller identifier.
                       \t\t-mid <mushroomId>: Mushroom identifier. Indicates which mushroom body released the spores. This integer value also defines the owning player (e.g., 1 means Player 1).
                       \t\t-t <type>: Effect type of the spore. Accepted values are: {slow, frozen, fast, exhausting, duplicate}
                       """;
            }
        });


        /**
         * Leírás: Az paraméterként kapott rovar elfogyaszt egy gombaspórát az adott tektonon lévő spórák közül.
        */
        commands.put("/eat-spore", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String insectId = args[0];
                Insect insect = controller.getInsectById(insectId);
                if(controller.isGameRunning()){
                    InsectPicker ipicker = (InsectPicker)(controller.getPlayerHandler().getActualPlayer());
                    ipicker.eatSpore(insect);
                }else{
                    insect.eatSpores(1);
                }
            }

            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: The specified insect consumes one mushroom spore from the tecton it is currently on.\n" +
                       "\tUsing: /eat-spore <insect>\n" +
                       "\tParameters:\n" +
                       "\t\t<insect>: ID of the insect that will consume the spore.";
            }
        });


        /**
         * Leírás:  Gombaspóra dobása a paraméterként átadott tektontonazonosítóval rendelkezó tektonra történik 
         * az átadott gombatesttől. A tektonnak szomszédosnak kell lenni a gombász 
         * egyik gombatestjének tektonjával, és a gombának rendelekeznie kell gombaspórákkal.
        */
        commands.put("/throw-spore", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String mushroomId = args[0];
                String tectonId = args[1];
                Mushroom mushroom = controller.getMushroomById(mushroomId);
                Tecton tecton = controller.getTectonById(tectonId);

                if(controller.isGameRunning()){
                    MushroomPicker mpicker = (MushroomPicker)(controller.getPlayerHandler().getActualPlayer());
                    mpicker.ThrowSpore(tecton);
                }else{
                    mushroom.throwSpores(tecton);
                }                
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: Throws a mushroom spore from the given mushroom to the specified tecton.\n" +
                       "\tUsage: /throw-spore <mushroom> <tecton>\n" +
                       "\tParameters:\n" +
                       "\t\t<mushroom>: ID of the mushroom that throws the spore.\n" +
                       "\t\t<tecton>: ID of the target tecton to receive the spore.\n" +
                       "\tConditions:\n" +
                       "\t\t- The target tecton must be adjacent to the tecton the mushroom is on.\n" +
                       "\t\t- The mushroom must have available spores.";
            }
        });


        /**
         * Leírás: Megadható manuálisan a random-generátor által előállított számérték. Hivatott helyettesíteni a randomszám-generálást.
        */
        commands.put("/set-random", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                int fixRandom = Integer.parseInt(args[0]);
                RandTools.setFixRandom(fixRandom);
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Sets a manual value for the random number generator to override randomness.
                       \tUsage: /set-random <value>
                       \tParameters:
                       \t<value>: Integer value that replaces the next random number the system would generate.
                       """;
            }
        });


        /**
         * Leírás: Létrehoz egy mátrixot a tektononkból és autómatikusan a szomszédosságokat létrehozza. 
         * Paraméterként át kell adni, hogy hány sort és hány oszlopot szeretnénk.
         * Opciók:
         * -unbind : Nem állítja be a szomszédosságokat.
        */
        commands.put("/matrix-tecton", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                int rowCount = Integer.parseInt(args[0]);
                int colCount = Integer.parseInt(args[1]);

                boolean unbind = getOption(options, "-unbind", "false").equalsIgnoreCase("true");
                boolean randomType = getOption(options, "-random", "false").equalsIgnoreCase("true");

                

                Tecton[][] tectonMatrix = new Tecton[rowCount][colCount];
                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < colCount; j++) {
                        String tectonId = "t" + i + "_" + j;
                        
                        int type = RandTools.random(10);
                        type = randomType ? type : 0;

                        Tecton tecton;

                        switch (type) {
                            case 6:
                                tecton = new TectonInfertile();
                                break;
                            case 7:
                                tecton = new TectonKeepAlive();
                                break;
                            case 8:
                                tecton = new TectonOnlyLine();
                                break;
                            case 9:
                                tecton = new TectonTime();
                                break;
                            default:
                                tecton = new Tecton();
                                break;
                        }

                        tectonMatrix[i][j] = tecton;
                        controller.addTecton(tectonId, tecton);
                    }
                }


                for (int i = 0; i < rowCount && !unbind; i++) {
                    for (int j = 0; j < colCount; j++) {
                        if (i < rowCount - 1) {
                            tectonMatrix[i][j].setNeighbors(tectonMatrix[i + 1][j]);
                        }
                        if (i > 0) {
                            tectonMatrix[i][j].setNeighbors(tectonMatrix[i - 1][j]);
                        }
                        if (j < colCount - 1) {
                            tectonMatrix[i][j].setNeighbors(tectonMatrix[i][j + 1]);
                        }
                        if (j > 0) {
                            tectonMatrix[i][j].setNeighbors(tectonMatrix[i][j - 1]);
                        }
                    }
                }

                System.out.println("Tecton matrix created with " + rowCount + " rows and " + colCount + " columns.");
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: Creates a tecton matrix with specified rows and columns.\n" +
                       "\tUsage: /matrix-tecton <rows> <columns>\n" +
                       "\tOptions:\n" +
                       "\t\t-unbind: If specified, the tectons will not be bound to each other.\n" +
                       "\t\t-random: The tectons generated with random types.\n" +
                       "\tNote: The tectons are created in a 'grid', and the neighbors are set automatically unless -unbind is used.";
            }
        });


        /**
         * Leírás: Gombatest növesztése egy paraméterként átadott tektontonazonosítóval rendelkezó tektonra.
         * A tektonnak rendelkeznie kell a gombatest azonosítójával ellátott gombafonallal, és kettő darab a 
         * gombászhoz tartozó gombaspóra.
         * Opciók:
         * -mid <mushroomId>: Gombaazonosító. Ennek segítségével adható meg, hogy melyik gombatest fogja a fonálnövesztést 
         * végrehajtani. Megadja, hogy az adott objektum melyik játékoshoz tartozik. Értéke egy int-típusú érték, 
         * amely, ha például 1, akkor az adott objektum az 1-es játékoshoz fog tartozni.
        */
        commands.put("/build-mushroom", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                String tectonId = args[0];
                int id = Integer.parseInt(getOption(options, "-mid", "1"));
                Tecton tecton = controller.getTectonById(tectonId);


                if (controller.isGameRunning()) {
                    MushroomPicker mpicker = (MushroomPicker)(controller.getPlayerHandler().getActualPlayer());
                    mpicker.growBody(tecton);
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


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                        Description: Grows a mushroom body on the specified tecton.
                        \tUsing: /build-mushroom <TectonID>
                        \tOptions:
                        \t\t-mid <mushroomId>: Mushroom identifier. Specifies which mushroom body will perform the line growth. This integer value also determines which player owns the object (e.g., 1 means Player 1).
                        \tThe tecton must contain a line with this identifier and two spores belonging to the player who wants to grow mushroom body.
                        """;
            }
        });


        /**
         * Leírás: A játék állapotának mentése a paraméterként átadott helyre. Kétféle fájlt lehet vele elmenteni, egy “parancsfájlt” amit betöltve vissza lehet állítani a játék állapotát, és egy “log fájlt” ami pedig a program kimeneteit tartalmazza.
         * Opciók:
         * -log : Ha a parancsok kimeneteit szeretnénk menteni.
         * -cmd : Ha a kiadott parancsokat szeretnénk menteni.
        */
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
                        System.out.println("Successfully save to " + savePath);
                    } catch (IOException e) {
                        System.err.println("Failed to write in file: " + e.getMessage());
                    }
                }
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Saves the current state of the game objects to the specified path. 
                       Two types of files can be saved: a command file (to restore state) and a log file (program outputs).
                       \tUsing: /save <path_to_save>
                       \tOptions:
                       \t\t-log: Save the output log of commands.
                       \t\t-cmd: Save the issued commands.
                       """;
            }
        });

        
        /**
         * Leírás: Parancsok listázása.
         * Opciók:
         * -admin : Csak az adminisztrátori parancsokat listázza ki.
         * -insect : Csak az rovarász parancsokat listázza ki.
         * -mushroom : Csak a gombász parancsokat listázza ki
        */
        commands.put("/help", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {

                boolean admin = getOption(options, "-admin", "false").equalsIgnoreCase("true");
                boolean insect = getOption(options, "-insect", "false").equalsIgnoreCase("true");
                boolean mushroom = getOption(options, "-mushroom", "false").equalsIgnoreCase("true");

                if (!mushroom && !insect && !admin) {
                    mushroom = insect = admin = true;
                }

                System.out.println("Available commands: ");
                
                List<String> sorted = new ArrayList<>(commands.keySet());

                if (admin) {
                    System.out.println("=======Administrator commands=========");
                    sorted.sort((k1, k2) -> k1.compareTo(k2));
                    for (String name : sorted) {
                        if(!insectPickerCommands.contains(name) && !mushroomPickerCommands.contains(name)) {
                            System.out.println(name+" : \n\t" + commands.get(name).toString()+"\n");
                        }
                    }
                }
                
                if (mushroom) {
                    System.out.println("\n========MushroomPicker commands=========");
                    for (String name : sorted) {
                        if(mushroomPickerCommands.contains(name)) {
                            System.out.println(name+" : \n\t" + commands.get(name).toString()+"\n");
                        }
                    }
                }
                
                if (insect) {
                    System.out.println("\n========InsectPicker commands=========");
                    for (String name : sorted) {
                        if(insectPickerCommands.contains(name)) {
                            System.out.println(name+" : \n\t" + commands.get(name).toString()+"\n");
                        }
                    }
                }
                
                System.out.println("==================================");
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Lists available commands.
                       \tUsing: /help
                       \tOptions:
                       \t\t-admin: Lists only administrator commands.
                       \t\t-insect: Lists only insectpicker commands.
                       \t\t-mushroom: Lists only mushroompicker commands.
                       """;
            }
        });
        

        /**
         * Leírás: Az idő előrehajtása. A paraméterként átadott számú körrel előrébb hajtja az időt.
        */
        commands.put("/skip", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                int time = Integer.parseInt(args[0]);

                for(int i = 0; i < time; i++) {
                    Timer.forwardTime();
                }
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return "Description: Fast-forwards the game by the specified number of rounds.\n" +
                       "\tUsing: /skip <number_of_rounds>";
            }
        });
        
        commands.put("/checkcon", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                Line time = controller.getLineById(args[0]);

                if (time.checkConnections(new ArrayList<Line>(), null)) {
                    System.out.println("The line is connected to mushroom.");
                } else {
                    System.out.println("The line is not connected to mushroom.");
                }
            }
        });
        

        /**
         * Leírás: Játék indítása a megadott kezdeti paraméterekkel. alapértelmezetten, kapcsolók nélkül kiadta 
         * a játék kezdetben létrehoz két rovarász és két gombászt, valamint beállít 10 kört. 
         * A kapcsolók megadásával ez finomhangolható a következő módon:
         * Opciók:
         * -m <számérték>: Gombászok számának egyéni beállítása.
         * -i <számérték>: Rovarászok számának egyéni beállítása.
         * -k <számérték>: Körök számának egyéni beállítása.
         * -nomap: Amennyiben a tesztelő a játék kezdete előtt felépített egy játékpályát, 
         *  amelyen teszteket hajtott végre, úgy ez az opció lehetővé teszi, hogy a /start kiadásakor (az opciót használva) 
         *  ne álljon vissza a játékpálya a kiinduló állapotára.
        */
        commands.put("/start", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                int mCount = Integer.parseInt(getOption(options, "-m", "2"));
                int iCount = Integer.parseInt(getOption(options, "-i", "2"));
                int roundCount = Integer.parseInt(getOption(options, "-k", "10"));
                boolean noMap = getOption(options, "-nomap", "false").equalsIgnoreCase("true");
                boolean noName = getOption(options, "-noname", "false").equalsIgnoreCase("true");

                if (!noMap) {
                    controller.HardReset();
                    int mSize = mCount > 5 ? mCount : 5;
                    ExecuteCommand("/matrix-tecton "+mSize+" "+mSize+" -random");

                    for (int i = 0; i<mCount; i++){
                        String tectonId = "t"+i+"_"+1;
                        ExecuteCommand("/create-mushroom "+tectonId+" -mid "+i+" -i m"+i);
                    }

                    for (int i = 0; i<iCount; i++){
                        String tectonId = "t"+i+"_"+1;
                        ExecuteCommand("/create-insect "+tectonId+" -mid "+i+" -i i"+i);
                    }
                }

                controller.StartGame(mCount, iCount, !noName);
                controller.setMaxRound(roundCount);
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Starts the game with the given initial parameters.
                       \tUsing: /start
                       \tOptions:
                       \t\t-m <number>: Sets the number of mushroompickers.
                       \t\t-i <number>: Sets the number of insectpickers.
                       \t\t-k <number>: Sets the number of game rounds.
                       \t\t-nomap: Prevents the game map from resetting to the default state if it was already built before starting.
                       \t\t-noname: Prevents the game from asking names. Automatically generates them.
                       """;
            }
        });
        

        /**
         * Leírás: Az aktuális állapot összehasonlítása egy régebben elmentett log fájlban lévő állapottal.
         * Opciók:
         * -f <elérési út> : Ha nem a program aktuális állapotát szeretnénk összehasonlítani, 
         *  hanem kettő már elmentett log fájlt akkor ezzel az opcióval meg lehet adni a másik fájlnak az elérési útját.
        */
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

            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: Compares the current state of the game with a previously saved state.
                       \tUsing: /compare <file_path>
                       \tOptions:
                       \t\t-f <file_path>: If we want to compare two previously saved states instead of the current state.
                       """;
            }
        });
        
        
        commands.put("/next", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                controller.getPlayerHandler().nextPlayer();
            }
            
            @Override
            public String toString() {
                return "Calls the next player. If all the players called, it forwards time and starts a new rount.";
            }
        });
        
        commands.put("/end", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                if (controller.isGameRunning()) {
                    controller.endGame();
                } else {
                    System.out.println("Game is not running.");
                }
            }
            
            @Override
            public String toString() {
                return "Immediately ends the game session.";
            }
        });
        
        commands.put("/reset", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                controller.HardReset();
                System.out.println("Game session has been reset.");
            }
            
            @Override
            public String toString() {
                return "Resets the game session to its initial state.";
            }
        });
        

        /**
         * Leírás: Ha az insect “freezing” effekt hatása alatt áll, akkor a gombász a parancs kiadásával, 
         * az adott rovart megadva paraméterül “elfogyaszthatja” a rovart és gombatestet tud növeszteni 
         * így a tektonon, melyen a rovar tartózkodott. Második paraméterül meg kell adni a vonalat, 
         * amely “megeszi” az adott rovart.
        */
        commands.put("/eat-insect", new Command() {
            public void execute(String[] args, HashMap<String, String> options) {
                Insect insect = controller.getInsectById(args[0]);
                int mid = Integer.parseInt(getOption(options, "-mid", "1"));
                
                if (insect == null && !insect.getCanMove()) {
                    System.out.println("Freezed insect with ID " + args[0] + " not found.");
                    return;
                } else {
                    if(controller.isGameRunning()) {
                        MushroomPicker mpicker = (MushroomPicker)(controller.getPlayerHandler().getActualPlayer());
                        mpicker.eatInsect(insect);
                    } else {
                        for (int i = 0; i<insect.getTecton().getConnections().size(); i++){
                            if (insect.getTecton().getConnections().get(i).getId() == mid) {
    
                                insect.getTecton().getSporeContainer().addSpores(SporeContainer.generateSpores(3, mid));
                                insect.getTecton().addMushroom(mid);
                                insect.destroy();
                                
                                return;
                            }
                        }    
                    }
                    
                }
            }


            //Felülírt toString() metódus, hogy a parancs leírását ki tudjuk írni a felhasználónak
            @Override
            public String toString() {
                return """
                       Description: A mushroom line consumes a frozen insect to grow a mushroom body on the tecton it occupies.
                       \tUsing: /eat-insect <InsectID> <LineID>
                       \tNote: The insect must be under the 'freezing' effect. The specified line will be used to consume the insect.
                       """;
            }
        });


        //TODO: checkConnections
        //TODO: Player leszármazott osztályainak metódusai

        //TODO: /next parancs implementálása
        /*
        commands.put("/next", new Command() {
            //public void execute(String[] args, HashMap<String, String> options);
        });
        */
    }


    /**
     * Egy parancsot hajt végre, amelyet a felhasználó adott meg.
     * A parancsot és az opcionális argumentumokat feldolgozza, majd a megfelelő műveletet végrehajtja.
     *
     * @param command A végrehajtandó parancs szöveges formában. Tartalmazhat opciókat és argumentumokat.
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

            if(commands.get(baseCommand).getOption(options, "-help", "false").equalsIgnoreCase("true")) {
                System.out.println("\t"+commands.get(baseCommand).toString());
            }
            else {
                commands.get(baseCommand).execute(plainArgs, options);
            }

            if (!baseCommand.equalsIgnoreCase("/save")){
                commandHistory.add(command);
            }
        } else {
            System.out.println("Unknown command: " + command);
        }

    }

}
