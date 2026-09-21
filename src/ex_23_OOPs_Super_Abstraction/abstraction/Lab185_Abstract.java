package ex_23_OOPs_Super_Abstraction.abstraction;

public class Lab185_Abstract {

    public static void main(String[] args) {
        Son s1  = new Son();
        s1.loan50K();
        s1.load10K();
    }


}


abstract class Father{
    abstract void loan50K();
    // concrete - complete method
    void load10K(){
        System.out.println("Given!");
    }
}

class Son extends Father{

    @Override
    void loan50K() {
        System.out.println("Given by Son!");
    }
}