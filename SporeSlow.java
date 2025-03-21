public class SporeSlow extends Spore
{
    public SporeSlow(String name, int id, int value)
    {
        super(name, id, value);
        Logger.Constructor(this, name, new Object[]{id, value});
        Logger.FunctionEnd();
    }
}
