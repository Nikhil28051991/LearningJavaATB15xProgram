package ex_03_Literals;

public class Lab034_Char_Literals {
    public static void main(String[] args) {
        char c1 = 'A';
        // A to Z, a-z, !@#$%^&*()_+

        // char c2 = "A"; // Not possible because "" are used for String data infact for char only use single character is allowed
        // String is nothing but A Bunch of Characters

        char c2 = 'B';
        System.out.println(c2);

        char c3 = '@'; // Special Characters we can also store in char data type but only single character with single quote ''
        char c4 = '_';
        char c5 = '9';
        char c6 = '1';
        char c7 = '(';
        char c8 = ' '; // blank space is also character

        // Escape Sequence                // below data types we can not use other values or interchange with each other
        char new_line = '\n';            // i,e for new line we must use only this value /n  new_line = '\n' and for next data type also
        char tab_line = '\t';           // tab_line = '\t' use only \t
        char back_space = '\b';        // back_space = '\b' use only \b
        char carriage_return = '\r';  // carriage_return = '\r' use ony \r

        System.out.println("NikhilSonawane");
        System.out.println("Nikhil"+new_line+"Sonawane");
        System.out.println("Nikhil\nSonawane");
        System.out.println("Nikhil"+tab_line+"Sonawane");
        System.out.println("Nikhil"+back_space+"Sonawane");

        System.out.print("Nikhil"+carriage_return+"Sonawane");

        System.out.println( " ----- ");

        System.out.println("Hi, This is a First line"+new_line+"This is second line\n This is Third line");



        char c10 = 'A';    // Characters are actually an Integral Number
        //  // ASCII, (limited numbers) - A -> 65


        char ruppes = '₹';
        System.out.println(ruppes);


        char my_laugh_smily = '\u1f60'; // :)  // Smily characters not printed properly in IntelliJ IDEA
        System.out.println(my_laugh_smily);   // Smily characters not printed properly in IntelliJ IDEA

        char c11  = '\u1F60';




        int binary = 0b1010;
        int hex = 0xFF;
        long amount = 1_00_000L; // for this Instead of using comma , we can use _ underscore also it ignores it
        System.out.println(amount);

        // char A  = "10";  // Not allowed because two character
        char c = 'A';      // 65 The value is 65, but It will print only A
        System.out.println(c);


    }
}
