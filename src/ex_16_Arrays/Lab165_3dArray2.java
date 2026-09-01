package ex_16_Arrays;

public class Lab165_3dArray2 {

    public static void main(String[] args) {

        // Declare and initialize 3D array
        int[][][] numbers = {
                {
                        {10, 20, 30},
                        {40, 50, 60}
                },
                {
                        {70, 80, 90},
                        {100, 110, 120}
                }
        };

        // Print a particular element (all 12 elements)
        System.out.println("Value of element: " + numbers[0][0][0]); // 10    │  │  └── column 0
                                                                        //    │  └───── row 0
                                                                        //    └──────── layer 0

        System.out.println("Value of element: " + numbers[0][0][1]); // 20    │  │  └── column 1
                                                                        //    │  └───── row 0
                                                                        //    └──────── layer 0

        System.out.println("Value of element: " + numbers[0][0][2]); // 30    │  │  └── column 2
                                                                        //    │  └───── row 0
                                                                        //    └──────── layer 0

        System.out.println("Value of element: " + numbers[0][1][0]); // 40    │  │  └── column 0
                                                                        //    │  └───── row 1
                                                                        //    └──────── layer 0

        System.out.println("Value of element: " + numbers[0][1][1]); // 50    │  │  └── column 1
                                                                        //    │  └───── row 1
                                                                        //    └──────── layer 0

        System.out.println("Value of element: " + numbers[0][1][2]); // 60    │  │  └── column 2
                                                                        //    │  └───── row 1
                                                                        //    └──────── layer 0

        System.out.println("Value of element: " + numbers[1][0][0]); // 70    │  │  └── column 0
                                                                        //    │  └───── row 0
                                                                        //    └──────── layer 1

        System.out.println("Value of element: " + numbers[1][0][1]); // 80    │  │  └── column 1
                                                                        //    │  └───── row 0
                                                                        //    └──────── layer 1

        System.out.println("Value of element: " + numbers[1][0][2]); // 90    │  │  └── column 2
                                                                        //    │  └───── row 0
                                                                        //    └──────── layer 1

        System.out.println("Value of element: " + numbers[1][1][0]); // 100   │  │  └── column 0
                                                                        //    │  └───── row 1
                                                                        //    └──────── layer 1

        System.out.println("Value of element: " + numbers[1][1][1]); // 110   │  │  └── column 1
                                                                        //    │  └───── row 1
                                                                        //    └──────── layer 1

        System.out.println("Value of element: " + numbers[1][1][2]); // 120   │  │  └── column 2
                                                                        //    │  └───── row 1
                                                                        //    └──────── layer 1

        // Read all values using 3 nested loops
        System.out.println("All values of 3D array:");

        for (int i = 0; i < numbers.length; i++) {

            for (int j = 0; j < numbers[i].length; j++) {

                for (int k = 0; k < numbers[i][j].length; k++) {

                    System.out.println(numbers[i][j][k]);
                }
            }
        }
    }
}
