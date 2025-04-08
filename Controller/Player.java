package Controller;

public class Player {
    /* - Privát attribútumok*/
    private int playerId;
    private static int playerIdCounter = 0;
    private String displayName;


    /* - Protected attribútumok*/
    protected Controller controller;

    /* - Publikus attribútumok*/
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
        // TODO - implement Player.calculateScore
        return 0;
    }
}
