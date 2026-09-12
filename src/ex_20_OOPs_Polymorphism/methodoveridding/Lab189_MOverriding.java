package ex_20_OOPs_Polymorphism.methodoveridding;

public class Lab189_MOverriding {

    public static void main(String[] args) {
        Nikhil n1  = new Nikhil();
        n1.home();

        Father f1 = new Father();
        f1.home();

        Father f2 = new Nikhil(); // Dynamic Dispatch / To call the common Functions between the Parent Class and Child Class we have to use Dynamic Dispatch
        f2.home();


        //  Nikhil n1 = new Father();
        // When father is getting born, child reference cannot be given to.


    }
}

class Father{
    void home(){
        System.out.println("2BHK");
    }
}

class Nikhil extends Father{

    @Override
    void home(){
        System.out.println("3BHK");
    }

}
