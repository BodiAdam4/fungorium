package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.Line;
import javax.swing.JPanel;
public class Map extends JPanel implements MouseListener {

    
    /* - Privát attribútumok*/
    //private HashMap<Point, GTecton> tectons;            //A térképen elhelyezkedő grafikus tektonok. Kulcsként a tekton griden lévő pozícióját kapja, ezzel biztosítva az egyedi pozíciót.
    //private List<GInsect> insects;                      //A térképen lévő rovarok grafikus objektumainak listája.
    //private List<GLine> lines;                          //A térképen elhelyezkedő grafikus gombafonalak listája.
    //private GraphicController graphicController;        //A grafikus vezérlést megvalósító objektum.

    //TODO: ezt eltávolítani!!
    private HashMap<Point, GMushroom> mushrooms;        //A térképen elhelyezkedő grafikus gombatestek. Kulcsként a gombatest griden lévő pozícióját kapja, ezzel biztosítva az egyedi pozíciót.


    /* - Konstuktor(ok)*/
    public Map() {
        addMouseListener(this);
    }

    /* - Getter/Setter metódusok*/

    /* - Grafikus gombatest keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GMushroom getMushroom(String id) {}


    /* - Grafikus tekton keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GTecton getTecton(String id) {}


    /* - Grafikus gombafonal keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GLine getLine(String id) {}


    /* - Grafikus rovar keresése a térképen a kontrollerbeli azonosító szerint.*/
    //public GInsect getInsect(String id) {}


    /* - Egyéb metódusok*/

    /* - Tekton hozzáadása a térképhez. Paraméterként át kell adni a hozzáadandó grafikus tecton objektumot.*/
    //public void addTecton(GTecton gtecton) {}


    /* - Gombatest hozzáadása a térképhez. Paraméterként elvárja a hozzáadandó grafikus gombatest példányt.*/
    public void addMushroom(GMushroom gmushroom) {
        if (mushrooms == null) {
            mushrooms = new HashMap<>();
        }
        Point p = new Point(0, 0); //TODO: ezt majd át kell írni, hogy a gombatest pozícióját is figyelembe vegye.
        mushrooms.put(p, gmushroom);
        this.add(gmushroom); //Hozzáadja a grafikus gombatestet a térképhez.
    }


    /* - Gombatest eltávolítása a térképről. Szükséges megadni a gombatest kontrollerbeli azonosítóját.*/
    public void removeMushroom(String id) {}


    /* - Rovar hozzáadása a térképhez. Szükséges megadni a rovar grafikus példányát, amit el szeretnénk helyezni.*/
    //public void addInsect(GInsect ginsect) {}


    /* - Rovar eltávolítása a játéktérképről. Ehhez meg kell adni a rovar kontrollerbeli azonosítóját.*/
    public void removeInsect(String id) {}


    /* - Gombafonal hozzáadása a játéktérképhez, amit a grafikus gombafonal példányának megadásával lehet végrehajtani.*/
    //public void addLine(GLine line) {}


    /* - Gombafonal eltávolítása a térképről. Az eltávolításhoz meg kell adni az eltávolítandó fonál kontrollerbeli azonosítóját.*/
    public void removeLine(String id) {}


    /**
     * Initializes the game board with a grid of islands.
     * @param cols Number of columns in the grid.
     * @param rows Number of rows in the grid.
     * @param imagePath Path to the image file for the islands.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();                     //A panel szélessége
        int height = getHeight();                   //A panel magassága
        int cellSize = 150;                         //Cellák méretezése

        //A háttérszín legyen fekete
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        int cols = width / cellSize;                //Az oszlopok számának meghatározása
        int rows = height / cellSize;               //A sorok számáának meghatározása

        //Grid rajzolása, ez pedig legyen fehér
        //TODO: a grig körvonalát majd el kell tüntetni
        g.setColor(Color.WHITE);
        for (int col = 0; col <= cols; col++) {
            int x = col * cellSize;
            g.drawLine(x, 0, x, rows * cellSize);
        }
        for (int row = 0; row <= rows; row++) {
            int y = row * cellSize;
            g.drawLine(0, y, cols * cellSize, y);
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


    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse click event
        int col = e.getX() / 150;
        int row = e.getY() / 150;
        Point cell = new Point(col, row);
        //cell.addMushroom(new GMushroom()); //TODO: ezt majd át kell írni, hogy a gombatest pozícióját is figyelembe vegye.
        System.out.println("Cell clicked: " + cell.x + ", " + cell.y);
        //GMushroom m = new GMushroom("12","s1");
        //addMushroom(m);
        this.repaint();
        this.revalidate();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release event
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Handle mouse enter event
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Handle mouse exit event
    }

}
