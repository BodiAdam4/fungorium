package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import listeners.ObjectChangeListener;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Schedule;
import model.Tecton;
import model.Timer;

public class Controller {
    /* - Privát attribútumok*/
    private HashMap<String, Mushroom> allMushroom;
    private HashMap<String, Line> allLine;
    private HashMap<String, Insect> allInsect;
    private HashMap<String, Tecton> allTecton;
    private PlayerHandler playerHandler;
    private boolean isGameRunning = false;
    private int round = 0;
    private int maxRound = 5;

    private ObjectChangeListener objectChangeListener;

    /* - Publikus attribútumok*/

    /* - Konstruktorok*/

    //Konstruktor
    public Controller(){
        this.allMushroom = new HashMap<>();
        this.allLine = new HashMap<>();
        this.allInsect = new HashMap<>();
        this.allTecton = new HashMap<>();
        //this.mushroomPickers = new ArrayList<>();
        //this.insectPickers = new ArrayList<>();

        objectChangeListener = new ObjectChangeListener() {
            @Override
            public void insectChanged(ObjectChangeEvent event, Insect insect) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addInsect(("i"+(allInsect.size()+1)), insect);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allInsect.remove(getInsectId(insect));
                }
            }

            @Override
            public void lineChanged(ObjectChangeEvent event, Line line) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addLine(("l"+(allLine.size()+1)), line);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allLine.remove(getLineId(line));
                }
            }

            @Override
            public void tectonChanged(ObjectChangeEvent event, Tecton tecton) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addTecton(("t"+(allTecton.size()+1)), tecton);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allTecton.remove(getTectonId(tecton));
                }
            }

            @Override
            public void mushroomChanged(ObjectChangeEvent event, Mushroom mushroom) {
                if(event == ObjectChangeEvent.OBJECT_ADDED) {
                    addMushroom(("m"+(allMushroom.size()+1)), mushroom);
                } else if (event == ObjectChangeEvent.OBJECT_REMOVED) {
                    allMushroom.remove(getMushroomId(mushroom));
                }
            }
        };


        Timer.addRepeatSchedule(new Schedule() {
            @Override
            public void onTime() {
                List<Line> deadLines = new ArrayList<>();
                
                for (String id : allLine.keySet()) {
                    Line line = allLine.get(id);
                    if (line.ttl != -1) {
                        line.ttl--;
                        if (line.ttl == 0) {
                            deadLines.add(line);
                            System.out.println("Line destroyed: " + id);
                        }
                    }
                }

                for (Line line : deadLines) {
                    line.Destroy();
                }

                for (String id : allInsect.keySet()) {
                    allInsect.get(id).resetEffect();
                }
            }
        }, 1);

        Timer.addRepeatSchedule(new Schedule() {
            @Override
            public void onTime() {
                for (String id : allInsect.keySet()) {
                    allInsect.get(id).resetEffect();
                    System.out.println("Insect effect reset: " + id);
                }
            }
        }, 2);
    }

    public void StartGame(int mushroomPickerCount, int insectPickerCount, boolean askName) {
        playerHandler = new PlayerHandler(mushroomPickerCount, insectPickerCount, this, askName);
    }

    /* - Getter/Setter metódusok*/

    /* - Propertyk*/
    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean isGameRunning) {
        this.isGameRunning = isGameRunning;
    }

    /* - Mushroom */
    public HashMap<String, Mushroom> getAllMushroom() {
        return allMushroom;
    }


    public void addMushroom(String id, Mushroom mushroom) {
        if (allMushroom.containsKey(id)) {
            System.out.println("Mushroom with this ID already exists.");
            return;
        }
        mushroom.changeListener = objectChangeListener;
        allMushroom.put(id, mushroom);
    }


    public Mushroom getMushroomById(String id) {
        if (!allMushroom.containsKey(id)) {
            return null;
        }

        return allMushroom.get(id);
    }


    public String getMushroomId(Mushroom mushroom) {
        for (String id : allMushroom.keySet()) {
            if (allMushroom.get(id).equals(mushroom)) {
                return id;
            }
        }
        return "Null";
    }


    /* - Line */
    public HashMap<String, Line> getAllLine() {
        return allLine;
    }


    public void addLine(String id, Line line) {
        if (allLine.containsKey(id)) {
            System.out.println("Line with this ID already exists.");
            return;
        }
        line.changeListener = objectChangeListener;
        allLine.put(id, line);
    }


    public Line getLineById(String id) {
        if (!allLine.containsKey(id)) {
            return null;
        }

        return allLine.get(id);
    }

    public void setMaxRound(int maxRound) {
        this.maxRound = maxRound;
    }

    public void nextRound() {
        round++;
        Timer.forwardTime();
        System.out.println("Round " + (round+1) + " started.");
        if (round == maxRound) {
            endGame();
        }
    }

    public void endGame() {
        System.out.println("Game ended!");
        System.out.println(playerHandler.getWinner());
    }

    public String getLineId(Line line) {
        for (String id : allLine.keySet()) {
            if (allLine.get(id).equals(line)) {
                return id;
            }
        }
        return "Null";
    }


    /* - Insect */
    public HashMap<String, Insect> getAllInsect() {
        return allInsect;
    }


    public void addInsect(String id, Insect insect) {
        if (allInsect.containsKey(id)) {
            System.out.println("Insect with this ID already exists.");
            return;
        }
        insect.changeListener = objectChangeListener;
        allInsect.put(id, insect);
    }


    public Insect getInsectById(String id) {
        if (!allInsect.containsKey(id)) {
            return null;
        }

        return allInsect.get(id);
    }


    public String getInsectId(Insect insect) {
        for (String id : allInsect.keySet()) {
            if (allInsect.get(id).equals(insect)) {
                return id;
            }
        }
        return "Null";
    }


    /* - Tecton */
    public HashMap<String, Tecton> getAllTecton() {
        return allTecton;
    }


    public void addTecton(String id, Tecton tecton) {
        if (allTecton.containsKey(id)) {
            System.out.println("Tecton with this ID already exists.");
            return;
        }
        tecton.changeListener = objectChangeListener;
        allTecton.put(id, tecton);
    }


    public Tecton getTectonById(String id) {
        if (!allTecton.containsKey(id)) {
            return null;
        }

        return allTecton.get(id);
    }


    public String getTectonId(Tecton tecton) {
        for (String id : allTecton.keySet()) {
            if (allTecton.get(id).equals(tecton)) {
                return id;
            }
        }
        return "Null";
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public void HardReset() {
        allMushroom.clear();
        allLine.clear();
        allInsect.clear();
        allTecton.clear();
        playerHandler = null;
        isGameRunning = false;
        round = 0;
    }
}
