package model;

import java.util.List;

import listeners.ObjectChangeListener.ObjectChangeEvent;

/**
 * A Tecton osztály leszármazottja, mely rendelkezik egy olyan különleges hatással, ami miatt csak egyfajta gombafonal tud kapcsolódni hozzá.
 */
public class TectonOnlyLine extends Tecton{

    //Konstruktor
    public TectonOnlyLine(){
        super();
    }


    /**
     * Gombafonál csatlakoztatása a tektonhoz, viszont ellenőrzi, 
     * hogy már létezik kapcsolata bármilyen gombafonallal.
     * Ha igen akkor ellenőrzi, hogy az új kapcsolódó fonal 
     * megegyező azonosítóval rendelkezik, és csak akkor engedélyezi.
     * @param line A hozzáadandó gombafonal
     * @return Visszatér egy boolean-el, hogy sikerült-e hozzáadni a fonalat.
     */
    public boolean addLine(Line line){

        if ((!this.connections.isEmpty() && this.connections.get(0).getId() == line.getId()) || this.connections.isEmpty()) {
            connections.add(line);
            return true;
        }
        else {
            System.out.println("You can't add lines with different id!");
            return false;
        }
    }


    /**
     * A tekton két tektonra törése
     */
    public void breakTecton(){
        List<Tecton> ng = getNeighbors();
        TectonOnlyLine t3 = new TectonOnlyLine();
        t3.setNeighbors(this);
        this.setNeighbors(t3);
        changeListener.tectonChanged(ObjectChangeEvent.OBJECT_ADDED, t3);
        for (Tecton t : ng) {
            t3.setNeighbors(t);
            t.setNeighbors(t3);
        }
        int size = connections.size();
        for (int i = 0; i < size; i++) {
            connections.get(0).Destroy();
        }
    }
}
