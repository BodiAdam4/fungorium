package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Image extends JPanel implements MouseListener {

    
    private BufferedImage baseImage;
    private BufferedImage image;
    private boolean selected = false;

    public Image(String imagePath){
        SetImage(imagePath);
        setOpaque(false);
    }

    /**
     * Kép beállítása elérési útvonal alapján
     * @param imagePath A kép elérési útja
     */
    public void SetImage(String imagePath){
        try {
            File imgFile = new File(imagePath);
            if(imgFile.exists() && imagePath != "")
                baseImage = ImageIO.read(imgFile);
            else
                throw new IOException("Image file not found: " + imagePath);
            
            image = baseImage;
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        revalidate();
        repaint();
    }

    
    public void SetImage(BufferedImage img){
        baseImage = img;
        image = baseImage;

        revalidate();
        repaint();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        this.repaint();
    }

    
    /**
     * Kép színezése
     * @param color A szín mellyel színeznénk
     * @return A színezett kép
     */
    public void TintImage(Color color){
        int width = baseImage.getWidth();
        int height = baseImage.getHeight();
        BufferedImage tintedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = baseImage.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;

                if(alpha != 0 && whiteCloseness(pixel) > 0.6)
                {
                    int pixelRed = (pixel >> 16) & 0xFF;
                    int pixelGreen = (pixel >> 8) & 0xFF;
                    int pixelBlue = pixel & 0xFF;

                    int targetRed = color.getRed();
                    int targetGreen = color.getGreen();
                    int targetBlue = color.getBlue();

                    double blendFactor = 0.5;

                    int red = (int) (blendFactor * targetRed + (1 - blendFactor) * pixelRed);
                    int green = (int) (blendFactor * targetGreen + (1 - blendFactor) * pixelGreen);
                    int blue = (int) (blendFactor * targetBlue + (1 - blendFactor) * pixelBlue);

                    // Összerakjuk az új ARGB pixelt
                    int tintedPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    tintedImage.setRGB(x, y, tintedPixel);
                }else {
                    tintedImage.setRGB(x, y, pixel);
                }
            }
        }

        image = tintedImage;
    }

    public static double whiteCloseness(int argb) {
        // Szétszedjük az ARGB értéket komponensekre
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        // Fehértől való távolság
        int dr = 255 - r;
        int dg = 255 - g;
        int db = 255 - b;

        double distance = Math.sqrt(dr * dr + dg * dg + db * db);
        double maxDistance = Math.sqrt(3 * 255 * 255); // ≈ 441.67

        // Fehér közelség = 1 - normalizált távolság
        return 1.0 - (distance / maxDistance);
    }

    //TODO: Új függvény
    public void ResetTint(){
        image = baseImage;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

            if (selected) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(3, 3, Map.CELL_SIZE-6, Map.CELL_SIZE-6, 20, 20);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
