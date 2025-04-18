package Model;
import java.util.ArrayList;
import java.util.List;

/**
 * A gombaspórák tárolására és kezelésére alkalmas osztály.
 */
public class SporeContainer 
{
    /* Privát attribútumok */
    private List<Spore> spores;


    /**
     * Paraméter nélküli kontstruktor. (A String name paraméter csupán tesztelés céljából van bent)
     */
    public SporeContainer() {
        spores = new ArrayList<>();
    }


    /**
     * Spóra hozzáadása a listához.
     * @param spore A hozzáadandó spóra
     * @return A hozzáadás sikerességét tartalmazó logikai érték
     */
    public boolean addSpores(Spore spore) {

        spores.add(spore);

        return true;
    }


    /**
     * Spóra kivétele a listából. Az utolsó spórát törli a listából és visszaadja
     * @param count A spórák száma, amiket ki szeretnénk venni.
     * @return A kivett spórák.
     */
    public Spore[] popSpores(int count) {

        Spore[] selected = new Spore[count];

        for (int i = 0; i<count; i++) {
            selected[i] = spores.get(i);
        }

        for (int i = 0; i<count; i++) {
            spores.remove(selected[i]);
        }

        return selected;
    }


    /**
     * Spórák kivétele a listából gombaazonosító szerint.
     * @param id A gombaazonosító.
     * @param count A spórák száma, amiket ki szeretnénk venni.
     * @return A kivett spórák.
     */
    public Spore[] popSpores(int id, int count) {

        Spore[] selected = new Spore[count];
        int idx = 0;
        for (int i = 0; i<count; i++) {
            if (spores.get(i).getSporeId() == id) {
                selected[idx++] = spores.get(i);
            }
        }

        for (int i = 0; i<count; i++) {
            spores.remove(selected[i]);
        }

        return selected;
    }


    /**
     * A listában lévő spórák számának lekérdezése
     * @return A spórák száma.
     */
    public int getSporeCount() {
        return spores.size();
    }


    /**
     * Adott azonosítóval rendelkező gombaspórák számának lekérdezése.
     * @param id A gombaazonosító.
     * @return A gombaazonosítóval rendelkező spórák száma.
     */
    public int getSporeCount(int id) {
        
        int count = 0;

        for (int i = 0; i<spores.size(); i++) {
            if (spores.get(i).getSporeId() == id) {
                count++;
            }
        }
        
        return count;
    }
}
