package ex_16_Arrays;

public class Lab152_Arrays_Print {
    public static void main(String[] args) {

        int[] marks = {51, 100, 91, 87, 90};

    // 1. First way Not Recommended
        System.out.println(marks.length);
        System.out.println(marks[0]);
        System.out.println(marks[1]);
        System.out.println(marks[2]);
        System.out.println(marks[3]);
        System.out.println(marks[4]);

    // 2. Second way Recommended
        for (int mark:marks){
            System.out.println(mark);
        }

        System.out.println(" ====== ");

    // 3. Third way Recommended
        for (int i = 0; i < marks.length ; i++) {
            System.out.println(marks[i]);
        }

    }
}
