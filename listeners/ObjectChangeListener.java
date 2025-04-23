package listeners;

import model.Insect;
import model.Line;
import model.Mushroom;
import model.Tecton;

public interface ObjectChangeListener {
    
    public enum ObjectChangeEvent {
        OBJECT_ADDED,
        OBJECT_REMOVED
    }

    public void insectChanged(ObjectChangeEvent event, Insect insect);
    public void lineChanged(ObjectChangeEvent event, Line line);
    public void tectonChanged(ObjectChangeEvent event, Tecton tecton);
    public void mushroomChanged(ObjectChangeEvent event, Mushroom mushroom);

}
