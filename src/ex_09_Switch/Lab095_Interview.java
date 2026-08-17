package ex_09_Switch;

public class Lab095_Interview {
    public static void main(String[] args) {
        int a = 11;
        switch (-1){     // (-1) will match will case -1: directly    int a = 11 is just to confuse
            default:
                System.out.println("Default");
                break;
            case -1:
                System.out.println("10");
                break;
            case 9:
                System.out.println("9");
                break;
        }

    }
}
