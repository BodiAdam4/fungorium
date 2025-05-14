package graphics;

import controller.Controller;
import listeners.ObjectChangeListener;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Tecton;
import userinterface.CommandProcessor;

public class GraphicMain {

    public static Controller controller;
    public static CommandProcessor cmdProcessor;
    public static GraphicController gController;
    private static MainWindow mainWindow;

    public static void main(String[] args) {
        gController = new GraphicController();
        controller = new Controller();
        controller.addObjectListener(new ObjectChangeListener() {

            @Override
            public void insectChanged(ObjectChangeEvent event, Insect insect) {
                if (event == ObjectChangeEvent.OBJECT_ADDED)
                    gController.createInsect(insect);
            }

            @Override
            public void lineChanged(ObjectChangeEvent event, Line line) {
                if (event == ObjectChangeEvent.OBJECT_ADDED)
                    gController.createLine(line);
            }

            @Override
            public void tectonChanged(ObjectChangeEvent event, Tecton tecton) {
                if (event == ObjectChangeEvent.OBJECT_ADDED)
                    gController.createTecton(tecton);
            }

            @Override
            public void mushroomChanged(ObjectChangeEvent event, Mushroom mushroom) {
                if (event == ObjectChangeEvent.OBJECT_ADDED)
                    gController.createMushroom(mushroom);
            }
            
        });
        cmdProcessor = new CommandProcessor(controller);

        MainWindow mainWindow = new MainWindow(gController);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        mainWindow.revalidate();
        mainWindow.repaint();
        
        controller.addControlListener(mainWindow);
        controller.addJobListener(mainWindow);
        
    }
}
