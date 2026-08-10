package ex_05_TypeCasting;

public class Lab61_Extra_Example_Char {
    public static void main(String[] args) {

        char ch = 'A';
        int ascii = ch; // Widening

        int num = 66; // Narrow

        // char letter = num;  // Direct narrowing is not possible

        char letter = (char) num;
    }
}
