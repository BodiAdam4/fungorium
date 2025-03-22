public class SporeFast extends Spore
{
    public SporeFast(String name, int id, int value)
    {
        super(name, id, value);
        Logger.Constructor(this, name, new Object[]{id, value});
        Logger.FunctionEnd();
    }

    
    @Override
    public void addEffect(Insect insect) {
        Logger.FunctionStart(this, "addEffect", new Object[]{insect});
        insect.setSpeed(3);
        Logger.FunctionEnd();
    }
}
