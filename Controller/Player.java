package Controller;

public class Player {
    /* - Privát attribútumok*/
    private String playerId;
    private String displayName;
    private Controller controller;

    /* - Publikus attribútumok*/
    /* - Konstruktorok*/

    //Konstruktor
    public Player(String playerId, String displayName, Controller controller) {
        this.playerId = playerId;
        this.displayName = displayName;
        this.controller = controller;
    }


    /* - Getter/Setter metódusok*/
}
