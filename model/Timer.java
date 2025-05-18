package model;

import java.util.HashMap;


/**
 * Időzíttésért felelős osztály
 */
public class Timer {
    /* - Privát attribútumok*/
    private static HashMap<Schedule, Integer> oneTimeSchedules = new HashMap<>();
    private static HashMap<Schedule, Integer[]> repeatSchedules = new HashMap<>();

    private static HashMap<Schedule, Integer> oneBuffer = new HashMap<>();
    private static HashMap<Schedule, Integer[]> repeatBuffer = new HashMap<>();

    private static boolean forwarding = false;

    /* - Publikus attribútumok*/


    /* - Konstruktorok*/


    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/

    public static void ResetTimer() {
        oneTimeSchedules.clear();
        repeatSchedules.clear();

        oneBuffer.clear();
        repeatBuffer.clear();
    }

    /**
     * Adder függvény, mely felvesz egy időzítést az "egyszer használatos" időzítések közé.
    */
    public static void addOneTimeSchedule(Schedule schedule, int time) {
        if (forwarding)
            oneBuffer.put(schedule, time);
        else
            oneTimeSchedules.put(schedule, time);
    }


    /**
     * Adder függvény, mely felvesz egy időzítést az ismétlődő időzítések közé.
    */
    public static void addRepeatSchedule(Schedule schedule, int time) {
        if (forwarding)
            repeatBuffer.put(schedule, new Integer[]{time, time});
        else
            repeatSchedules.put(schedule, new Integer[]{time, time});
    }


    /**
     * A metódus végigiterál a oneTimeSchedules, és a repeatSchedules bejegyzésein, 
     * és mindegyik “idő” (,azaz int) -értékéből levon egyet. Ha valamelyik listabejegyzés közül az egyik eléri a nullát, 
     * akkor annak lefuttatja a Schedule interfész felülírt onTime függvényét, ha ez a bejegyzés 
     * az onTimeSchedules listában volt megtalálható, akkor eltávolítja ezt a listából.
    */
    public static void forwardTime() {
        forwarding = true;

        HashMap<Schedule, Integer> updated = new HashMap<>();

        for (Schedule schedule : oneTimeSchedules.keySet()) {
            int time = oneTimeSchedules.get(schedule)-1;
            if (time == 0) {
                schedule.onTime();
            } else {
                updated.put(schedule, time);
            }
        }

        oneTimeSchedules = updated;

        for (Schedule schedule : oneBuffer.keySet()) {
            oneTimeSchedules.put(schedule, oneBuffer.get(schedule));
        }
        oneBuffer.clear();
        
        HashMap<Schedule, Integer[]> updatedRepeat = new HashMap<>();

        for (Schedule schedule : repeatSchedules.keySet()) {
            int time = repeatSchedules.get(schedule)[0]-1;
            int defaultTime = repeatSchedules.get(schedule)[1];

            if (time == 0) {
                schedule.onTime();
                updatedRepeat.put(schedule, new Integer[]{defaultTime, defaultTime});
            } else {
                updatedRepeat.put(schedule, new Integer[]{time, defaultTime});
            }
        }

        repeatSchedules = updatedRepeat;

        for (Schedule schedule : repeatBuffer.keySet()) {
            repeatSchedules.put(schedule, repeatBuffer.get(schedule));
        }
        repeatBuffer.clear();
        forwarding = false;
    }
}
