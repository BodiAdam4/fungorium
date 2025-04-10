package Userinterface;

import java.util.HashMap;

public abstract class Command {

    public String getOption(HashMap<String, String> options, String toFind, String defValue) {
        return options.containsKey(toFind) ? options.get(toFind) : defValue;
    }

    public abstract void execute(String[] args, HashMap<String, String> options);

    public String toString() {
        return "This is a command class";
    }
}
