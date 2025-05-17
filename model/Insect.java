package model;

import java.util.ArrayList;
import java.util.List;
import listeners.InsectListener;
import listeners.JobListener;
import listeners.ObjectChangeListener;
import listeners.ObjectChangeListener.ObjectChangeEvent;

/**
 * Egy rovart reprezentál különböző tulajdonságokkal, például sebesség,
 * spóraszám, valamint a vágás és mozgás képessége.
 */
public class Insect {

    /* - Privát attribútumok */
    private int insectId;           //A rovar azonosítója
    private int speed;              //A rovar sebessége
    private int sporeCount;         //A rovar által elfogyasztott spórák száma
    private boolean canCut;         //Tud-e éppen vágni a rovar
    private boolean canMove;        //Mozgásképes-e a rovar
    private Tecton currentTecton;   //A tekton amin a rovar éppen van.
    private boolean unusable = false;

    /* - Publikus attribútumok */
    public ObjectChangeListener changeListener; //A rovarhoz tartozó eseménykezelők listája

    /* - Listenerlisták */
    private List<InsectListener> insectListeners = new ArrayList<>();
    private List<JobListener> jobListeners = new ArrayList<>();

    /**
     * Egy Insect-eseményfigyelő beállítása.
     * @param listener az adott InsectListener
     */
    public void addInsectListener(InsectListener listener) {
        this.insectListeners.add(listener);
    }


    /**
     * Műveletek eseményfigyelőjének beállítása.
     * @param listener az adott JobListener
     */
    public void addJobListener(JobListener listener) {
        this.jobListeners.add(listener);
    }

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

    public List<JobListener> getJobListeners() {
        return jobListeners;
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
     * A rovart egy új helyre mozgatja.
     * @param to a cél Tecton
     * @return igaz, ha a mozgás sikeres volt, egyébként hamis
     */
    public boolean move(Tecton to) {
        if (unusable) {
            System.out.println("Insect is currently moving");
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Insect is currently moving");
            }
            return false;
        }

        boolean inRange = false;
        for (Line line : currentTecton.getConnections()){
            if (line.getEnds()[0] == to || line.getEnds()[1] == to){
                inRange = true;
                break;
            }
            else if (speed == 3){
                Tecton toSearch = line.getEnds()[0] == currentTecton ? line.getEnds()[1] : line.getEnds()[0];
                for (Line next : toSearch.getConnections()){
                    if (next.getEnds()[0] == to || next.getEnds()[1] == to){
                        inRange = true;
                        break;
                    }
                }
            }
        }

        if(inRange){
            if(!canMove){
                System.out.println("Insect cannot move");
                for (JobListener listener : jobListeners) {
                    listener.jobFailed("Insect cannot move");
                }
                return false;
            }

            if (speed == 1) {
                unusable = true;
                for (InsectListener listener : insectListeners ) {
                    listener.moveStarted(currentTecton, to);
                }

                Timer.addOneTimeSchedule(new Schedule() {
                    @Override
                    public void onTime() {
                        to.addInsect(Insect.this);
                        currentTecton.removeInsect(Insect.this);
                        currentTecton = to;
                        System.out.println("Insect moved to tecton");
                        
                        for (JobListener listener : jobListeners) {
                            listener.jobSuccessfull("Insect moved to tecton");
                        }

                        for (InsectListener listener : insectListeners ) {
                            listener.moveFinished(to);
                        }

                        unusable = false;
                    }
                }, 1);
            }
            else {
                to.addInsect(this);
                currentTecton.removeInsect(this);
                currentTecton = to;
                for (JobListener listener : jobListeners) {
                    listener.jobSuccessfull("Insect moved to tecton");
                }
                for (InsectListener listener : insectListeners ) {
                    listener.moveFinished(to);
                }
                System.out.println("Insect moved to tecton");
            }
        }
        else{
            System.out.println("No line between the tectons or not in range");
            for (JobListener listener : jobListeners) {
                listener.jobFailed("No line between the tectons or not in range");
            }
            return false;
        }
        return true;
    }


    public String getEffect() {
        
        if (!getCanCut()) return "Tired";
        else if (!getCanMove()) return "Freezed";
        else if (getSpeed() == 1) return "Slowed";
        else if (getSpeed() == 3) return "Fast";
        else return "";
    }


    /**
     * Csökkenti a spórák számát a megadott mennyiséggel.
     * @param count az elfogyasztandó spórák száma
     */
    public int eatSpores(int count) {
        if (unusable) {
            System.out.println("Insect is currently moving");
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Insect is currently moving");
            }
            return 0;
        }

        if(count > currentTecton.getSporeContainer().getSporeCount()){
            return 0;
        }
        Spore[] selected = currentTecton.getSporeContainer().popSpores(count);
        int nutrientCount = 0;
        for(int i = 0; i < count; i++){
            selected[i].addEffect(this);
            sporeCount += selected[i].getValue();
            nutrientCount += selected[i].getValue();
        }
        String effect = getEffect();
        for (InsectListener listener : insectListeners ) {
            listener.sporeEaten(effect);
        }
        return nutrientCount;
    }


    /**
     * Megpróbál elvágni egy fonalat.
     * @param line az elvágandó fonal
     * @return igaz, ha a fonalat sikeresen elvágta, egyébként hamis
     */
    public boolean cutLine(Line line) {
        if (unusable) {
            System.out.println("Insect is currently moving");
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Insect is currently moving");
            }
            return false;
        }

        if (!canCut) {
            System.out.println("Insect cannot cut line");
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Insect cannot cut line");
            }
            return false;
        }
        line.Destroy();
        return true;
    }


    /**
     * Visszaállítja a rovar hatásait alapállapotba.
     */
    public void resetEffect() {
        canMove = true;
        canCut = true;
        speed = 2;

        
        for (InsectListener listener : insectListeners ) {
            listener.effectReseted();
        }
        for(JobListener listeners : jobListeners){
            listeners.jobSuccessfull("Insect's status has been reverted.");
        }
    }
    

    public void destroy(){
        currentTecton.removeInsect(this);
        changeListener.insectChanged(ObjectChangeEvent.OBJECT_REMOVED, this);
    }

}
