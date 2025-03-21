public class SporeFrozen extends Spore
{
    public SporeFrozen(String name, int id, int value)
    {
        super(name, id, value);
        Logger.Constructor(this, name, new Object[]{id, value});
        Logger.FunctionEnd();
    }
}
