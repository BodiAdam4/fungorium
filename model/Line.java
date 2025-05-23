package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import listeners.JobListener;
import listeners.LineListener;
import listeners.ObjectChangeListener;
import listeners.ObjectChangeListener.ObjectChangeEvent;

/**
 * A Line osztály valósítja meg a gombafonalakat a játékban.
 * A gombafonalak két tektont kötnek össze, és új gombatestek növesztéséért felelősek.
 */
public class Line 
{
    /* - Privát attribútumok*/
    private int mushroomId;
    private Tecton[] ends;
    private List<LineListener> lineListeners = new ArrayList<>();
    private List<JobListener> jobListeners = new ArrayList<>();

    /* - Publikus attribútumok */
    public int ttl = -1;
    public ObjectChangeListener changeListener;


    /**
     * Line konstruktor.
     * @param name Line neve
     * @param t1 A vonal által összekötött első Tecton
     * @param t2 A vonal által összekötött második Tecton
     * @param id A gombafaj azonosítója
     */
    public Line(Tecton t1, Tecton t2, int id)
    {
        this.mushroomId = id;
        boolean res1 = t1.addLine(this);

        boolean res2 = false;

        if (res1) {
            res2 = t2.addLine(this);
        }

        if (!res1 && res2) {
            t2.removeLine(this);
        }

        if (!res2 && res1) {
            t1.removeLine(this);
        }
        ends = new Tecton[2];
        ends[0] = t1;
        ends[1] = t2;

        boolean connected = checkConnections(new ArrayList<>(), null);
        notifyNeighbors(connected, new ArrayList<>(), null);
    }


    /** - Getter/Setter metódusok*/

    public int getId() {
        return mushroomId;
    }

    public int getTtl() {
        return ttl;
    }

    public Tecton[] getEnds() {
        return ends;
    }

    public List<LineListener> getLineListeners(){
        return lineListeners;
    }

    public List<JobListener> getJobListeners(){
        return jobListeners;
    }

    /** - Egyéb metódusok*/

    
    /**
     * Megnézi a vele összekötött vonalakon keresztül, hogy a gombafajnak van-e teste.
     * @param checkList az eddig megvizsgált fonalak listája
     * @param last az a tekton, amelyik hívtuk a metüdust
     * @return true, ha a gombafajnak van teste, false ha nincs
     */
    public boolean checkConnections(List<Line> checkList, Tecton last)
    {
        checkList.add(this);

        if (ends[0].hasBody(mushroomId) || ends[1].hasBody(mushroomId)) {
            return true;
        }

        for (Tecton tecton : ends) {
            if (tecton != last) {
                for (Line line : tecton.getConnections()){
                    if (!checkList.contains(line) && line.checkConnections(checkList, tecton)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Értesíti a fonalakat a gombatest hiányáról vagy meglétéről.
     * Ez a függvény a vezérlővel lesz erősebb kapcsolatban
     * @param alive üzenet
     * @param checkList az eddig megvizsgált fonalak listája
     * @param last az a tekton, amelyik hívtuk a metüdust
     */
    public void notifyNeighbors(boolean alive, List<Line> checkList, Tecton last) {

        if (alive && ttl != -1) {
            ttl = -1;
            for(LineListener ll : lineListeners)
                ll.phaseChange(1);
        }
        else if (!alive && ttl == -1) {
            ttl = 3;
            for(LineListener ll : lineListeners)
                ll.phaseChange(2);
        }

        List<Line> connections = new ArrayList<>();

        for (Tecton tecton : ends) {
            if (tecton != last) {
                connections.addAll(tecton.getConnections());
            }
        }

        for (Line line : connections) {
            if (!checkList.contains(line)) {
                checkList.add(line);
                line.notifyNeighbors(alive, checkList, ends[0]);
            }
        }
    }


    /**
     * Növeszt egy gombát valamelyik Tectonjára
     * @param to Az a Tecton, amelyre a gomba nő
     * @return Tesztelés miatt mindig true
     */
    public boolean growMushroom(Tecton to)
    {
        return to.addMushroom(mushroomId);
    }


    /**
     * Növeszt egy fonalat a két Tecton között.
     * @param to1 Az első Tecton
     * @param to2 A második Tecton
     * @return Tesztelés miatt mindig true
     */
    public boolean growLine(Tecton to1, Tecton to2)
    {
        boolean neighbor = false;

        for (Tecton t : to1.getNeighbors()) {
            if (t == to2) {
                neighbor = true;
                break;
            }
        }

        neighbor = (ends[0] == to1 || ends[1] == to1) || (ends[0] == to2 || ends[1] == to2);

        if(!neighbor) {
            for (JobListener listener : jobListeners) {
                listener.jobFailed("The two tectons are not neighbors");
            }
            return false;
        }

        if (!to1.canAddLine(mushroomId) || !to2.canAddLine(mushroomId)) {
            for (JobListener listener : jobListeners) {
                listener.jobFailed("Can't grow more than two types of line on this tecton");
            }
            return false;
        }
            
        for (JobListener listener : jobListeners) {
            listener.jobSuccessfull("Line started to grow!");
        }

        Timer.addOneTimeSchedule(new Schedule() {
            @Override
            public void onTime() {

                if (!to1.canAddLine(mushroomId) || !to2.canAddLine(mushroomId)) {
                    for (JobListener listener : jobListeners) {
                        listener.jobFailed("Failed to grow mushroom on tecton");
                    }
                    return;
                }

                Line nl = new Line(to1, to2, mushroomId);
                changeListener.lineChanged(ObjectChangeEvent.OBJECT_ADDED, nl);
                for (JobListener listener : jobListeners) {
                    listener.jobSuccessfull("Line successfully grown!");
                }
            }
        }, 1);
        return true;
    }

    /**
     * Megpróbálja megenni fonál a megadott rovart.
     * @param insect
     * @return igaz, ha a fonál megevett egy rovart, különben hamis
     */
    public boolean eatInsect(Insect insect) {
        Spore[] newSpores = SporeContainer.generateSpores(3, this.mushroomId);

        Tecton insectSideTecton = null;

        for (int i = 0; i<ends.length; i++) {
            if (this.ends[i].equals(insect.getTecton()))
                insectSideTecton = ends[i];
        }

        insectSideTecton.getSporeContainer().addSpores(newSpores);
        insectSideTecton.addMushroom(this.mushroomId);
        insect.destroy();
        System.out.println("Insect eaten and Mushroom grown");
        for (JobListener listener : jobListeners) {
            listener.jobSuccessfull("Insect eaten and Mushroom grown");
        }
        return true;
    }


    /**
     * Megsemmisíti a fonalat.
     */
    public void Destroy() {
        ends[0].removeLine(this);
        ends[1].removeLine(this);
        changeListener.lineChanged(ObjectChangeEvent.OBJECT_REMOVED, this);

        for (int i = 0; i<2; i++) {
            Optional<Line> found = ends[i].getConnections().stream()
            .filter(line -> line.mushroomId == mushroomId)
            .findFirst();

            if (found.isPresent()) {
                boolean connected = found.get().checkConnections(new ArrayList<>(), null);
                found.get().notifyNeighbors(connected, new ArrayList<>(), null);
            }
        }

        for(LineListener ll : lineListeners)
            ll.lineDestroyed();

        for (JobListener listener : jobListeners) {
            listener.jobSuccessfull("Line destroyed");
        }
    }


    /**
     * Hozzáad egy LineListenert a lineListeners-hez
     * @param listener a listener, amit a listához adunk
     */
    public void addLineListener(LineListener listener){
        lineListeners.add(listener);
    }


    /**
     * Hozzáad egy JobListenert a jobListeners-hez
     * @param listener a listener, amit a listához adunk
     */
    public void addJobListener(JobListener listener){
        jobListeners.add(listener);
    }
}
