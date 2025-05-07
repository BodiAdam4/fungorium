package graphics;

import com.sun.tools.javac.Main;
import controller.Controller;
import userinterface.CommandProcessor;

public class GraphicMain {

    private Controller controller;
    //private CommandProcessor cmdProcessor; TODO: Valószínűleg nem kell
    //private GraphicController gController; TODO: Ha meglesz a GraphicController, akkor ezt kell implementálni
    private MainWindow mainWindow;

    public static void main(String[] args) {
        controller = new Controller();
        MainWindow mainWindow = new MainWindow();
    }
}
