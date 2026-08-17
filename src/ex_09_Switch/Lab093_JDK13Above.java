package ex_09_Switch;

public class Lab093_JDK13Above {
    public static void main(String[] args) {
        // in JDK > 13
        int itemCode  = 002;
        switch (itemCode){
            case 001 -> System.out.println("001"); // above JDK13 break is included in -> arrow no need to type break this is new syntax
            case 002 -> System.out.println("002"); // -> this new syntax break is included in this
            case 003 -> System.out.println("003");
            default -> System.out.println("Default");
        }

    }
}
