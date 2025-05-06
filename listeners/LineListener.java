package listeners;

import model.Insect;

public interface LineListener {
    public void insectEaten(Insect insect);
    public void lineDestroyed();
    public void phaseChange(int phase);
}
