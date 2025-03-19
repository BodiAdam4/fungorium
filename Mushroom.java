public class Mushroom{

    //Private fields
    private  int sporeCount;        //Number of spores in the mushroom
    private int id;                 //Unique identifier for the mushroom
    private int level;              //Level of the mushroom
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
     * Allows the growth of a mushroom thread on the tecton given as a parameter.
     * It checks whether the given tecton is adjacent to its tecton or adjacent to 
     * tectons connected by mushroom threads.
     * 
     * It also takes into account if the parameter tecton has an effect that 
     * adversely affects the growth of a mushroom thread.
     * 
     * If the growth of a mushroom thread is not possible, it returns false, otherwise true.
     * @param to
     * @return
     */
    /*
    public boolean growLine(Tecton to){
        //TODO: Implement this method
    }
    */

    /**
     * In the case of a neighboring or more advanced mushroom body, 
     * it is used to throw spores onto the neighbors of the neighbors.
     * 
     * It expects the target tecton to which we want to throw the spores 
     * and the number of spores as parameters.
     * 
     * If the tecton is not neighboring, it returns false. 
     * If the spore throw is successful, it returns true.
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
     * Termination of the mushroom body.
     * The function is for the tecton of the mushroom body and the 
     * fungal hyphae connected to it.
     * 
     * The fungal hyphae then check whether they are connected to another 
     * mushroom body, and if not, they begin to die.
     */
    /*
    public void destroy(){
        //TODO: Implement this method
    }
    */
    
}