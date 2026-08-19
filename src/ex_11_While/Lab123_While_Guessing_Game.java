package ex_11_While;

import java.util.Random;
import java.util.Scanner;

public class Lab123_While_Guessing_Game {
    public static void main(String[] args) {

        // Guess a number from 1 to 100
        // n = 56
        // 90 , number < , 50, numner > , 55,  - 56 - times= 9

        Random random  = new Random();
        int numberToGuess = random.nextInt(100);
//        System.out.println(numberToGuess);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number");
        int guess;
        int attempts = 0;

        while(true){

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter interger number only!");
                scanner.next(); // Consume invalid token
                continue;
            }



            guess = scanner.nextInt();
            attempts++;
            if(guess < numberToGuess){
                System.out.println("Too Low, Try again!!");
            }else if( guess > numberToGuess){
                System.out.println("Too High, Try again");
            }else{
                System.out.println("Correct! You have taken -> " +  attempts );
                break;
            }
        }

    }
}
