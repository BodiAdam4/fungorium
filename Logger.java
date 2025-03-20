import java.util.HashMap;
import java.util.Map;



/**
 * A Logger osztály a Skeleton program futása során bekövetkező metódushívások követésére van.
 * Az osztály különböző függvényeit a követett metódusban adott helyeken kell meghívni.
 */
class Logger {
    private static Map<Object, String> objectList = new HashMap();
    private static int indentation = 0;


    /**
     * Konstruktorok elején meghívandó függvény. Ezzel lehet az objektumot regisztrálni a konstruktorból.
     * FONTOS: Minden olyan objektum konstruktora elején jelenjen meg, amiket szertnénk később látni a konzolon.
     * @param classObject Az objektum aminek a konstruktorában vagyunk. (Általában a {@code this} kulcsszóval érhető el)
     * @param className Az objektum neve, ahogyan az változóban el van mentve.
     */
    public static void Constructor(Object classObject, String objectName) {
        objectList.put(classObject, objectName);
        FunctionStart(classObject, objectName);
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

        if (firstMsg) {
            System.out.println("");
        }
        firstMsg = true;

        for (int i = 0; i<indentation; i++) {
            System.out.print("\t");
        }
        

        System.out.print("->"+objectList.get(functionContainer)+":"+functioName+"():");

        
        funcLength = functioName.length()+3;
        objLength = objectList.get(functionContainer).length();
        
        indentation++;
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
        
        if (firstMsg) {
            System.out.println("");
        }

        firstMsg = true;
        
        for (int i = 0; i<indentation; i++) {
            System.out.print("\t");
        }

        int paramLength = 0;
        System.out.print("->"+objectList.get(functionContainer)+":"+functioName+"(");

        for(int i = 0; i<parameters.length; i++) {

            String param = parameters[i].toString();

            if (objectList.containsKey(parameters[i])) {
                param = objectList.get(parameters[i]);
            }

            System.out.print(param);
            paramLength+=param.length();

            if(i<parameters.length-1) {
                System.out.print(", ");
                paramLength+=2;
            }
        }

        System.out.print("):");
        
        funcLength = functioName.length()+3+paramLength;
        objLength = objectList.get(functionContainer).length();
        
        indentation++;
    }

    /**
     * A függvények végén ezzel lehet jelezni, hogy a függvény véget ért.
     * FONTOS: Legyen ott minden olyan függvény végén, ahol meghívtuk az elején a {@code FunctionStart()}
     * vagy a {@code Constructor()}
     * metódust, vagy különben elromlanak a behúzások.
     */
    public static void FunctionEnd() {
        indentation--;
    }

    private static boolean firstMsg = true;
    private static int objLength;
    private static int funcLength;


    /**
     * Függvényen belüli kiírásokra alkalmas függvény. Ha egy függvényt követünk a {@code Logger} osztállyal
     * és abban ki szeretnénk iratni konzolra akkor az ezzel lehet megtenni.
     * @param msg A kiíratandó szöveg
     */
    public static void Log(String msg) {
        
        if (firstMsg) {
            System.out.println(msg);
            firstMsg = false;
        }
        else {
            for (int i = 0; i<indentation-1; i++) {
                System.out.print("\t");
            }
            for (int i = 0; i<objLength+funcLength+3; i++) {
                System.out.print(" ");
            }
            System.out.println(msg);
        }
    }
}