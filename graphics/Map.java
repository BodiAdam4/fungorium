package graphics;

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
import javax.swing.JPanel;
import model.Tecton;

/**
 * Map osztály
 * Tárolja a grafikus elemeket.
 * Megjelenítésért felelős.
 */
public class Map extends JPanel implements MouseListener, MouseMotionListener {

    
    /* - Privát attribútumok*/
    private List<GTecton> tectons = new ArrayList<>();            //A térképen elhelyezkedő grafikus tektonok. Kulcsként a tekton griden lévő pozícióját kapja, ezzel biztosítva az egyedi pozíciót.
    private java.util.Map<GInsect, JPanel> insects = new HashMap<>();                      //A térképen lévő rovarok grafikus objektumainak listája.
    private List<GLine> lines = new ArrayList<>();                          //A térképen elhelyezkedő grafikus gombafonalak listája.
    private List<GMushroom> mushrooms = new ArrayList<>();       //A térképen elhelyezkedő grafikus gombatestek. Kulcsként a gombatest griden lévő pozícióját kapja, ezzel biztosítva az egyedi pozíciót.

    //private GraphicController graphicController;        //A grafikus vezérlést megvalósító objektum.

    final static public int CELL_SIZE = 150;
    final public int ROW_COUNT = 3;
    final public int FIRST_TECTON_POSITION_X = 10;
    final public int FIRST_TECTON_POSITION_Y = 10;
    final public int TECTON_DISTANCE = 4;

    final public int MAP_SIZE = 5000;
    final public int MAP_START_POSITION = -2000;

    //Start pos : 50 55

    /* - Konstuktor(ok)*/
    public Map() {
        this.addMouseListener(this); //MouseListener hozzáadása a térképhez, hogy érzékelje a kattintásokat.
        this.addMouseMotionListener(this);
        this.setBounds(MAP_START_POSITION, MAP_START_POSITION, MAP_SIZE, MAP_SIZE); //A térkép pozíciója és mérete
        this.setOpaque(false);
        this.setLayout(null);
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

    /* - Grafikus gombatest keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GMushroom getMushroom(String id) {}


    /* - Grafikus tekton keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GTecton getTecton(String id) {}


    /* - Grafikus gombafonal keresése a térképen a kontrollerbeli azonosító szerint.*/
    public GLine getLine(String id) {
        for(GLine gl : lines)
            if(gl.id.equals(id))
                return gl;
        return null;
    }


    /* - Grafikus rovar keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GInsect getInsect(String id) {}


    /* - Egyéb metódusok*/

    public Point getCell(int x, int y) {
        int col = x * CELL_SIZE;
        int row = y * CELL_SIZE;
        return new Point(col, row);
    }

    public Point getCell(Point cell) {
        int col = cell.x * CELL_SIZE;
        int row = cell.y * CELL_SIZE;
        return new Point(col, row);
    }

    public boolean isCellEmpty(Point cell) {
        Point tPos = getCell(cell);
        for (GTecton t : tectons) {
            if (t.getLocation().equals(tPos)) {
                return false;
            }
        }
        return true;
    }

    public double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

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

    public void physicSorting() {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
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
        }
    }


    Point nextTecton = new Point(FIRST_TECTON_POSITION_X, FIRST_TECTON_POSITION_Y);
    int rows = 0;
    double maxDist = 200;

    /* - Tekton hozzáadása a térképhez. Paraméterként át kell adni a hozzáadandó grafikus tecton objektumot.*/
    public void addTecton(GTecton gtecton) {
        Point tPos = getCell(nextTecton);
        System.out.println("Tecton position: " + tPos.x + ", " + tPos.y);
        gtecton.setBounds(MAP_SIZE/2+new Random().nextInt(1,5), MAP_SIZE/2+new Random().nextInt(1,5), CELL_SIZE, CELL_SIZE);
        this.add(gtecton);
        System.out.println("Tecton bounds: " + gtecton.getBounds());
        this.revalidate();
        this.repaint();

        tectons.add(gtecton);

        gtecton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                gtecton.TintImage(Color.RED);
                gtecton.repaint();

                for(GTecton t : tectons) {
                    if (t.getMyTecton().getNeighbors().contains(gtecton.getMyTecton())) {
                        t.TintImage(Color.white);
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
        });

        nextTecton.x += TECTON_DISTANCE;

        if (nextTecton.x > FIRST_TECTON_POSITION_X + (TECTON_DISTANCE * ROW_COUNT)) {
            if (rows % 2 == 0) {
                nextTecton.x = FIRST_TECTON_POSITION_X + (TECTON_DISTANCE / 2);
            } else {
                nextTecton.x = FIRST_TECTON_POSITION_X;
            }
            nextTecton.y += TECTON_DISTANCE;

            rows++;
        }
        Thread t = new Thread(() -> {
            physicSorting();
        });
        t.start();
    }

    public void addTecton(Point position, GTecton tecton) {
        tectons.add(tecton);
        tecton.setBounds(position.x, position.y, CELL_SIZE, CELL_SIZE);
        
        Thread thread = new Thread(() -> {
            physicSorting();
        });
        thread.start();

        this.add(tecton);
        this.revalidate();
        this.repaint();
    }


    /* - Gombatest hozzáadása a térképhez. Paraméterként elvárja a hozzáadandó grafikus gombatest példányt.*/
    public void addMushroom(GMushroom gmushroom) {
        mushrooms.add(gmushroom);
        GTecton parentTecton = getTecton(gmushroom.getMyMushroom().getMyTecton());
        parentTecton.addMushroom(gmushroom);
    }


    /* - Gombatest eltávolítása a térképről. Szükséges megadni a gombatest kontrollerbeli azonosítóját.*/
    public void removeMushroom(String id) {
        
    }


    /* - Rovar hozzáadása a térképhez. Szükséges megadni a rovar grafikus példányát, amit el szeretnénk helyezni.*/
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


    /* - Rovar eltávolítása a játéktérképről. Ehhez meg kell adni a rovar kontrollerbeli azonosítóját.*/
    public void removeInsect(String id) {}

    /* - Rovarok pozíciójának frissítése */
    public void refreshInsects() {
        for (GInsect insect : insects.keySet()) {
            insect.setLocation(insects.get(insect).getLocation());
        }
        this.revalidate();
        this.repaint();
    }


    /* - Gombafonal hozzáadása a játéktérképhez, amit a grafikus gombafonal példányának megadásával lehet végrehajtani.*/
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

        lines.add(line);
    }

    public void RefreshLines() {
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
            
            this.setComponentZOrder(line, this.getComponentCount() - 1);
            line.setEndPoints(t1RelativPos, t2RelativPos);
            line.repaint();
        }
    }


    /**
     * Gombafonal eltávolítása a térképről. 
     * Az eltávolításhoz meg kell adni az eltávolítandó fonál kontrollerbeli azonosítóját.
     * @param id az eltávolítandó gombafonal azonosítója
     */
    public void removeLine(String id) {
        for(GLine gl : lines)
            if(gl.id.equals(id))
                lines.remove(gl);
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

        //Draw lines between islands
        /*
        Graphics2D g2 = (Graphics2D) g;
        for (Line line : handler.getLines()) {
            line.draw(g2);
        }
        
        //Draw mushroom images in the grid cells
        for (java.util.Map.Entry<Point, GMushroom> entry : mushrooms.entrySet()) {
            Point p = entry.getKey();
            GMushroom mushroom = new GMushroom("12","s1");
            addMushroom(mushroom);
        }
        */
        
    }

    private Point mousePosition;

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse click event
        int col = e.getX() / CELL_SIZE;
        int row = e.getY() / CELL_SIZE;
        Point cell = new Point(col, row);
        //cell.addMushroom(new GMushroom()); //TODO: ezt majd át kell írni, hogy a gombatest pozícióját is figyelembe vegye.
        System.out.println("Cell clicked: " + cell.x + ", " + cell.y);
        System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());


        //TODO: Csak teszt miatt van benne ki kell venni
        //GraphicMain.cmdProcessor.ExecuteCommand("/create-tecton");


        //TODO: Idáig csak teszt miatt van benne ki kell venni
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
