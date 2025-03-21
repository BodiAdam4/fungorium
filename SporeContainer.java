import java.util.ArrayList;
import java.util.List;

public class SporeContainer 
{
    private List<Spore> spores;

    public SporeContainer(String name) {
        Logger.Constructor(this, name);
        spores = new ArrayList<>();
        Logger.FunctionEnd();
    }

    public boolean addSpores(Spore spore, Mushroom sender) {
        Logger.FunctionStart(this, "addSpores", new Object[]{spore, sender});

        spores.add(spore);

        Logger.FunctionEnd();
        return true;
    }

    public Spore[] popSpores(int count) {
        Logger.FunctionStart(this, "popSpores", new Object[]{count});

        Spore[] selected = new Spore[count];

        for (int i = 0; i<count; i++) {
            selected[i] = spores.get(i);
        }

        for (int i = 0; i<count; i++) {
            spores.remove(selected[i]);
        }

        Logger.FunctionEnd();
        return selected;
    }

    public Spore[] popSpores(int id, int count) {
        Logger.FunctionStart(this, "popSpores", new Object[]{count});

        Spore[] selected = new Spore[count];
        int idx = 0;
        for (int i = 0; i<count; i++) {
            if (spores.get(i).id == id) {
                selected[idx++] = spores.get(i);
            }
        }

        for (int i = 0; i<count; i++) {
            spores.remove(selected[i]);
        }

        Logger.FunctionEnd();
        return selected;
    }

    public int getSporeCount() {
        Logger.FunctionStart(this, "getSporeCount");
        Logger.FunctionEnd();
        return spores.size();
    }

    public int getSporeCount(int id) {
        Logger.FunctionStart(this, "getSporeCount", new Object[]{id});
        
        int count = 0;

        for (int i = 0; i<count; i++) {
            if (spores.get(i).id == id) {
                count++;
            }
        }
        
        Logger.FunctionEnd();
        return count;
    }
}
