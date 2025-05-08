package model;
import java.util.ArrayList;
import java.util.List;

import listeners.ObjectChangeListener;
import listeners.ObjectChangeListener.ObjectChangeEvent;
import listeners.TectonListener;

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

    private List<Tecton> neighbors = new ArrayList<>();     //A tektononnal szomszédos tektonok listája.

    private List<Insect> insects = new ArrayList<>();       //A tektonon tartózkodó rovarok listája.

    private List<TectonListener> tectonListeners = new ArrayList<>();  //A tektonhoz tartozó eseménykezelők listája
    
    public ObjectChangeListener changeListener;

    
    
    /**
     * Tecton konstruktor.
     * @param objectName Objektum neve
     */
    public Tecton(){
        sporeContainer = new SporeContainer();
    }

    
    
    
    /** - Getter/Setter metódusok*/
    public Mushroom getMyMushroom() {
        return this.myMushroom;
    }

    public void setMyMushroom(Mushroom myMushroom) {
        this.myMushroom = myMushroom;
    }

    public List<Line> getConnections() {
        return this.connections;
    }

    public void setConnections(List<Line> connections) {
        this.connections = connections;
    }

    public SporeContainer getSporeContainer() {
        return this.sporeContainer;
    }

    public void setSporeContainer(SporeContainer spores) {
        this.sporeContainer = spores;
    }

    public List<Tecton> getNeighbors() {
        return this.neighbors;
    }

    public List<Insect> getInsects() {
        return this.insects;
    }

    public void setNeighbors(Tecton neighbor) {
        if(!neighbors.contains(neighbor))
            neighbors.add(neighbor);
    }



    /** - Egyéb metódusok*/

    /**
     * Egy tekton-eseményfigyelő beállítása
     * @param listener A tekton eseményfigyelője
     */
    public void addTectonListener(TectonListener listener){
        this.tectonListeners.add(listener);
    }


    /**
     * A tektonon lévő gombatest eltávolítása.
     */
    public void removeMushroom(){
        if (myMushroom != null) {
            myMushroom = null;
            System.out.println("Mushroom removed");
            for (TectonListener tl : tectonListeners) {
                tl.mushroomRemoved();
            }
        }
    }

    /**
     * Gombafonál csatlakoztatása a tektonhoz. 
     * @param line A hozzáadandó fonal.
     * @return A visszatérési érték a csatlakoztatás sikerességéről küld visszajelzést.
     */
    public boolean addLine(Line line){
        connections.add(line);
        return true;
    }


    /**
     * Gombafonal leválasztása a tektonról.
     * @param toRemove A leválasztandó fonal.
     */
    public void removeLine(Line toRemove){
        connections.remove(toRemove);
    }


    /**
     * Gombatest növesztése a tektonon. 
     * @param id A hozzá adandó gombatest id - je.
     * @return Ha effekt vagy egyéb indok miatt nem tud ott gomba létesülni, akkor false értéket ad vissza.
     */
    public boolean addMushroom(int id){
        boolean sporeCountOK = sporeContainer.getSporeCount(id) >= 3;
        if (sporeCountOK) {
            Tecton t = this;
            Timer.addOneTimeSchedule(new Schedule() {
                @Override
                public void onTime() {
                    myMushroom = new Mushroom(id, t);
                    changeListener.mushroomChanged(ObjectChangeEvent.OBJECT_ADDED, myMushroom);
                    for (TectonListener tl : tectonListeners) {
                        tl.mushroomAdded(myMushroom);
                    }
                    System.out.println("Mushroom successfully grown");
                }
            }, 2);
            
            sporeContainer.popSpores(id, 3);
            System.out.println("Mushroom started to grow");
            return true;
        }
        else {
            System.out.println("Mushroom failed to build");
            return false;
        }
        
    }

    
    /**
      * A függvény segítségével lekérdezhető, hogy a tektonon van-e gombatest 
      * @return Visszaadja, hogy van-e a tektonon gombatest.
      */
    public boolean hasBody(int mushroomId){
        if (myMushroom != null) {
            return myMushroom.getMushroomId() == mushroomId;
        }
        return false;
        
    }

    
    /**
     * A tekton két tektonra törése
     */
    public void breakTecton(){
        List<Tecton> ng = getNeighbors();
        Tecton t3 = new Tecton();
        changeListener.tectonChanged(ObjectChangeEvent.OBJECT_ADDED, t3);
        for (Tecton t : ng) {
            t3.setNeighbors(t);
            t.setNeighbors(t3);
        }
        t3.setNeighbors(this);
        this.setNeighbors(t3);
        int size = connections.size();
        for (int i = 0; i < size; i++) {
            connections.get(0).Destroy();
        }
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
