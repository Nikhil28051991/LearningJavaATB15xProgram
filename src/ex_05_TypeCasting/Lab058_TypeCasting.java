package ex_05_TypeCasting;

public class Lab058_TypeCasting {
    public static void main(String[] args) {

        byte b = 10;
        int a = b; // widening ->  implcit
        int a1 = (int)b; // widening ->  explicit
        System.out.println(a);

        System.out.println(a1);
        System.out.println(b);
    }
}
