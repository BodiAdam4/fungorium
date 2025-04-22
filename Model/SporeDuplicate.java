package Model;

public class SporeDuplicate extends Spore{
    /**
     * Konstruktor
     * @param name A spóra neve (teszteléshez szükséges)
     * @param id A gombafaj azonosítója
     * @param value A spóra tápértéke
     */
    public SporeDuplicate(int id, int value)
    {
        super(id, value);
    }
    public void addEffect(Insect i) {
        i.duplicate();
    }
}
