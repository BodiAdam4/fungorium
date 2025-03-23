/**
 * A Spóra leszármazottja.
 * Felülírja az addEffect metódust, hogy a saját hatását átadja a rovarnak.
 */
public class SporeFrozen extends Spore
{
    /**
     * Konstruktor
     * @param name A spóra neve (teszteléshez szükséges)
     * @param id A gombafaj azonosítója
     * @param value A spóra tápértéke
     */
    public SporeFrozen(String name, int id, int value)
    {
        super(name, id, value);
        Logger.Constructor(this, name, new Object[]{id, value});
        Logger.FunctionEnd();
    }

    /**
     * Spóra megevése után meghívódó függvény.
     * Az adott rovar megkapja a spóra tápértékét.
     * Beállítja, hogy a rovar ne tudjon mozogni.
     * @param i A rovar, aki megeszi a spórát.
     */
    @Override
    public void addEffect(Insect i) {
        Logger.FunctionStart(this, "addEffect", new Object[]{i});
        i.setSporeCount(i.getSporeCount() + value);
        i.setCanMove(false);
        Logger.FunctionEnd();
    }
}
