import java.util.ArrayList;
import java.util.List;

public class Line 
{
    List<Tecton> connections = new ArrayList<Tecton>();
    int id;

    /**
     * Line konstruktor.
     *
     * @param name Line neve
     * @param t1 A vonal által összekötött első Tecton
     * @param t2 A vonal által összekötött második Tecton
     * @param id A gombafaj azonosítója
     */
    public Line(String name, Tecton t1, Tecton t2, int id)
    {
        Logger.Constructor(this, name, new Object[]{t1, t2, id});
        this.id = id;
        boolean res1 = t1.addLine(this);
        boolean res2 = t2.addLine(this);

        if (!res1 && res2) {
            t2.removeLine(this);
        }

        if (!res2 && res1) {
            t1.removeLine(this);
        }

        Logger.FunctionEnd();
    }

    /**
     * Megnézi a vele összekötött vonalakon keresztül, hogy a gombafajnak van-e teste.
     * 
     * @return true, ha a gombafajnak van teste, false ha nincs
     */
    public boolean checkConnections()
    {
        Logger.FunctionStart(this, "checkConnections");
        boolean t1h = connections.get(0).hasBody();
        if(t1h)
        {
            Logger.FunctionEnd();
            return true;
        }
        else
        {
            String ans = Logger.Ask("Ez az utolsó vonal?");
            if(ans.equals("igen"))
            {
                Logger.FunctionEnd();
                return connections.get(1).hasBody();
            }
            else
            {
                Tecton nt = new Tecton("t" + (Integer.parseInt((Logger.GetObjectName(connections.get(1)).substring(1))) + 1));
                Line nl = new Line("l" + (Integer.parseInt((Logger.GetObjectName(this).substring(1))) + 1), connections.get(1), nt, id);
                boolean res = nl.checkConnections();
                Logger.FunctionEnd();
                return res;
            }
        }
    }

    public void notifyNeighbors()
    {

    }

    /**
     * Növeszt egy gombát valamelyik Tectonjára
     *
     * @param to Az a Tecton, amelyre a gomba nő
     * @return Tesztelés miatt mindig true
     */
    public boolean growMushroom(Tecton to)
    {
        Logger.FunctionStart(this, "growMushroom", new Object[]{to});
        //if()
        to.addMushroom(id);
        Logger.FunctionEnd();
        return true;
    }

    /**
     * Növeszt egy vonalat a két Tecton között.
     *
     * @param to1 Az első Tecton
     * @param to2 A második Tecton
     * @return Tesztelés miatt mindig true
     */
    public boolean growLine(Tecton to1, Tecton to2)
    {
        Logger.FunctionStart(this, "growLine", new Object[]{to1, to2});
        Line nl = new Line("l" + (Integer.parseInt((Logger.GetObjectName(this).substring(1))) + 1), to1, to2, id);
        return true;
    }

    public int getId() {
        return id;
    }
}
