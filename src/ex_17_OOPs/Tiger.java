package ex_17_OOPs;

public class Tiger {

    String name;                                      // Instance variable

    Tiger() {                                         // No-Argument Constructor
        name = "Royal Bengal Tiger";                  // Giving default value to name
    }
}

class Main {

    public static void main(String[] args) {

        Tiger t1 = new Tiger();                       // Creating object, so Tiger() constructor is called

        System.out.println(t1.name);                  // Printing tiger name
    }
}
