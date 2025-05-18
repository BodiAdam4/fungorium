package graphics;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Egy-egy játékos beállítását lehetővé tevő UI elem. Ezen helyezkedik el a név beviteli mezeje, 
 * illetve itt lehet kiválasztani a játékos színét és kasztját is.
 */
public class PlayerPanel extends JPanel {
    
    /* - Privát attribútumok*/

    /**
     * A játékos nevének beviteléhez szükséges szövegdoboz.
     */
    private JTextField nameBox;     

    /**
     * A játékos színe, mely a későbbiekben segít megkülönböztetni a különböző objektumok tulajdonosait.
     */
    private Color color;  

    /**
     *  Egy rádiógomb, mellyel eldönthető, hogy a játékos rovarász vagy gombász szeretne lenni.
     */             
    private JRadioButton insectButton;   

    /**
     * A játékos sorszáma, mely a fejlécben jelenik meg.
     */
    private int playerNumber;  

    /**
     * A szülő panel, mely a PlayerPanel-t tartalmazza. Ezen keresztül lehet eltávolítani a panelt a főmenüből.
     */
    private MainMenu parent;            

    /**
     * A fejléc szövege, mely a játékos sorszámát tartalmazza.
     */
    private JLabel headerLabel;         

    /**
     * A színválasztó négyzetek színeit tároló tömb.
     */
    private Color[] colors = new Color[16];


    /* - Konstruktor(ok)*/

    /**
     * Konstruktor
     * @param playerNumber
     * @param parent
     */
    public PlayerPanel(int playerNumber, MainMenu parent) {
        
        this.playerNumber = playerNumber; //A játékos sorszámának beállítása
        this.parent = parent; //A szülő panel beállítása

        //A panel stílusának beállítása
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setPreferredSize(new Dimension(300, 300));
        this.setMaximumSize(new Dimension(300, 300));
        this.setMinimumSize(new Dimension(300, 300));
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        //JPanel a kontenthez
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        //Fejléc beállítása játékossorszámozással és "X" gombbal
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(300, 35)); //Fejléc mérete
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));

        //JLabel a fejléchez
        headerLabel = new JLabel("#" + playerNumber + ". Player");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.WEST);

        //JButton a fejléchez "X"-el
        JButton closeButton = new JButton(" X ");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setPreferredSize(new Dimension(30, 20));
        closeButton.setMaximumSize(new Dimension(30, 20));
        closeButton.setMinimumSize(new Dimension(30, 20));
        closeButton.setForeground(Color.RED);
        closeButton.setBackground(Color.BLACK);
        //closeButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); //Fehér szegély
        closeButton.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, Color.WHITE)); //Fehér szegély
        //closeButton.setBorder(null); // No border
        closeButton.setFocusPainted(false);
        
        //JButton eseménykezelője
        closeButton.addActionListener(e -> parent.removePlayerPanel(this));
        headerPanel.add(closeButton, BorderLayout.EAST);

        //Hozzáadjuk a fejlécet a kontenthez
        contentPanel.add(headerPanel);

        //JPanel a szeparátorhoz
        JPanel separatorPanel = new JPanel();
        separatorPanel.setOpaque(false);
        //separatorPanel.setBackground(Color.RED); //Fekete háttér
        separatorPanel.setPreferredSize(new Dimension(300, 30)); //Szeparátor mérete
        separatorPanel.setMaximumSize(new Dimension(300, 30)); //Szeparátor mérete
        contentPanel.add(separatorPanel); //Hozzáadjuk a szeparátort a kontenthez
        

        //#######################################################################################################//

        //JTextfield a névhez
        nameBox = new JTextField("Enter your name here");
        nameBox.setForeground(Color.LIGHT_GRAY);
        nameBox.setOpaque(false);
        nameBox.setPreferredSize(new Dimension(300, 25)); //A szövegdoboz mérete
        nameBox.setMaximumSize(new Dimension(Short.MAX_VALUE, 25));
        nameBox.setAlignmentX(CENTER_ALIGNMENT);
        nameBox.setFont(new Font("Arial", Font.ITALIC, 14));
        nameBox.setCaretColor(Color.WHITE);
        // Removing the top border of the text field
        nameBox.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
        nameBox.setToolTipText("Please do not use special characters, like: !@#$%^&*()_+-=[]{}|;:'\",.<>/?`~\\\\");

        nameBox.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (e.getKeyChar() == ' ' ) {
                    e.consume(); // Megakadályozza a szóköz beírását
                }
            }
        });

        // Add focus listener to handle placeholder text
        nameBox.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
            if (nameBox.getText().equals("Enter your name here")) {
                nameBox.setText("");
                nameBox.setForeground(Color.WHITE);
                nameBox.setFont(new Font("Arial", Font.BOLD, 14));
            }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
            if (nameBox.getText().isEmpty()) {
                nameBox.setText("Enter your name here");
                nameBox.setForeground(Color.LIGHT_GRAY);
                nameBox.setFont(new Font("Arial", Font.ITALIC, 14));
            }
            }
        });
        //nameBox.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); // Set margin height to 20

        //Hozzáadjuk a név mezőt a kontenthez
        contentPanel.add(nameBox);
        contentPanel.add(Box.createVerticalStrut(15));

        //#######################################################################################################//

        //Színválasztó sáv horizontális scrollbar-al
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        colorPanel.setOpaque(false);
        colorPanel.setAlignmentX(CENTER_ALIGNMENT);

        //JLabel a színválasztóhoz
        JLabel colorLabel = new JLabel("Pick a color:");
        colorLabel.setForeground(Color.WHITE);
        colorLabel.setAlignmentX(LEFT_ALIGNMENT);
        colorPanel.add(colorLabel);
        colorPanel.add(Box.createVerticalStrut(10));

        //Jpanel a színválasztó négyzetekkel
        // Create color squares panel
        JPanel colorsPanel = new JPanel();
        colorsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 8));
        //colorsPanel.setOpaque(false);
        colorsPanel.setBackground(Color.BLACK);

        //Színek tömbje
        colors = new Color[]{
            // Kék tartomány (neon árnyalatok)
            Color.decode("#70faff"), // Neon Cyan
            Color.decode("#33ccff"), // Neon Sky Blue
            Color.decode("#1f51ff"), // Neon Azure
            Color.decode("#1d008f"), // Neon Electric Blue

            // Zöld tartomány (neon árnyalatok)
            Color.decode("#39ff14"), // Neon Green
            Color.decode("#87ffca"), // Neon Mint
            Color.decode("#a2ff00"), // Neon Spring
            Color.decode("#018a01"), // Neon Lime

            // Piros/Narancs tartomány (neon árnyalatok)
            Color.decode("#ff073a"), // Neon Red
            Color.decode("#ff5e00"), // Neon Orange
            Color.decode("#ff1493"), // Neon Pink
            Color.decode("#ff87d5"), // Neon Scarlet

            // Lila/Sárga tartomány (neon árnyalatok)
            Color.decode("#cc00ff"), // Neon Purple
            Color.decode("#ff00ff"), // Neon Magenta
            Color.decode("#ffff00"), // Neon Yellow
            Color.decode("#ffb700")  // Neon Amber
        };

        //Színek négyzetek létrehozása
        for (Color color : colors) {
            JPanel colorSquare = new JPanel();
            colorSquare.setPreferredSize(new Dimension(24, 24));
            colorSquare.setBackground(color);
            colorSquare.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
            
            // Add selection logic
            colorSquare.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Clear previous selections
                    for (Component c : colorsPanel.getComponents()) {
                        if (c instanceof JPanel) {
                            ((JPanel) c).setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
                        }
                    }
                    // Highlight selected color
                    colorSquare.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                    PlayerPanel.this.color = colorSquare.getBackground(); // Set the selected color
                    //System.out.println("Selected color: " + colorSquare.getBackground().toString()); // Debug output
                }
            });
            
            //Hozzáadjuk a színnégyzeteket a színválasztó panelhez
            colorsPanel.add(colorSquare);
        }

        //Minimalista scrollbar beállítása
        JScrollPane scrollPane = new JScrollPane(colorsPanel);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //JScrollbar beállítása
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();

        verticalScrollBar.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        horizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 8));

        //Vertikális scrollbar színének beállítása
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

        //Horizontális scrollbar színének beállítása
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

        //Hozzáadjuk a scrollpane-t a színválasztó panelhez
        colorPanel.add(scrollPane);

        //Hozzáadjuk a színválasztó panelt a kontenthez
        contentPanel.add(colorPanel);
        contentPanel.add(Box.createVerticalStrut(15));

        //#######################################################################################################//

        //JPanel a kasztválasztáshoz
        JPanel castPanel = new JPanel();
        castPanel.setLayout(new BoxLayout(castPanel, BoxLayout.Y_AXIS));
        castPanel.setOpaque(false);
        castPanel.setAlignmentX(CENTER_ALIGNMENT);

        //JLabel a kasztválasztóhoz
        JPanel castLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        castLabelPanel.setOpaque(false);
        JLabel castLabel = new JLabel("Chose your cast:");
        castLabel.setForeground(Color.WHITE);
        castLabelPanel.add(castLabel);
        castPanel.add(castLabelPanel);

        //ButtonGroup a kasztválasztáshoz
        ButtonGroup castGroup = new ButtonGroup();

        //JPanel az rovarászoknak
        JPanel insectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        insectPanel.setOpaque(false);

        //JRadioButton a rovarászoknak
        insectButton = new JRadioButton("Insectpicker");
        insectButton.setForeground(Color.WHITE);
        insectButton.setOpaque(false);
        insectButton.setSelected(true);
        insectButton.setFocusable(false);
        castGroup.add(insectButton);
        insectPanel.add(insectButton);
        castPanel.add(insectPanel);

        //JPanel a gombászoknak
        JPanel mushroomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        mushroomPanel.setOpaque(false);

        //JRadioButton a gombászoknak
        JRadioButton mushroomButton = new JRadioButton("Mushroompicker");
        mushroomButton.setForeground(Color.WHITE);
        mushroomButton.setOpaque(false);
        mushroomButton.setFocusable(false);
        castGroup.add(mushroomButton);
        mushroomPanel.add(mushroomButton);
        castPanel.add(mushroomPanel);

        //Hozzáadjuk a kasztválasztó panelt a kontenthez
        contentPanel.add(castPanel);

        //Hozzáadjuk a kontent panelt a fő panelhez
        this.add(contentPanel, BorderLayout.CENTER);

    }


    /* - Getter/Setter metódusok*/

    /**
     * Visszaadja a felhasználó által bevitt játékosnevet.
     * @return a játékos neve
     */
    public String getName() {
        //If the playerName equals "Enter your name here", then add a "Player_[number]" name to the player. The "[]number]" is the player number.
        if (nameBox.getText().equals("Enter your name here") || nameBox.getText().isEmpty()) {
            return "Player_" + playerNumber;
            
        } else{
            //If the playerName is not empty, then return the playerName.
            return nameBox.getText();
        }
        
    }


    /** 
     * Visszaadja a játékos által kiválasztott színt.
     * @return a játékos színe
     */
    public Color getColor() {
        if (color == null) {
            int index = (int) (Math.random() * colors.length);
            color = colors[index];
        }
        return color;
    }

    public void setColor(Color newColor){
        this.color= newColor;
    }


    /**
     * Visszaad egy logikai változót, ami megmondja, hogy a játékos rovarász vagy gombász kasztot választotta.
     * @return rovarász, ha igaz
     */
    public boolean isInsect(){
        return insectButton.isSelected();
    }


    /* - Egyéb metódusok*/

    /**
     * Játékos számának frissítése automatikus név megadás esetén
     * @param newNumber az új szám
     */
    public void updatePlayerNumber(int newNumber) {
        this.playerNumber = newNumber;
        headerLabel.setText("#" + playerNumber + ". Player");
    }

}
