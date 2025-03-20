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

    public Tecton(){
        Logger.Constructor(this, "Tecton");
        spores = new SporeContainer();
        Logger.FunctionEnd();
    }

    /**
     * Gombafonál csatlakoztatása a tektonhoz. A visszatérési érték a csatlakoztatás sikerességéről küld visszajelzést.
     * @param line
     * @return
     */
    boolean addLine(Line line){
        Logger.FunctionStart(this, "addLine", new Object[]{line});
        connections.add(line);
        Logger.FunctionEnd();
        return true;
    }
    /**
     * Gombafonal leválasztása a tektonról.
     * @param toRemove
     */
    void removeLine(Line toRemove){
        Logger.FunctionStart(this, "removeLine");
        //TODO
        Logger.FunctionEnd();
        return;
    }
    /**
     * Gombatest növesztése a tektonon. Ha effekt vagy egyéb indok miatt nem tud ott gomba létesülni, akkor false értéket ad vissza.
     * @param id
     * @return
     */
    boolean addMushroom(int id){
        Logger.FunctionStart(this, "addMushroom", new Object[]{id});
        //TODO
        Logger.FunctionEnd();
        return true;
    }

    
    /**
     * A tektonon lévő gombatest eltávolítása
    */ 
    void destroyMushroom(){
        Logger.FunctionStart(this, "destroyMushroom");
        //TODO
        Logger.FunctionEnd();
        return;
    }
    /**
      * A függvény segítségével lekérdezhető, hogy a tektonon van-e gombatest 
      * @return
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
