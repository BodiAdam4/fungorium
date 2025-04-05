package Controller;

public class Player {
    /* - Privát attribútumok*/
    private int playerId;
    private String displayName;
    private Controller controller;

    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public Player(int playerId, String displayName, Controller controller) {
        this.playerId = playerId;
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
