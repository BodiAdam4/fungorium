package listeners;
/**
 * - Az eredménylistáért felelős listener
 * A listener begyűjti a játékosok ereményeit egy String-típusú objektumba.
 * Ezt követően a String-ből ki lesz bonta az eredménylista (dicsőségtábla)
*/
public interface ResultListener {

    /* - Az eredmények átadása String formában*/
    public void showResults(String data);
}
