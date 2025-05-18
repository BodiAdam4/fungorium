package listeners;

import model.Insect;
import model.Line;
import model.Mushroom;
import model.Tecton;

/**
 * A modelben lévő változásokért felelős listener
 */
public interface ObjectChangeListener {
    
    /**
     * Események fajtái.
     * Lehet osztály törlése vagy egy üj létrejövése.
     */
    public enum ObjectChangeEvent {
        OBJECT_ADDED,
        OBJECT_REMOVED
    }

    /**
     * Rovar változásakor meghívandó függvény
     * @param event az esemény fajtája
     * @param insect a rovar, amire vonatkozik
     */
    public void insectChanged(ObjectChangeEvent event, Insect insect);

    /**
     * Gombafonal változásakor meghívandó függvény
     * @param event az esemény fajtája
     * @param insect a gombafonal, amire vonatkozik
     */
    public void lineChanged(ObjectChangeEvent event, Line line);

    /**
     * Tekton változásakor meghívandó függvény
     * @param event az esemény fajtája
     * @param insect a tekton, amire vonatkozik
     */
    public void tectonChanged(ObjectChangeEvent event, Tecton tecton);

    /**
     * Gombatest változásakor meghívandó függvény
     * @param event az esemény fajtája
     * @param insect a gombatest, amire vonatkozik
     */
    public void mushroomChanged(ObjectChangeEvent event, Mushroom mushroom);
}
