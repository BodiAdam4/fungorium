import java.util.List;
import java.util.Random;

/**
 * A Mushroom osztály valósítja meg a gombatesteket a játékban. 
 * A gombatestek felelősek a gombaspórák szórásáért, illetve a tektonok között gombafonalak növesztéséért, 
 * amik segítik a rovarok mozgását. A gombatestek egy adott mennyiségű spórával keletkeznek, 
 * és amikor kifogy belőlük, akkor meghalnak. Két életfázisuk van. 
 * Az elsőben csupán a szomszédos tektonra tudnak spórát szórni, 
 * a másodikban pedig már a szomszéd szomszédjára is.
 */
public class Mushroom{

    //Private fields
    private  int sporeCount = 5;        //A gomba spórarekeszében lévő spórák száma
    private int id;                 //A gomba egyedi azonosítója
    private int level = 1;              //A gomba szintje
    private Tecton myTecton;        //A tekton, ahol a gombatest áll éppen


    //Public fields


    //Constructor
    public Mushroom(int id, Tecton myTecton, String objectName){
        Logger.Constructor(this, objectName, new Object[]{id, myTecton});
        this.id = id;
        this.myTecton = myTecton;
        Logger.FunctionEnd();
    }

    //Default constructor
    public Mushroom(Tecton myTecton, String objectName){
        Logger.Constructor(this, objectName, new Object[]{myTecton});
        this.id = 1;
        this.myTecton = myTecton;
        Logger.FunctionEnd();
    }

    /* - Getter/Setter methods*/

    public Tecton getMyTecton(){
        Logger.FunctionStart(this, "getMyTecton");
        Logger.FunctionEnd();
        return this.myTecton;
    }

    public void setMyTecton(Tecton myTecton){
        Logger.FunctionStart(this, "setMyTecton", new Object[]{myTecton});
        this.myTecton = myTecton;
        Logger.FunctionEnd();
    }

    public int getSporeCount(){
        Logger.FunctionStart(this, "getSporeCount");
        Logger.FunctionEnd();
        return this.sporeCount;
    }

    public void setSporeCount(int sporeCount){
        Logger.FunctionStart(this, "setSporeCount",new Object[]{sporeCount});
        this.sporeCount = sporeCount;
        Logger.FunctionEnd();
    }

    public int getId(){
        Logger.FunctionStart(this, "getId");
        Logger.FunctionEnd();
        return this.id;
    }

    public void setId(int id){
        Logger.FunctionStart(this, "setId");
        this.id = id;
        Logger.FunctionEnd();
    }

    public int getLevel(){
        Logger.FunctionStart(this, "getLevel");
        Logger.FunctionEnd();
        return this.level;
    }

    public void setLevel(int level){
        Logger.FunctionStart(this, "setLevel",new Object[]{level});
        this.level = level;
        Logger.FunctionEnd();
    }


    /* - Other operation methods*/

    
    /**
     * Gombafonál növesztését teszi lehetővé a paraméterként kapott tektonon. 
     * Ellenőrzi, hogy a kapott tekton szomszédos-e a tektonjával, 
     * vagy gombafonalakkal összekapcsolt tektonokkal szomszédos-e. Emellett figyelembe veszi, 
     * ha a paraméter tekton rendelkezik gombafonál növesztését hátrányosan érintő effekttel.
     * @param to
     * @return false, ha a gombafonál növesztése nem lehetséges, true, ha sikeres a gombafonál növesztése
     */
    public boolean growLine(Tecton to){
        Logger.FunctionStart(this, "growLine", new Object[]{to});
        
        List<Tecton> neighborlist = this.myTecton.getNeighbors();
        
        if (neighborlist.contains(to)){
            Line line = new Line("line", this.myTecton, to, this.id);
            Logger.FunctionEnd();
            return true;
        } else{
            Logger.Log("The given tecton is not a neighbor of the current tecton.");
            Logger.FunctionEnd();
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
    public boolean throwSpores(Tecton to, int count){
        Logger.FunctionStart(this, "throwSpores", new Object[]{to, count});
        Random random = new Random();

        int dist = 1;

        if ((dist == 1 && this.level == 1) || (dist == 2 && this.level > 1)){

            if (this.sporeCount >= count){
                for (int i = 0; i < count; i++){
                    Spore spore = new Spore("spore", this.id, random.nextInt(10));
                    to.getSporeContainer().addSpores(spore);
                }

                //Spóraszám csökkentése
                this.sporeCount -= count;

                //Ha eldobtunk 5db spórákat, akkor a gomba meghal
               

                Logger.Log("Successfully threw spores to the tecton.");

                if (this.sporeCount == 0){
                    Logger.Log("The mushroom has thrown 5 spores and will now be destroyed.");
                    this.destroy();
                }
                
                Logger.FunctionEnd();
                return true;
            } else{
                Logger.Log("The spore count is not enough to throw spores.");
                Logger.FunctionEnd();
                return false;
            }
        }else{
            Logger.Log("The given tecton is not a neighbor of the current tecton.");
            Logger.FunctionEnd();
            return false;
        }
    }
    

    /**
     * A gombatest megszüntetése. A függvény szól a gombatest tektonjának, 
     * valamint a hozzá kapcsolódó gombafonalaknak. A gombafonalak ekkor ellenőrzik, 
     * hogy más gombatesttel kapcsolatban vannak-e, és ha nem akkor elindul bennük az elhalás.
     */
    public void destroy(){
        Logger.FunctionStart(this, "destroy");
        this.myTecton.setMyMushroom(null);
        Logger.DestroyObject(this);
    }
    
}