package Model;

import Listeners.ObjectChangeListener;
import Listeners.ObjectChangeListener.ObjectChangeEvent;

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

    public ObjectChangeListener changeListener; //A rovarhoz tartozó eseménykezelők listája

    /**
     * Visszaadja, hogy hány spórát evett meg a rovar.
     * @return Elfogyasztott spórák száma.
     */
    public int getSporeCount() {
        return sporeCount;
    }

    /**
     * beállítja, az elfogyasztott spórák számát, csak a duplicate függvény miatt kell.
     */
    private void setSporeCount(int sporeCount) {
        this.sporeCount = sporeCount;
    }


    /**
     * Lekéri a rovar azaonosítóját.
     *
     * @return rovarazonosító.
     */
    public int getInsectId() {
        return insectId;
    }

    public void setInsectId(int insectId) {
        this.insectId = insectId;
    }

    /**
     * Beállítja a rovar sebességét.
     *
     * @param speed az új sebesség értéke
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Lekéri a rovar jelenlegi Tectonját.
     *
     * @return a jelenlegi Tecton
     */
    public Tecton getTecton() {
        return currentTecton;
    }

    /**
     * Beállítja a rovar Tectonját.
     *
     * @param t az új Tecton
     */
    public void setTecton(Tecton t) {
        currentTecton = t;
    }

    /**
     * Lekéri a rovar sebességét.
     *
     * @return a jelenlegi sebesség értéke
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Beállítja, hogy a rovar tud-e vágni.
     *
     * @param canCut igaz, ha a rovar tud vágni, egyébként hamis
     */
    public void setCanCut(boolean canCut) {
        this.canCut = canCut;
    }

    /**
     * Ellenőrzi, hogy a rovar tud-e vágni.
     *
     * @return igaz, ha a rovar tud vágni, egyébként hamis
     */
    public boolean getCanCut() {
        return canCut;
    }

    /**
     * Beállítja, hogy a rovar tud-e mozogni.
     *
     * @param canMove igaz, ha a rovar tud mozogni, egyébként hamis
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * Ellenőrzi, hogy a rovar tud-e mozogni.
     *
     * @return igaz, ha a rovar tud mozogni, egyébként hamis
     */
    public boolean getCanMove() {
        return canMove;
    }

    /**
     * Létrehoz egy rovart alapértelmezett értékekkel.
     */
    public Insect() {
        insectId = 1; //A rovar azonosítója
        speed = 2;
        sporeCount = 0;
        canCut = true;
        canMove = true;
    }
    /**
     * Létrehoz egy rovart a megadott tulajdonságokkal.
     * @param insectId A rovar azonosítója
     * @param speed a rovar sebessége
     * @param sporeCount a rovar által birtokolt spórák száma
     * @param canCut képes-e vágni a rovar
     * @param canMove képes-e mozogni a rovar
     * @param currentTecton Melyik tektonon van a rovar
     */
    public Insect(int insectId, int speed, int sporeCount, boolean canCut, boolean canMove, Tecton currentTecton ){
        this.insectId = insectId;
        this.speed = speed;
        this.sporeCount = sporeCount;
        this.canCut = canCut;
        this.canMove = canMove;
        this.currentTecton = currentTecton;
    }

    /**
     * A rovart egy új helyre mozgatja.
     *
     * @param to a cél Tecton
     * @return igaz, ha a mozgás sikeres volt, egyébként hamis
     */
    public boolean move(Tecton to) {
        if(to.getConnections().stream().anyMatch(line -> line.getEnds()[0] == currentTecton || line.getEnds()[1] == currentTecton)){
            to.addInsect(this);
            currentTecton.removeInsect(this);
            currentTecton = to;
            Logger.Log("Insect moved to tecton");
        }
        else{
            Logger.Log("No line between the tectons");
        }
        return true;
    }

    /**
     * Csökkenti a spórák számát a megadott mennyiséggel.
     *
     * @param count az elfogyasztandó spórák száma
     */
    public void eatSpores(int count) {
        Spore[] selected = currentTecton.getSporeContainer().popSpores(count);
        for(int i = 0; i < count; i++){
            selected[i].addEffect(this);
            sporeCount += selected[i].getValue();
        }
    }

    /**
     * Megpróbál elvágni egy vonalat.
     *
     * @param line az elvágandó vonal
     * @return igaz, ha a vonalat sikeresen elvágta, egyébként hamis
     */
    public boolean cutLine(Line line) {
        line.Destroy();
        return true;
    }

    /**
     * Visszaállítja a rovar hatásait alapállapotba.
     */
    public void resetEffect() {
        canMove = true;
        canCut = true;
    }

    public void delete(){
        currentTecton.removeInsect(this);
        changeListener.insectChanged(ObjectChangeEvent.OBJECT_REMOVED, this);
    }
}
