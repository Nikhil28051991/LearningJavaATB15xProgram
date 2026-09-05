package ex_17_OOPs;

public class Lab_160_CONSTRUCTOR {
    public static void main(String[] args) {

        A a = new A();

        Animal1 animal1 = new Animal1(); // It is a Separate Class

    }


}

class A {
         A () {

             System.out.println("Default Constructor of - A");

         } // we have two ways to Create Constructor either in a same class or in a different class just like Animal1 is a Separate class

}