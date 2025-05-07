package listeners;

import java.util.HashMap;

public interface ControlListener {
    public void onNextRound(String player, boolean isInsect, int round, HashMap<String, Integer> points);
}
