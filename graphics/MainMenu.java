package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.lang.reflect.Array;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;


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
    public MainMenu(){


        //Jpanel a főablakhoz
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        //Jpanel a címhez és a készítőkhöz
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));

        //JLabel a címhez
        JLabel titleLabel = new JLabel("FUNGORIUM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        //JLabel a készítőkhöz
        JLabel authorLabel = new JLabel("by oet_kis_malacz");
        authorLabel.setForeground(Color.GRAY);
        authorLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        authorLabel.setBorder(BorderFactory.createEmptyBorder(14, 20, 0, 0));
        titlePanel.add(authorLabel, BorderLayout.CENTER);

        //###################################################################################################################
        
        //JButton a játék indításához
        JButton enterButton = new JButton("Enter the game");
        enterButton.setForeground(Color.RED);
        enterButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        enterButton.setBackground(Color.BLACK);
        enterButton.setFocusPainted(false);
        enterButton.setPreferredSize(new Dimension(150, 40));
        titlePanel.add(enterButton, BorderLayout.EAST);

        //Hozzáadás a főpanelhez
        mainPanel.add(titlePanel, BorderLayout.NORTH);


        //###################################################################################################################
        
        //JPanel a középső részhez
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.BLACK);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));


        //JPanel a Beállításokhoz
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBackground(Color.BLACK);
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        //JPanel a játék köreinek beállítására
        JPanel turnsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        turnsPanel.setBackground(Color.BLACK);
        JLabel turnsLabel = new JLabel("Game turns");
        turnsLabel.setForeground(Color.WHITE);
        turnsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        turnsPanel.add(turnsLabel);

        //JLabel a körök max.- és min. számának megadásához
        JLabel rangeLabel1 = new JLabel(" (10 to 50)");
        rangeLabel1.setForeground(Color.GRAY);
        rangeLabel1.setFont(new Font("Arial", Font.ITALIC, 12));
        turnsPanel.add(rangeLabel1);

        //JLabel a ":" szöveghez
        JLabel colonLabel = new JLabel(":");
        colonLabel.setForeground(Color.WHITE);
        colonLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        turnsPanel.add(colonLabel);
        
        JPanel turnsSpinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));      //Középre igazítja a JSpinner-t
        turnsSpinnerPanel.setBackground(Color.BLACK);

        //JSpinner a körök számának megadásához
        turnSpinner = new JSpinner(new SpinnerNumberModel(10, 10, 50, 1));
        turnSpinner.setPreferredSize(new Dimension(40, 30));
        turnSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        turnSpinner.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        turnSpinner.setBackground(Color.BLACK);
        turnSpinner.setForeground(Color.WHITE);

        //Hozzáadás a panelhez
        turnsSpinnerPanel.add(turnSpinner);
        turnsPanel.add(turnsSpinnerPanel);

        //JSpinner stílusa
        for (Component component : turnSpinner.getComponents()) {
            if (component instanceof JButton) {
                component.setBackground(Color.BLACK);
                component.setForeground(Color.WHITE);
                ((JButton) component).setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); //Kövérebb keret
            }
        }

        //JSpinner szövegmezőjének stílusa
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) turnSpinner.getEditor();
        editor.getTextField().setBackground(Color.BLACK);
        editor.getTextField().setForeground(Color.WHITE);
        editor.getTextField().setBorder(BorderFactory.createLineBorder(Color.GRAY));

        //Hozzáadás a turnsPanelhez és a 'beállítások' panelhez
        turnsPanel.add(turnSpinner);
        settingsPanel.add(turnsPanel);

        //###################################################################################################################
        
        //JPanel a játékosok számának beállítására
        JPanel numbersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        numbersPanel.setBackground(Color.BLACK);
        JLabel playersLabel = new JLabel("Player numbers");
        playersLabel.setForeground(Color.WHITE);
        playersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        playersLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        numbersPanel.add(playersLabel);


        //JLabel a játékosszám max.- és min. számának megadásához
        JLabel rangeLabel2 = new JLabel(" (4 to 15)");
        rangeLabel2.setForeground(Color.GRAY);
        rangeLabel2.setFont(new Font("Arial", Font.ITALIC, 12));
        rangeLabel2.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        numbersPanel.add(rangeLabel2);


        //JLabel a ":" szöveghez
        JLabel pontBehindLabel = new JLabel(":");
        pontBehindLabel.setForeground(Color.WHITE);
        pontBehindLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        pontBehindLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        numbersPanel.add(pontBehindLabel);
        
        JPanel playerSpinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));      //Középre igazítja a JSpinner-t
        playerSpinnerPanel.setBackground(Color.BLACK);

        //JSpinner a játékosok számának megadásához
        playerSpinner = new JSpinner(new SpinnerNumberModel(5, 4, 15, 1));
        playerSpinner.setPreferredSize(new Dimension(40, 30));
        playerSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        playerSpinner.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        playerSpinner.setBackground(Color.BLACK);
        playerSpinner.setForeground(Color.WHITE);

        //JSpinner stílusa
        for (Component component : playerSpinner.getComponents()) {
            if (component instanceof JButton) {
            component.setBackground(Color.BLACK);
            component.setForeground(Color.WHITE);
            ((JButton) component).setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); //Kövérebb keret
            }
        }

        //JSpinner szövegmezőjének stílusa
        JSpinner.DefaultEditor playerEditor = (JSpinner.DefaultEditor) playerSpinner.getEditor();
        playerEditor.getTextField().setBackground(Color.BLACK);
        playerEditor.getTextField().setForeground(Color.WHITE);
        playerEditor.getTextField().setBorder(BorderFactory.createLineBorder(Color.GRAY));

        //Hozzáadás a panelhez
        playerSpinner.addChangeListener(e -> updatePlayerPanels());
        playerSpinnerPanel.add(playerSpinner);
        numbersPanel.add(playerSpinnerPanel);
        numbersPanel.add(playerSpinner);
        settingsPanel.add(numbersPanel);

        //###################################################################################################################

        //JSeparator a beállítások és a játékosok között
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setBackground(Color.BLACK);
        //TODO: Rövidebbre kell venni a JSeparator-t, hogy ne érjen el a panel széléig
        //separator.setPreferredSize(new Dimension(0, 2));
        //separator.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        settingsPanel.add(separator);

        //###################################################################################################################

        //JPanel a játékosok beállításaira
        JPanel configPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        configPanel.setBackground(Color.BLACK);
        JLabel configLabel = new JLabel("Configure the players:");
        configLabel.setForeground(Color.WHITE);
        configLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        configLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        //Hozzáadás a configPanelhez és a 'beállítások' panelhez
        configPanel.add(configLabel);
        settingsPanel.add(configPanel);

        //Hozzáadás a főpanelhez
        contentPanel.add(settingsPanel, BorderLayout.NORTH);

        //###################################################################################################################
        //PlayerPanel-ek létrehozása és hozzáadása a configPanelhez
        //TODO: Itt folytasd
    }
    

    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/

    /* - A játékospaneleket tartalmazó configPanel létrehozására alkalmas függvény.*/
    public JPanel createConfigPanel() {}


    /* - A játékospanelek létrehozása és változás esetén frissítése.*/
    public void updatePlayerPanels() {}


    /* - A játék indítását elvégző függvény.*/
    public void startGame() {}


}
