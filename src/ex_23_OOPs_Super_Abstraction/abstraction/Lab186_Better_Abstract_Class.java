package ex_23_OOPs_Super_Abstraction.abstraction;

public class Lab186_Better_Abstract_Class {

    public static void main(String[] args) {
        Alto car = new Alto();
        car.drive();
    }

}

class Alto extends Car1{

    void drive(){
        startCar();
        System.out.println("We are driving a Car");
        stopCar();
    }


    @Override
    void startCar() {
        System.out.println("Starting a car alto");
    }

    @Override
    void stopCar() {
        System.out.println("Stopping a car alto");

    }
}

abstract class Car1{
    abstract void startCar();
    abstract void stopCar();

}