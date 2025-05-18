package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Dialog;


/**
 * A MainMenu osztály felelős a játék elején megjelenő menü megjelenítéséért. 
 * Itt lehet a játékos nevét, színét és típusát beállítani, illetve ezen a panelen található meg a játékot elindító gomb.
*/
public class MainMenu extends JPanel {
    
    /* - Privát attribútumok*/
    /**
    * A játékosok beállításához szükséges panelek listája. Minden játékoshoz egy panel tartozik.
    */
    private List<PlayerPanel> playerPanels;

    /**
    * A játékosszám megadásához szükséges szám bevitelére alkalmas bemeneti mező.
    */
    private JSpinner playerSpinner;

    /**
    * A körök számának megadásához szükséges szám bevitelére alkalmas bemeneti mező.
    */
    private JSpinner turnSpinner;

    /**
    * A játékospaneleket tartalmazó JPanel típusú grafikus panel.
    */
    private JPanel configPanel;

    /**
    * A főablak, amelyben a menü található.
    */
    private MainWindow mainWindow;


    /* - Konstruktor*/

    /**
     * Konstruktor
     * @param mainWindow
     */
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

        //Ha a playerPanel-ek száma meghaladja a 5-öt, akkor új sort kezdünk [végül nem lett benne]
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
        
        //JButton a játék elindításához
        JButton enterButton = new JButton("Enter the game");
        enterButton.setForeground(Color.RED);
        enterButton.setFont(new Font("Arial", Font.BOLD, 25));
        enterButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));      //Szélesebb oldalszegély
        enterButton.setBackground(Color.BLACK);
        enterButton.setFocusPainted(false);
        enterButton.setPreferredSize(new Dimension(200, 40));                   //Gombméret beállítása
        enterButton.addActionListener(e -> {
            playerPanels.forEach(playerPanel -> {
                //Példakiiratás a konzolra, hogy lássuk jól veszi-e át az értékeket a játék.
                System.out.println("Player color: " + playerPanel.getColor() + " player name: " + playerPanel.getName() + " is insectPicker? " + playerPanel.isInsect());
            });
            //System.out.println("Enter the game button clicked!");

            //################################[Ne lehessen azonos színt választani]############################################
            //1. Gyűjtsük ki az összes kiválasztott színt
            Set<Color> usedColors = new HashSet<>();
            for (PlayerPanel pPanel : playerPanels) {
                Color selected = pPanel.getColor();
                if (selected != null) {
                    usedColors.add(selected);
                }
            }

            //2. Maradék színek meghatározása
            List<Color> availableColors = new ArrayList<>();
            for (Color color : PlayerPanel.COLORS) {
                if (!usedColors.contains(color)) {
                    availableColors.add(color);
                }
            }
            Collections.shuffle(availableColors); // randomizálás

            //3. Színek kiosztása azoknak, akiknek nincs beállítva
            for (PlayerPanel pPanel : playerPanels) {
                if (pPanel.getColor() == null) {
                    if (!availableColors.isEmpty()) {
                        Color randomColor = availableColors.remove(0);
                        pPanel.setColor(randomColor);
                    } else {
                        // Ha elfogytak a színek, beállíthatunk egy default színt vagy hibaüzenetet
                        showCustomErrorDialog(this, "There aren't enough colors for all the players!");
                        return;
                    }
                }
            }
            //############################################################################

            //Amennyiben nincs legalább 2 gombász és 2 rovarász, akkor hibaüzenetet adunk
            long insectPlayerCount = playerPanels.stream().filter(PlayerPanel::isInsect).count();
            long mushroomPlayerCount = playerPanels.stream().filter(playerPanel -> !playerPanel.isInsect()).count();

            // Biztosítsuk, hogy ne lehessen azonos színt választani:
            boolean sameColor = playerPanels.stream()
                .map(PlayerPanel::getColor)
                .collect(Collectors.groupingBy(color -> color, Collectors.counting()))
                .values()
                .stream()
                .anyMatch(count -> count > 1);
            
            //Biztosítjuk, hogy ne legyenek a nevek azonosak
            boolean sameNames = playerPanels.stream()
                .map(PlayerPanel::getName)
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()))
                .values()
                .stream()
                .anyMatch(count -> count > 1);

            //Ha a darabszám nem megfelelő
            if (insectPlayerCount < 2 || mushroomPlayerCount < 2) {
                //Egyedi dialógusablak hívása
                showCustomErrorDialog(this, "There must be at least two selected player from each caste!");
                return;
            } else if (sameNames) {
                showCustomErrorDialog(this, "Each players must use different names!");
                return;
            } else if (sameColor) {
                showCustomErrorDialog(this, "Each players must use different colors!");
                return;
            }
            startGame();                    //Különben: játék indítása
            mainWindow.removeMenu();        //A menü eltávolítása a főablakból
        });
        panel.add(enterButton, BorderLayout.EAST);
        
        return panel;
    }
    

    /**
     * Segédfüggvény egy egyedi dialógusablak előállításához, melyet akkor veszünk igénybe, ha a játékosok száma
     * nem felel meg az elvárt paramétereknek. Ekkor ezt a módosított figyelmeztetőablakot küldjük el.
     * A JDialog dialog egyedi fejlécű ablakot hoz létre üres címsorral, setIconImage(null), pedig kiveszi a dialógusablak ikonját.
     * @param parent - a dialógusablak szülője
     */
    public void showCustomErrorDialog(Component parent, String notificationSub) {
        //Cím és üzenet
        JLabel titleLabel = new JLabel("Warning!");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel messageLabel = new JLabel(notificationSub);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(60, 63, 65));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(messageLabel);

        //Egyéni JDialog, ikon nélkül
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), " ", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(false);           //Ne legyen teljesen díszítés nélküli, ha csak ikont akarjuk eltüntetni
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);

        //Ikon eltávolítása
        dialog.setIconImage(null);

        dialog.setVisible(true);
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
        
        //###########################################################[Játékban megtett körök darabszámának beállítása]############################################################################

        //JPanel a játék körök számának beállításához
        JPanel turnsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        turnsPanel.setBackground(null);
        turnsPanel.setOpaque(false);            //A panel háttérszínének beállítása

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
        
        //###########################################################[Játékosok darabszámának beállítása]############################################################################

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

        JPanel playerSpinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));     //Horizontáis szintezés felvétele
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
            ((JButton) component).setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));        //Kövérebb szegély
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

        //JPanel alkalmazása szeparátorként
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

        //###########################################################[Játékosokat felkonfiguráló panel beállítása]############################################################################

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


    /**
     * A játék indítását végző függvény,
     * hívja a "/start <gombásznevek> <rovarásznevek> -m <gombászok száma> -i <rovarászok száma> -k <körök száma>" parancsot,
     * melyet végrehajt a CommandProccessor osztály executeCommand metódusa.
     * A parancsot úgy egészíti ki, hogy kiolvassa a JSpinnerek és a textBoxok értékét a grafikus ablakról, 
     * majd ezeket lesznek a parancs argumentumai.
    */
    public void startGame() {

        
        mainWindow.showMap();
        mainWindow.revalidate();
        this.repaint();
        

        List<String> insectPickers = new ArrayList<>();
        List<String> mushroomPickers = new ArrayList<>();

        List<Color> colors = new ArrayList<>();
        List<String> names = new ArrayList<>();

        for (PlayerPanel playerPanel : playerPanels) {
            if (!playerPanel.isInsect()) {
                colors.add(playerPanel.getColor());
                names.add(playerPanel.getName());
                mushroomPickers.add(playerPanel.getName());
            }
        }
        
        for (PlayerPanel playerPanel : playerPanels) {
            if (playerPanel.isInsect()) {
                colors.add(playerPanel.getColor());
                names.add(playerPanel.getName());
                insectPickers.add(playerPanel.getName());
            }
        }

        //GraphicController a GraphicMain ostályból, mely felel a grafikus vezérlésért.
        GraphicMain.gController.setPlayers(colors, names, mushroomPickers.size());

        String insectNames = String.join(" ", insectPickers);
        String mushroomNames = String.join(" ", mushroomPickers);

        //A start parancs: /start <gombásznevek> <rovarásznevek> -m <gombászok száma> -i <rovarászok száma> -k <körök száma>
        String command = "/start " + mushroomNames + " " + insectNames + " -m " + mushroomPickers.size() + " -i " + insectPickers.size() + " -k " + turnSpinner.getValue();
        System.out.println(command);
        GraphicMain.cmdProcessor.ExecuteCommand(command);
        
        mainWindow.removeMenu();

        //Revalitálás és újrarajzolás
        this.revalidate();
        this.repaint();
    }
}