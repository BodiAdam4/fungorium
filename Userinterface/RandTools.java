package Userinterface;

public class RandTools {
    private static int fixRandom;
    private static boolean active;

    /**
     * Ha teszteléshez determinisztikusan szeretnénk futtatni a programot, akkor
     * ezzel a metódussal beállíthatunk egy fix random számot, ami mindenhol ugyanazt
     * adja vissza. Ezt a metódust csak teszteléshez használjuk.
     * @param value a fix random szám, amit vissza akarunk adni
     */
    public static void setFixRandom(int value) {
        fixRandom = value;
        active = true;
        System.out.println("Random number fixed to: " + fixRandom);
    }

    /**
     * Visszaad egy random számot a megadott tartományban. Ha a fixRandom metódust
     * meghívtuk, akkor mindig ugyanazt a számot adja vissza.
     * @param minIncluded a minimum érték, amit vissza akarunk adni (inclusive)
     * @param maxExcluded a maximum érték, amit vissza akarunk adni (exclusive)
     * @return a random szám
     */
    public static int random(int minIncluded, int maxExcluded) {
        if (active) {
            return fixRandom;
        } else {
            return (int) (Math.random() * (maxExcluded - minIncluded)) + minIncluded;
        }
    }

    /**
     * Visszaad egy random számot 0-tól a megadott maximumig. Ha a fixRandom
     * metódust meghívtuk, akkor mindig ugyanazt a számot adja vissza.
     * @param maxExcluded a maximum érték, amit vissza akarunk kapni (exclusive)
     */
    public static int random(int maxExcluded) {
        return random(0, maxExcluded);
    }

    /**
     * A fixRandom metódus visszavonása. Ekkor a random metódus már nem a fix
     * random számot adja vissza, hanem a normál random számot.
     */
    public static void resetFix() {
        active = false;
        System.out.println("Random number is no longer fixed.");
    }
}
