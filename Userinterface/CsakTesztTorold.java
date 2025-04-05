package Userinterface;

import Controller.Controller;
import Model.Insect;
import Model.Line;
import Model.Mushroom;
import Model.Tecton;
import Model.TectonOnlyLine;

public class CsakTesztTorold {
    public static void main(String[] args) {
        Controller controller = new Controller();
        
        Tecton t1 = new TectonOnlyLine("t1");
        controller.addTecton("t_1", t1);

        Tecton t2 = new TectonOnlyLine("t2");
        controller.addTecton("t_2", t2);

        t1.setNeighbors(t2);
        t2.setNeighbors(t1);

        Mushroom m1 = new Mushroom(1, t1, "m1");
        controller.addMushroom("m_1", m1);

        t1.setMyMushroom(m1);

        Line l1 = new Line(t1, t2, 1);
        controller.addLine("l_1", l1);

        m1.growLine(t2);


        Insect i1 = new Insect();
        controller.addInsect("i_1", i1);

        t1.addInsect(i1);
        i1.setTecton(t1);
        i1.move(t2);
        m1.throwSpores(t2, 2);

        Tecton t3 = new TectonOnlyLine("t3");
        controller.addTecton("t_3", t3);

        t2.setNeighbors(t3);
        t3.setNeighbors(t2);
        t1.setNeighbors(t3);
        t3.setNeighbors(t1);
        
        Line l2 = new Line(t1, t3, 1);
        controller.addLine("l_2", l2);

        t3.breakTecton();

        i1.cutLine(l1);

        System.out.println(TestTools.GetStatus(controller));
    }
}
