package graphics;


import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Color;

/**
 * Egy-egy játékos beállítását lehetővé tevő UI elem. Ezen helyezkedik el a név beviteli mezeje, 
 * illetve itt lehet kiválasztani a játékos színét és kasztját is.
*/
public class PlayerPanel extends JPanel {
    
    /* - Privát attribútumok*/
    private JTextField nameBox;         //A játékos nevének beviteléhez szükséges szövegdoboz.
    private Color color;                //A játékos színe, mely a későbbiekben segít megkülönböztetni a különböző objektumok tulajdonosait.
    private JRadioButton insectRadio;   //Egy rádiógomb, mellyel eldönthető, hogy a játékos rovarász vagy gombász szeretne lenni.



    /* - Konstruktor(ok)*/


    /* - Getter/Setter metódusok*/

    /* - Visszaadja a felhasználó által bevitt játékosnevet.*/
    public String getName() {
        return nameBox.getText();
    }


    /* - Visszaadja a játékos által kiválasztott színt*/
    public Color getColor(){
        return color;
        //TODO: Lehetséges, hogy a színt nem így kellene visszaadni.
    }


    /* - Visszaad egy logikai változót, ami megmondja, hogy a játékos rovarász vagy gombász kasztot választotta.*/
    public boolean isInsect(){
        return insectRadio.isSelected();
    }


    /* - Egyéb metódusok*/

}
