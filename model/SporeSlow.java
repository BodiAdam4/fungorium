package model;

import listeners.JobListener;

/**
 * A Spóra leszármazottja.
 * Felülírja az addEffect metódust, hogy a saját hatását átadja a rovarnak.
 */
public class SporeSlow extends Spore
{
    /**
     * Konstruktor
     * @param name A spóra neve (teszteléshez szükséges)
     * @param id A gombafaj azonosítója
     * @param value A spóra tápértéke
     */
    public SporeSlow(int id, int value)
    {
        super(id, value);
    }

    
    /**
     * Spóra megevése után meghívódó függvény.
     * Csökkenti a rovar sebességét.
     * @param i A rovar, aki megeszi a spórát.
     */
    @Override
    public void addEffect(Insect i) {
        i.setSpeed(1);
        for(JobListener listeners : i.getJobListeners()){
            listeners.jobSuccessfull("Spore eaten with value: " + value + " and insect became slow.");
        }
    }
}
