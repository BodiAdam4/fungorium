package graphics;

import controller.Controller;
import listeners.ObjectChangeListener;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Tecton;
import userinterface.CommandProcessor;

public class GraphicMain {

    private static Controller controller;
    public static CommandProcessor cmdProcessor;
    private static GraphicController gController;
    private static MainWindow mainWindow;

    public static void main(String[] args) {
        gController = new GraphicController();
        controller = new Controller();
        controller.addObjectListener(new ObjectChangeListener() {

            @Override
            public void insectChanged(ObjectChangeEvent event, Insect insect) {
                gController.createInsect(insect);
            }

            @Override
            public void lineChanged(ObjectChangeEvent event, Line line) {
                gController.createLine(line);
            }

            @Override
            public void tectonChanged(ObjectChangeEvent event, Tecton tecton) {
                gController.createTecton(tecton);
            }

            @Override
            public void mushroomChanged(ObjectChangeEvent event, Mushroom mushroom) {
                gController.createMushroom(mushroom);
            }
            
        });
        cmdProcessor = new CommandProcessor(controller);

        MainWindow mainWindow = new MainWindow(gController);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        mainWindow.revalidate();
        mainWindow.repaint();
        
        controller.addJobListener(mainWindow);
        
        cmdProcessor.ExecuteCommand("/load TestFiles\\InsectMoveTwice_input.txt");
    }
}
