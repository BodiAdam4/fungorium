public class Mushroom{

    //Private fields
    private  int sporeCount;        //A gomba spórarekeszében lévő spórák száma
    private int id;                 //A gomba egyedi azonosítója
    private int level;              //A gomba szintje
    //private Tecton myTecton;      //A tekton, ahol a gombatest áll éppen


    //Public fields


    //Constructor
    public Mushroom(int id, String objectName){
        Logger.Constructor(this, objectName, new Object[]{id});
        this.id = id;
        Logger.FunctionEnd();
    }

    //Default constructor
    public Mushroom(String objectName){
        Logger.Constructor(this, objectName);
        this.id = 1;
        Logger.FunctionEnd();
    }

    /* - Getter/Setter methods*/

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
     * Ha a gombafonál növesztése nem lehetséges, akkor false értékkel tér vissza, másképp true-val.
     * @param to
     * @return
     */
    /*
    public boolean growLine(Tecton to){
        Logger.FunctionStart(this, "growLine", new Object[]{to});
        //TODO: Implement this method
        Logger.FunctionEnd();
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
        Logger.FunctionStart(this, "throwSpores", new Object[]{to, count});
        //TODO: Implement this method
        Logger.FunctionEnd();
    }
    */

    /**
     * A gombatest megszüntetése. A függvény szól a gombatest tektonjának, 
     * valamint a hozzá kapcsolódó gombafonalaknak. A gombafonalak ekkor ellenőrzik, 
     * hogy más gombatesttel kapcsolatban vannak-e, és ha nem akkor elindul bennük az elhalás.
     */
    /*
    public void destroy(){
        Logger.FunctionStart(this, "destroy");
        //TODO: Implement this method
        Logger.FunctionEnd();
    }
    */
    
}