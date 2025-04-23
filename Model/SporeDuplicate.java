package Model;

import Listeners.ObjectChangeListener;

public class SporeDuplicate extends Spore{
    /**
     * Konstruktor
     * @param name A spóra neve (teszteléshez szükséges)
     * @param id A gombafaj azonosítója
     * @param value A spóra tápértéke
     */
    public SporeDuplicate(int id, int value) {
        super(id, value);
    }
    public void addEffect(Insect i) {
        Insect insect = new Insect();
        insect.setInsectId(i.getInsectId());
        insect.setTecton(i.getTecton());
        insect.changeListener.insectChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, insect);
    }
}
