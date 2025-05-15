package listeners;

import model.Tecton;

/**
 * A TectonListener interfész a tektonokon bekövetkező változások figyelését valósítja meg. 
 * Az interfész tartalmaz minden lehetséges eseményhez egy függvényt, amit a tekton lefuttat, ha bekövetkezik.
 */
public interface TectonListener {
    
    /**
     *  A tektonon való gombatest növesztésének következtében lefutó metódus. Paraméterként megkapja a kinövesztett gombatestet.
     * @param mushroom a kinövesztett gombatestet adja meg
     */
    public void mushroomGrowStarted(int id);

    /**
     * A tektonon lévő gombatest megszűnésének következtében lefutó metódus.
     */
    public void mushroomRemoved();
    
    /**
     * A tekton eltörésekor lefutó metódus, mely paraméterként átadja a másik tektont, ami a törés során keletkezett.
     * @param tecton a törés során keletkezett tektont adja meg
     */
    public void tectonBroken(Tecton tecton);
}
