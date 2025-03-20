import java.io.BufferedInputStream;
import java.util.Scanner;

public class Skeleton {


    /** - Operation methods here: */


    /**
     * Prompts the user with a question and reads their input from the console.
     *
     * @param question The question to display to the user.
     * @return The user's input as a string.
     */
    public static String Ask(String question) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        System.out.println(question);
        return scanner.nextLine();

    }

    public static void main(String[] args) {

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
     * We create two tectons and the line connecting them.
     * We place as many spores on the tecton as the tester specified.
     * If this amount is 3 or more, we grow a mushroom body on the tecton.
     * If less, we indicate that there are not enough spores.
     */
    public static void test_BuildBody(){
        System.out.println("Test BuildBody");
        //TODO: Implement this method
    }


    /**
     * We create an infertile tecton with as many spores as the tester specifies.
     * The tecton then indicates that it cannot be used to grow a mushroom body.
     */
    public static void test_BuildBodyInfertileTecton(){
        System.out.println("Test BuildBodyInfertileTecton");
        //TODO: Implement this method
    }


    /**
     * We create a connection between the two tectons 
     * and then check whether the mushroom line has been created.
     */
    public static void test_BuildLine(){
        System.out.println("Test BuildLine");
        //TODO: Implement this method
    }


    /**
     * A mycelium already exists between two tectons.
     * We then want to grow a mycelium with a different mycelium ID between them.
     * This fails because only one type of mycelium can grow on tectons.
     */
    public static void test_BuildLineInOnlyLineTectons(){
        System.out.println("Test BuildLineInOnlyLineTectons");
        //TODO: Implement this method
    }


    /**
     * The given insect, standing on the tecton, 
     * tries to cut a fungal line that connects two tectons.
     */
    public static void test_CutLine(){
        System.out.println("Test CutLine");
        //TODO: Implement this method
    }


    /**
     * The insect consumes a spore on the tecton where it is currently located.
     */
    public static void test_EatSpore(){
        System.out.println("Test EatSpore");
        //TODO: Implement this method
    }


    /**
     * The insect consumes a spore, causing it to lose its cutting ability.
     */
    public static void test_EatExhaustingSpore(){
        System.out.println("Test EatExhaustingSpore");
        //TODO: Implement this method
    }


    /**
     * The insect will consume a spore, which will cause 
     * the insect to be unable to move.
     */
    public static void test_EatFreezingSpore(){
        System.out.println("Test EatFreezingSpore");
        //TODO: Implement this method
    }


    /**
     * The insect consumes a spore, which causes the insect to move more slowly.
     */
    public static void test_EatSlowingSpore(){
        System.out.println("Test EatSlowingSpore");
        //TODO: Implement this method
    }


    /**
     * The insect will consume a spore, which will allow the insect to move faster.
     */
    public static void test_EatFastSpore(){
        System.out.println("Test EatFastSpore");
        //TODO: Implement this method
    }


    /**
     * The insect moves from one tecton to another through the line.
     */
    public static void test_MoveInsect(){
        System.out.println("Test MoveInsect");
        //TODO: Implement this method
    }


    /**
     * The mushroom body throws spores from the tecton it is standing 
     * on to the other tecton that is adjacent to it. At this point, 
     * the number of spores the mushroom body can throw will decrease, 
     * while the number of spores found on the adjacent tecton will increase.
     */
    public static void test_ThrowSpores(){
        System.out.println("Test ThrowSpores");
        //TODO: Implement this method
    }


    /**
     * The tecton is broken in two. This creates two different, 
     * adjacent tectons. The mushroom body on the tecton then 
     * remains on one tecton, but the lines that touched the broken tecton are lost.
     */
    public static void test_TectonBreak(){
        System.out.println("Test TectonBreak");
        //TODO: Implement this method
    }


    /**
     * First, we check the first tecton of the starting mushroom line. 
     * If there is no mushroom body on this, we check whether this 
     * line is the last in the row. If so, we check the tecton of the other one. 
     * If not, we move on and repeat this process until we reach the last line.
     */
    public static void test_ChackBody(){
        System.out.println("Test ChackBody");
        //TODO: Implement this method
    }


    /**
     * The mushroom throws spores five times onto the neighboring tecton, 
     * thereby running out of spores and dying.
     */
    public static void test_MushroomDieThrowingSpores(){
        System.out.println("Test MushroomDieThrowingSpores");
        //TODO: Implement this method
    }
}
