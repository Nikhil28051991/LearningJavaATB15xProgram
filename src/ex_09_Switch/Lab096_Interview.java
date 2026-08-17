package ex_09_Switch;

public class Lab096_Interview {
    public static void main(String[] args) {

        char code = 'C';
        switch (code){
            default:                    // default case can be placed at the beginning, middle, or end of a switch
                System.out.println("Hellooooooo");
                // break
            case 'A':
                System.out.println("65");   // // The output is Hellooooooo and 65  because break is missing
                break;
            case 'B':
                System.out.println("66");
                break;
        }

    }
}
