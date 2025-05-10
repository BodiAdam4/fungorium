package graphics;

import java.awt.Color;
import java.awt.Graphics;
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

                if(alpha != 0 && new Color(pixel, true).equals(Color.WHITE))
                {
                    int red = (color.getRed());
                    int green = (color.getGreen());
                    int blue = (color.getBlue());
                    int tintedPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    tintedImage.setRGB(x, y, tintedPixel);
                }
                else if (alpha != 0 && !new Color(pixel, true).equals(Color.BLACK)) {
                    Color originalColor = new Color(pixel, true);
                    int red = (originalColor.getRed() + color.getRed()) / 2;
                    int green = (originalColor.getGreen() + color.getGreen()) / 2;
                    int blue = (originalColor.getBlue() + color.getBlue()) / 2;
                    int tintedPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    tintedImage.setRGB(x, y, tintedPixel);
                } else {
                    tintedImage.setRGB(x, y, pixel);
                }
            }
        }

        image = tintedImage;
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
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
    
}
