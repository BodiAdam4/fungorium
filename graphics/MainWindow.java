package graphics;

import java.awt.Color;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * A játék ablakát megvalósító osztály, tartalmazza a játék során megjelenő elemeket.
*/
public class MainWindow implements JobListener{

    /* - Privát attribútumok*/

    private Map map;                        //A játék térképét megjelenítő JPanel leszármazott osztály példánya.
    private MainMenu menu;                  //A játék kezdetén megjelenő, a beállításokat tartalmazó panel.
    private ControlPanel controlPanel;      //A játék irányításához szükséges elemeket tartalmazó panel.
    private JPanel notificationBar;         //Az értesítéseknél felugró panel.
    private JLabel notificationText;        //Az értesítéseknél megjelenő szöveg.

    /* - Konstruktor(ok)*/
    

    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/

    /* - Értesítések megjelenítésére szolgáló függvény. Paraméterként át kell adni az értesítés szövegét és színét.*/
    public void showNotification(String msg, Color color) {}


    /* - Az eredményhirdetés megjelenítése, paraméterként át kell adni az eredményeket szöveges formában.*/
    public void showResults(String data) {}


    /* - Sikeres műveletvégrehajtás esetén lefutó metódus. Paraméterként átad egy szöveges üzenetet a műveletről.*/
    public void jobSuccessfull(String msg) {}


    /* - Sikertelen műveletvégrehajtás esetén lefutó metódus. Paraméterként átad egy szöveges üzenetet a műveletről.*/
    public void jobFailed(String msg) {}



    
}
