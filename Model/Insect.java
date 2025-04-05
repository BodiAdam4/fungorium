package Model;

/**
 * Egy rovart reprezentál különböző tulajdonságokkal, például sebesség,
 * spóraszám, valamint a vágás és mozgás képessége.
 */
public class Insect {

    private int insectId; //A rovar azonosítója
    private int speed; //A rovar sebessége
    private int sporeCount; //A rovar által elfogyasztott spórák száma
    private boolean canCut; //Tud-e éppen vágni a rovar
    private boolean canMove; //Mozgásképes-e a rovar
    private Tecton currentTecton; // A tekton amin a rovar éppen van.


    /**
     * Lekéri a rovar azaonosítóját.
     *
     * @return rovarazonosító.
     */
    public int getInsectId() {
        Logger.FunctionStart(this, "getInsectId");
        Logger.FunctionEnd();
        return insectId;
    }

    /**
     * Beállítja a rovar sebességét.
     *
     * @param speed az új sebesség értéke
     */
    public void setSpeed(int speed) {
        Logger.FunctionStart(this, "setSpeed", new Object[]{speed});
        this.speed = speed;
        Logger.FunctionEnd();
    }

    /**
     * Lekéri a rovar jelenlegi Tectonját.
     *
     * @return a jelenlegi Tecton
     */
    public Tecton getTecton() {
        Logger.FunctionStart(this, "getTecton");
        Logger.FunctionEnd();
        return currentTecton;
    }

    /**
     * Beállítja a rovar Tectonját.
     *
     * @param t az új Tecton
     */
    public void setTecton(Tecton t) {
        Logger.FunctionStart(this, "setTecton", new Object[]{t});
        currentTecton = t;
        Logger.FunctionEnd();
    }

    /**
     * Lekéri a rovar sebességét.
     *
     * @return a jelenlegi sebesség értéke
     */
    public int getSpeed() {
        Logger.FunctionStart(this, "getSpeed");
        Logger.FunctionEnd();
        return speed;
    }

    /**
     * Beállítja, hogy a rovar tud-e vágni.
     *
     * @param canCut igaz, ha a rovar tud vágni, egyébként hamis
     */
    public void setCanCut(boolean canCut) {
        Logger.FunctionStart(this, "setCanCut", new Object[]{canCut});
        this.canCut = canCut;
        Logger.FunctionEnd();
    }

    /**
     * Ellenőrzi, hogy a rovar tud-e vágni.
     *
     * @return igaz, ha a rovar tud vágni, egyébként hamis
     */
    public boolean getCanCut() {
        Logger.FunctionStart(this, "getCanCut");
        Logger.FunctionEnd();
        return canCut;
    }

    /**
     * Beállítja, hogy a rovar tud-e mozogni.
     *
     * @param canMove igaz, ha a rovar tud mozogni, egyébként hamis
     */
    public void setCanMove(boolean canMove) {
        Logger.FunctionStart(this, "setCanMove", new Object[]{canMove});
        this.canMove = canMove;
        Logger.FunctionEnd();
    }

    /**
     * Ellenőrzi, hogy a rovar tud-e mozogni.
     *
     * @return igaz, ha a rovar tud mozogni, egyébként hamis
     */
    public boolean getCanMove() {
        Logger.FunctionStart(this, "getCanMove");
        Logger.FunctionEnd();
        return canMove;
    }

    /**
     * Létrehoz egy rovart a megadott tulajdonságokkal.
     *
     * @param speed a rovar sebessége
     * @param sporeCount a rovar által birtokolt spórák száma
     * @param canCut képes-e vágni a rovar
     * @param canMove képes-e mozogni a rovar
     */

    /**
     * Létrehoz egy rovart alapértelmezett értékekkel.
     */
    public Insect(String objectName) {
        Logger.Constructor(this, objectName);
        insectId = 1; //A rovar azonosítója
        speed = 1;
        sporeCount = 0;
        canCut = true;
        canMove = true;
        Logger.FunctionEnd();
    }

    /**
     * A rovart egy új helyre mozgatja.
     *
     * @param to a cél Tecton
     * @return igaz, ha a mozgás sikeres volt, egyébként hamis
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
     * Csökkenti a spórák számát a megadott mennyiséggel.
     *
     * @param count az elfogyasztandó spórák száma
     */
    public void eatSpores(int count) {
        Logger.FunctionStart(this, "eatSpores", new Object[]{count});
        Spore[] selected = currentTecton.getSporeContainer().popSpores(count);
        for(int i = 0; i < count; i++){
            selected[i].addEffect(this);
            sporeCount += selected[i].getValue();
        }
        Logger.FunctionEnd();
    }

    /**
     * Megpróbál elvágni egy vonalat.
     *
     * @param line az elvágandó vonal
     * @return igaz, ha a vonalat sikeresen elvágta, egyébként hamis
     */
    public boolean cutLine(Line line) {
        Logger.FunctionStart(this, "cutLine", new Object[]{line});
        line.Destroy();
        Logger.FunctionEnd();
        return true;
    }

    /**
     * Visszaállítja a rovar hatásait alapállapotba.
     */
    public void resetEffect() {
        Logger.FunctionStart(this, "resetEffect");
        canMove = true;
        canCut = true;
        Logger.FunctionEnd();
    }
}
