package model;

import listeners.JobListener;
import listeners.ObjectChangeListener;

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

    /**
     * Spóra megevése után meghívódó függvény.
     * Felülírja, hogy duplikálja a rovart
     * @param i A rovar, aki megeszi a spórát.
     */
    @Override
    public void addEffect(Insect i) {
        Insect insect = new Insect();
        insect.setInsectId(i.getInsectId());
        insect.setTecton(i.getTecton());
        i.getTecton().addInsect(insect);
        for(JobListener listeners : i.getJobListeners()){
            listeners.jobSuccessfull("Spore eaten with value: " + value + " and insect was duplicated.");
        }
        i.changeListener.insectChanged(ObjectChangeListener.ObjectChangeEvent.OBJECT_ADDED, insect);
    }
}
