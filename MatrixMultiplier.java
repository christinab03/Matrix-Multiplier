import java.io.*;
import java.util.Scanner;

public class MatrixMultiplier {

    public static void main(String[] args) {
        int[][] matrix1= null, matrix2 = null, resultMatrix;

        if (args.length == 2) {
            matrix1 = readMatrixFromFile(args[0]);
            matrix2 = readMatrixFromFile(args[1]);

        } else if (args.length == 1 && isInteger(args[0])) {
            int size = Integer.parseInt(args[0]);
            matrix1 = generateRandomMatrix(size);
            matrix2 = generateRandomMatrix(size);
            writeMatrixToFile(matrix1, "matrix1.txt");
            writeMatrixToFile(matrix2, "matrix2.txt");

        } else {
            System.out.println("Enter file names or an integer:");
            try (Scanner scanner = new Scanner(System.in)) {
                String input = scanner.nextLine();

                if (isInteger(input)) {
                    int size = Integer.parseInt(input);
                    matrix1 = generateRandomMatrix(size);
                    matrix2 = generateRandomMatrix(size);
                    writeMatrixToFile(matrix1, "matrix1.txt");
                    writeMatrixToFile(matrix2, "matrix2.txt");
                } else {
                    String[] fileNames = input.split("\\s+");
                    if (fileNames.length == 2) {
                        matrix1 = readMatrixFromFile(fileNames[0]);
                        matrix2 = readMatrixFromFile(fileNames[1]);
                    } else {
                        System.out.println("Invalid input. Please provide two file names or an integer.");
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        resultMatrix = multiplyMatrices(matrix1, matrix2);

        writeMatrixToFile(resultMatrix, "matrix3.txt");

        System.out.println("Matrix multiplication completed. Result saved to matrix3.txt.");
    }

    private static int[][] readMatrixFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            int[][] matrix = new int[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }
            return matrix;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error reading matrix from file: " + fileName);
            return null;
        }
    }

    private static void writeMatrixToFile(int[][] matrix, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            int rows = matrix.length;
            int cols = matrix[0].length;

            writer.println(rows);
            writer.println(cols);

            for (int[] row : matrix) {
                for (int num : row) {
                    writer.print(num + " ");
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error writing matrix to file: " + fileName);
        }
    }

    private static int[][] generateRandomMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = (int) (Math.random() * 10); // Adjust the range as needed
            }
        }
        return matrix;
    }

    private static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;

        if (cols1 != rows2) {
            System.out.println("Error: Matrix multiplication not possible. Incompatible dimensions.");
            return null;
        }

        int[][] resultMatrix = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return resultMatrix;
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
