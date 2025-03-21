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
        Logger.FunctionEnd();
    }

    public boolean checkConnections()
    {
        return true;
    }

    public void notifyNeighbors()
    {

    }

    public boolean growMushroom(Tecton to)
    {
        Logger.FunctionStart(this, "growMushroom", new Object[]{to});
        Logger.FunctionEnd();
        return true;
    }

    void testConflict(){

    }

    public boolean growLine(Tecton to)
    {
        return true;
    }
}
