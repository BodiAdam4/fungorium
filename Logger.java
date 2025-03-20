import java.util.HashMap;
import java.util.Map;

class Logger {
    private static Map<Object, String> objectList = new HashMap();
    private static int indentation = 0;

    
    /**
     * Objektum létrehozásánál, ezzel a függvénnyel lehet az új objektumot regisztrálni az osztálynál.
     * Fontos, mivel az itt regisztrált objektumokat lehet csak megjeleníteni a hívások során.
     * @param name Az objektum neve. (A változó neve)
     * @param obj Az objektum. (A változó)
     */
    public static void AddObject(String name, Object obj) {
        objectList.put(obj, name);
    }

    /**
     * A függvény elején meghívandó. Ezzel lesz megjelenítve a függvényhívás.
     * Itt nem lehet megadni paramétereket, szóval csak paraméter nélküli függvényekre hívható meg.
     * FONTOS: A függvényneveket zárójelek nélkül kell megadni ().
     * @param functionContainer A függvényt tartalmazó objektum. (Általában ehhez elég egy {@code this})
     * @param functioName A függvény neve szövegesen.
     */
    public static void FunctionStart(Object functionContainer, String functioName) {
        if (!objectList.containsKey(functionContainer)) {
            System.err.println("Az objektum nincs regisztrálva a Logger osztályban");
        }

        for (int i = 0; i<indentation; i++) {
            System.out.print("\t");
        }

        System.out.print("->"+objectList.get(functionContainer)+":"+functioName+"():");
    }

    /**
     * A függvény elején meghívandó. Ezzel lesz megjelenítve a függvényhívás.
     * Itt meg lehet megadni paramétereket, szóval ha paraméteres függvényről van szó, ezt kell használni.
     * FONTOS: A függvényneveket zárójelek nélkül kell megadni ().
     * @param functionContainer A függvényt tartalmazó objektum. (Általában ehhez elég egy {@code this})
     * @param functioName A függvény neve szövegesen.
     * @param parameters A paraméterek objektumai. (Az objektum alapján megtalálja az objektum regisztrált nevét)
     */
    public static void FunctionStart(Object functionContainer, String functioName, Object[] parameters) {
        if (!objectList.containsKey(functionContainer)) {
            System.err.println("Az objektum nincs regisztrálva a Logger osztályban");
        }
        
        for (int i = 0; i<indentation; i++) {
            System.out.print("\t");
        }

        System.out.print("->"+objectList.get(functionContainer)+":"+functioName+"(");

        for(int i = 0; i<parameters.length; i++) {

            String param = parameters[i].toString();

            if (objectList.containsKey(parameters[i])) {
                param = objectList.get(parameters[i]);
            }

            System.out.print(param);

            if(i<parameters.length-1) {
                System.out.print(", ");
            }
        }

        System.out.print("):");
    }

    /**
     * A függvények végén ezzel lehet jelezni, hogy a függvény véget ért.
     * FONTOS: Legyen ott minden olyan függvény végén, ahol meghívtuk az elején a {@code FunctionStart()}
     * metódust, vagy különben elromlanak a behúzások.
     */
    public static void FunctionEnd() {
        indentation--;
    }
}