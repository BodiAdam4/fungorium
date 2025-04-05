package Controller;

import java.util.HashMap;

public class Timer {
    /* - Privát attribútumok*/
    private HashMap<Schedule, Integer> oneTimeSchedules;
    private HashMap<Schedule, Integer> repeatSchedules;
    /* - Publikus attribútumok*/

    /* - Konstruktorok*/

    //Konstruktor
    public Timer() {
        this.oneTimeSchedules = new HashMap<>();
        this.repeatSchedules = new HashMap<>();
    }


    /* - Getter/Setter metódusok*/


    /* - Egyéb metódusok*/
    public void addOneTimeSchedule(Schedule schedule, int time) {
        oneTimeSchedules.put(schedule, time);
    }


    public void addRepeatSchedule(Schedule schedule, int time) {
        repeatSchedules.put(schedule, time);
    }

    public void forwardTime(int time) {
    }
}
