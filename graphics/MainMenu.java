package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollBarUI;


/**
 * A MainMenu osztály felelős a játék elején megjelenő menü megjelenítéséért. 
 * Itt lehet a játékos nevét, színét és típusát beállítani, illetve ezen a panelen található meg a játékot elindító gomb.
*/
public class MainMenu extends JPanel {
    
    /* - Privát attribútumok*/
    private List<PlayerPanel> playerPanels;         //A játékosok beállításához szükséges panelek listája. Minden játékoshoz egy panel tartozik.
    private GraphicController gController;          //A grafikus felületet irányító objektum. //TODO: Ha meglesz a GraphicController, akkor ezt kell implementálni
    private JSpinner playerSpinner;                 //A játékosszám megadásához szükséges szám bevitelére alkalmas bemeneti mező.
    private JSpinner turnSpinner;                   //A körök számának megadásához szükséges szám bevitelére alkalmas bemeneti mező.
    private JPanel configPanel;                     //A játékospaneleket tartalmazó JPanel típusú grafikus panel.

    private MainWindow mainWindow;                 //A főablak, amelyben a menü található.


    /* - Konstruktor(ok)*/
    public MainMenu(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.setLayout(new BorderLayout()); //A főmenü elrendezése
        //JPanel a főmenünek
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        
        //JPanel a főcímnek, és a szerzők nevének
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        //JPanel a játékmenetbeállításoknak
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.BLACK);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        //JPanel az egyéni beállításoknak
        JPanel settingsPanel = createSettingsPanel();
        contentPanel.add(settingsPanel, BorderLayout.NORTH);

        //Jpanel a játékoskonténerhez: itt jelennek meg a PlayerPanel példányok
        configPanel = new JPanel();
        configPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        //configPanel.setPreferredSize(new Dimension(300, 300)); //A panel méretének beállítása
        //configPanel.setMaximumSize(new Dimension(300, 300)); //A panel méretének beállítása
        //configPanel.setMinimumSize(new Dimension(300, 300)); //A panel méretének beállítása
        configPanel.setBackground(null);
        configPanel.setOpaque(false); //A panel háttérszínének beállítása
        contentPanel.revalidate();
        contentPanel.repaint(); //A panel újrarajzolása

        //Lista a játékos panelekről
        playerPanels = new ArrayList<>();

        //Ha a playerPanel-ek száma meghaladja a 5-öt, akkor új sort kezdünk
        //configPanel.setLayout(new GridLayout(0, 5, 30, 30));    //5 oszlopos elrendezés, dinamikus sorokkal
        configPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));

        //JScrollPane a játékosok beállításainak görgetéséhez
        JScrollPane scrollPane = new JScrollPane(configPanel);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(null);

        //Személyre szabjuk a görgetősávokat
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();

        //Vertikális görgetősáv felülírása
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
            this.thumbColor = Color.WHITE;
            this.trackColor = Color.DARK_GRAY;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
            return createEmptyButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
            return createEmptyButton();
            }

            private JButton createEmptyButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
            }
        });

        //Horizontális görgetősáv felülírása
        horizontalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
            this.thumbColor = Color.WHITE;
            this.trackColor = Color.DARK_GRAY;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
            return createEmptyButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
            return createEmptyButton();
            }

            private JButton createEmptyButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
            }
        });

        //Szűkítjük a görgetősávok méretét
        horizontalScrollBar.setPreferredSize(new Dimension(8, 8));
        verticalScrollBar.setPreferredSize(new Dimension(8, 8));

        //Hozzáadjuk a görgetősávokat a panelhez
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        //A playerPanel-ek frissítése a játékosok számának megváltoztatásakor
        updatePlayerPanels();
        
        //Hozzáadjuk a beállításokat tartalmazó panelt a fő panelhez
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        //Hozzáadjuk a fő panelt a JFrame-hez
        this.add(mainPanel);
        this.setVisible(true);
    }
    

    /**
     * Elkészíti a főcímet és a szerző nevét tartalmazó panelt.
     * @return A cím panel.
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
        
        //JLabel a főcímhez
        JLabel titleLabel = new JLabel("FUNGORIUM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
        panel.add(titleLabel, BorderLayout.WEST);
        
        //JLabel a szerzők nevéhez
        JLabel authorLabel = new JLabel("by oet_kis_malacz");
        authorLabel.setForeground(Color.GRAY);
        authorLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        authorLabel.setBorder(BorderFactory.createEmptyBorder(14, 20, 0, 0));
        panel.add(authorLabel, BorderLayout.CENTER);
        
        // JButton a játék elindításához
        JButton enterButton = new JButton("Enter the game");
        enterButton.setForeground(Color.RED);
        enterButton.setFont(new Font("Arial", Font.BOLD, 25));
        enterButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3)); // Thicker border
        enterButton.setBackground(Color.BLACK);
        enterButton.setFocusPainted(false);
        enterButton.setPreferredSize(new Dimension(200, 40)); // Set button size
        enterButton.addActionListener(e -> {
            playerPanels.forEach(playerPanel -> {
                System.out.println("Player color: " + playerPanel.getColor() + "player name: " + playerPanel.getName());
            });
            System.out.println("Enter the game button clicked!");
            startGame(); // Add mouse click event
            mainWindow.removeMenu();
        });
        panel.add(enterButton, BorderLayout.EAST);
        
        return panel;
    }
    

    /**
     * Elkészíti a beállítások panelt, ahol a játékosok beállíthatják a játék körök számát és a játékosok számát.
     * @return A settings panel.
     */
    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        //JPanel a játék körök számának beállításához
        JPanel turnsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        turnsPanel.setBackground(null);
        turnsPanel.setOpaque(false); //A panel háttérszínének beállítása

        //JLabel a játék körök számának beállításához
        JLabel turnsLabel = new JLabel("Game turns");
        turnsLabel.setForeground(Color.WHITE);
        turnsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        turnsPanel.add(turnsLabel);

        //JLabel a körök számának megadásához
        JLabel rangeLabel1 = new JLabel(" (10 to 50)");
        rangeLabel1.setForeground(Color.GRAY);
        rangeLabel1.setFont(new Font("Arial", Font.ITALIC, 12));
        turnsPanel.add(rangeLabel1);

        //JLabel a ":" karakterhez
        JLabel colonLabel = new JLabel(":");
        colonLabel.setForeground(Color.WHITE);
        colonLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        turnsPanel.add(colonLabel);
        
        JPanel turnsSpinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));      //Horizontális elrendezés, 20 pixel távolsággal
        turnsSpinnerPanel.setBackground(Color.BLACK);

        //JSpinner a körök számának megadásához
        turnSpinner = new JSpinner(new SpinnerNumberModel(10, 10, 50, 1));
        turnSpinner.setPreferredSize(new Dimension(40, 30));
        turnSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        turnSpinner.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        turnSpinner.setBackground(Color.BLACK);
        turnSpinner.setForeground(Color.WHITE);

        turnsSpinnerPanel.add(turnSpinner);
        turnsPanel.add(turnsSpinnerPanel);

        //JSpinner felületének testreszabása
        for (Component component : turnSpinner.getComponents()) {
            if (component instanceof JButton) {
                component.setBackground(Color.BLACK);
                component.setForeground(Color.WHITE);
                ((JButton) component).setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Thicker border
            }
        }

        //JSpinner szövegmezőjének testreszabása
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) turnSpinner.getEditor();
        editor.getTextField().setBackground(Color.BLACK);
        editor.getTextField().setForeground(Color.WHITE);
        editor.getTextField().setEditable(false);
        editor.getTextField().setFocusable(false);
        editor.getTextField().setBorder(BorderFactory.createLineBorder(Color.GRAY));
        turnsPanel.add(turnSpinner);
        panel.add(turnsPanel);
        
        //##########################################################################################################################################

        //JPanel a játékosok számának beállításához
        JPanel numbersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        numbersPanel.setBackground(null);
        numbersPanel.setOpaque(false); //A panel háttérszínének beállítása

        //JLabel a játékosok számának beállításához
        JLabel playersLabel = new JLabel("Player numbers");
        playersLabel.setForeground(Color.WHITE);
        playersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        playersLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        numbersPanel.add(playersLabel);

        //JLabel a játékosok számának megadásához
        JLabel rangeLabel2 = new JLabel(" (4 to 16)");
        rangeLabel2.setForeground(Color.GRAY);
        rangeLabel2.setFont(new Font("Arial", Font.ITALIC, 12));
        rangeLabel2.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        numbersPanel.add(rangeLabel2);

        //JLabel a ":" karakterhez
        JLabel pontBehindLabel = new JLabel(":");
        pontBehindLabel.setForeground(Color.WHITE);
        pontBehindLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        pontBehindLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        numbersPanel.add(pontBehindLabel);
        

        JPanel playerSpinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Add some horizontal spacing
        playerSpinnerPanel.setBackground(Color.BLACK);
        

        //JSpinner a játékosok számának megadásához
        playerSpinner = new JSpinner(new SpinnerNumberModel(4, 4, 16, 1));
        playerSpinner.setPreferredSize(new Dimension(40, 30));
        playerSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        playerSpinner.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        playerSpinner.setBackground(Color.BLACK);
        playerSpinner.setForeground(Color.WHITE);

        //JSpinner felületének testreszabása
        for (Component component : playerSpinner.getComponents()) {
            if (component instanceof JButton) {
            component.setBackground(Color.BLACK);
            component.setForeground(Color.WHITE);
            ((JButton) component).setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); //kövérebb szegély
            }
        }

        //JSpinner szövegmezőjének testreszabása
        JSpinner.DefaultEditor playerEditor = (JSpinner.DefaultEditor) playerSpinner.getEditor();
        playerEditor.getTextField().setBackground(Color.BLACK);
        playerEditor.getTextField().setForeground(Color.WHITE);
        playerEditor.getTextField().setEditable(false);
        playerEditor.getTextField().setFocusable(false);
        playerEditor.getTextField().setBorder(BorderFactory.createLineBorder(Color.GRAY));

        //JSpinner eseménykezelője, ha megváltozik a játékosok száma, akkor frissíti a játékos paneleket
        playerSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("Játékosok száma: " + playerSpinner.getValue());
                if (((Integer)playerSpinner.getValue()) < 4) {
                    playerSpinner.setValue(4);
                    repaint();
                }else if (((Integer)playerSpinner.getValue()) > 17) {
                    playerSpinner.setValue(17);
                    repaint();
                }
                
                updatePlayerPanels();
            }
        });
        playerSpinnerPanel.add(playerSpinner);
        numbersPanel.add(playerSpinnerPanel);
        numbersPanel.add(playerSpinner);
        panel.add(numbersPanel);
        
        //JSeparator a beállítások és a játékosok közé
        /*
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setBackground(Color.WHITE);
        separator.setPreferredSize(new Dimension(panel.getWidth() - 200, 10)); // Rövidebb és 3px vastag
        //separator.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3)); // 3px vastagság beállítása

        panel.add(separator);
        */

        //JPanel for separating
        JPanel separatorPanel = new JPanel(new BorderLayout());
        separatorPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 3));
        separatorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 3));
        separatorPanel.setBorder(null);
        separatorPanel.setBackground(null);
        separatorPanel.setOpaque(false);
        panel.add(separatorPanel);
        

        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setBackground(Color.BLACK);
        rightSidePanel.setPreferredSize(new Dimension(650, separatorPanel.getHeight()));
        rightSidePanel.setMaximumSize(new Dimension(650, separatorPanel.getHeight()));
        separatorPanel.add(rightSidePanel, BorderLayout.EAST);

        JPanel leftSidePanel = new JPanel();
        leftSidePanel.setBackground(Color.WHITE);
        leftSidePanel.setPreferredSize(new Dimension(300, separatorPanel.getHeight()));
        leftSidePanel.setMaximumSize(new Dimension(300, separatorPanel.getHeight()));
        separatorPanel.add(leftSidePanel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();

        //JPanel a játékosok konfigurálásához
        JPanel configPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        configPanel.setBackground(Color.BLACK);

        //JLabel a játékosok konfigurálásához
        JLabel configLabel = new JLabel("Configure the players:");
        configLabel.setForeground(Color.WHITE);
        configLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        configLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        configPanel.add(configLabel);
        panel.add(configPanel);
        
        return panel;
    }
    

    /**
     * Frissíti a játékos paneleket a játékosok számának megváltoztatásakor.
     */
    private void updatePlayerPanels() {
        int playerCount = (Integer) playerSpinner.getValue();
        
        //Panel hozzáadása, ha szükséges
        while (playerPanels.size() < playerCount) {
            PlayerPanel panel = new PlayerPanel(playerPanels.size() + 1, this);
            playerPanels.add(panel);
            configPanel.add(panel);
        }
        
        //Panel eltávolítása, ha szükséges
        while (playerPanels.size() > playerCount) {
            PlayerPanel panel = playerPanels.remove(playerPanels.size() - 1);
            configPanel.remove(panel);
        }
        
        //Játékospanel-ek újrarajzolása
        configPanel.revalidate();
        configPanel.repaint();
    }
    

    /**
     * A játékos panel eltávolítása a listából.
     * @param panel A törlendő játékos panel.
     */
    public void removePlayerPanel(PlayerPanel panel) {

        
        //Eltávolítjuk a panelt a listából és a konténerből
        playerPanels.remove(panel);
        configPanel.remove(panel);
        
        //Frissítjük a játékosok számát a spinneren
        playerSpinner.setValue(playerPanels.size());
        
        //Újraszámozzuk a játékos paneleket
        for (int i = 0; i < playerPanels.size(); i++) {
            playerPanels.get(i).updatePlayerNumber(i + 1);
        }
        
        //Újrarajzoljuk a játékospanel-eket
        configPanel.revalidate();
        configPanel.repaint();
        
        
    }


    /* - A játék indítását elvégző függvény.*/
    public void startGame() {

        mainWindow.showMap();
        mainWindow.revalidate();
        this.repaint();

        List<String> insectPickers = new ArrayList<>();
        List<String> mushroomPickers = new ArrayList<>();

        for (PlayerPanel playerPanel : playerPanels) {
            if (!playerPanel.isInsect()) {
                mushroomPickers.add(playerPanel.getName());
            } else if (playerPanel.isInsect()) {
                insectPickers.add(playerPanel.getName());
            }
        }

        String insectNames = String.join(" ", insectPickers);
        String mushroomNames = String.join(" ", mushroomPickers);

        String command = "/start" + mushroomNames + " " + insectNames + " -m " + mushroomPickers.size() + " -i " + insectPickers.size() + " -k " + turnSpinner.getValue();
        GraphicMain.cmdProcessor.ExecuteCommand(command);
        //a start parancs: /start <gombásznevek> <rovarásznevek> -m <gombászok száma> -i <rovarászok száma> -k <körök száma>
    }
}