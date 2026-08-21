package ex_13_Functions;

public class Lab129_Method_Functions {
    // First method - BEFORE main()

    static void name_of_function() {

        System.out.println("Hi Nikhil Before main method");
    }

    public static void main(String[] args) {

        // Calling first method
        name_of_function();

        // Calling second method
        name_of_function2();

    }

    // Second method - AFTER main()
    static void name_of_function2() {

        System.out.println("Hi Nikhil Sonawane After main method");
    }
}

