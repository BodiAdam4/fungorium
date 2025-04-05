package Userinterface;

import Controller.Controller;
import Model.Tecton;

public class TestTools {
    
    public static String GetStatus(Controller controller) {
        StringBuilder sb = new StringBuilder();
        for(String tId : controller.getAllTecton().keySet()) {
            sb.append(GetTectonStatus(controller, tId));
        }
        return sb.toString();
    }

    public static String GetTectonStatus(Controller controller, String tectonId) {
        Tecton tecton = controller.getTectonById(tectonId);
        StringBuilder sb = new StringBuilder();
        sb.append("Tecton: ").append(tectonId).append("\n");
        sb.append("\tmyMushroom: ").append(controller.getMushroomId(tecton.getMyMushroom())).append("\n");
        sb.append("\tconnections: ");

        if(tecton.getConnections().isEmpty()) {
            sb.append("NULL\n");
        }
        else {
            for(int i = 0; i < tecton.getConnections().size(); i++) {
                sb.append(controller.getLineId(tecton.getConnections().get(i)));

                if (i < tecton.getConnections().size() - 1) {
                    sb.append("; ");
                }
            }
            sb.append("\n");
        }

        sb.append("\tsporeContainer: ").append(tecton.getSporeContainer().getSporeCount()).append("\n");
        sb.append("\tneighbors: ");

        if(tecton.getNeighbors().isEmpty()) {
            sb.append("NULL\n");
        }
        else {
            for(int i = 0; i < tecton.getNeighbors().size(); i++) {
                sb.append(controller.getTectonId(tecton.getNeighbors().get(i)));

                if (i < tecton.getNeighbors().size() - 1) {
                    sb.append("; ");
                }
            }
            sb.append("\n");
        }

        sb.append("\tinsects: ");

        if(tecton.getInsects().isEmpty()) {
            sb.append("NUll\n");
        }
        else {
            for(int i = 0; i < tecton.getInsects().size(); i++) {
                sb.append(controller.getInsectId(tecton.getInsects().get(i)));

                if (i < tecton.getInsects().size() - 1) {
                    sb.append("; ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
