package listeners;

/**
 * Parancsokra és egyéb eseményekre vonatközó értesítéseket kezelő interfész 
 */
public interface JobListener {

    /**
     * Sikeres esemény után meghívandó függvény.
     * @param msg üzenet
     */
    public void jobSuccessfull(String msg);

    /**
     * Sikertelen esemény után meghívandó függvény.
     * @param msg üzenet
     */
    public void jobFailed(String msg);
}
