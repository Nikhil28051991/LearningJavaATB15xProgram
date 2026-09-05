package ex_02_Java_Basics;

public class Lab014_Main_Method {
   /* public static void main(String[] args) {

    }

    public static void main(String[] args) {

    }*/

   // Two main methods in single class are not allowed
   public static void main(String[] args) {
       System.out.println("The main method is only one in class 2");

   }

    public static void main(String args) {

       //but second main without [] it is allowed because it is not main function it is clone and also no run Green button for main without []


    }

    public static void Main(String[] args) {
       // It is also not consider as a main method having M capita in main

        System.out.println("The main method is only one in class 1"); // it will not Execute because it is under Main not main

        //we can have multiple main function look-like in a single program but only 1 will run
        //public static void main(String[]
        // In java function and method both are same Function==Method
    }
}


