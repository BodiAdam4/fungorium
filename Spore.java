class Spore
{
    int value;
    int id;

    public Spore(String name, int id, int value)
    {
        Logger.Constructor(this, name, new Object[]{id, value});
        this.id = id;
        this.value = value;
        Logger.FunctionEnd();
    }

    public void addEffect(Insect i)
    {
        Logger.FunctionStart(this, "addEffect", new Object[]{i});
        Logger.FunctionEnd();
    }
}