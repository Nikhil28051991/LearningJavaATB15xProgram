package ex_19_OOPs_Part2.OOPs_01_inheritance.hierarchical;

public class Problem {

    public static void main(String[] args) {

        Animal a = new Dog2();                    // Parent reference pointing to Child object
        // Animal a = new Dog2(); Animal = reference type, Dog2 = actual object
        a.sound();                              // Calling sound() method
    }                                          // Java calls Dog2's sound() because the actual object is Dog
}


class Animal {

    void sound() {                                   // Parent class method
        System.out.println("Animal makes a sound");  // Printing Animal sound
    }
}


class Dog2 extends Animal {

    void sound() {                               // Child class overriding Parent class sound() method
        System.out.println("Dog barks");         // Printing Dog sound
    }
}
