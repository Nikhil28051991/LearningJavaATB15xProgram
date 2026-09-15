package ex_22_OOPs__Ecap_AccessModifer.criminal;

import ex_22_OOPs__Ecap_AccessModifer.police.Cop;

public class Rahul extends Cop {
    public Rahul(int bullet) {
        super(bullet);
    }

    public static void main(String[] args) {
        Cop rahul  = new Cop(100);
      //  rahul.canIShoot();
      //  rahul.thisDefaultF1();
    }
}
