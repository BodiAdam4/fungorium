package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import listeners.JobListener;
import listeners.MushroomListener;
import listeners.ObjectChangeListener;
import listeners.ObjectChangeListener.ObjectChangeEvent;

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

    /* - Listenerlisták*/
    private List<MushroomListener> mushroomListeners = new ArrayList<>();
    private List<JobListener> jobListeners = new ArrayList<>();


    public ObjectChangeListener changeListener; //A gombatesthez tartozó eseménykezelők listája

    /* - Publikus attribútumok*/


    /* - Konstruktorok*/

    //Konstruktor
    public Mushroom(int id, Tecton myTecton){
        this.id = id;
        this.myTecton = myTecton;
        NotifyLines(false);
    }


    //Alapértelmezett konstruktor
    public Mushroom(Tecton myTecton){
        this.id = 1;
        this.myTecton = myTecton;
        NotifyLines(false);
    }

    /**
     * A gombatest tektonjához tartozó gombafonalak értesítése. Akkor adunk igaz paramétert neki, ha szükséges, hogy megnézzük
     * a gombatestel való összeköttetést, ez akkor szükséges, ha a gombatest megsemmisül és meg kell nézni, hogy létezik-e még valahol gomba.
     * Ha a gombatest létrehozásánál hívjuk, akkor tudjuk hogy van gombatestel összeköttetés mert az új gombatest az, és hamis paramétert adunk neki.
     * @param check Ha igaz, akkor nem ellenőrzi, hogy van-e összeköttetés és automatikusan feltételezi, hogy van.
     */
    public void NotifyLines(boolean check) {
        Optional<Line> found = myTecton.getConnections().stream()
        .filter(line -> line.getId() == id)
        .findFirst();

        if (found.isPresent()) {
            boolean connected = true;
            if (check) {
                connected = found.get().checkConnections(new ArrayList<>(), null);
            }
            System.out.println("Connected: " + connected);
            for (JobListener listener : jobListeners) {
                listener.jobSuccessfull("Connected: " + connected);
            }
            found.get().notifyNeighbors(connected, new ArrayList<>(), null);
        }
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


    /* - Adder metódusok*/


    /**
     * Egy gombatest-eseményfigyelő beállítása.
    */
    public void addMushroomListener(MushroomListener listener) {
        this.mushroomListeners.add(listener);
    }


    /**
     * Műveletek eseményfigyelőjének beállítása.
    */
    public void addJobListener(JobListener listener) {
        this.jobListeners.add(listener);
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

        if (!to.canAddLine(id)) {
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Can't grow more than two types of line on this tecton");
            }
            return false;
        }
        
        if (neighborlist.contains(to)){
            Timer.addOneTimeSchedule(new Schedule() {
                @Override
                public void onTime() {

                    if (!to.canAddLine(id)) {
                        for (JobListener listener : jobListeners) {
                            listener.jobFailed("Failed to grow mushroom on tecton");
                        }
                        return;
                    }

                    Line line = new Line(myTecton, to, id);
                    changeListener.lineChanged(ObjectChangeEvent.OBJECT_ADDED, line); //Eseménykezelő értesítése
                    System.out.println("Line successfully grown");

                    //A listener értesítése a gombafonal növesztéséről
                    for (MushroomListener listener : mushroomListeners) {
                        listener.lineGrew(line);
                    }

                    //Joblistener értesítése a gombafonal növesztéséről
                    for (JobListener listener : jobListeners) {
                        listener.jobSuccessfull("Line successfully grown");
                    }
                }
            }, 1);
            System.out.println("Line grow started");

            //Joblistener értesítése a gombafonal növesztéséről
            for (JobListener listener : jobListeners) {
                listener.jobSuccessfull("Line grow started");
            }

            return true;
        } else{
            System.out.println("Cannot grow line to this tecton, it is not a neighbor");

            //Joblistener értesítése a gombafonal növesztéséről
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Cannot grow line to this tecton, it is not a neighbor");
            }

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
                for (MushroomListener listener : mushroomListeners) {
                    listener.mushroomUpgraded();
                }
            }

            if (this.sporeCount == 0) {
                destroy();
            }

            System.out.println("Spore succesfully thrown to with value: " + spore.getValue());

            //Joblistener értesítése a spóra dobásáról
            for (JobListener listener : jobListeners) {
                listener.jobSuccessfull("Spore successfully thrown to tecton with value: " + spore.getValue());
            }

            return true;
        }

        System.out.println("Tecton is not in range");

        //Joblistener értesítése a spóra dobásáról
        for (JobListener listener : jobListeners) {
            listener.jobFailed("Tecton is not in range");
        }

        return false;
    }
    

    /**
     * A gombatest megszüntetése. A függvény szól a gombatest tektonjának, 
     * valamint a hozzá kapcsolódó gombafonalaknak. A gombafonalak ekkor ellenőrzik, 
     * hogy más gombatesttel kapcsolatban vannak-e, és ha nem akkor elindul bennük az elhalás.
     */
    public void destroy(){
        this.myTecton.removeMushroom();      //A tektonon lévő gombatest nullázása
        changeListener.mushroomChanged(ObjectChangeEvent.OBJECT_REMOVED, this); //A gombatest eltávolítása a tektonról

        NotifyLines(true);

        //Listenerek értesítése a gombatest eltávolításáról
        for (MushroomListener listener : mushroomListeners) {
            listener.mushroomDestroyed();
        }

    }
    
}