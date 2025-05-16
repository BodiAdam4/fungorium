package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import listeners.ControlListener;
import listeners.JobListener;
import listeners.ResultListener;




/**
 * A játék ablakát megvalósító osztály, tartalmazza a játék során megjelenő elemeket.
*/
public class MainWindow extends JFrame implements JobListener, ControlListener, ResultListener{

    /* - Privát attribútumok*/

    private Map map;                        //A játék térképét megjelenítő JPanel leszármazott osztály példánya.
    private MainMenu menu;                  //A játék kezdetén megjelenő, a beállításokat tartalmazó panel.
    private ControlPanel controlPanel;      //A játék irányításához szükséges elemeket tartalmazó panel. //TODO: Később implementálni kell

    private JPanel scrollablePanel;
    
    /* - Notification privát elemei */
    private JPanel notificJPanel;           //Az értesítéseknél felugró panel.
    private JLabel notificationText;        //Az értesítéseknél megjelenő szöveg.
    private JLabel notificationLabel;       //Az értesítéseknél megjelenő cím.

    /* - A dicsőségfal privát elemei */
    private JPanel resultPanel;
    private JTextArea mushroomResults;
    private JTextArea insectResults;



    private GraphicController gController;

    /* - Konstruktor(ok)*/
    public MainWindow(GraphicController gctrl){
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
        
        this.gController = gctrl;
        showMenu();

        //showMap();
        
    }
    

    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/

    public void showMap(){

        //JPanel az állapotsávnak
        JPanel statusBar = new JPanel();            //A státusz sáv inicializálása
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));        //A státusz sáv elrendezése
        statusBar.setBackground(Color.decode("#38778a"));       //A státusz sáv háttérszínének beállítása
        statusBar.setPreferredSize(new Dimension(this.getWidth(), 50));
        //statusBar.setMaximumSize(new Dimension(this.getWidth(), 50));

        // Görgethető panel létrehozása
        scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.X_AXIS));
        //scrollablePanel.setOpaque(false);
        scrollablePanel.setBackground(Color.decode("#38778a"));     //A görgethető panel háttérszínének beállítása

        //Görgetősáv hozzáadása egyedi stílussal
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(scrollablePanel) {
            @Override
            public void updateUI() {
            super.updateUI();
            //Egyedi görgetősáv stílus
            javax.swing.JScrollBar hScrollBar = getHorizontalScrollBar();
            hScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                this.thumbColor = Color.GRAY;       //Görgetősáv színe
                this.trackColor = Color.decode("#38778a");      //Háttér színe
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();      //Gombok eltávolítása
                }

                @Override
                protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();      //Gombok eltávolítása
                }

                private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
                }
            });
            hScrollBar.setPreferredSize(new Dimension(5, 0));       //Görgetősáv szélessége
            }
        };
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(this.getWidth(), 50));
        scrollPane.setToolTipText("Scroll to see all players"); //Tooltip hozzáadása a görgetősávhoz
        statusBar.add(scrollPane);

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

        map = new Map(gController); //A térkép inicializálása
        mapPanel.add(map); //A térkép hozzáadása a térkép panelhez
        gController.setMap(map);

        mainContentPanel.add(mapPanel, BorderLayout.CENTER); //A térkép panel hozzáadása a fő tartalom panelhez


        //###############################################################################
        //JPanel a controlPanel-nek
        /* 
        JPanel controlPanel = new JPanel(); //A vezérlő panel inicializálása
        controlPanel.setLayout(new BorderLayout()); //A vezérlő panel elrendezése
        controlPanel.setBackground(Color.DARK_GRAY); //A vezérlő panel háttérszínének beállítása
        controlPanel.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));
        //controlPanel.setMaximumSize(new Dimension(this.getWidth()/4, this.getHeight()));
        mainContentPanel.add(controlPanel, BorderLayout.EAST); //A vezérlő panel hozzáadása a fő tartalom panelhez
        */
        controlPanel = new ControlPanel(gController, this); //A vezérlő panel inicializálása
        controlPanel.setPreferredSize(new Dimension(this.getWidth()/5, this.getHeight())); //A vezérlő panel méretének beállítása
        controlPanel.setMaximumSize(new Dimension(this.getWidth()/5, this.getHeight())); //A vezérlő panel méretének beállítása
        mainContentPanel.add(controlPanel, BorderLayout.EAST);
        GraphicMain.controller.addControlListener(controlPanel);
        gController.addSelectionListener(controlPanel);

        //###############################################################################

        //JLayeredPane az egymásrailleszthetőséghez
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // Fontos: nincs LayoutManager
        layeredPane.setBounds(0, 0, this.getWidth(), this.getHeight());

        // Méretezhető fő tartalom panel
        mainContentPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        layeredPane.add(mainContentPanel, JLayeredPane.DEFAULT_LAYER);
    
        //###############################[resultPanel]#################################

        //JPanel az eredményhirdetéshez
        resultPanel = new JPanel(); //Az eredményhirdetés panel inicializálása
        //resultPanel.setBackground(Color.BLUE);
        resultPanel.setBackground(null); //Az eredményhirdetés panel háttérszínének beállítása
        resultPanel.setOpaque(false);
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS)); //Az eredményhirdetés panel elrendezése
        resultPanel.setBounds((this.getWidth()/2)-(700/2), (this.getHeight()/2)-(500/2), 700, 500); //Az eredményhirdetés panel méretének beállítása
        //TODO: itt lehet beállítnai a láthatóságát, hogy kezdetben ne legyen látható
        resultPanel.setVisible(false); // Kezdetben látható
        layeredPane.add(resultPanel, JLayeredPane.PALETTE_LAYER); //Az eredményhirdetés panel hozzáadása a rétegelt panelhez

        // Görgethető eredmény panel (ScrollPane)
        JPanel resultContent = new JPanel();
        resultContent.setLayout(new BoxLayout(resultContent, BoxLayout.Y_AXIS));
        resultContent.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        resultContent.setPreferredSize(new Dimension(resultPanel.getWidth(), resultPanel.getHeight())); //A görgethető eredmény panel méretének beállítása
        resultContent.setMaximumSize(new Dimension(resultPanel.getWidth(), resultPanel.getHeight())); //A görgethető eredmény panel méretének beállítása
        resultContent.setBackground(Color.DARK_GRAY);

        //JPanel a Game Over feliratnak
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BorderLayout());
        //gameOverPanel.setBackground(Color.GREEN);
        gameOverPanel.setBackground(null);
        gameOverPanel.setOpaque(false);
        gameOverPanel.setPreferredSize(new Dimension(resultPanel.getWidth(), 30)); //A görgethető eredmény panel méretének beállítása
        gameOverPanel.setMaximumSize(new Dimension(resultPanel.getWidth(), 30)); //A görgethető eredmény panel méretének beállítása
        gameOverPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultContent.add(gameOverPanel);

        // "Game Over" cím
        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.RED);
        gameOverPanel.add(gameOverLabel, BorderLayout.CENTER);

        // "Results" felirat
        JLabel resultsLabel = new JLabel("Results:");
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultsLabel.setForeground(Color.WHITE);
        resultsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultContent.add(Box.createRigidArea(new Dimension(0, 10)));
        resultContent.add(resultsLabel);

        // "Top Mushroompickers" felirat
        JLabel topMushLabel = new JLabel("Top Mushroompickers:");
        topMushLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topMushLabel.setForeground(Color.WHITE);
        topMushLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultContent.add(Box.createRigidArea(new Dimension(0, 10)));
        resultContent.add(topMushLabel);

        // Eredmények szöveges megjelenítése (Mushroompickers)
        mushroomResults = new JTextArea("Empty");
        mushroomResults.setFont(new Font("Monospaced", Font.PLAIN, 16));
        mushroomResults.setEditable(false);
        mushroomResults.setBackground(Color.DARK_GRAY);
        mushroomResults.setForeground(Color.WHITE);
        //mushroomResults.setAlignmentX(Component.LEFT_ALIGNMENT);
        mushroomResults.setFocusable(false);
        resultContent.add(Box.createRigidArea(new Dimension(0, 5)));

        // Görgethető szöveges eredmények panel modern stílussal
        JScrollPane mushroomScroll = new JScrollPane(mushroomResults) {
            @Override
            public void updateUI() {
            super.updateUI();
            // Egyedi görgetősáv stílus
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            verticalScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                this.thumbColor = Color.GRAY; // Görgetősáv színe
                this.trackColor = Color.DARK_GRAY; // Háttér színe
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                return createZeroButton(); // Gombok eltávolítása
                }

                @Override
                protected JButton createIncreaseButton(int orientation) {
                return createZeroButton(); // Gombok eltávolítása
                }

                private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
                }
            });
            verticalScrollBar.setPreferredSize(new Dimension(8, 0)); // Görgetősáv szélessége
            }
        };
        mushroomScroll.setPreferredSize(new Dimension(600, 115));
        mushroomScroll.setMaximumSize(new Dimension(600, 115));
        mushroomScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mushroomScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mushroomScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        mushroomScroll.setBorder(null); // Border removed
        resultContent.add(Box.createRigidArea(new Dimension(0, 5)));
        resultContent.add(mushroomScroll);

        // Szeparátor
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 3)); // Thicker separator
        separator.setForeground(Color.WHITE); // White color
        separator.setBackground(Color.WHITE); // White color
        resultContent.add(Box.createRigidArea(new Dimension(0, 10)));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultContent.add(separator);

        // "Top Insectpickers" felirat
        JLabel topInsectLabel = new JLabel("Top Insectpickers:");
        topInsectLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topInsectLabel.setForeground(Color.WHITE);
        topInsectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultContent.add(Box.createRigidArea(new Dimension(0, 10)));
        resultContent.add(topInsectLabel);

        // Eredmények szöveges megjelenítése (Insectpickers)
        insectResults = new JTextArea("Empty");
        insectResults.setFont(new Font("Monospaced", Font.PLAIN, 16));
        insectResults.setEditable(false);
        insectResults.setBackground(Color.DARK_GRAY);
        insectResults.setForeground(Color.WHITE);
        //insectResults.setAlignmentX(Component.LEFT_ALIGNMENT);
        insectResults.setFocusable(false);
        resultContent.add(Box.createRigidArea(new Dimension(0, 5)));

        // Görgethető szöveges eredmények panel modern stílussal
        JScrollPane insectScroll = new JScrollPane(insectResults) {
            @Override
            public void updateUI() {
            super.updateUI();
            // Egyedi görgetősáv stílus
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            verticalScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
            this.thumbColor = Color.GRAY; // Görgetősáv színe
            this.trackColor = Color.DARK_GRAY; // Háttér színe
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
            return createZeroButton(); // Gombok eltávolítása
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
            return createZeroButton(); // Gombok eltávolítása
            }

            private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
            }
            });
            verticalScrollBar.setPreferredSize(new Dimension(8, 0)); // Görgetősáv szélessége
            }
        };
        insectScroll.setPreferredSize(new Dimension(600, 115));
        insectScroll.setMaximumSize(new Dimension(600, 115));
        //Set policy to never show the horizontal scrollbar
        insectScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        insectScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        insectScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        insectScroll.setBorder(null); // Border removed
        resultContent.add(Box.createRigidArea(new Dimension(0, 5)));
        resultContent.add(insectScroll);

        // JPanel az exit gombnak
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new BorderLayout()); // A vezérlő panel elrendezése
        exitPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        //exitPanel.setBackground(Color.CYAN); // A vezérlő panel háttérszínének beállítása
        exitPanel.setBackground(null);
        exitPanel.setOpaque(false);
        exitPanel.setPreferredSize(new Dimension(resultPanel.getWidth(), 40)); // A görgethető eredmény panel méretének beállítása
        exitPanel.setMaximumSize(new Dimension(resultPanel.getWidth(), 40)); // A görgethető eredmény panel méretének beállítása
        resultContent.add(Box.createRigidArea(new Dimension(0, 40)));
        resultContent.add(exitPanel);

        // Exit Game gomb
        JButton exitButton = new JButton("Exit game");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.RED);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        exitButton.setPreferredSize(new Dimension(200, 30));
        exitButton.setMaximumSize(new Dimension(200, 30));
        exitButton.addActionListener(e -> System.exit(0)); // Exit event added

        // Középre igazítás
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false); // Átlátszó háttér
        centerPanel.add(exitButton);
        exitPanel.add(centerPanel, BorderLayout.CENTER); // A középre igazított panel hozzáadása

        // Eredmény tartalom hozzáadása a fő resultPanel-hez
        resultPanel.removeAll(); // csak ha újratöltöd
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.add(resultContent);

        //###########################[notificJPanel]####################################

        //A notification panel létrehozása
        notificJPanel = new JPanel();
        notificJPanel.setBackground(Color.DARK_GRAY);
        notificJPanel.setOpaque(true);
        notificJPanel.setLayout(new BoxLayout(notificJPanel, BoxLayout.Y_AXIS));
        notificJPanel.setBounds((this.getWidth()/2)-(700/2), this.getHeight()-155, 700, 70);
        notificJPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 5 pixel magas üres hely
        //TODO: itt lehet beállítnai a láthatóságát, hogy kezdetben ne legyen látható
        notificJPanel.setVisible(false); // Kezdetben látható
        layeredPane.add(notificJPanel, JLayeredPane.PALETTE_LAYER);

        //JLabel a notification címhez (felül)
        notificationLabel = new JLabel("Notification Bar");
        notificationLabel.setForeground(Color.RED);
        notificationLabel.setFont(new Font("Arial", Font.BOLD, 18));
        notificationLabel.setBorder(new EmptyBorder(5, 5, 0, 0)); // Top, Left, Bottom, Right
        notificationLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Balra igazítás a panelen belül (vagy CENTER_ALIGNMENT, ha középre kéred)

        //JLabel az üzenet szövegéhez (alatta)
        notificationText = new JLabel("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididun!");
        notificationText.setForeground(Color.WHITE);
        notificationText.setFont(new Font("Arial", Font.ITALIC, 14));
        notificationText.setBorder(new EmptyBorder(0, 5, 0, 0)); // Top, Left, Bottom, Right
        notificationText.setAlignmentX(Component.LEFT_ALIGNMENT); // Igazítás megegyezően

        //Hozzáadás a panelhez
        notificJPanel.add(notificationLabel);
        notificJPanel.add(Box.createRigidArea(new Dimension(5, 5))); // Kis térköz a két szöveg között
        notificJPanel.add(notificationText);
        

        this.add(layeredPane, BorderLayout.CENTER);

        //Resize listener
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getContentPane().getWidth();
                int height = getContentPane().getHeight();
        
                layeredPane.setBounds(0, 0, width, height);
                mainContentPanel.setBounds(0, 0, width, height);
        
                mapPanel.setPreferredSize(new Dimension(width, height));
                mapPanel.setSize(width, height);
        
                controlPanel.setPreferredSize(new Dimension(width / 5, height));
                controlPanel.setSize(width / 5, height);
                controlPanel.revalidate();
                controlPanel.repaint();
        
                //Az értesítéspanel pozícionálása az alsó részre
                int notWidth = 700;
                int notHeight = 70;
                int stateX = (width/2)-(notWidth/2);
                int stateY = height - notHeight - 50;
        
                notificJPanel.setBounds(stateX, stateY, notWidth, notHeight);

                //Az eredményhirdetés panel pozicionálása
                int resultWidth = 700;
                int resultHeight = 500;
                int resultX = (width/2)-(resultWidth/2);
                int resultY = (height/2)-(resultHeight/2);

                resultPanel.setBounds(resultX, resultY, resultWidth, resultHeight);
        
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        });
        
        this.setVisible(true); //A főablak láthatóvá tétele
        this.revalidate();
        this.repaint();
    }

    public void showMenu(){

        this.menu = new MainMenu(this); //A főmenü inicializálása
        this.add(menu, BorderLayout.CENTER); //A főmenü inicializálása
        menu.revalidate();
        menu.repaint(); //A főmenü újrarajzolása
    }

    public void removeMenu(){
        this.remove(menu); //A főmenü eltávolítása
        menu.revalidate();
        menu.repaint(); //A főmenü újrarajzolása
    }

    /* - Az eredményhirdetés megjelenítése, paraméterként át kell adni az eredményeket szöveges formában.*/
    public void showResults(String data) {

        /**
         * //TODO: String data formátuma legyen:
         * [MPicker_playername];[points]$[IPicker_playername];[points];"
         * "$" karakterrel vannak elválasztva a mushroompickerek nevei és pontjai az insectpicker szekciótól
        */
        resultPanel.setVisible(true);

        // String data felszeletelése "$" karakter mentén
        String[] sections = data.split(" ");

        //Mushroompickers eredmények feldolgozása
        String[] mushroomPickers = sections[0].split(";");
        StringBuilder mushroomResultsBuilder = new StringBuilder();
        int index = 1;
        for (int i = 0; i < mushroomPickers.length; i+=2) {
            mushroomResultsBuilder.append("# ").append((index++)).append(".: ")
            .append(mushroomPickers[i]).append("__________________").append(mushroomPickers[i+1]).append("\n");
        }
        mushroomResults.setText(mushroomResultsBuilder.toString());

        //Insectpickers eredmények feldolgozása
        String[] insectPickers = sections[1].split(";");
        StringBuilder insectResultsBuilder = new StringBuilder();
        index = 1;
        for (int i = 0; i < insectPickers.length; i+=2) {
            insectResultsBuilder.append("# ").append((index++)).append(".: ")
            .append(insectPickers[i]).append("__________________").append(insectPickers[i+1]).append("\n");
        }
        insectResults.setText(insectResultsBuilder.toString());

    }


    /* - Sikeres műveletvégrehajtás esetén lefutó metódus. Paraméterként átad egy szöveges üzenetet a műveletről.*/
    public void jobSuccessfull(String msg) {
        notificationLabel.setText("Success!"); //A notification címének beállítása
        notificationLabel.setForeground(Color.GREEN); //A notification címének színének beállítása
        notificationText.setText(msg); //Az üzenet szövegének beállítása
        notificJPanel.setVisible(true); //A notification panel láthatóvá tétele
        revalidate();
        repaint();
        startNotificationHide();
    }
    
    private  Thread closeThread;
    public void startNotificationHide() {
        
        if (closeThread != null) {
            closeThread.interrupt();
        }

        //Értesítési panel eltűntetésének időzítése
        closeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    notificJPanel.setVisible(false);
                } catch (InterruptedException e) {
                    System.out.println("Notification timer interrupted");
                }
            }
            
        });
        closeThread.start();
    }

    /* - Sikertelen műveletvégrehajtás esetén lefutó metódus. Paraméterként átad egy szöveges üzenetet a műveletről.*/
    public void jobFailed(String msg) {
        notificationLabel.setText("Failed!"); //A notification címének beállítása
        notificationLabel.setForeground(Color.RED); //A notification címének színének beállítása
        notificationText.setText(msg); //Az üzenet szövegének beállítása
        notificJPanel.setVisible(true); //A notification panel láthatóvá tétele
        revalidate();
        repaint();
        startNotificationHide();
    }


    @Override
    public void onNextRound(String player, boolean isInsect, int round, HashMap<String, Integer> points) {
        //Dinamikus elemek felvétele a státusz sávra
        int playerCount = points.size();
        int i = 0;

        scrollablePanel.removeAll(); // A játékos panel törlése a régi elemek eltávolításához
        for (String playerpoints : points.keySet()) {
            Integer score = points.get(playerpoints);
            System.out.println(playerpoints+"\n");

            // Játékospanel
            JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 15));
            playerPanel.setOpaque(false);

            JLabel label = new JLabel(playerpoints + ": ");
            label.setForeground(gController.getPlayerColor(playerpoints));
            label.setFont(label.getFont().deriveFont(Font.BOLD, 18));

            JLabel scoreLabel = new JLabel(score.toString());
            scoreLabel.setForeground(Color.RED);
            scoreLabel.setFont(scoreLabel.getFont().deriveFont(Font.BOLD, 18));

            playerPanel.add(label);
            playerPanel.add(scoreLabel);
            scrollablePanel.add(playerPanel, BorderLayout.CENTER);

            // Elválasztó
            if (i < playerCount - 1) {
            JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
            separator.setPreferredSize(new Dimension(3, 40));
            separator.setForeground(Color.WHITE);
            scrollablePanel.add(separator);
            }
            i++;
        }
        scrollablePanel.repaint();
    }



    
}
