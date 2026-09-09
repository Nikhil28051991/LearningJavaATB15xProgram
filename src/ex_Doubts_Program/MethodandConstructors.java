package ex_Doubts_Program;

public class MethodandConstructors {

    String name;
    int age;

    // Default / No-Argument Constructor
    MethodandConstructors() {
        name = "Unknown";
        age = 0;
    }

    // Parameterized Constructor
    MethodandConstructors(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Method
    void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }

    public static void main(String[] args) {

        // Calling Default Constructor
        MethodandConstructors s1 = new MethodandConstructors();

        System.out.println("Default Constructor:");
        s1.displayDetails();

        System.out.println("----------------------");

        // Calling Parameterized Constructor
        MethodandConstructors s2 =
                new MethodandConstructors("Nikhil", 25);

        System.out.println("Parameterized Constructor:");
        s2.displayDetails();
    }
}
