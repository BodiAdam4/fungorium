import java.util.ArrayList;
import java.util.List;

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

    public Mushroom getMyMushroom() {
        Logger.FunctionStart(this, "getMyMushroom");
        Logger.FunctionEnd();
        return this.myMushroom;
    }

    public void setMyMushroom(Mushroom myMushroom) {
        Logger.FunctionStart(this, "setMyMushroom", new Object[]{myMushroom});
        this.myMushroom = myMushroom;
        Logger.FunctionEnd();
    }

    /**
     * A Tecton osztály ismeri a hozzá csatlakoztatott gombafonalakat,
     * és a gombafonalak is ismerik a hozzájuk kapcsolt
     * kezdő- és végpontjukként szolgáló tektonokat.
     * Fontos a fonalak bejárásához.
     */
    protected List<Line> connections = new ArrayList<>();

    public List<Line> getConnections() {
        Logger.FunctionStart(this, "getConnections");
        Logger.FunctionEnd();
        return this.connections;
    }

    public void setConnections(List<Line> connections) {
        Logger.FunctionStart(this, "setConnections", new Object[]{connections});
        this.connections = connections;
        Logger.FunctionEnd();
    }

    /**
     * A SporeContainerben tárolja a rá dobott Spore példányokat.
     * Ha egy rovar a tektonra jut, akkor képes ezáltal megnézni
     * az ott található spórákat, ami a spóraevéshez létfontosságú.
     */
    private SporeContainer spores;

    public SporeContainer getSpores() {
        Logger.FunctionStart(this, "getSpores");
        Logger.FunctionEnd();
        return this.spores;
    }

    public void setSpores(SporeContainer spores) {
        Logger.FunctionStart(this, "setSpores", new Object[]{spores});
        this.spores = spores;
        Logger.FunctionEnd();
    }

    /**
     * A tektononnal szomszédos tektonok listája.
     */
    private List<Tecton> neighbors;

    public List<Tecton> getNeighbors() {
        Logger.FunctionStart(this, "getNeighbors");
        Logger.FunctionEnd();
        return this.neighbors;
    }

    public void setNeighbors(List<Tecton> neighbors) {
        Logger.FunctionStart(this, "setNeighbors", new Object[]{neighbors});
        this.neighbors = neighbors;
        Logger.FunctionEnd();
    }

    /**
     * A tekton x koordinátája.
     */
    private int x;

    public int getX() {
        Logger.FunctionStart(this, "getX");
        Logger.FunctionEnd();
        return this.x;
    }

    public void setX(int x) {
        Logger.FunctionStart(this, "setX", new Object[]{x});
        this.x = x;
        Logger.FunctionEnd();
    }

    /**
     * A tekton y koordinátája.
     */ 
    private int y;

    public int getY() {
        Logger.FunctionStart(this, "getY");
        Logger.FunctionEnd();
        return this.y;
    }

    public void setY(int y) {
        Logger.FunctionStart(this, "setY", new Object[]{y});
        this.y = y;
        Logger.FunctionEnd();
    }

    public Tecton(String objectName){
        Logger.Constructor(this, objectName);
        spores = new SporeContainer("spores");
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
        Logger.FunctionStart(this, "removeLine", new Object[]{toRemove});
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

    List<Insect> insects = new ArrayList<>();

    public void addInsect(Insect insect) {
        insects.add(insect);
    }

    public void removeInsect(Insect insect) {
        insects.remove(insect);
    }
}
