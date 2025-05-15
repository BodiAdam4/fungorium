package model;

import java.util.HashMap;

public class Timer {
    /* - Privát attribútumok*/
    private static HashMap<Schedule, Integer> oneTimeSchedules = new HashMap<>();;
    private static HashMap<Schedule, Integer[]> repeatSchedules = new HashMap<>();;

    /* - Publikus attribútumok*/


    /* - Konstruktorok*/


    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/

    public static void ResetTimer() {
        oneTimeSchedules.clear();
        repeatSchedules.clear();
    }

    /**
     * Adder függvény, mely felvesz egy időzítést az "egyszer használatos" időzítések közé.
    */
    public static void addOneTimeSchedule(Schedule schedule, int time) {
        oneTimeSchedules.put(schedule, time);
    }


    /**
     * Adder függvény, mely felvesz egy időzítést az ismétlődő időzítések közé.
    */
    public static void addRepeatSchedule(Schedule schedule, int time) {
        repeatSchedules.put(schedule, new Integer[]{time, time});
    }


    /**
     * A metódus végigiterál a oneTimeSchedules, és a repeatSchedules bejegyzésein, 
     * és mindegyik “idő” (,azaz int) -értékéből levon egyet. Ha valamelyik listabejegyzés közül az egyik eléri a nullát, 
     * akkor annak lefuttatja a Schedule interfész felülírt onTime függvényét, ha ez a bejegyzés 
     * az onTimeSchedules listában volt megtalálható, akkor eltávolítja ezt a listából.
    */
    public static void forwardTime() {
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
    }
}
