public class Mushroom{

    //Private fields
    private  int sporeCount;        //A gomba spórarekeszében lévő spórák száma
    private int id;                 //A gomba egyedi azonosítója
    private int level;              //A gomba szintje
    //private Tecton myTecton;


    //Public fields


    //Constructor
    public Mushroom(int id){
        this.id = id;;
    }

    //Default constructor
    public Mushroom(){
        this.id = 1;
    }

    /* - Getter/Setter methods*/

    public int getSporeCount(){
        return this.sporeCount;
    }

    public void setSporeCount(int sporeCount){
        this.sporeCount = sporeCount;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getLevel(){
        return this.level;
    }

    public void setLevel(int level){
        this.level = level;
    }


    /* - Other operation methods*/

    
    /**
     * Gombafonál növesztését teszi lehetővé a paraméterként kapott tektonon. 
     * Ellenőrzi, hogy a kapott tekton szomszédos-e a tektonjával, 
     * vagy gombafonalakkal összekapcsolt tektonokkal szomszédos-e. Emellett figyelembe veszi, 
     * ha a paraméter tekton rendelkezik gombafonál növesztését hátrányosan érintő effekttel. 
     * Ha a gombafonál növesztése nem lehetséges, akkor false értékkel tér vissza, másképp true-val.
     * @param to
     * @return
     */
    /*
    public boolean growLine(Tecton to){
        //TODO: Implement this method
    }
    */

    /**
     * Egy szomszédos, vagy fejlettebb gombatest esetén a szomszédok 
     * szomszédjaira történő spóra dobására szolgál. Paraméterként elvárja a 
     * céltektont, amelyre a spórát dobni szeretnénk és a spórák számát. 
     * Ha a tekton nem szomszédos, akkor false értékkel tér vissza. Abban az esetben, 
     * ha sikeres a spóra dobás, true értékkel.
     * @param to
     * @param count
     * @return
     */
    /*
    public boolean throwSpores(Tecton to, int count){
        //TODO: Implement this method
    }
    */

    /**
     * A gombatest megszüntetése. A függvény szól a gombatest tektonjának, 
     * valamint a hozzá kapcsolódó gombafonalaknak. A gombafonalak ekkor ellenőrzik, 
     * hogy más gombatesttel kapcsolatban vannak-e, és ha nem akkor elindul bennük az elhalás.
     */
    /*
    public void destroy(){
        //TODO: Implement this method
    }
    */
    
}