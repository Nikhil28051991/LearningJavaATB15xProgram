package ex_06_Ternary_Operator;

public class Lab067_To_Three_Max3 {
    public static void main(String[] args) {
        //NTSBI
        int n1 = 2;
        int n2 = 9;
        int n3 = -11;

        System.out.println("MAX OUT OF THREE");

        int max = n1 > n2 ? n1 : n2;               // This method is recommended
        max = max > n3 ? max : n3;

        System.out.println("Maximum = " + max);    // This method is recommended



        // int max = n1 > n2                       // This method is also Correct for Nested Ternary Operator
       //         ? (n1 > n3 ? n1 : n3)
       //         : (n2 > n3 ? n2 : n3);          // int max = n1 > n2 ? (n1 > n3 ? n1 : n3) : (n2 > n3 ? n2 : n3);

       // System.out.println("Maximum = " + max);   // This method is also Correct for Nested Ternary Operator



       // int max = n2 > n3 ? n2 : n3;
       // max = n1 > max ? n1 : max;

       // System.out.println("Maximum = " + max);


    }
}
