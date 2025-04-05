package Model;
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
    //Privat attribútumok
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
    protected List<Line> connections = new ArrayList<>();


    /**
     * A SporeContainerben tárolja a rá dobott Spore példányokat.
     * Ha egy rovar a tektonra jut, akkor képes ezáltal megnézni
     * az ott található spórákat, ami a spóraevéshez létfontosságú.
     */
    private SporeContainer sporeContainer;

    /**
     * A tektononnal szomszédos tektonok listája.
     */
    private List<Tecton> neighbors = new ArrayList<>();

    /**
     * A tektonon tartózkodó rovarok listája.
     */
    List<Insect> insects = new ArrayList<>();

    /**
     * A tekton x koordinátája.
     */
    private int x;

    /**
     * A tekton y koordinátája.
     */ 
    private int y;


    
    /**
     * Tecton konstruktor.
     * @param objectName Objektum neve
     */
    public Tecton(String objectName){
        Logger.Constructor(this, objectName);
        sporeContainer = new SporeContainer("sc" + (Integer.parseInt((Logger.GetObjectName(this).substring(1)))));
        Logger.FunctionEnd();
    }

    
    
    
    //Getterek és setterek
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

    public SporeContainer getSporeContainer() {
        Logger.FunctionStart(this, "getSporeContainer");
        Logger.FunctionEnd();
        return this.sporeContainer;
    }

    public void setSporeContainer(SporeContainer spores) {
        Logger.FunctionStart(this, "setSporeContainer", new Object[]{spores});
        this.sporeContainer = spores;
        Logger.FunctionEnd();
    }

    public List<Tecton> getNeighbors() {
        Logger.FunctionStart(this, "getNeighbors");
        Logger.FunctionEnd();
        return this.neighbors;
    }

    public List<Insect> getInsects() {
        Logger.FunctionStart(this, "getInsects");
        Logger.FunctionEnd();
        return this.insects;
    }

    public void setNeighbors(Tecton neighbor) {
        Logger.FunctionStart(this, "setNeighbors", new Object[]{neighbor});
        neighbors.add(neighbor);
        Logger.FunctionEnd();
    }

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


    //Függvények
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
        boolean sporeCountOK = sporeContainer.getSporeCount(id) >= 3;
        if (sporeCountOK) {
            myMushroom = new Mushroom(id, this, "Mushroom");
            Logger.Log("Mushroom built successfully");
            sporeContainer.popSpores(id, 3);
            Logger.FunctionEnd();
            return true;
        }
        else {
            Logger.Log("Mushroom failed to build");
            Logger.FunctionEnd();
            return false;
        }
    }

    
    /**
      * A függvény segítségével lekérdezhető, hogy a tektonon van-e gombatest 
      * @return Visszaadja, hogy van-e a tektonon gombatest.
      */
    boolean hasBody(){
        Logger.FunctionStart(this, "hasBody");
        String ans = Logger.Ask("Van gombatest a tektonon(y/n)?");
        if(ans.equalsIgnoreCase("y")){
            Logger.FunctionEnd();
            return true;
        }
        else{
            Logger.FunctionEnd();
            return false;
        }
    }
    /**
     * A tekton két tektonra törése
     */
    void breakTecton(){
        Logger.FunctionStart(this, "breakTecton");
        List<Tecton> ng = getNeighbors();
        Tecton t3 = new Tecton("t3");

        for (Tecton t : ng) {
            t3.setNeighbors(t);
        }

        Logger.FunctionEnd();
    }
    /**
     * A tektonra rárakja az átadott rovart.
     * @param insect A rárakandó rovar.
     */
    public void addInsect(Insect insect) {
        insects.add(insect);
    }

    /**
     * A tektonról leveszi az átadott rovart.
     * @param insect A leveszendő rovar.
     */
    public void removeInsect(Insect insect) {
        insects.remove(insect);
    }
}
