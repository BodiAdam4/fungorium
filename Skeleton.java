import java.io.BufferedInputStream;
import java.util.Scanner;

public class Skeleton {


    /** - Operation methods here: */


    /**
     * Kérdést tesz fel a felhasználónak, és visszaadja a választ.
     * Konzolról olvas be egy sort, és visszaadja a választ.
     *
     * @param question The question to display to the user.
     * @return The user's input as a string.
     */
    public static String Ask(String question) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        System.out.println(question);
        return scanner.nextLine();

    }

    //Entry point of the program
    public static void main(String[] args) {

        //A Logger működését bemutató osztály
        Example example = new Example("example");
        example.Outside();
        
        Example exampleParam = new Example("example",2);

        //The given test cases
        System.out.println("Welcome to the Fungorium Test Program");
        System.out.println("Please select the test you want to run:");
        System.out.println("1) Test the 'BuildBody' method");
        System.out.println("2) Test the 'Build body on infertile tecton' method");
        System.out.println("3) Test the 'BuildLine' method");
        System.out.println("4) Test the 'Build line between two 'TectonOnlyLine' tectons' method");
        System.out.println("5) Test the 'CutLine' method");
        System.out.println("6) Test the 'EatSpore' method");
        System.out.println("7) Test the 'EatExhaustingSpore' method");
        System.out.println("8) Test the 'EatFreezingSpore' method");
        System.out.println("9) Test the 'EatSlowingSpore' method");
        System.out.println("10) Test the 'EatFastSpore' method");
        System.out.println("11) Test the 'MoveInsect' method");
        System.out.println("12) Test the 'ThrowSpores' method");
        System.out.println("13) Test the 'TectonBreak' method");
        System.out.println("14) Test the 'CheckBody' method");
        System.out.println("15) Test the 'Mushroom dies after throwing spores' method");

        
        //Ask the user which test to run
        String answer = Ask("Which test do you want to run?");

        //Choose the test to run
        if (answer.equals("1")) {
            test_BuildBody();
        } else if (answer.equals("2")) {
            test_BuildBodyInfertileTecton();
        } else if (answer.equals("3")) {
            test_BuildLine();
        } else if (answer.equals("4")) {
            test_BuildLineInOnlyLineTectons();
        } else if (answer.equals("5")) {
            test_CutLine();
        } else if (answer.equals("6")) {
            test_EatSpore();
        } else if (answer.equals("7")) {
            test_EatExhaustingSpore();
        } else if (answer.equals("8")) {
            test_EatFreezingSpore();
        } else if (answer.equals("9")) {
            test_EatSlowingSpore();
        } else if (answer.equals("10")) {
            test_EatFastSpore();
        } else if (answer.equals("11")) {
            test_MoveInsect();
        } else if (answer.equals("12")) {
            test_ThrowSpores();
        } else if (answer.equals("13")) {
            test_TectonBreak();
        } else if (answer.equals("14")) {
            test_ChackBody();
        } else if (answer.equals("15")) {
            test_MushroomDieThrowingSpores();
        } else {
            System.out.println("Invalid test number");
        }
    }

    /** - Here you can place the test methods */

    /**
     * Létrehozunk két tektont, és az őket összekötő fonalat. 
     * A tektonra annyi spórát helyezünk el, amennyit a tesztelő megadott. 
     * Ha ez a mennyiség 3, vagy annál több, akkor gombatestet növesztünk a tektonra. 
     * Ha kevesebb, akkor jelezzük, hogy nincs elég spóra.
     */
    public static void test_BuildBody(){
        System.out.println("Test BuildBody");
        //TODO: Implement this method
    }


    /**
     * Létrehozunk egy terméketlen tektont annyi spórával, amennyit a tesztelő megad. 
     * Ezután tekton jelzi, hogy nem lehet rá gombatestet növeszteni.
     */
    public static void test_BuildBodyInfertileTecton(){
        System.out.println("Test BuildBodyInfertileTecton");
        //TODO: Implement this method
    }


    /**
     * A két tekton között hozunk létre összeköttetést, 
     * majd leellenőrizzük, hogy létrejött-e a gombafonal.
     */
    public static void test_BuildLine(){
        System.out.println("Test BuildLine");
        //TODO: Implement this method
    }


    /**
     * Két tekton között létezik már gombafonal. Ezután egy másik 
     * gombaazonosítóval rendelkező gombafonalat akarunk közéjük növeszteni. 
     * Ez meghíusúl, mivel a tektononokon csak egyfajta gombafonal nőhet.
     */
    public static void test_BuildLineInOnlyLineTectons(){
        System.out.println("Test BuildLineInOnlyLineTectons");
        //TODO: Implement this method
    }


    /**
     * Az adott rovar a tektonon állva megpróbál elvágni 
     * egy gombafonalat, mely két tektont köt össze.
     */
    public static void test_CutLine(){
        System.out.println("Test CutLine");
        //TODO: Implement this method
    }


    /**
     * A rovar elfogyaszt egy spórát azon a tektonon, ahol éppen tartózkodik.
     */
    public static void test_EatSpore(){
        System.out.println("Test EatSpore");
        //TODO: Implement this method
    }


    /**
     * A rovar elfogyaszt egy spórát, aminek következtében elveszti a vágóképességét. 
     */
    public static void test_EatExhaustingSpore(){
        System.out.println("Test EatExhaustingSpore");
        //TODO: Implement this method
    }


    /**
     * A rovar elfogyaszt egy spórát, aminek következtében a rovar nem fog tudni mozogni.
     */
    public static void test_EatFreezingSpore(){
        System.out.println("Test EatFreezingSpore");
        //TODO: Implement this method
    }


    /**
     * A rovar elfogyaszt egy spórát, aminek következtében a rovar lassabban tud mozogni.
     */
    public static void test_EatSlowingSpore(){
        System.out.println("Test EatSlowingSpore");
        //TODO: Implement this method
    }


    /**
     * A rovar elfogyaszt egy spórát, aminek következtében a rovar gyorsabban fog tudni mozogni.
     */
    public static void test_EatFastSpore(){
        System.out.println("Test EatFastSpore");
        //TODO: Implement this method
    }


    /**
     * A rovar átmegy az egyik tektonról a másikra a fonalon keresztül.
     */
    public static void test_MoveInsect(){
        System.out.println("Test MoveInsect");
        //TODO: Implement this method
    }


    /**
     * A gombatest spórát dob arról a tektonról, ahol ő áll, 
     * a másik tektonra, amely szomszédos vele. 
     * Ekkor a gombatestnek az eldobható spóráinak száma csökkenni fog, 
     * a szomszédos tektonon megtalálható spórák száma növekszik.
     */
    public static void test_ThrowSpores(){
        System.out.println("Test ThrowSpores");
        //TODO: Implement this method
    }


    /**
     * A tektont kettétörjük. Ezáltal létrejön két különböző szomszédos tekton. 
     * A tektonon lévő gombatest ekkor fennmarad az egyik tektonon, a fonalak, 
     * melyek a kettétört tektont érintették, azonban elvesznek.
     */
    public static void test_TectonBreak(){
        System.out.println("Test TectonBreak");
        //TODO: Implement this method
    }


    /**
     * Először a kiinduló gombafonal első tektonját ellenőrizzük. 
     * Ha ezen nincs gombatest, akkor megnézzük, hogy ez a fonal-e az utolsó a sorban. 
     * Ha igen, akkor megnézzük a másik tektonját is. Ha nem, akkor továbbmegyünk és 
     * megismételjük ezt a folyamatot, amíg az utolsó fonálig nem érünk.
     */
    public static void test_ChackBody(){
        System.out.println("Test ChackBody");
        //TODO: Implement this method
    }


    /**
     * A gomba spórát dob ötször a szomszédos tektonra, ezáltal kifogy a gombaspórákból és meghal.
     */
    public static void test_MushroomDieThrowingSpores(){
        System.out.println("Test MushroomDieThrowingSpores");
        //TODO: Implement this method
    }
}