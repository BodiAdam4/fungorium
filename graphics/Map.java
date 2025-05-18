package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import model.Insect;
import model.Line;
import model.Spore;
import model.Tecton;

/**
 * Map osztály
 * Tárolja a grafikus elemeket.
 * Megjelenítésért felelős.
 */
public class Map extends JPanel implements MouseListener, MouseMotionListener {

    
    /* - Privát attribútumok*/

    /**
     * A térképen elhelyezkedő grafikus tektonok. Kulcsként a tekton griden lévő pozícióját kapja, ezzel biztosítva az egyedi pozíciót.
     */
    private List<GTecton> tectons = new ArrayList<>();      
    
    /**
     * A térképen lévő rovarok grafikus objektumainak listája.
     */
    private java.util.Map<GInsect, JPanel> insects = new HashMap<>();  
    
    /**
     * A térképen elhelyezkedő grafikus gombafonalak listája.
     */
    private List<GLine> lines = new ArrayList<>();    
    
    /**
     * A térképen elhelyezkedő grafikus gombatestek. Kulcsként a gombatest griden lévő pozícióját kapja, ezzel biztosítva az egyedi pozíciót.
     */
    private List<GMushroom> mushrooms = new ArrayList<>();

    final static public int CELL_SIZE = 150;
    final public int FIRST_TECTON_POSITION_X = 10;
    final public int FIRST_TECTON_POSITION_Y = 10;

    final public int MAP_SIZE = 5000;
    final public int MAP_START_POSITION = -2000;

    private GraphicController gController;

    /* - Konstuktor(ok)*/

    /**
     * Konstruktor
     * @param gController
     */
    public Map(GraphicController gController) {
        this.addMouseListener(this); //MouseListener hozzáadása a térképhez, hogy érzékelje a kattintásokat.
        this.addMouseMotionListener(this);
        this.setBounds(MAP_START_POSITION, MAP_START_POSITION, MAP_SIZE, MAP_SIZE); //A térkép pozíciója és mérete
        this.setOpaque(false);
        this.setLayout(null);

        this.gController = gController;
    }

    /* - Getter/Setter metódusok*/
    public GTecton getTecton(Tecton t) {
        for (GTecton gTecton : tectons) {
            if (gTecton.getMyTecton().equals(t)) {
                return gTecton;
            }
        }
        return null;
    }

    public GraphicController getGraphicController() {
        return gController;
    }

    /* - Grafikus gombatest keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GMushroom getMushroom(String id) {}


    /* - Grafikus tekton keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GTecton getTecton(String id) {}


    /* - Grafikus gombafonal keresése a térképen a kontrollerbeli azonosító szerint.*/
    public GLine getLine(Line line) {
        for(GLine gl : lines)
            if(gl.getMyLine().equals(line))
                return gl;
        return null;
    }

    public void addToList(GLine line) {
        synchronized (this) {
            lines.add(line);
        }
    }

    public void removeFromList(GLine line) {
        synchronized (this) {
            lines.remove(line);
        }
    }


    public GInsect getInsect(Insect insect) {
        for (GInsect i : insects.keySet()) {
            if (i.getMyInsect().equals(insect)) {
                return i;
            }
        }
        return null;
    }


    /* - Grafikus rovar keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GInsect getInsect(String id) {}


    /* - Egyéb metódusok*/

    /**
     * Cella keresése
     * @param x x koordináta, ami még nincs beszorozva a cella mérettel
     * @param y y koordináta, ami még nincs beszorozva a cella mérettel
     * @return a cella pontja
     */
    public Point getCell(int x, int y) {
        int col = x * CELL_SIZE;
        int row = y * CELL_SIZE;
        return new Point(col, row);
    }


    /**
     * Cella keresése
     * @param cell a keresett cella pontja, ami még nincs beszorozva a cella mérettel
     * @return a cella pontja
     */
    public Point getCell(Point cell) {
        int col = cell.x * CELL_SIZE;
        int row = cell.y * CELL_SIZE;
        return new Point(col, row);
    }


    /**
     * Megnézi, hogy üres-e az adott cella
     * @param cell az adott cella
     * @return üres-e
     */
    public boolean isCellEmpty(Point cell) {
        Point tPos = getCell(cell);
        for (GTecton t : tectons) {
            if (t.getLocation().equals(tPos)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Két pont közötti távolság.
     * @param p1 az első pont
     * @param p2 a második pont
     * @return távolság
     */
    public double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }


    /**
     * Két pont közti irány normalizálása
     * @param from a kiinduló pont
     * @param to a végpont
     * @param sc a skála
     * @return a normalizált vektor pont osztályban eltárolva
     */
    public static Point normalize(Point from, Point to, double sc) {
        int dx = to.x - from.x;
        int dy = to.y - from.y;

        double length = Math.sqrt(dx * dx + dy * dy);
        if (length == 0) return new Point(0, 0); // azonos pont esetén nincs irány

        double scale = sc / length;
        int nx = (int)Math.round(dx * scale);
        int ny = (int)Math.round(dy * scale);

        return new Point(nx, ny);
    }


    /**
     * Tektonok rendezése a térképen.
     * Minden tekton között egy adott távolságnak kell lennie.
     * Kezdetben túl kicsi a távolság, így elttolják egymást.
     */
    public void physicSorting() {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            try {
                for (int i = 0; i < tectons.size() - 1; i++) {
                    GTecton t1 = tectons.get(i);
                    for (int k = 0; k<tectons.size(); k++) {
                        GTecton t2 = tectons.get(k);
                        if (t1.equals(t2)) {
                            continue;
                        }

                        if (CELL_SIZE*2 > getDistance(t1.getLocation(), t2.getLocation())) {
                            isSorted = false;
                            Point t1Pos = t1.getLocation();
                            Point t2Pos = t2.getLocation();
                            Point dir1 = normalize(t2Pos, t1Pos,2.0);
                            Point dir2 = normalize(t1Pos, t2Pos,2.0);

                            t1.setLocation(new Point(t1Pos.x + dir1.x, t1Pos.y + dir1.y));
                            t2.setLocation(new Point(t2Pos.x + dir2.x, t2Pos.y + dir2.y));
                            
                        }
                    }
                }
                RefreshLines();
                this.revalidate();
                this.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }

                //Rovarok áthelyezése
                refreshInsects();

                //Szomszédok kiszámítása
                for (GTecton tecton1 : tectons) {
                    tecton1.getMyTecton().clearNeighbors();
                    for (GTecton tecton2 : tectons) {
                        if (CELL_SIZE*3 > getDistance(tecton1.getLocation(), tecton2.getLocation()) && tecton1 != tecton2) {
                            tecton1.getMyTecton().setNeighbors(tecton2.getMyTecton());
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }


    Point nextTecton = new Point(FIRST_TECTON_POSITION_X, FIRST_TECTON_POSITION_Y);
    int rows = 0;
    double maxDist = 200;


    /**
     * Tekton hozzáadása a térképhez. Paraméterként át kell adni a hozzáadandó grafikus tecton objektumot.
     * @param gtecton az adott grafikus tekton
     */
    public void addTecton(GTecton gtecton) {

        Tecton breakTecton = gtecton.getMyTecton().getBreakTecton();

        if (breakTecton != null) {
            GTecton parentTecton = getTecton(breakTecton);
            Random random = new Random();
            Point breakPos = new Point(parentTecton.getX()+random.nextInt(2, 7), parentTecton.getY()+random.nextInt(2, 7));
            gtecton.setBounds(breakPos.x, breakPos.y, CELL_SIZE, CELL_SIZE);
        }
        else {
            gtecton.setBounds(MAP_SIZE/2+new Random().nextInt(1,5), MAP_SIZE/2+new Random().nextInt(1,5), CELL_SIZE, CELL_SIZE);
        }

        this.add(gtecton);
        this.revalidate();
        this.repaint();

        tectons.add(gtecton);

        gtecton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                gtecton.ChangeColor(Color.RED);
                gtecton.repaint();

                for(GTecton t : tectons) {
                    if (t.getMyTecton().getNeighbors().contains(gtecton.getMyTecton())) {
                        t.ChangeColor(Color.white);
                        t.repaint();
                    }
                }
            }

            public void mouseExited(MouseEvent e) {
                gtecton.ResetTint();
                gtecton.repaint();

                for(GTecton t : tectons) {
                    if (t.getMyTecton().getNeighbors().contains(gtecton.getMyTecton())) {
                        t.ResetTint();
                        t.repaint();
                    }
                }
            }

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    gController.addSelected(gtecton);
                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    drawInfoPanel(gtecton, e.getPoint());
                }
            }
        });

        Thread t = new Thread(() -> {
            physicSorting();
        });
        t.start();
    }


    /**
     * Infópanel kirajzolása a tekton felületén elhelyezett objektumok listázásához
     */
    public JScrollPane infoScrollPane;

    /**
     * Információs panel megjelenítése az adott tekton mellett.
     * Megjeleníti a tektonon lévő játékosokat és a spórák számát.
     * @param gTecton az adott tekton
     * @param position a panel pozíciója
     */
    public void drawInfoPanel(GTecton gTecton, Point position) {
        if (infoScrollPane != null) {
            this.remove(infoScrollPane);
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.setOpaque(true);
        infoPanel.setBackground(new Color(100, 100, 100, 200));
        infoPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Set 5 px margin around the panel

        // mushroom info
        if (gTecton.getMyTecton().getMyMushroom() != null) {
            int id = gTecton.getMyTecton().getMyMushroom().getMushroomId();

            JPanel mushroomInfoPanel = new JPanel();
            mushroomInfoPanel.setLayout(new BoxLayout(mushroomInfoPanel, BoxLayout.PAGE_AXIS));
            mushroomInfoPanel.setOpaque(false);
            infoPanel.add(mushroomInfoPanel);

            JLabel mushroomSubLabel = new JLabel("Mushroom: ");
            mushroomSubLabel.setForeground(Color.WHITE);
            mushroomInfoPanel.add(mushroomSubLabel, BorderLayout.WEST);

            JLabel mushroomPickerJLabel = new JLabel("  •  " + gController.getMushroomName(id));
            mushroomPickerJLabel.setForeground(gController.getMushroomColor(id));
            infoPanel.add(mushroomPickerJLabel, BorderLayout.SOUTH);
        }

        // insect info
        JPanel insectInfoPanel = new JPanel(new BorderLayout());
        insectInfoPanel.setLayout(new BoxLayout(insectInfoPanel, BoxLayout.PAGE_AXIS));
        insectInfoPanel.setBackground(null);
        insectInfoPanel.setOpaque(false);
        infoPanel.add(insectInfoPanel, BorderLayout.WEST);

        JLabel insectSubLabel = new JLabel("Insects: ");
        insectSubLabel.setForeground(Color.WHITE);
        insectInfoPanel.add(insectSubLabel, BorderLayout.WEST);

        if (gTecton.getMyTecton().getInsects().isEmpty()) {

            //JLabel, ha a tekton rovartól mentes
            JLabel insectEmptyLabel = new JLabel("  •  None");
            insectEmptyLabel.setForeground(Color.decode("#ff6e6e"));
            insectInfoPanel.add(insectEmptyLabel, BorderLayout.SOUTH);

        }else {
            for (Insect insect : gTecton.getMyTecton().getInsects()) {
                Color color = gController.getInsectColor(insect.getInsectId());
                String effect = "";
    
                if (!insect.getCanCut()) effect = " (Tired)";
                else if (!insect.getCanMove()) effect = " (Freezed)";
                else if (insect.getSpeed() == 1) effect = " (Slowed)";
                else if (insect.getSpeed() == 3) effect = " (Fast)";
    
                JLabel insectNamesLabel = new JLabel("  •  " + gController.getInsectName(insect.getInsectId()) + effect);
                insectNamesLabel.setForeground(color);
                insectInfoPanel.add(insectNamesLabel, BorderLayout.SOUTH);
            }
        }
        

        // spore info
        HashMap<Integer, Integer> sporeCounts = new HashMap<>();
        for (Spore spore : gTecton.getMyTecton().getSporeContainer().getSpores()) {
            int id = spore.getSporeId();
            sporeCounts.put(id, sporeCounts.getOrDefault(id, 0) + 1);
        }

        JPanel sporesInfoPanel = new JPanel();
        sporesInfoPanel.setLayout(new BoxLayout(sporesInfoPanel, BoxLayout.PAGE_AXIS));
        sporesInfoPanel.setBackground(null);
        sporesInfoPanel.setOpaque(false);
        infoPanel.add(sporesInfoPanel);

        JLabel sporeLabel = new JLabel("Spores: ",SwingConstants.LEFT);
        sporeLabel.setForeground(Color.WHITE);
        sporesInfoPanel.add(sporeLabel, BorderLayout.WEST);


        if (sporeCounts.isEmpty()) {

            //JLabel, ha a tekton spórától mentes
            JLabel sporeEmptyLabel = new JLabel("  •  None");
            sporeEmptyLabel.setForeground(Color.decode("#ff6e6e"));
            sporesInfoPanel.add(sporeEmptyLabel, BorderLayout.SOUTH);

        } else {
            for (int id : sporeCounts.keySet()) {
                Color color = gController.getMushroomColor(id);
                JLabel SporeNameLabel = new JLabel("  •  " +gController.getMushroomName(id) + ": " + sporeCounts.get(id));
                System.out.println(gController.getMushroomName(id) + ": " + sporeCounts.get(id));
                SporeNameLabel.setForeground(color);
                sporesInfoPanel.add(SporeNameLabel, BorderLayout.SOUTH);
                //infoPanel.revalidate();
                //infoPanel.repaint();
            }
        }
        

        //ScrollPane létrehozása
        //ScrollPane felülírt megjelenése
        infoScrollPane = new JScrollPane(infoPanel) {
            @Override
            public JScrollBar createVerticalScrollBar() {
            JScrollBar verticalScrollBar = super.createVerticalScrollBar();
            verticalScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                this.thumbColor = Color.LIGHT_GRAY;
                this.trackColor = new Color(100, 100, 100, 200);
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
                }

                @Override
                protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
                }

                private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
                }
            });
            verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));
            verticalScrollBar.setBorder(null);
            return verticalScrollBar;
            }

            @Override
            public JScrollBar createHorizontalScrollBar() {
            JScrollBar horizontalScrollBar = super.createHorizontalScrollBar();
            horizontalScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                this.thumbColor = Color.BLACK;
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
                }

                @Override
                protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
                }

                private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
                }
            });
            horizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 5));
            return horizontalScrollBar;
            }
        };
        infoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        infoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoScrollPane.setOpaque(false);
        infoScrollPane.setBorder(null);
        infoScrollPane.getViewport().setOpaque(false);

        Point infoPos = new Point(gTecton.getX() + position.x, gTecton.getY() + position.y);
        infoScrollPane.setLocation(infoPos);
        infoScrollPane.setSize(150, 100);

        this.add(infoScrollPane);
        this.setComponentZOrder(infoScrollPane, 0);
        this.revalidate();
        this.repaint();
    }


    /**
     * Gombatest hozzáadása a térképhez. Paraméterként elvárja a hozzáadandó grafikus gombatest példányt.
     * @param gmushroom az adott gombatest
     */
    public void addMushroom(GMushroom gmushroom) {
        mushrooms.add(gmushroom);
        GTecton parentTecton = getTecton(gmushroom.getMyMushroom().getMyTecton());
        parentTecton.addMushroom(gmushroom);
    }


    /**
     * Gombatest eltávolítása a térképről. Szükséges megadni a gombatest kontrollerbeli azonosítóját.
     * @param id a gombatest azonosítója
     */
    public void removeMushroom(String id) {

    }


    /**
     * Rovar hozzáadása a térképhez. Szükséges megadni a rovar grafikus példányát, amit el szeretnénk helyezni.
     * @param ginsect az adott rovar
     */
    public void addInsect(GInsect ginsect) {
        GTecton parentTecton = getTecton(ginsect.getMyInsect().getTecton());
        ginsect.setBounds(parentTecton.getX(), parentTecton.getY(), CELL_SIZE, CELL_SIZE);
        insects.put(ginsect, parentTecton);
        this.add(ginsect);

        for (GInsect insect : insects.keySet()) {
            this.setComponentZOrder(insect, 0);
        }

        this.revalidate();
        this.repaint();
    }


    /**
     * Rovar mozgatása a térképen.
     * @param ginsect az adott grafikus rovar
     * @param moveTectons a két tetkton, ami között mozog a rovar
     */
    public void InsectMoved(GInsect ginsect, List<Tecton> moveTectons) {
        if (moveTectons.size() == 1) {
            //A rovar sebessége miatt egyből átkerül a másik tektonra
            GTecton parentTecton = getTecton(moveTectons.get(0));
            Random random = new Random();
            ginsect.setBounds(parentTecton.getX()+random.nextInt(-10,10), parentTecton.getY(), CELL_SIZE, CELL_SIZE);
            insects.put(ginsect, parentTecton);
        }
        else {
            //A rovar sebessége miatt még megy át a fonalon
            GTecton start = getTecton(moveTectons.get(0));
            GTecton end = getTecton(moveTectons.get(1));

            for (GLine line : lines) {
                if (line.getEnds().contains(start) && line.getEnds().contains(end)) {
                    int x = line.getMiddlePoint().x+line.getX()-CELL_SIZE/2;
                    int y = line.getMiddlePoint().y+line.getY()-CELL_SIZE/2;
                    System.out.println("Moved to X:"+x+" Y:"+y);
                    ginsect.setBounds(x, y, CELL_SIZE, CELL_SIZE);
                    insects.put(ginsect, line);
                }
            }
        }
        this.revalidate();
        this.repaint();
    }


    /**
     * Rovar eltávolítása a játéktérképről. Ehhez meg kell adni a rovar kontrollerbeli azonosítóját.
     * @param insect az adott rovar
     */
    public void removeInsect(Insect insect) {
        GInsect gInsect = getInsect(insect);
        insects.remove(gInsect);
        this.remove(gInsect);
        this.revalidate();
        this.repaint();
    }


    /**
     * Rovarok pozíciójának frissítése
     */
    public void refreshInsects() {
        for (GInsect insect : insects.keySet()) {
            insect.setLocation(insects.get(insect).getLocation());
        }
        this.revalidate();
        this.repaint();
    }


    /**
     * Gombafonal hozzáadása a játéktérképhez, amit a grafikus gombafonal példányának megadásával lehet végrehajtani.
     * @param line az adott grafikus gombafonal
     */
    public void addLine(GLine line) {
        Point t1Pos = line.getEnds().get(0).getLocation();
        Point t2Pos = line.getEnds().get(1).getLocation();

        Point min = new Point(Math.min(t1Pos.x, t2Pos.x), Math.min(t1Pos.y, t2Pos.y));
        Point max = new Point(Math.max(t1Pos.x, t2Pos.x), Math.max(t1Pos.y, t2Pos.y));

        Dimension size = new Dimension(max.x - min.x + CELL_SIZE, max.y - min.y+ CELL_SIZE);
        line.setBounds(min.x, min.y, size.width, size.height); //A gombafonal pozíciója és mérete

        Point t1RelativPos = new Point(t1Pos.x - line.getLocation().x+CELL_SIZE/2, t1Pos.y - line.getLocation().y+CELL_SIZE/2+10);
        Point t2RelativPos = new Point(t2Pos.x - line.getLocation().x+CELL_SIZE/2, t2Pos.y - line.getLocation().y+CELL_SIZE/2+10);

        if (t2RelativPos.x < t1RelativPos.x) {
            Point temp = t1RelativPos;
            t1RelativPos = t2RelativPos;
            t2RelativPos = temp;
        }


        for (GLine l : lines) {
            if (l.getEnds().contains(line.getEnds().get(0)) && l.getEnds().contains(line.getEnds().get(1))) {
                line.stepCurve();
                System.out.println("Line already exists between ");
            }
        }

        line.setEndPoints(t1RelativPos, t2RelativPos);
        System.out.println("Line endpoints: "+t1RelativPos.x + ", " + t1RelativPos.y + " - " + t2RelativPos.x + ", " + t2RelativPos.y);
        this.add(line);
        this.setComponentZOrder(line, this.getComponentCount() - 1);
        this.revalidate();
        this.repaint();

        addToList(line);
    }


    /**
     * Gombafonalak pozíciójának frissítése
     */
    public void RefreshLines() {
        synchronized (this) {
            for (GLine line : lines) {
                Point t1Pos = line.getEnds().get(0).getLocation();
                Point t2Pos = line.getEnds().get(1).getLocation();

                Point min = new Point(Math.min(t1Pos.x, t2Pos.x), Math.min(t1Pos.y, t2Pos.y));
                Point max = new Point(Math.max(t1Pos.x, t2Pos.x), Math.max(t1Pos.y, t2Pos.y));

                Dimension size = new Dimension(max.x - min.x + CELL_SIZE, max.y - min.y+ CELL_SIZE);
                line.setBounds(min.x, min.y, size.width, size.height); //A gombafonal pozíciója és mérete

                Point t1RelativPos = new Point(t1Pos.x - line.getLocation().x+CELL_SIZE/2, t1Pos.y - line.getLocation().y+CELL_SIZE/2+10);
                Point t2RelativPos = new Point(t2Pos.x - line.getLocation().x+CELL_SIZE/2, t2Pos.y - line.getLocation().y+CELL_SIZE/2+10);

                if (t2RelativPos.x < t1RelativPos.x) {
                    Point temp = t1RelativPos;
                    t1RelativPos = t2RelativPos;
                    t2RelativPos = temp;
                }
                
                try {
                    this.setComponentZOrder(line, this.getComponentCount() - 1);
                } catch (Exception e) {

                }
                line.setEndPoints(t1RelativPos, t2RelativPos);
                line.repaint();
            }
        }
    }


    /**
     * Gombafonal eltávolítása a térképről. 
     * Az eltávolításhoz meg kell adni az eltávolítandó fonál kontrollerbeli azonosítóját.
     * @param id az eltávolítandó gombafonal azonosítója
     */
    public void removeLine(Line line) {

        GLine gLine = getLine(line);

        if (gLine == null)  //Ha a fonál már megsimmisült a TectonTime vagy fonalvágás miatt
            return;

        if (gLine.getParent() == this)
            this.remove(gLine);

        removeFromList(gLine);
        this.repaint();
    }


    /**
     * Initializes the game board with a grid of islands.
     * @param cols Number of columns in the grid.
     * @param rows Number of rows in the grid.
     * @param imagePath Path to the image file for the islands.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();


        //A háttérszín legyen fekete
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);

        
        boolean drawGrid = false;
        boolean drawBarrier = false;

        if (drawGrid) {

            int cols = width / CELL_SIZE;                //Az oszlopok számának meghatározása
            int rows = height / CELL_SIZE;               //A sorok számáának meghatározása

            //Grid rajzolása, ez pedig legyen fehér
            //TODO: a grig körvonalát majd el kell tüntetni
            g.setColor(new Color(255, 255, 255, 50));
            for (int col = 0; col <= cols; col++) {
                int x = col * CELL_SIZE;
                g.drawLine(x, 0, x, rows * CELL_SIZE);
            }
            for (int row = 0; row <= rows; row++) {
                int y = row * CELL_SIZE;
                g.drawLine(0, y, cols * CELL_SIZE, y);
            }
        }

        if (drawBarrier){
            g.setColor(Color.RED);
            for (GTecton t : tectons) {
                g.drawOval(t.getLocation().x-(int) (maxDist/2)+(CELL_SIZE/2), t.getLocation().y-(int) (maxDist/2)+(CELL_SIZE/2), (int) maxDist, (int) maxDist);
                
                for (GTecton n : tectons) {
                    if (t.getMyTecton().getNeighbors().contains(n.getMyTecton()) && !t.equals(n)) {
                        g.drawLine(t.getLocation().x+(CELL_SIZE/2), t.getLocation().y+(CELL_SIZE/2), n.getLocation().x+(CELL_SIZE/2), n.getLocation().y+(CELL_SIZE/2));
                    }
                }
            }
        }
        
    }

    private Point mousePosition;

    @Override
    public void mouseClicked(MouseEvent e) {
        gController.RemoveSelection();

        if (infoScrollPane != null) {
            this.remove(infoScrollPane);
            infoScrollPane = null;
        }

        this.revalidate();
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePosition = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Handle mouse enter event
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Handle mouse exit event
    }

    
    @Override
    public void mouseDragged(MouseEvent e) {
        // Térkép mozgatása
        Point currentLocation = this.getLocation();

        int newX = currentLocation.x + e.getX() - mousePosition.x;
        int newY = currentLocation.y + e.getY() - mousePosition.y;

        this.setLocation(newX, newY);
    
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
