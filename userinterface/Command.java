package userinterface;

import java.util.HashMap;

/**
 * A parancsot megvalósító osztály.
 */
public abstract class Command {

    /**
     * Opciók kiválasztása.
     * @param options Az opciókat tartalmazó HashMap
     * @param toFind A keresett opció
     * @param defValue Alapértelmezett érték
     * @return Ha létezik, akkor a keresett opció, egyébként az alapértelmezett érték
     */
    public String getOption(HashMap<String, String> options, String toFind, String defValue) {
        return options.containsKey(toFind) ? options.get(toFind) : defValue;
    }


    /**
     * Parancs futtatása
     * @param args A parancs szövege
     * @param options A parancshoz tartozó opciók
     */
    public abstract void execute(String[] args, HashMap<String, String> options);

    public String toString() {
        return "This is a command class";
    }
}
