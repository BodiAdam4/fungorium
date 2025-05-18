package graphics;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import listeners.ControlListener;
import listeners.SelectionListener;

public class ControlPanel extends JPanel implements ControlListener, SelectionListener {
    
    private final GraphicController gController;
    private MainWindow mainWindow;
    private JPanel insectPanel;
    private JPanel mushroomPanel;

    private JLabel playerTextMushroom;
    private JLabel roundTextMushroom;

    private JLabel roundNumberTextInsect;
    private JLabel roundNumberTextMushroom;

    private JLabel playerTextInsect;
    private JLabel roundTextInsect;

    /**
     * A gombok aktiválásához szükséges hashmap
     */
    private HashMap<JButton, Integer> actionButtons = new HashMap<>();
    
    /**
     * Konstruktor
     * @param gController
     * @param mainWindow
     */
    public ControlPanel(GraphicController gController, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.gController = gController;
        setLayout(new CardLayout());
        
        // Create insect panel
        insectPanel = createInsectPanel("", 0);
        add(insectPanel, "insect");
        
        // Create mushroom panel
        mushroomPanel = createMushroomPanel("", 0);
        add(mushroomPanel, "mushroom");
        
        // Show insect panel by default
        showPanel(true);
    }
    

    /**
     * Rovarász parancsainak megjelenítése.
     * @param player az adott játékos
     * @param round a kör száma
     * @return az elkészült JPanel
     */
    private JPanel createInsectPanel(String player, int round) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setForeground(Color.WHITE);
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(boxLayout);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel title = new JLabel("Available actions");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Player label
        JLabel playerLabel = new JLabel("Actual player: ");
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(playerLabel);

        playerTextInsect = new JLabel(player);
        playerTextInsect.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerTextInsect.setFont(new Font("Arial", Font.BOLD, 20));
        playerTextInsect.setForeground(Color.RED);
        panel.add(playerTextInsect);
        
        // Buttons
        String[] actions = {"Move insect", "Cut line", "Eat spore"};

        //ButtonPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));

        for (String action : actions) {
            JButton button = new JButton(action);
            button.setMaximumSize(new Dimension((mainWindow.getWidth()/5), 40));
            button.setAlignmentX(MainWindow.CENTER_ALIGNMENT);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);  // Szöveg színe fehér
            button.setBorder(new LineBorder(Color.WHITE, 3)); // 3px vastag keret
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setFocusPainted(false);  // Ne legyen fókusz keret
            switch (action) {
                case "Move insect":
                    actionButtons.put(button, 2);
                    button.addActionListener(e -> gController.sendCommand("/move"));
                    break;
                case "Cut line":
                    actionButtons.put(button, 2);
                    button.addActionListener(e -> gController.sendCommand("/cut-line"));
                    break;
                case "Eat spore":
                    actionButtons.put(button, 1);
                    button.addActionListener(e -> gController.sendCommand("/eat-spore"));
                    break;
                default:
                    break;
            }
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            buttonPanel.add(button);
        }

        //Láthatatlan JButton a távolságtartásra.
        JButton invisibleButton = new JButton();
        invisibleButton.setMaximumSize(new Dimension((mainWindow.getWidth()/5), 40));
        invisibleButton.setAlignmentX(MainWindow.CENTER_ALIGNMENT);
        invisibleButton.setBackground(null);
        invisibleButton.setForeground(null);
        invisibleButton.setBorder(null);
        invisibleButton.setEnabled(false);
        invisibleButton.setFocusPainted(false);  // Ne legyen fókusz keret
        //actionButtons.put(invisibleButton, 1);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(invisibleButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(buttonPanel);
        
        // Round label
        JPanel roundpanel = new JPanel();
        roundpanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Round:
        roundTextInsect = new JLabel("ROUND:");
        roundTextInsect.setForeground(Color.WHITE);
        roundTextInsect.setFont(new Font("Arial", Font.BOLD, 40));
        roundTextInsect.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        roundpanel.add(roundTextInsect);

        //Round number
        roundNumberTextInsect = new JLabel(" " + round);
        roundNumberTextInsect.setForeground(Color.RED);
        roundNumberTextInsect.setFont(new Font("Arial", Font.BOLD, 40));
        roundNumberTextInsect.setAlignmentX(Component.RIGHT_ALIGNMENT);

        roundpanel.add(roundNumberTextInsect);
        roundpanel.setBackground(Color.DARK_GRAY);
        roundpanel.setMaximumSize(new Dimension(mainWindow.getWidth()/5 - 40, 100));


        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        panel.add(roundpanel);
        
        // Finish button
        JButton finishButton = new JButton("Finish my round");
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishButton.setMaximumSize(new Dimension(mainWindow.getWidth()/5 - 40, 40));
        finishButton.setFont(new Font("Arial", Font.BOLD, 20));
        finishButton.setForeground(Color.RED);
        finishButton.setBackground(Color.DARK_GRAY);
        finishButton.setFocusable(false);
        finishButton.setBorder(new LineBorder(Color.WHITE, 3));
        finishButton.addActionListener(e -> gController.sendCommand("/next"));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(finishButton);
        
        return panel;
    }
    

    /**
     * Gombász parancsainak megjelenítése.
     * @param player az adott játékos
     * @param round a kör száma
     * @return az elkészült JPanel
     */
    private JPanel createMushroomPanel(String player, int round) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setForeground(Color.WHITE);
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(boxLayout);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel title = new JLabel("Available actions");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Player label
        JLabel playerLabel = new JLabel("Actual player: ");
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(playerLabel);

        playerTextMushroom = new JLabel(player);
        playerTextMushroom.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerTextMushroom.setFont(new Font("Arial", Font.BOLD, 20));
        playerTextMushroom.setForeground(Color.RED);
        panel.add(playerTextMushroom);
        
        //ButtonPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));

        // Buttons
        String[] actions = {"Grow mushroom", "Throw spore", "Build line", "Eat insect"};
        for (String action : actions) {
            JButton button = new JButton(action);
            button.setMaximumSize(new Dimension((mainWindow.getWidth()/5), 40));
            button.setAlignmentX(MainWindow.CENTER_ALIGNMENT);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);  // Szöveg színe fehér
            button.setBorder(new LineBorder(Color.WHITE, 3)); // 3px vastag keret
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setFocusPainted(false);  // Ne legyen fókusz keret
            switch (action) {
                case "Grow mushroom":
                    actionButtons.put(button, 1);
                    button.addActionListener(e -> gController.sendCommand("/build-mushroom"));
                    break;
                case "Throw spore":
                    actionButtons.put(button, 2);
                    button.addActionListener(e -> gController.sendCommand("/throw-spore"));
                    break;
                case "Build line":
                    actionButtons.put(button, 2);
                    button.addActionListener(e -> gController.sendCommand("/grow-line"));
                    break;
                case "Eat insect":
                    actionButtons.put(button, 1);
                    button.addActionListener(e -> gController.sendCommand("/eat-insect"));
                default:
                    break;
            }
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            buttonPanel.add(button);
        }
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(buttonPanel);

        
        // Round label
        JPanel roundpanel = new JPanel();
        roundpanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Round:
        roundTextMushroom = new JLabel("ROUND:");
        roundTextMushroom.setForeground(Color.WHITE);
        roundTextMushroom.setFont(new Font("Arial", Font.BOLD, 40));
        roundTextMushroom.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        roundpanel.add(roundTextMushroom);

        //Round number
        roundNumberTextMushroom = new JLabel(" " + round);
        roundNumberTextMushroom.setForeground(Color.RED);
        roundNumberTextMushroom.setFont(new Font("Arial", Font.BOLD, 40));
        roundNumberTextMushroom.setAlignmentX(Component.RIGHT_ALIGNMENT);

        roundpanel.add(roundNumberTextMushroom);
        roundpanel.setBackground(Color.DARK_GRAY);
        roundpanel.setMaximumSize(new Dimension(mainWindow.getWidth()/5 - 40, 100));


        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        panel.add(roundpanel);
        
        // Finish button
        JButton finishButton = new JButton("Finish my round");
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishButton.setMaximumSize(new Dimension(mainWindow.getWidth()/5 - 40, 40));
        finishButton.setFont(new Font("Arial", Font.BOLD, 20));
        finishButton.addActionListener(e -> gController.sendCommand("/next"));
        finishButton.setForeground(Color.RED);
        finishButton.setBackground(Color.DARK_GRAY);
        finishButton.setFocusable(false);
        finishButton.setBorder(new LineBorder(Color.WHITE, 3));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(finishButton);
        
        return panel;
    }
    
    /**
     * Panel megjelenításe
     * @param showInsect rovarász vagy gombász
     */
    private void showPanel(boolean showInsect) {
        CardLayout cl = (CardLayout) getLayout();
        if (showInsect) {
            cl.show(this, "insect");
        } else {
            cl.show(this, "mushroom");
        }
    }
    

    /**
     * Következő játékos kiválasztásáért felelős függvény
     * Ha a játékos nem tud játszani, azaz minden rovara és gombateste halott, akkor átugorjuk.
     * @param player játékos neve
     * @param isInsect rovarász vagy gombász
     * @param round kör száma
     * @param points pontok száma
     */
    @Override
    public void onNextRound(String player, boolean isInsect, int round, HashMap<String, Integer> points) {
        if(isInsect){
            playerTextInsect.setText(player);
            playerTextInsect.setForeground(gController.getPlayerColor(player));
            roundNumberTextInsect.setText(" " + round);
            showPanel(isInsect);
        }
        else{
            playerTextMushroom.setText(player);
            playerTextMushroom.setForeground(gController.getPlayerColor(player));
            roundNumberTextMushroom.setText(" " + round);
            showPanel(isInsect);
        }
        
        this.revalidate();
        this.repaint();
    }
    
    // Helper methods for testing
    public JPanel getInsectPanel() {
        return insectPanel;
    }
    
    public JPanel getMushroomPanel() {
        return mushroomPanel;
    }

    /**
     * Tecton kiválasztásakor meghívandó függvény
     * @param selectedCount hanyadik kiválasztás száma
     */
    @Override
    public void OnSelection(int selectedCount) {
        for (JButton button : actionButtons.keySet()) {
            button.setEnabled(actionButtons.get(button) == selectedCount);
        }
    }
}