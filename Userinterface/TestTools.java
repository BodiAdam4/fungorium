package Userinterface;

import Controller.Controller;
import Model.Tecton;

public class TestTools {
    
    public static String GetStatus(Controller controller) {
        
    }

    public static String GetTectonStatus(Controller controller, String tectonId) {
        Tecton tecton = new Tecton("Szia");
        StringBuilder sb = new StringBuilder();
        sb.append("Tecton: ").append(tectonId).append("\n");
        sb.append("\tmyMushroom":).append(tecton.getMyMushroom()).append("\n");
    }
}
