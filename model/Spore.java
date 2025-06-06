package model;

import listeners.JobListener;

/**
 * A játékban lévő spórákat valósítja meg.
 * Tartalmazza a tápértéket és a gombához tartozó azonosítót.
 */
public class Spore
{
    /* - Privát attribútumok*/
    private int id;

    /* - Védett attribútumok*/
    protected int value;

    /* - Konstruktorok*/

    /**
     * Konstruktor
     * @param id A gombafaj azonosítója
     * @param value A spóra tápértéke
     */
    public Spore(int id, int value)
    {
        this.id = id;
        this.value = value;
    }


    /* - Getter/Setter metódusok*/

    public int getValue() {
        return value;
    }

    
    public int getSporeId() {
        return id;
    }

    
    /**
     * Spóra megevése után meghívódó függvény.
     * @param i A rovar, aki megeszi a spórát.
     */
    public void addEffect(Insect i)
    {
        for(JobListener listeners : i.getJobListeners()){
            listeners.jobSuccessfull("Spore eaten with value: " + value);
        }
        return;
    }
}