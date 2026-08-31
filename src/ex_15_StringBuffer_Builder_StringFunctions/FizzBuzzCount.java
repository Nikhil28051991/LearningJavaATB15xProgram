package ex_15_StringBuffer_Builder_StringFunctions;

public class FizzBuzzCount {
    public static void main(String[] args) {

        int fizzCount = 0;
        int buzzCount = 0;
        int fizzBuzzCount = 0;
        int normalCount = 0;

        for (int i = 1; i <= 100; i++) {

            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
                fizzBuzzCount++;

            } else if (i % 3 == 0) {
                System.out.println("Fizz");
                fizzCount++;

            } else if (i % 5 == 0) {
                System.out.println("Buzz");
                buzzCount++;

            } else {
                System.out.println(i);
                normalCount++;
            }
        }

        System.out.println("--------------------");
        System.out.println("Fizz Count     : " + fizzCount);
        System.out.println("Buzz Count     : " + buzzCount);
        System.out.println("FizzBuzz Count : " + fizzBuzzCount);
        System.out.println("Normal Count   : " + normalCount);

    }
}
