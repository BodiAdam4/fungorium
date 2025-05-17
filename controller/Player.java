package controller;

import java.util.List;
import listeners.JobListener;

public class Player {
    /* - Privát attribútumok*/
    private int playerId;
    public static int playerIdCounter = 0;
    private String displayName;


    /* - Protected attribútumok*/
    protected Controller controller;
    protected List<JobListener> jobListeners;

    /* - Publikus attribútumok*/
    protected int score = 0;
    /* - Konstruktorok*/

    //Konstruktor
    public Player(String displayName, Controller controller) {
        this.playerId = playerIdCounter++;
        this.displayName = displayName;
        this.controller = controller;
    }


    /* - Getter/Setter metódusok*/

    public int getPlayerId() {
        return playerId;
    }


    public String getDisplayName() {
        return displayName;
    }

    /* - Egyéb metódusok*/

    public int calculateScore() {
        return 0;
    }

    
    public void setJobListeners(List<JobListener> listeners) {
        jobListeners = listeners;
    }

    /**
     * A játékos által végrahajtott akciók megtörténését figyelő metódust nullázó függvény.
     */
    public void ResetInsectActions() {
        return;
    }


    /**
     * Ahhoz szükséges, hogy lekérdezhessük, hogy a játékosnak még vannak-e olyan objektumai amivel képes játszani
     * @return Képes-e játszani a játékos
     */
    public boolean canPlay() {
        return true;
    }
}
