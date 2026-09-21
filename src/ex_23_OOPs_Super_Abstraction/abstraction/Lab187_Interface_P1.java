package ex_23_OOPs_Super_Abstraction.abstraction;

public class Lab187_Interface_P1 {

    public static void main(String[] args) {
        Car2 car2 = new Car2();
        car2.drive();
    }
}



class Car2 implements Brakes, Engine1 {

    void drive() {
        startEngine();
        applyBreak();
        testEngine();
        Engine1.testEngineStatic();
        stopEngine();
    }


    @Override
    public void applyBreak() {
        System.out.println("applyBreak");
    }

    @Override
    public void startEngine() {
        System.out.println("startEngine");

    }

    @Override
    public void stopEngine() {
        System.out.println("stopEngine");


    }
}


interface Brakes {
    void applyBreak();
}

interface Engine1 {
    void startEngine();

    void stopEngine();

    default void testEngine() {
        System.out.println("concrete Default");
    }

    static void testEngineStatic() {
        System.out.println("concrete Static");
    }

}
