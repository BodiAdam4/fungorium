import java.util.List;

public class Line 
{
    List<Tecton> connections;
    int id;

    Line(String name, Tecton t1, Tecton t2, int id)
    {
        Logger.Constructor(this, name, new Object[]{t1, t2, id});
        this.id = id;
        connections.add(t1);
        connections.add(t2);
        Logger.FunctionEnd();
    }

    boolean checkConnections()
    {
        return true;
    }

    void notifyNeighbors()
    {

    }

    boolean growMushroom(Tecton to)
    {
        Logger.FunctionStart(this, "growMushroom");
        return true;
    }

    boolean growLine(Tecton to)
    {
        return true;
    }
}
