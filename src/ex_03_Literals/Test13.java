package ex_03_Literals;

public class Test13 {
    public static void main(String[] args) {
        char carriage_return = '\r';

        System.out.println("Nikhil"+carriage_return+"Sonawane");

        System.out.println("Nikhil");
        System.out.println("\rSon");

        System.out.println("ABCD"+carriage_return+"12");

        System.out.print("ABCDE");
        System.out.print("\r12");


/*
=========================================================================================
Different Output of Carriage Return (\r)
=========================================================================================

+----------------------+-------------------+----------+--------------------------------------+
| Example              | Environment       | Output   | Explanation                          |
+----------------------+-------------------+----------+--------------------------------------+
| print("ABCDE");      | Notepad++ (CMD)  | 12DE     | Overwrites first 2 characters.       |
| print("\r12");       | IntelliJ IDEA    | 12       | Console redraws the line.            |
|                      | Eclipse          | ABCDE    | Shows both outputs on separate lines.|
|                      |                  | 12       |                                      |
|                      | Programiz        | 12       | Browser shows latest text only.      |
+----------------------+-------------------+----------+--------------------------------------+

+----------------------+-------------------+----------+--------------------------------------+
| Example              | Environment       | Output   | Explanation                          |
+----------------------+-------------------+----------+--------------------------------------+
| println("Nikhil" +   | Notepad++ (CMD)  | Sonil    | "Son" overwrites "Nik".              |
| '\r' + "Sonawane");  | IntelliJ IDEA    | Sonawane | Console redraws the line.            |
|                      | Eclipse          | Nikhil   | Shows both outputs on separate lines.|
|                      |                  | Sonawane |                                      |
|                      | Programiz        | Son      | Browser shows overwritten text.      |
+----------------------+-------------------+----------+--------------------------------------+

+----------------------+-------------------+----------+--------------------------------------+
| Example              | Environment       | Output   | Explanation                          |
+----------------------+-------------------+----------+--------------------------------------+
| println("Hello" +    | Notepad++ (CMD)  | Javalo   | "Java" overwrites "Hell".            |
| '\r' + "Java");      | IntelliJ IDEA    | Java     | Console redraws the line.            |
|                      | Eclipse          | Hello    | Shows both outputs on separate lines.|
|                      |                  | Java     |                                      |
|                      | Programiz        | Java     | Browser shows latest text only.      |
+----------------------+-------------------+----------+--------------------------------------+

Note:
• '\r' moves the cursor to the beginning of the current line.
• Output depends on the console implementation.
• Different IDEs/terminals may produce different results.
=========================================================================================
NOTE:
The behavior of '\r' (Carriage Return) is implementation-dependent.
Different IDEs, terminals, operating systems, and browser consoles may
produce different outputs for the same Java program.
=========================================================================================
 */

    }
}
