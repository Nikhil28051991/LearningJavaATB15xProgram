package ex_16_Arrays;

public class Lab162_2D_Array_Iterate_For_Loop {
    public static void main(String[] args) {

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[i].length ; j++) {  //why we take [i] here because how many columns we have in a row

                System.out.print(matrix[i][j] +"|");      // why not println because every element will print in new line
            }
            System.out.println("");                      // why this after each row we want new line

        }
    }
}
