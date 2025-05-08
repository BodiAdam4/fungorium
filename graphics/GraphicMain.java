package graphics;

import controller.Controller;

public class GraphicMain {

    private Controller controller;
    //private CommandProcessor cmdProcessor; TODO: Valószínűleg nem kell
    //private GraphicController gController; TODO: Ha meglesz a GraphicController, akkor ezt kell implementálni
    private MainWindow mainWindow;

    public static void main(String[] args) {
        //controller = new Controller();
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        mainWindow.revalidate();
        mainWindow.repaint();
    }
}
