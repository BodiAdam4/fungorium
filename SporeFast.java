/**
 * A Spóra leszármazottja.
 * Felülírja az addEffect metódust, hogy a saját hatását átadja a rovarnak.
 */
public class SporeFast extends Spore
{
    /**
     * Konstruktor
     * @param name A spóra neve (teszteléshez szükséges)
     * @param id A gombafaj azonosítója
     * @param value A spóra tápértéke
     */
    public SporeFast(String name, int id, int value)
    {
        super(name, id, value);
        Logger.Constructor(this, name, new Object[]{id, value});
        Logger.FunctionEnd();
    }

    /**
     * Spóra megevése után meghívódó függvény.
     * Növeli a rovar sebességét.
     * @param i A rovar, aki megeszi a spórát.
     */
    @Override
    public void addEffect(Insect i) {
        Logger.FunctionStart(this, "addEffect", new Object[]{i});
        i.setSpeed(3);
        Logger.FunctionEnd();
    }
}
