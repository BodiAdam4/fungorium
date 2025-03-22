public class SporeSlow extends Spore
{
    public SporeSlow(String name, int id, int value)
    {
        super(name, id, value);
        Logger.Constructor(this, name, new Object[]{id, value});
        Logger.FunctionEnd();
    }

    
    @Override
    public void addEffect(Insect insect) {
        Logger.FunctionStart(this, "addEffect", new Object[]{insect});
        insect.setSpeed(1);
        Logger.FunctionEnd();
    }
}
