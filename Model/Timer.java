package Model;

import java.util.HashMap;

public class Timer {
    /* - Privát attribútumok*/
    private static HashMap<Schedule, Integer> oneTimeSchedules = new HashMap<>();;
    private static HashMap<Schedule, Integer[]> repeatSchedules = new HashMap<>();;

    /* - Publikus attribútumok*/

    
    /* - Konstruktorok*/


    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/
    public static void addOneTimeSchedule(Schedule schedule, int time) {
        oneTimeSchedules.put(schedule, time);
    }


    public static void addRepeatSchedule(Schedule schedule, int time) {
        repeatSchedules.put(schedule, new Integer[]{time, time});
    }

    public static void forwardTime() {
        for (Schedule schedule : oneTimeSchedules.keySet()) {
            int time = oneTimeSchedules.get(schedule);
            if (time == 0) {
                schedule.onTime();
                oneTimeSchedules.remove(schedule);
            } else {
                oneTimeSchedules.put(schedule, time - 1);
            }
        }

        for (Schedule schedule : repeatSchedules.keySet()) {
            int time = repeatSchedules.get(schedule)[0];
            int defaultTime = repeatSchedules.get(schedule)[1];

            if (time == 0) {
                schedule.onTime();
                repeatSchedules.put(schedule, new Integer[]{defaultTime, defaultTime});
            } else {
                repeatSchedules.put(schedule, new Integer[]{time - 1, defaultTime});
            }
        }
    }
}
