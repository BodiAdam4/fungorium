import java.util.List;

public class Line 
{
    List<Tecton> connections;
    int id;

    public Line(String name, Tecton t1, Tecton t2, int id)
    {
        Logger.Constructor(this, name, new Object[]{t1, t2, id});
        this.id = id;
        connections.add(t1);
        connections.add(t2);
        t1.addLine(this);
        t2.addLine(this);
        Logger.FunctionEnd();
    }
    /**
    * 
    * @return
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
                Tecton nt = new Tecton("t" + (Logger.GetObjectName(connections.get(1)).substring(1) + 1));
                Line nl = new Line("l" + (Logger.GetObjectName(this).substring(1) + 1), connections.get(1), nt, id);
                boolean res = nl.checkConnections();
                Logger.FunctionEnd();
                return res;
            }
        }
    }

    public void notifyNeighbors()
    {

    }

    public boolean growMushroom(Tecton to)
    {
        Logger.FunctionStart(this, "growMushroom", new Object[]{to});

        //if()

        to.addMushroom(id);

        Logger.FunctionEnd();
        return true;
    }

    public boolean growLine(Tecton to1, Tecton to2)
    {
        Logger.FunctionStart(this, "growLine", new Object[]{to1, to2});
        Line nl = new Line("l" + (Logger.GetObjectName(this).substring(1) + 1), to1, to2, id);
        return true;
    }

    public int getId() {
        return id;
    }
}
