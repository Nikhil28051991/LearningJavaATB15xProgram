package ex_16_Arrays;

public class Lab164_2nd_highlest_ArraymissCondn {
    public static void main(String[] args) {

        int[] numbers = {100, 20, 50};
    //  int[] numbers1 = {90, 10, 80, 30, 70, 40, 60};
   //   int[] numbers2 = {95, 10, 50, 20, 80, 30};
  //    int[] numbers3 = {75, 5, 60, 20, 72};

        int highest = 0;
        int secondHighest = 0;
        // 67
        for (int num : numbers) {
            if (num > highest) {
                secondHighest = highest;
                highest = num;

            } //else if (num > secondHighest && num != highest) {  // If we not use this condition then our program will be fail for all above array
             //secondHighest = num;                                // numbers, numbers1, numbers2,numbers3
        }
        System.out.println(secondHighest);
    }
}