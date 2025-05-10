package graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Tecton;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        JPanel mapPanel = new Image("graphics\\images\\bg.jpg");
        mapPanel.setLayout(null);
        frame.add(mapPanel);

        Map map = new Map();
        mapPanel.add(map);

        frame.revalidate();
        frame.repaint();

        List<Tecton> tectons = new ArrayList<>();
        List<GTecton> gTectons = new ArrayList<>();

        Tecton mainTecton = new Tecton();
        GTecton gTecton = new GTecton(mainTecton);
        map.addTecton(gTecton);
        gTectons.add(gTecton);
        

        for (int i = 0; i<20; i++){
            Tecton tecton = new Tecton();
            tectons.add(tecton);
            tecton.setNeighbors(mainTecton);
            mainTecton.setNeighbors(tecton);

            gTecton = new GTecton(tecton);
            map.addTecton(gTecton);
            gTectons.add(gTecton);
        }

        GLine gLine = new GLine(gTectons.get(0), gTectons.get(4));
        map.addLine(gLine);

        GLine gLine1 = new GLine(gTectons.get(1), gTectons.get(4));
        map.addLine(gLine1);


        GLine gLineMulty = new GLine(gTectons.get(2), gTectons.get(4));
        map.addLine(gLineMulty);

        for (int i = 0; i < 3; i++) {
            gLineMulty = new GLine(gTectons.get(new Random().nextInt(0, gTectons.size())), gTectons.get(new Random().nextInt(0, gTectons.size())));
            gLineMulty.setBackground(new Color(i*60+100, i*60+50, i*30+60));
            map.addLine(gLineMulty);
        }

    }
}
