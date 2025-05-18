package listeners;

import java.util.HashMap;

/**
 * Játékosok váltásáért felelős listener
 */
public interface ControlListener {

    /**
     * Következő játékos kiválasztásáért felelős függvény
     * Ha a játékos nem tud játszani, azaz minden rovara és gombateste halott, akkor átugorjuk.
     * @param player játékos neve
     * @param isInsect rovarász vagy gombász
     * @param round kör száma
     * @param points pontok száma
     */
    public void onNextRound(String player, boolean isInsect, int round, HashMap<String, Integer> points);
}
