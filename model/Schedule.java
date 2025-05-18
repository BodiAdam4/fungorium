package model;

/**
 * Időzítést segítő interfész.
 * Az onTime felülírásával valósulnak meg az események.
 */
public interface Schedule {
    public void onTime();
}
