package graphics;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import listeners.ControlListener;
import java.awt.Color;

public class ControlPanel extends JPanel implements ControlListener {
    
    private final GraphicController gController;
    private JPanel insectPanel;
    private JPanel mushroomPanel;
    
    public ControlPanel(GraphicController gController) {
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
    
    private JPanel createInsectPanel(String player, int round) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.DARK_GRAY);
        panel.setForeground(Color.WHITE);
        
        // Title
        JLabel title = new JLabel("Available actions");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        panel.add(title);
        
        // Player label
        JLabel playerLabel = new JLabel("Actual player: ");
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel.setForeground(Color.WHITE);
        panel.add(playerLabel);

        JLabel playerLabel2 = new JLabel(player);
        playerLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel2.setForeground(Color.RED);
        panel.add(playerLabel2);
        
        // Buttons
        String[] actions = {"Move insect", "Cut line", "Eat spore"};
        for (String action : actions) {
            JButton button = new JButton(action);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(150, 30));
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);  // Szöveg színe fehér
             button.setBorder(new LineBorder(Color.WHITE, 3)); // 3px vastag keret
            button.setFocusPainted(false);  // Ne legyen fókusz keret
            switch (action) {
                case "Move insect":
                    button.addActionListener(e -> gController.sendCommand("/move-insect"));
                    break;
                case "Cut line":
                    button.addActionListener(e -> gController.sendCommand("/cut-line"));
                    break;
                case "Eat spore":
                    button.addActionListener(e -> gController.sendCommand("/eat-spore"));
                    break;
                default:
                    break;
            }
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(button);
        }
        
        // Round label
        JLabel roundLabel = new JLabel("ROUND: " + round);
        roundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundLabel.setForeground(Color.WHITE);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(roundLabel);
        
        // Finish button
        JButton finishButton = new JButton("Finish my round");
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishButton.setMaximumSize(new Dimension(150, 30));
        finishButton.setForeground(Color.WHITE);
        finishButton.setBackground(Color.DARK_GRAY);
        finishButton.setBorder(new LineBorder(Color.WHITE, 3));
        finishButton.addActionListener(e -> gController.sendCommand("/next"));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(finishButton);
        
        return panel;
    }
    
    private JPanel createMushroomPanel(String player, int round) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setForeground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Title
        JLabel title = new JLabel("Available actions");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        panel.add(title);
        
        // Player label
        JLabel playerLabel = new JLabel("Actual player: ");
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel.setForeground(Color.WHITE);
        panel.add(playerLabel);

        JLabel playerLabel2 = new JLabel(player);
        playerLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel2.setForeground(Color.RED);
        panel.add(playerLabel2);
        
        
        // Buttons
        String[] actions = {"Grow mushroom", "Throw spore", "Build line", "Eat insect"};
        for (String action : actions) {
            JButton button = new JButton(action);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(150, 30));
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);  // Szöveg színe fehér
            button.setBorder(new LineBorder(Color.WHITE, 3)); // 3px vastag keret
            button.setFocusPainted(false);  // Ne legyen fókusz keret
            switch (action) {
                case "Grow mushroom":
                    button.addActionListener(e -> gController.sendCommand("/grow-mushroom"));
                    break;
                case "Throw spore":
                    button.addActionListener(e -> gController.sendCommand("/throw-spore"));
                    break;
                case "Build line":
                    button.addActionListener(e -> gController.sendCommand("/grow-line"));
                    break;
                case "Eat insect":
                    button.addActionListener(e -> gController.sendCommand("/eat-insect"));
                default:
                    break;
            }
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(button);
        }
        
        // Round label
        JLabel roundLabel = new JLabel("ROUND: " + round);
        roundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundLabel.setForeground(Color.WHITE);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(roundLabel);
        
        // Finish button
        JButton finishButton = new JButton("Finish my round");
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishButton.setMaximumSize(new Dimension(150, 30));
        finishButton.addActionListener(e -> gController.sendCommand("/next"));
        finishButton.setForeground(Color.WHITE);
        finishButton.setBackground(Color.DARK_GRAY);
        finishButton.setBorder(new LineBorder(Color.WHITE, 3));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(finishButton);
        
        return panel;
    }
    
    private void showPanel(boolean showInsect) {
        CardLayout cl = (CardLayout) getLayout();
        if (showInsect) {
            cl.show(this, "insect");
        } else {
            cl.show(this, "mushroom");
        }
    }
    
    @Override
    public void onNextRound(String player, boolean isInsect, int round, HashMap<String, Integer> points) {
        if(isInsect){
            insectPanel = createInsectPanel(player, round);
            showPanel(true);
        }
        else{
            mushroomPanel = createMushroomPanel(player, round);
            showPanel(true);
        }
    }
    
    // Helper methods for testing
    public JPanel getInsectPanel() {
        return insectPanel;
    }
    
    public JPanel getMushroomPanel() {
        return mushroomPanel;
    }
}