package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.border.EmptyBorder;

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
        //statusBar.setMaximumSize(new Dimension(this.getWidth(), 50));
        this.add(statusBar, BorderLayout.NORTH); //A státusz sáv hozzáadása a főablakhoz

        //JPanel amely a map-ot és a a controlPanel-t tartalmazza
        JPanel mainContentPanel = new JPanel(); //A fő tartalom panel inicializálása
        mainContentPanel.setLayout(new BorderLayout()); //A fő tartalom panel elrendezése
        //mainContentPanel.setBackground(Color.GREEN); //A fő tartalom panel háttérszínének beállítása
        mainContentPanel.setBackground(null); //A fő tartalom panel háttérszínének beállítása
        mainContentPanel.setOpaque(false);
        //mainContentPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        //mainContentPanel.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()));
        //this.add(mainContentPanel, BorderLayout.CENTER); //A fő tartalom panel hozzáadása a főablakhoz

        //JPanel a map-nak
        JPanel mapPanel = new Image("graphics\\images\\bg.jpg"); //A térkép panel inicializálása
        mapPanel.setLayout(null); //A térkép panel elrendezése
        mainContentPanel.setBackground(null); //A fő tartalom panel háttérszínének beállítása
        mapPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight())); //A térkép panel méretének beállítása
        mainContentPanel.setOpaque(false);
        //mapPanel.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()));

        map = new Map(); //A térkép inicializálása
        mapPanel.add(map); //A térkép hozzáadása a térkép panelhez

        mainContentPanel.add(mapPanel, BorderLayout.CENTER); //A térkép panel hozzáadása a fő tartalom panelhez

        //JPanel a controlPanel-nek
        JPanel controlPanel = new JPanel(); //A vezérlő panel inicializálása
        controlPanel.setLayout(new BorderLayout()); //A vezérlő panel elrendezése
        controlPanel.setBackground(Color.DARK_GRAY); //A vezérlő panel háttérszínének beállítása
        controlPanel.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));
        //controlPanel.setMaximumSize(new Dimension(this.getWidth()/4, this.getHeight()));
        mainContentPanel.add(controlPanel, BorderLayout.EAST); //A vezérlő panel hozzáadása a fő tartalom panelhez


        //TODO: Ezt majd el kell innen pakolni

        // JLayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // Fontos: nincs LayoutManager
        layeredPane.setBounds(0, 0, this.getWidth(), this.getHeight());

        // Méretezhető fő tartalom panel
        mainContentPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        layeredPane.add(mainContentPanel, JLayeredPane.DEFAULT_LAYER);

        // Piros overlay panel
        JPanel notificJPanel = new JPanel();
        notificJPanel.setBackground(Color.DARK_GRAY);
        notificJPanel.setOpaque(true);
        notificJPanel.setLayout(new BoxLayout(notificJPanel, BoxLayout.Y_AXIS));
        notificJPanel.setBounds(0, 0, 600, 60);
        notificJPanel.setVisible(true); // Kezdetben látható
        notificJPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 5 pixel magas üres hely
        layeredPane.add(notificJPanel, JLayeredPane.PALETTE_LAYER);

        // JLabel a notification címhez (felül)
        JLabel notificationLabel = new JLabel("Notification Bar");
        notificationLabel.setForeground(Color.RED);
        notificationLabel.setFont(new Font("Arial", Font.BOLD, 18));
        notificationLabel.setBorder(new EmptyBorder(5, 5, 0, 0)); // Top, Left, Bottom, Right
        notificationLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Balra igazítás a panelen belül (vagy CENTER_ALIGNMENT, ha középre kéred)

        // JLabel az üzenet szövegéhez (alatta)
        notificationText = new JLabel("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididun!");
        notificationText.setForeground(Color.WHITE);
        notificationText.setFont(new Font("Arial", Font.ITALIC, 14));
        notificationText.setBorder(new EmptyBorder(0, 5, 0, 0)); // Top, Left, Bottom, Right
        notificationText.setAlignmentX(Component.LEFT_ALIGNMENT); // Igazítás megegyezően

        // Hozzáadás a panelhez
        notificJPanel.add(notificationLabel);
        notificJPanel.add(Box.createRigidArea(new Dimension(5, 5))); // Kis térköz a két szöveg között
        notificJPanel.add(notificationText);
        

        this.add(layeredPane, BorderLayout.CENTER);

        // Resize listener
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getContentPane().getWidth();
                int height = getContentPane().getHeight();
        
                layeredPane.setBounds(0, 0, width, height);
                mainContentPanel.setBounds(0, 0, width, height);
        
                mapPanel.setPreferredSize(new Dimension(width, height));
                mapPanel.setSize(width, height);
        
                controlPanel.setPreferredSize(new Dimension(width / 4, height));
                controlPanel.setSize(width / 4, height);
        
                // Dinamikus pozícionálás az alsó részre
                int stateWidth = 700;
                int stateHeight = 70;
                int stateX = (width/2)-(stateWidth/2); // 50 pixel távolság a jobb széltől
                int stateY = height - stateHeight - 50;
        
                notificJPanel.setBounds(stateX, stateY, stateWidth, stateHeight);
        
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        });
        this.setVisible(true); //A főablak láthatóvá tétele
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
