package listeners;
/**
 * - Az eredménylistáért felelős listener
 * A listener begyűjti a játékosok ereményeit egy String-típusú objektumba.
 * Ezt követően a String-ből ki lesz bonta az eredménylista (dicsőségtábla)
*/
public interface ResultListener {

    /**
     * Eredmény megjelenítése
     * @param data az eredmény szövege
     */
    public void showResults(String data);
}
