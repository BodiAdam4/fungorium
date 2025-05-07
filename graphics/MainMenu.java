package graphics;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSpinner;


/**
 * A MainMenu osztály felelős a játék elején megjelenő menü megjelenítéséért. 
 * Itt lehet a játékos nevét, színét és típusát beállítani, illetve ezen a panelen található meg a játékot elindító gomb.
*/
public class MainMenu extends JPanel {
    
    /* - Privát attribútumok*/
    private List<PlayerPanel> playerPanels;         //A játékosok beállításához szükséges panelek listája. Minden játékoshoz egy panel tartozik.
    private GraphicController gController;          //A grafikus felületet irányító objektum.
    private JSpinner playerSpinner;                 //A játékosszám megadásához szükséges szám bevitelére alkalmas bemeneti mező.
    private JSpinner turnSpinner;                   //A körök számának megadásához szükséges szám bevitelére alkalmas bemeneti mező.
    private JPanel configPanel;                     //A játékospaneleket tartalmazó JPanel típusú grafikus panel.


    /* - Konstruktor(ok)*/
    

    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/

    /* - A játékospaneleket tartalmazó configPanel létrehozására alkalmas függvény.*/
    public JPanel createConfigPanel() {}


    /* - A játékospanelek létrehozása és változás esetén frissítése.*/
    public void updatePlayerPanels() {}


    /* - A játék indítását elvégző függvény.*/
    public void startGame() {}


}
