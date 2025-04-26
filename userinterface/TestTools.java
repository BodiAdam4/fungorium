package userinterface;

import controller.Controller;
import controller.Player;
import controller.PlayerHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Tecton;

public class TestTools {
    
    public static String GetStatus(Controller controller) {
        StringBuilder sb = new StringBuilder();

        List<String> sorted = new ArrayList<>(controller.getAllTecton().keySet());
        sorted.sort((k1, k2) -> k1.compareTo(k2));

        for(String tId : sorted) {
            sb.append(GetTectonStatus(controller, tId));
            sb.append("\n");
        }


        sorted = new ArrayList<>(controller.getAllLine().keySet());
        sorted.sort((k1, k2) -> k1.compareTo(k2));

        for(String lId : sorted) {
            sb.append(GetLineStatus(controller, lId));
            sb.append("\n");
        }


        sorted = new ArrayList<>(controller.getAllInsect().keySet());
        sorted.sort((k1, k2) -> k1.compareTo(k2));

        for(String iId : sorted) {
            sb.append(GetInsectStatus(controller, iId));
            sb.append("\n");
        }


        sorted = new ArrayList<>(controller.getAllMushroom().keySet());
        sorted.sort((k1, k2) -> k1.compareTo(k2));

        for(String mId : sorted) {
            sb.append(GetMushroomStatus(controller, mId));
            sb.append("\n");
        }


        if (controller.isGameRunning()) {
            sb.append("======= Game Status ======\n");
            PlayerHandler pHandler = controller.getPlayerHandler();
            sb.append("Actual player: ").append(pHandler.getActualPlayer().getDisplayName()).append("\n");
            List<Player> allPlayer = controller.getPlayerHandler().getAllPlayer();
            sb.append("Players in order: ");
            for(int i = 0; i<allPlayer.size(); i++) {
                Player player = allPlayer.get(i);
                String username = player.getDisplayName();
                int score = player.calculateScore();
                sb.append("\n\t"+username+" (score: "+score+")");
            }

            sb.append("\n");
            sb.append(pHandler.getWinner()+"\n");
            
            sb.append("==========================\n");

            return sb.toString();
        }


        return sb.toString();
    }

    public static String GetTectonStatus(Controller controller, String tectonId) {
        Tecton tecton = controller.getTectonById(tectonId);
        StringBuilder sb = new StringBuilder();
        sb.append(tecton.getClass().getName()).append(": ").append(tectonId).append("\n");
        sb.append("\tmyMushroom: ").append(controller.getMushroomId(tecton.getMyMushroom())).append("\n");
        sb.append("\tconnections: ");

        if(tecton.getConnections().isEmpty()) {
            sb.append("NULL\n");
        }
        else {
            for(int i = 0; i < tecton.getConnections().size(); i++) {
                sb.append(controller.getLineId(tecton.getConnections().get(i)));

                if (i < tecton.getConnections().size() - 1) {
                    sb.append("; ");
                }
            }
            sb.append("\n");
        }

        sb.append("\tsporeContainer: ").append(tecton.getSporeContainer().getSporeCount()).append("\n");
        sb.append("\tneighbors: ");

        if(tecton.getNeighbors().isEmpty()) {
            sb.append("NULL\n");
        }
        else {
            for(int i = 0; i < tecton.getNeighbors().size(); i++) {
                sb.append(controller.getTectonId(tecton.getNeighbors().get(i)));

                if (i < tecton.getNeighbors().size() - 1) {
                    sb.append("; ");
                }
            }
            sb.append("\n");
        }

        sb.append("\tinsects: ");

        if(tecton.getInsects().isEmpty()) {
            sb.append("NUll\n");
        }
        else {
            for(int i = 0; i < tecton.getInsects().size(); i++) {
                sb.append(controller.getInsectId(tecton.getInsects().get(i)));

                if (i < tecton.getInsects().size() - 1) {
                    sb.append("; ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String GetLineStatus(Controller controller, String lineId) {
        Line line = controller.getLineById(lineId);
        StringBuilder sb = new StringBuilder();
        sb.append(line.getClass().getName()).append(": ").append(lineId).append("\n");
        sb.append("\tttl: ").append(line.getTtl()).append("\n");
        sb.append("\tmushroomId: ").append(line.getId()).append("\n");
        sb.append("\tends: ").append(controller.getTectonId(line.getEnds()[0])).append(";");
        sb.append(controller.getTectonId(line.getEnds()[1])).append("\n");
        return sb.toString();
    }

    public static String GetInsectStatus(Controller controller, String insectId) {
        Insect insect = controller.getInsectById(insectId);
        StringBuilder sb = new StringBuilder();
        sb.append(insect.getClass().getName()).append(": ").append(insectId).append("\n");
        sb.append("\tinsectId: ").append(insect.getInsectId()).append("\n");
        sb.append("\tspeed: ").append(insect.getSpeed()).append("\n");
        sb.append("\tsporeCount: ").append(insect.getSporeCount()).append("\n");
        sb.append("\tcanCut: ").append(insect.getCanCut()).append("\n");
        sb.append("\tcanMove: ").append(insect.getCanMove()).append("\n");
        sb.append("\tactualTecton: ").append(controller.getTectonId(insect.getTecton())).append("\n");

        return sb.toString();
    }

    public static String GetMushroomStatus(Controller controller, String mushroomId) {
        Mushroom mushroom = controller.getMushroomById(mushroomId);
        StringBuilder sb = new StringBuilder();
        sb.append(mushroom.getClass().getName()).append(": ").append(mushroomId).append("\n");
        sb.append("\tsporeCount: ").append(mushroom.getSporeCount()).append("\n");
        sb.append("\tmushroomId: ").append(mushroom.getMushroomId()).append("\n");
        sb.append("\tlevel: ").append(mushroom.getLevel()).append("\n");
        sb.append("\tmyTecton: ").append(controller.getTectonId(mushroom.getMyTecton())).append("\n");
        return sb.toString();
    }

    public static String GetGameStatus(Controller controller) {
        return "Hello";
    }

    public static void writeLogToFile(String fileName, Controller controller) {
        String content = GetStatus(controller);
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
            System.out.println("Sikeresen kiírva a fájlba: " + fileName);
        } catch (IOException e) {
            System.err.println("Hiba történt a fájl írása közben: " + e.getMessage());
        }
    }

    public static void getSporesOnTecton(Tecton tectOn){
        System.out.println("Spórák száma a tektonon: " + tectOn.getSporeContainer().getSporeCount());
    }

    public static boolean compare(String filename, Controller controller){
        boolean success = true;
        String content = GetStatus(controller);
        String cLines[] = content.split("\n");
        long l = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            l = reader.lines().count();
            reader.close();
        } catch (IOException e) { e.printStackTrace(); success = false; }
        try {
            BufferedReader reader2 = new BufferedReader(new FileReader(filename));
            String line;
            try {
                for(int i = 0; i < l - 2; i++) {
                    line = reader2.readLine();
                    if(!line.equals(cLines[i])) {
                        System.out.println("A fájlban lévő sor nem egyezik a generált sorral: " + line);
                        System.out.println("A generált sor: " + cLines[i]);
                        success = false;
                    }
                }
                reader2.close();
            } catch (IOException e) { e.printStackTrace(); success = false; }
        } catch (FileNotFoundException e) { e.printStackTrace(); success = false; }
        if(success) {
            System.out.println("A fájl tartalma megegyezik a generált tartalommal.");
        }

        return success;
    }

    public static boolean compare(String exp, String out){
        boolean success = true;
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(exp));
            BufferedReader reader2 = new BufferedReader(new FileReader(out));
            String line1, line2;
            try {
                while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
                    if (!line1.equals(line2)) {
                        System.out.println("A fájlban lévő sor nem egyezik a generált sorral: " + line1);
                        System.out.println("A generált sor: " + line2);
                        success = false;
                    }
                }
                if (reader1.lines().count() != reader2.lines().count()) {
                    System.out.println("A fájlok különböző hosszúságúak.");
                    success = false;
                }
                reader1.close();
                reader2.close();
            } catch (IOException e) { e.printStackTrace(); success = false; }
        } catch (FileNotFoundException e) { e.printStackTrace(); success = false; }
        if(success) {
            System.out.println("A fájl tartalma megegyezik a generált tartalommal.");
        }

        return success;
    }

    public static HashMap<String, String> findInputExpPairs(String directoryPath) {
        HashMap<String, String> pairs = new HashMap<>();
        File directory = new File(directoryPath);

        if (!directory.isDirectory()) {
            System.out.println("A megadott útvonal nem mappa.");
            return pairs;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Nem sikerült kilistázni a fájlokat.");
            return pairs;
        }

        HashMap<String, String> inputs = new HashMap<>();
        HashMap<String, String> exps = new HashMap<>();

        // Fájlok szétválogatása
        for (File file : files) {
            String name = file.getName();
            if (name.endsWith("_input.txt")) {
                String baseName = name.substring(0, name.length() - "_input.txt".length());
                inputs.put(baseName, name);
            } else if (name.endsWith("_exp.txt")) {
                String baseName = name.substring(0, name.length() - "_exp.txt".length());
                exps.put(baseName, name);
            }
        }

        // Párosítás
        for (String baseName : inputs.keySet()) {
            if (exps.containsKey(baseName)) {
                pairs.put(inputs.get(baseName), exps.get(baseName));
            }
        }

        return pairs;
    }

    public static void RunAllTests(CommandProcessor cp, Controller control) {
        HashMap<String, String> pairs = findInputExpPairs("TestFiles/");
        List<String> failed = new ArrayList<>();
        List<String> success = new ArrayList<>();

        for (String name : pairs.keySet()) {
            System.out.println("-------------------------");
            System.out.println("Running test for: " + name+"\nExpected: " + pairs.get(name));
            try {
                cp.ExecuteCommand("/load TestFiles/"+name);
            } catch (Exception e) {
                System.out.println("Error : " + name + "\n" + e.getMessage());
                failed.add(name);
            }
            
            if (compare("TestFiles/"+pairs.get(name), control)) {
                success.add(name);
            }
            System.out.println("-------------------------");
        }

        System.out.println("Success rate: "+(failed.size()+success.size())+"/"+success.size());

        for(String fail : failed) {
            System.out.println("Failed test: " + fail);
        }
    }
}
