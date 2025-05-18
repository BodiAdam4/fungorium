package listeners;

/**
 * Tektonok kiválasztásáért felelős listener
 */
public interface SelectionListener {
    /**
     * Tecton kiválasztásakor meghívandó függvény
     * @param selectedCount hanyadik kiválasztás száma
     */
    public void OnSelection(int selectedCount);
}
