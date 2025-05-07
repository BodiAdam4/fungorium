package listeners;

/**
 * A SporeContainerListener interfész a spóratárolókban bekövetkező változások figyelését valósítja meg.
 *  Az interfész tartalmaz minden lehetséges eseményhez egy függvényt, amit a spóratároló lefuttat, ha bekövetkezik.
 */
public interface SporeContainerListener {
    
    /**
     * A spóratárolóhoz gombaspóra hozzáadása esetén lefutó függvény, paraméterként átadja a spóratárolóban lévő spórák számát
     * @param sporeCount a spóratárolóban lévő spórák számát adja meg
     */
    public void sporeAdded(int sporeCount);

    /**
     * A spóratárolóból gombaspóra elvétele esetén lefutó függvény, paraméterként átadja a spóratárolóban lévő spórák számát.
     * @param sporeCount a spóratárolóban lévő spórák számát adja meg
     */
    public void sporeRemoved(int sporeCount);

}
