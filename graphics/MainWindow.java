package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import listeners.JobListener;




/**
 * A játék ablakát megvalósító osztály, tartalmazza a játék során megjelenő elemeket.
*/
public class MainWindow extends JFrame implements JobListener{

    /* - Privát attribútumok*/

    private Map map;                        //A játék térképét megjelenítő JPanel leszármazott osztály példánya.
    private MainMenu menu;                  //A játék kezdetén megjelenő, a beállításokat tartalmazó panel.
    //private ControlPanel controlPanel;      //A játék irányításához szükséges elemeket tartalmazó panel. //TODO: Később implementálni kell
    private JPanel notificationBar;         //Az értesítéseknél felugró panel.
    private JLabel notificationText;        //Az értesítéseknél megjelenő szöveg.

    /* - Konstruktor(ok)*/
    public MainWindow(){
        
        this.setTitle("Fungorium_by_oet_kis_malacz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 600);     //Ablakméret beállítása
        this.setBackground(Color.BLACK);
        /*
        this.menu = new MainMenu(); //A főmenü inicializálása
        this.add(menu, BorderLayout.CENTER); //A főmenü inicializálása
        menu.revalidate();
        menu.repaint(); //A főmenü újrarajzolása
        */

        
        //JPanel az állapotsávnak
        JPanel statusBar = new JPanel(); //A státusz sáv inicializálása
        statusBar.setLayout(new BorderLayout()); //A státusz sáv elrendezése
        statusBar.setBackground(Color.decode("#38778a")); //A státusz sáv háttérszínének beállítása
        statusBar.setPreferredSize(new Dimension(this.getWidth(), 50));
        statusBar.setMaximumSize(new Dimension(this.getWidth(), 50));
        this.add(statusBar, BorderLayout.NORTH); //A státusz sáv hozzáadása a főablakhoz

        //JPanel amely a map-ot és a a controlPanel-t tartalmazza
        JPanel mainContentPanel = new JPanel(); //A fő tartalom panel inicializálása
        mainContentPanel.setLayout(new BorderLayout()); //A fő tartalom panel elrendezése
        //mainContentPanel.setBackground(Color.GREEN); //A fő tartalom panel háttérszínének beállítása
        mainContentPanel.setBackground(null); //A fő tartalom panel háttérszínének beállítása
        mainContentPanel.setOpaque(false);
        mainContentPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        mainContentPanel.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()));
        this.add(mainContentPanel, BorderLayout.CENTER); //A fő tartalom panel hozzáadása a főablakhoz

        //JPanel a map-nak
        JPanel mapPanel = new JPanel(); //A térkép panel inicializálása
        mapPanel.setLayout(new BorderLayout()); //A térkép panel elrendezése
        mainContentPanel.setBackground(null); //A fő tartalom panel háttérszínének beállítása
        mainContentPanel.setOpaque(false);
        mapPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight())); //A térkép panel méretének beállítása
        mapPanel.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()));
        mainContentPanel.add(mapPanel, BorderLayout.CENTER); //A térkép panel hozzáadása a fő tartalom panelhez

        //JPanel a controlPanel-nek
        JPanel controlPanel = new JPanel(); //A vezérlő panel inicializálása
        controlPanel.setLayout(new BorderLayout()); //A vezérlő panel elrendezése
        controlPanel.setBackground(Color.DARK_GRAY); //A vezérlő panel háttérszínének beállítása
        controlPanel.setPreferredSize(new Dimension(this.getWidth()/3, this.getHeight()));
        controlPanel.setMaximumSize(new Dimension(this.getWidth()/3, this.getHeight()));
        mainContentPanel.add(controlPanel, BorderLayout.EAST); //A vezérlő panel hozzáadása a fő tartalom panelhez


        //TODO: Ezt majd el kell innen pakolni
        this.map = new Map(); //A térkép inicializálása
        mapPanel.add(map); //A főmenü inicializálása
        
        this.revalidate();
        this.repaint();
    }
    

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
