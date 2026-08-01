package ex_02_Java_Basics;

public class Lab017_Variables {
    public static void main(String[] args) {
        //I want to store the age of person

        //byte, short, int/

        byte b=122; // This is correct because it will not occupy more memory as compared to short and int
                    // byte can store the value upto -128 to 127

       // byte b=123 // same variable name not allowed i,e b but value can be repeated

        byte b1=122; // it is allowed values will be repeated but not variable

       // byte b2=128; // not accepting the value because having limit between -128 to 127

        short s=128; // If user want to store value more than 127 then he must have to use short or int
    }
}
