import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



/**
 * A Logger osztály a Skeleton program futása során bekövetkező metódushívások követésére van.
 * Az osztály különböző függvényeit a követett metódusban adott helyeken kell meghívni.
 */
class Logger {
    private static Map<Object, String> objectList = new HashMap();
    private static int indentation = 0;
    

    /**
     * Objektumok nevének lekérdezése.
     * @param obj Objektum aminek a nevét szeretnénk.
     * @return A keresett objektum neve.
     */
    public static String GetObjectName(Object obj) {
        if (!objectList.containsKey(obj)) {
            System.err.println("Nincsen az objektum a listában.");
            return "null";
        }

        return objectList.get(obj);
    }

    /**
     * Konstruktorok elején meghívandó függvény. Ezzel lehet az objektumot regisztrálni a konstruktorból.
     * FONTOS: Minden olyan objektum konstruktora elején jelenjen meg, amiket szertnénk később látni a konzolon és 
     * nincs a konstruktorának paramétere.
     * @param classObject Az objektum aminek a konstruktorában vagyunk. (Általában a {@code this} kulcsszóval érhető el)
     * @param className Az objektum neve, ahogyan az változóban el van mentve.
     */
    public static void Constructor(Object classObject, String objectName) {
        objectList.put(classObject, objectName);
        //TODO:Javítani
        FunctionStart(classObject, "Constructor");
    }

    
    /**
     * Paraméteres konstruktorok elején meghívandó függvény. Ezzel lehet az objektumot regisztrálni a konstruktorból.
     * Ha a konstruktor paramétere csak az objektumnév akkor nem kell megadni azt paraméterként.
     * FONTOS: Minden olyan objektum paraméteres konstruktora elején jelenjen meg, amiket szertnénk később látni a konzolon.
     * @param classObject Az objektum aminek a konstruktorában vagyunk. (Általában a {@code this} kulcsszóval érhető el)
     * @param className Az objektum neve, ahogyan az változóban el van mentve.
     */
    public static void Constructor(Object classObject, String objectName, Object[] parameters) {
        objectList.put(classObject, objectName);
        //TODO:Javítani
        FunctionStart(classObject, "Constructor", parameters);
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

            String param = "";

            if (parameters[i] == null) {
                param = null;
                paramLength+=4;
            }
            else {
                param = parameters[i].toString();
                paramLength+=param.length();
            }


            if (objectList.containsKey(parameters[i])) {
                param = objectList.get(parameters[i]);
            }

            System.out.print(param);

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


    /**
     * Konzolos bemenethez lehet kérdést feltenni, majd a válasszal visszatér.
     * @param msg A kérdés, amit feltesz a függvény.
     * @return A kérdésre adott válasz.
     */
    public static String Ask(String msg) {
        //indentation? -Drenyó
        if (firstMsg) {
            System.out.println("");
        }
        System.out.print(msg+":");
        firstMsg = false;

        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        return scanner.nextLine();
    }

    /**
     * Ha egy objektum megsemmisül akkor ezt a függvényt kell meghívni, hogy
     * tudassa a logger osztállyal, hogy ő többet nem lesz.
     * @param obj A töröl objektum
     */
    public static void DestroyObject(Object obj) {
        indentation = 0;
        Log(objectList.get(obj)+" destroyed");
    }
}