package ex_04_Operators;

public class Lab042_Interview_concat_Plus {
    public static void main(String[] args) {
        String first_name = "Nikhil";
        String last_name = "Sonawane";

        int a = 10;
        int b = 10;

       System.out.println(first_name + last_name + a + b);

       System.out.println(a + b + first_name + last_name);

       System.out.println(first_name + last_name + (a + b));
       System.out.println(a+b+ first_name + last_name +(a+b) +a+b + (first_name + last_name));


        //         // BODMAS - Bracket of Div, mul, add, sub                   BODMAS is an acronym that helps us remember the correct order of mathematical operations.

        // First of all, whenever it sees strings,                                     B: Brackets
        // it will do concatenation, and for the next                                  O: Orders (powers, indices, roots, or exponents)
        // one also it will also do concatenation.                                     D: Division
        // But when it sees integers first, it will do mathematical operation.         M: Multiplication
        // The second time it will see strings,                                        A: Addition
        // then it will do concatenation as well.                                      S: Subtraction



        // 20NikhilSonawane

    }
}
