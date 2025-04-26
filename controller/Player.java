package controller;

public class Player {
    /* - Privát attribútumok*/
    private int playerId;
    public static int playerIdCounter = 0;
    private String displayName;


    /* - Protected attribútumok*/
    protected Controller controller;

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

    /**
     * A játékos által végrahajtott akciók megtörténését figyelő metódust nullázó függvény.
     */
    public void ResetInsectActions() {
        return;
    }
}
