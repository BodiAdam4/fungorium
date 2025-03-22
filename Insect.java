
/**
 * Represents an insect with various attributes such as speed, spore count,
 * and abilities to cut and move.
 */
public class Insect {

    private int speed;
    private int sporeCount;
    private boolean canCut;
    private boolean canMove;
    private Tecton currentTecton;

    /**
     * Sets the speed of the insect.
     *
     * @param speed the new speed value
     */
    public void setSpeed(int speed) {
        Logger.FunctionStart(this, "setSpeed", new Object[]{speed});
        this.speed = speed;
        Logger.FunctionEnd();
    }

    /**
     * Gets the current Tecton of the insect.
     *
     * @return the current Tecton
     */
    public Tecton getTecton() {
        Logger.FunctionStart(this, "getTecton");
        Logger.FunctionEnd();
        return currentTecton;
    }

    /**
     * Sets the Tecton of the insect.
     *
     * @param t the new Tecton;
     */
    public void setTecton(Tecton t) {
        Logger.FunctionStart(this, "setTecton", new Object[]{t});
        currentTecton = t;
        Logger.FunctionEnd();
    }

    /**
     * Gets the speed of the insect.
     *
     * @return the current speed value
     */
    public int getSpeed() {
        Logger.FunctionStart(this, "getSpeed");
        Logger.FunctionEnd();
        return speed;
    }

    /**
     * Sets whether the insect can cut.
     *
     * @param canCut true if the insect can cut, false otherwise
     */
    public void setCanCut(boolean canCut) {
        Logger.FunctionStart(this, "setCanCut", new Object[]{canCut});
        this.canCut = canCut;
        Logger.FunctionEnd();
    }

    /**
     * Checks if the insect can cut.
     *
     * @return true if the insect can cut, false otherwise
     */
    public boolean getCanCut() {
        Logger.FunctionStart(this, "getCanCut");
        Logger.FunctionEnd();
        return canCut;
    }

    /**
     * Sets whether the insect can move.
     *
     * @param canMove true if the insect can move, false otherwise
     */
    public void setCanMove(boolean canMove) {
        Logger.FunctionStart(this, "setCanMove", new Object[]{canMove});
        this.canMove = canMove;
        Logger.FunctionEnd();
    }

    /**
     * Checks if the insect can move.
     *
     * @return true if the insect can move, false otherwise
     */
    public boolean getCanMove() {
        Logger.FunctionStart(this, "getCanMove");
        Logger.FunctionEnd();
        return canMove;
    }

    /**
     * Constructs an Insect with specified attributes.
     *
     * @param speed the speed of the insect
     * @param sporeCount the number of spores the insect has
     * @param canCut whether the insect can cut
     * @param canMove whether the insect can move
     */
    

     /**
     * Constructs an Insect with default values.
     */
     public Insect(String objectName) {
        Logger.Constructor(this, objectName);
        speed = 1;
        sporeCount = 0;
        canCut = true;
        canMove = true;
        Logger.FunctionEnd();
    }

    /**
     * Moves the insect to a new location.
     *
     * @param to the destination Tecton
     * @return true if the move was successful, false otherwise
     */
    public boolean move(Tecton to) {
        Logger.FunctionStart(this, "move", new Object[]{to});
        if(to.getConnections().stream().anyMatch(line -> line.connections.contains(to))){
            to.addInsect(this);
            currentTecton.removeInsect(this);
            currentTecton = to;
            Logger.Log("Insect moved to tecton");
        }
        else{
            Logger.Log("No line between the tectons");
        }
        Logger.FunctionEnd();
        return true;
    }

    /**
     * Reduces the spore count by the specified amount.
     *
     * @param count the number of spores to eat
     */
    public void eatSpores(int count) {
        Logger.FunctionStart(this, "eatSpores", new Object[]{count});
        Spore[] selected = currentTecton.getSporeContainer().popSpores(count);
        for(int i = 0; i < count; i++){
            selected[i].addEffect(this);
        }
        sporeCount += count;
        Logger.FunctionEnd();
    }

    /**
     * Attempts to cut a line.
     *
     * @param line the line to be cut
     * @return true if the line was successfully cut, false otherwise
     */
    public boolean cutLine(Line line) {
        Logger.FunctionStart(this, "cutLine", new Object[]{line});
        line.Destroy();
        Logger.FunctionEnd();
        return true;
    }

    /**
     * Resets the insect's effects.
     */
    public void resetEffect() {
        Logger.FunctionStart(this, "resetEffect");
        Logger.FunctionEnd();
    }
}