import java.util.List;

import javax.sound.sampled.Line;

/** 
 * A játékban lévő tektonokat valósítja meg.
 * Tartalmazza a hozzájuk szükséges értékeket, 
 * és azok kezeléséhez szükséges függvényeket. 
 * Ezek mellett a rajtuk lévő gombatestet és a 
 * hozzájuk csatlakoztatott gombafonalakat is képesek
 * minimálisan kezelni.
 * Alap esetben akármennyi fonaltípus csatlakozhat hozzá,
 * viszont csak egy gombatest nőhet rajta.
*/
public class Tecton {
    /**
     * A Tecton osztály tartalmazza a tektonon lévő 
     * Mushroom példányt. 
     * Egyszerre csak egy példányt tud tárolni. 
     * Fontos a gombatest tárolása és kezelése szempontjából,
     * mivel a különböző hatású tektonok eltérő hatással lehetnek
     * a rajtuk lévő gombatestre.
     */
    private Mushroom myMushroom;
    /**
     * A Tecton osztály ismeri a hozzá csatlakoztatott gombafonalakat,
     * és a gombafonalak is ismerik a hozzájuk kapcsolt
     * kezdő- és végpontjukként szolgáló tektonokat.
     * Fontos a fonalak bejárásához.
     */
    protected List<Line> connections;
    /**
     * A SporeContainerben tárolja a rá dobott Spore példányokat.
     * Ha egy rovar a tektonra jut, akkor képes ezáltal megnézni
     * az ott található spórákat, ami a spóraevéshez létfontosságú.
     */
    private SporeContainer spores;
    /**
     * A tektononnal szomszédos tektonok listája.
     */
    private List<Tecton> neighbors;
    /**
     * A tekton x koordinátája.
     */
    private int x;
    /**
     * A tekton y koordinátája.
     */ 
    private int y;

    public Tecton(String objectName){
        Logger.Constructor(this, objectName);
        spores = new SporeContainer();
        Logger.FunctionEnd();
    }

    /**
     * Gombafonál csatlakoztatása a tektonhoz. 
     * @param line A hozzáadandó fonal.
     * @return A visszatérési érték a csatlakoztatás sikerességéről küld visszajelzést.
     */
    boolean addLine(Line line){
        Logger.FunctionStart(this, "addLine", new Object[]{line});
        connections.add(line);
        Logger.FunctionEnd();
        return true;
    }
    /**
     * Gombafonal leválasztása a tektonról.
     * @param toRemove A leválasztandó fonal.
     */
    void removeLine(Line toRemove){
        Logger.FunctionStart(this, "removeLine");
        connections.remove(toRemove);
        Logger.FunctionEnd();
    }
    /**
     * Gombatest növesztése a tektonon. 
     * @param id A hozzá adandó gombatest id - je.
     * @return Ha effekt vagy egyéb indok miatt nem tud ott gomba létesülni, akkor false értéket ad vissza.
     */
    boolean addMushroom(int id){
        Logger.FunctionStart(this, "addMushroom", new Object[]{id});
        myMushroom = new Mushroom(id, this, "Mushroom");
        Logger.FunctionEnd();
        return true;
    }

    
    /**
     * A tektonon lévő gombatest eltávolítása
    */ 
    public void destroyMushroom(){
        Logger.FunctionStart(this, "destroyMushroom");
        myMushroom = null;
        Logger.FunctionEnd();
        return;
    }
    /**
      * A függvény segítségével lekérdezhető, hogy a tektonon van-e gombatest 
      * @return Visszaadja, hogy van-e a tektonon gombatest.
      */
    boolean hasBody(){
        Logger.FunctionStart(this, "hasBody");
        //TODO
        Logger.FunctionEnd();
        return true;
    }
    /**
     * A tekton két tektonra törése
     */
    void breakTecton(){
        Logger.FunctionStart(this, "breakTecton");
        //TODO
        Logger.FunctionEnd();
        return;
    }
}
