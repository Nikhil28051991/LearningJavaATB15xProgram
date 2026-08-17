package ex_09_Switch;

public class Lab089_Interview {
    public static void main(String[] args) {
        char ch = 'A';
        switch (ch){
            case 65:
                System.out.println("Match ASCII"); // It will give this output Match ASCII
                break;                             // Char is also Imposter act as an Integer
            default:
                System.out.println("No Match");
        }
    }
}
