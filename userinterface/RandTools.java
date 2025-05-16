package userinterface;

import java.util.ArrayList;
import java.util.List;

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
     * Véletlenszerű különböző értékű számpárok előállítása
     * A számoknak páronként kell különbözniük
     * @param minIncluded a minimum érték, amit vissza akarunk adni (inclusive)
     * @param maxExcluded a maximum érték, amit vissza akarunk adni (exclusive)
     * @param count hány darab számpárt akaruk
     * @return egy lista a számokról. A számpárok [0, 1], [2, 3] módban vannak párban
     */
    public static List<Integer> randomPairs(int minIncluded, int maxExcluded, int count) {
        List<Integer> res = new ArrayList<Integer>();

        res.add(random(minIncluded, maxExcluded));
        res.add(random(minIncluded, maxExcluded));
        
        for(int i = 1; i < count; i++) {
            Integer i1 = random(minIncluded, maxExcluded);
            Integer i2 = random(minIncluded, maxExcluded);
            boolean isNew = true;
            for(int k = 0; k < i; k++){
                if(res.get(k * 2) == i1 && res.get((k * 2) + 1) == i2)
                    isNew = false;
            }
            if(isNew) {
                res.add(i1);
                res.add(i2);
            }
            else
                i--;
        }

        return res;
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
