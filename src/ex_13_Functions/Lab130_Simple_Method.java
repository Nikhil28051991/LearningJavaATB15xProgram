package ex_13_Functions;

public class Lab130_Simple_Method {
    public static void main(String[] args) {

    }

    static void non_return_function() {      // If I want to Print "print Something....."  non_return_function();
        System.out.println("Print something no return or return type");
    }

    static int return_int() {               // If I want to Print 10    System.out.println(return_int());
        System.out.println("Print something no return or return type");
        return 10;
    }

    static boolean return_boolean() {      // If I want to Print true    System.out.println(return_boolean());
        System.out.println("Print something no return or return type");
        return true;

    }

    static float return_float_pi_value(){     // If I want to Print 3.14    System.out.println(return_float_pi_value());
        return 3.14f;
    }

}
