package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.Line;
import javax.swing.JPanel;

public class Map extends JPanel {
    
    /* - Privát attribútumok*/
    //private HashMap<Point, GTecton> tectons;            //A térképen elhelyezkedő grafikus tektonok. Kulcsként a tekton griden lévő pozícióját kapja, ezzel biztosítva az egyedi pozíciót.
    //private List<GInsect> insects;                      //A térképen lévő rovarok grafikus objektumainak listája.
    //private List<GLine> lines;                          //A térképen elhelyezkedő grafikus gombafonalak listája.
    //private GraphicController graphicController;        //A grafikus vezérlést megvalósító objektum.


    /* - Konstuktor(ok)*/
    public Map() {

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
    public void addMushroom(GMushroom gmushroom) {}


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

        //Draw island images
        
        for (Map.Entry<Point, BufferedImage> entry : handler.getImageCells().entrySet()) {
            Point p = entry.getKey();
            BufferedImage img = entry.getValue();
            int x = p.x * cellSize;
            int y = p.y * cellSize;
            g.drawImage(img, x, y, null);
        }
        */
    }

}
