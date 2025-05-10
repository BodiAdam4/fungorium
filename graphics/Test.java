package graphics;

import java.util.ArrayList;
import java.util.List;
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

        for (int i = 0; i<10; i++){
            Tecton tecton = new Tecton();
            tectons.add(tecton);

            GTecton gTecton = new GTecton(tecton);
            map.addTecton(gTecton);
        }
    }
}
