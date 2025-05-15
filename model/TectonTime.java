package model;

import java.util.List;

import listeners.JobListener;
import listeners.ObjectChangeListener.ObjectChangeEvent;

/**
 * A Tecton osztály leszármazottja, mely rendelkezik egy olyan különleges hatással, ami miatt csak a kapcsolódott gombafonalak egy idő után megszűnnek
 */
public class TectonTime extends Tecton{
    
    //Konstruktor
    public TectonTime(){
        super();
    }

    
    /**
     * Gombafonál csatlakoztatása a tektonhoz.
     * @param line A hozzáadandó gombafonal
     * @return Visszatér egy boolean-el annak függvényében, hogy sikerült-e a fonalat hozzáadni.
     */
    public boolean addLine(Line line){
        connections.add(line);
        line.ttl = 3;
        Timer.addOneTimeSchedule(new Schedule() {
            @Override
            public void onTime() {
                line.Destroy();
            }
        }, 3);
        return true;
    }

    
    /**
     * A tekton két tektonra törése
     */
    public void breakTecton(){
        List<Tecton> ng = getNeighbors();
        TectonTime t3 = new TectonTime();
        changeListener.tectonChanged(ObjectChangeEvent.OBJECT_ADDED, t3);
        for (Tecton t : ng) {
            t3.setNeighbors(t);
            t.setNeighbors(t3);
        }
        t3.setNeighbors(this);
        this.setNeighbors(t3);
        int size = connections.size();
        for (int i = 0; i < size; i++) {
            connections.get(0).Destroy();
        }
        for (JobListener listener : jobListeners) {
            listener.jobSuccessfull("A tecton broke");
        }
    }

    
    public String toString() {
        return "Time";
    }
}
