package Model;
import Listeners.ObjectChangeListener;
import Listeners.ObjectChangeListener.ObjectChangeEvent;
import Userinterface.RandTools;
import java.util.List;

/**
 * A Mushroom osztály valósítja meg a gombatesteket a játékban. 
 * A gombatestek felelősek a gombaspórák szórásáért, illetve a tektonok között gombafonalak növesztéséért, 
 * amik segítik a rovarok mozgását. A gombatestek egy adott mennyiségű spórával keletkeznek, 
 * és amikor kifogy belőlük, akkor meghalnak. Két életfázisuk van. 
 * Az elsőben csupán a szomszédos tektonra tudnak spórát szórni, 
 * a másodikban pedig már a szomszéd szomszédjára is.
 * 
 * Attribútumok:
 * - sporeCount: A gomba spórarekeszében lévő spórák száma
 * - id: A gomba egyedi azonosítója
 * - level: A gomba szintje
 * - myTecton: A tekton, ahol a gombatest áll
 * 
 * Metódusok:
 * - growLine: Gombafonal növesztése a paraméterként kapott tektonra
 * - throwSpores: Spóra dobása a paraméterként kapott tektonra
 * - destroy: A gombatest megszüntetése
 */
public class Mushroom{

    /* - Privát attribútumok*/
    private int sporeCount = 5;     //A gomba 5 spórával rendelkezik
    private int id;
    private int level = 1;          //Kezdetben a gomba 1-es szintű
    private Tecton myTecton;


    public ObjectChangeListener changeListener; //A gombatesthez tartozó eseménykezelők listája

    /* - Publikus attribútumok*/


    /* - Konstruktorok*/

    //Konstruktor
    public Mushroom(int id, Tecton myTecton){
        this.id = id;
        this.myTecton = myTecton;
    }


    //Alapértelmezett konstruktor
    public Mushroom(Tecton myTecton){
        this.id = 1;
        this.myTecton = myTecton;
    }


    /* - Getter/Setter metódusok*/

    public Tecton getMyTecton(){
        return this.myTecton;
    }


    public void setMyTecton(Tecton myTecton){
        this.myTecton = myTecton;
    }


    public int getSporeCount(){
        return this.sporeCount;
    }


    public void setSporeCount(int sporeCount){
        this.sporeCount = sporeCount;
    }


    public int getMushroomId(){
        return this.id;
    }


    public void setMushroomId(int id){
        this.id = id;
    }


    public int getLevel(){
        return this.level;
    }


    public void setLevel(int level){
        this.level = level;
    }


    /* - Egyéb metódusok*/
    
    /**
     * Gombafonál növesztését teszi lehetővé a paraméterként kapott tektonon. 
     * Ellenőrzi, hogy a kapott tekton szomszédos-e a tektonjával, 
     * vagy gombafonalakkal összekapcsolt tektonokkal szomszédos-e. Emellett figyelembe veszi, 
     * ha a paraméter tekton rendelkezik gombafonál növesztését hátrányosan érintő effekttel.
     * @param to
     * @return false, ha a gombafonál növesztése nem lehetséges, true, ha sikeres a gombafonál növesztése
     */
    public boolean growLine(Tecton to){
        
        //A szomszédos tektonok listája
        List<Tecton> neighborlist = this.myTecton.getNeighbors();
        
        if (neighborlist.contains(to)){
            Timer.addOneTimeSchedule(new Schedule() {
                @Override
                public void onTime() {
                    Line line = new Line(myTecton, to, id);
                    changeListener.lineChanged(ObjectChangeEvent.OBJECT_ADDED, line); //Eseménykezelő értesítése
                    System.out.println("Line successfully grown");
                }
            }, 1);
            System.out.println("Line grow started");
            return true;
        } else{
            System.out.println("Cannot grow line to this tecton, it is not a neighbor");
            return false;
        }
    }
    

    /**
     * Egy szomszédos, vagy fejlettebb gombatest esetén a szomszédok 
     * szomszédjaira történő spóra dobására szolgál. Paraméterként elvárja a 
     * céltektont, amelyre a spórát dobni szeretnénk és a spórák számát. 
     * @param to
     * @param count
     * @return false, ha a tekton nem szomszédos, true, ha sikeres a spóra dobás
     */
    public boolean throwSpores(Tecton to){

        boolean inRange = false;

        for  (Tecton t : this.myTecton.getNeighbors()){
            if (level >= 2 && t.getNeighbors().contains(to)){
                inRange = true;
            } 
            
            if (t == to){
                inRange = true;
            }
        }

        if (inRange){
            Spore spore = SporeContainer.generateSpores(1, id)[0];
            to.getSporeContainer().addSpores(spore);
            this.sporeCount--; 

            if (this.sporeCount <= 2) {
                this.level = 2;
            }

            if (this.sporeCount == 0) {
                destroy();
            }

            System.out.println("Spore succesfully thrown to with value: " + spore.getValue());

            return true;
        }

        System.out.println("Tecton is not in range");
        return false;
    }
    

    /**
     * A gombatest megszüntetése. A függvény szól a gombatest tektonjának, 
     * valamint a hozzá kapcsolódó gombafonalaknak. A gombafonalak ekkor ellenőrzik, 
     * hogy más gombatesttel kapcsolatban vannak-e, és ha nem akkor elindul bennük az elhalás.
     */
    public void destroy(){
        this.myTecton.setMyMushroom(null);      //A tektonon lévő gombatest nullázása
        changeListener.mushroomChanged(ObjectChangeEvent.OBJECT_REMOVED, this); //A gombatest eltávolítása a tektonról
    }
    
}