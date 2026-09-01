package ex_16_Arrays;

public class Lab150_ArraysCodeStudio {
    public static void main(String[] args) {

        //declare and instantiate

     //   int[] roll = new int[5];  // Either we use this with below lines with Initialization

        //insert value in array

     //   roll[0] = 12; //1st element
    //    roll[1] = 22; //2nd element
   //     roll[2] = 32; //3rd element
  //      roll[3] = 45; //4th element
 //       roll[4] = 25; //5th element

        int[] roll = {12,22,32,45,25};  // or we can use this in one line Initialization and Depolarization

        //read values from array
        System.out.println("size of array:" + roll.length);

        //read values from array
        System.out.println("value of 4th element :" + roll[3]);

        //read all the values of roll array
        System.out.println("Values by using for loop");

        for (int i = 0; i < roll.length; i++) {
            //code to executed
            System.out.println(roll[i]);      //12,22,32,45,25
        }

        System.out.println("Values by using for each loop");

        //for each loop - enhanced loop      Recommended in case of Arrays

        for (int i : roll) {
            //code
            System.out.println(i);
        }

    }
}
