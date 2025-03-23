/**
 * A játékban lévő spórákat valósítja meg.
 * Tartalmazza a tápértéket és a gombához tartozó azonosítót.
 */
class Spore
{
    int value;
    int id;

    public int getValue() {
        Logger.FunctionStart(this, "getValue");
        Logger.FunctionEnd();
        return value;
    }

    /**
     * Konstruktor
     * @param name A spóra neve (teszteléshez szükséges)
     * @param id A gombafaj azonosítója
     * @param value A spóra tápértéke
     */
    public Spore(String name, int id, int value)
    {
        Logger.Constructor(this, name, new Object[]{id, value});
        this.id = id;
        this.value = value;
        Logger.FunctionEnd();
    }

    /**
     * Spóra megevése után meghívódó függvény.
     * @param i A rovar, aki megeszi a spórát.
     */
    public void addEffect(Insect i)
    {
        Logger.FunctionStart(this, "addEffect", new Object[]{i});
        Logger.FunctionEnd();
    }
}