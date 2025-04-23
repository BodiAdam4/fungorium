package model;

import listeners.ObjectChangeListener;
import listeners.ObjectChangeListener.ObjectChangeEvent;
import java.util.List;

/**
 * A Line osztály valósítja meg a gombafonalakat a játékban.
 * A gombafonalak két tektont kötnek össze, és új gombatestek növesztéséért felelősek.
 */
public class Line 
{
    /* - Privát attribútumok*/
    private int mushroomId;
    private Tecton[] ends;
    public int ttl = -1;

    public ObjectChangeListener changeListener;


    /**
     * Line konstruktor.
     *
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


    /** - Egyéb metódusok*/

    
    /**
     * Megnézi a vele összekötött vonalakon keresztül, hogy a gombafajnak van-e teste.
     * 
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
     * Értesíti a fonalakat a gombatest hiányáról.
     * (Ez a függvény a vezérlővel lesz erősebb kapcsolatban)
     */
    public void notifyNeighbors(boolean alive)
    {
        //TODO: implement this method
    }


    /**
     * Növeszt egy gombát valamelyik Tectonjára
     *
     * @param to Az a Tecton, amelyre a gomba nő
     * @return Tesztelés miatt mindig true
     */
    public boolean growMushroom(Tecton to)
    {
        to.addMushroom(mushroomId);
        return true;
    }


    /**
     * Növeszt egy fonalat a két Tecton között.
     *
     * @param to1 Az első Tecton
     * @param to2 A második Tecton
     * @return Tesztelés miatt mindig true
     */
    public boolean growLine(Tecton to1, Tecton to2)
    {
        Line nl = new Line(to1, to2, mushroomId);
        changeListener.lineChanged(ObjectChangeEvent.OBJECT_ADDED, nl);
        return true;
    }


    /**
     * Megsemmisíti a fonalat.
     */
    public void Destroy() {
        ends[0].removeLine(this);
        ends[1].removeLine(this);
        changeListener.lineChanged(ObjectChangeEvent.OBJECT_REMOVED, this);
    }
}
