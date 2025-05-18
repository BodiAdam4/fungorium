package graphics;

import controller.Controller;
import java.io.BufferedInputStream;
import java.util.Scanner;
import listeners.ObjectChangeListener;
import model.Insect;
import model.Line;
import model.Mushroom;
import model.Tecton;
import userinterface.CommandProcessor;

/**
 * Belépési pont grafikus futtatás esetén.
 */
public class GraphicMain {

    public static Controller controller;
    public static CommandProcessor cmdProcessor;
    public static GraphicController gController;
    private static MainWindow mainWindow;

    public static void main(String[] args) {
        gController = new GraphicController();
        controller = new Controller();

        //ObjectChangeListener megadása
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

        //MainWindow beállítása
        MainWindow mainWindow = new MainWindow(gController);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        mainWindow.revalidate();
        mainWindow.repaint();

        
        controller.addResultListeners(mainWindow);
        
        controller.addControlListener(mainWindow);
        controller.addJobListener(mainWindow);
        
        //Játék bemenetéért felelős szál
        Thread consoleThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Scanner scanner = new Scanner(new BufferedInputStream(System.in));

                while(scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if(input.equals("/exit")) {
                        System.out.println("Exiting the game.");
                        break;
                    }
                    try {
                        cmdProcessor.ExecuteCommand(input);
                    } catch (Exception e) {
                        System.out.println("Something went wrong :(\nInvalid command or invalid parameters.\n" +
                                "Please check the command and try again.\n" +
                                "Type /help to see the game commands."+e.getMessage());
                    }
                }
                scanner.close();
            }
            
        });
        consoleThread.start();
    }
}
