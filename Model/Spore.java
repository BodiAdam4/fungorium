package Model;
/**
 * A játékban lévő spórákat valósítja meg.
 * Tartalmazza a tápértéket és a gombához tartozó azonosítót.
 */
public class Spore
{
    private int value;
    private int id;

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
     * Getter a spóra azonosítójához.
     * @return A spóra azonosítója.
     */
    public int getSporeId() {
        return id;
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