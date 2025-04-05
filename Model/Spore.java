package Model;
/**
 * A játékban lévő spórákat valósítja meg.
 * Tartalmazza a tápértéket és a gombához tartozó azonosítót.
 */
class Spore
{
    int value;
    int id;

    public int getValue() {
        return value;
    }

    /**
     * Konstruktor
     * @param name A spóra neve (teszteléshez szükséges)
     * @param id A gombafaj azonosítója
     * @param value A spóra tápértéke
     */
    public Spore(int id, int value)
    {
        this.id = id;
        this.value = value;
    }

    /**
     * Spóra megevése után meghívódó függvény.
     * @param i A rovar, aki megeszi a spórát.
     */
    public void addEffect(Insect i)
    {
        return;
    }
}