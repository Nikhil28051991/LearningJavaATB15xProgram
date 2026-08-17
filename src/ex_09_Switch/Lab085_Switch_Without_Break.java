package ex_09_Switch;

import java.util.Scanner;

public class Lab085_Switch_Without_Break {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the day num(1 to 7)");

        int day = sc.nextInt();
        switch (day) {
            case 1:
                System.out.println("Mon");
                break;
            case 2:
                System.out.println("Tue");
            case 3:
                System.out.println("Wed");
            case 4:
                System.out.println("Thur");
            case 5:
                System.out.println("Friday");
            case 6:
                System.out.println("Sat");
            case 7:
                System.out.println("Sun");
            default:
                System.out.println("Invalid, day number");

        }

    }
}
