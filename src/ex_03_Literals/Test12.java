package ex_03_Literals;

public class Test12 {
    public static void main(String[] args) {
        char carriage_return = '\r';

        System.out.println("Nikhil"+carriage_return+"Sonawane");

        System.out.println("Nikhil");
        System.out.println("\rSon");

        System.out.println("ABCD"+carriage_return+"12");

        System.out.print("ABCDE");
        System.out.print("\r12");

        // For this program we see the different output
        // Environment	                                Output
        //Notepad++ (CMD Runner)	                    12 DE (as shown in your screenshot)
        //Programiz Online Compiler	                    12 (as shown in your screenshot)
        //IntelliJ IDEA Console	May display only        12 (depends on the IntelliJ version and console implementation)

        // Environment	                                Output
        //Notepad++ (CMD Runner)	                    Sonil (as shown in your screenshot)
        //IntelliJ IDEA Console	                        Son (from your previous IntelliJ output)
        //Programiz Online Compiler	May display only    Son (browser console implementation)



    }
}
