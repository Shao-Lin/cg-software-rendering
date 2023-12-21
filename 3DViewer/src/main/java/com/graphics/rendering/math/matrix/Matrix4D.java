package com.graphics.rendering.math.matrix;

import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.math.vector.Vector4D;

public class Matrix4D {
    private static final float esp = 1e-4f;
    private float[][] matrix;

    public Matrix4D() {
        this.matrix = new float[4][4];
    }


    /**
     * Конструктор
     *
     * @param isUnitMatrix true - единичная матрица
     * @param isUnitMatrix false - нулевая матрица
     */
    public Matrix4D(boolean isUnitMatrix) {
        if (isUnitMatrix) {
            this.matrix = new float[][]{
                    {1, 0, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}};
        } else {
            this.matrix = new float[4][4];
        }
    }

    public Matrix4D(float[][] matrix) {
        if (matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4");
        }
        this.matrix = matrix;
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public float getCell(int row, int col) {
        return matrix[row][col];
    }


    /**
     * Операция вывода матрицы
     */
    public void printMatrix() {
        System.out.println("Matrix: ");
        for (float[] floats : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(floats[j] + " ");
            }
            System.out.println();
        }
    }


    /**
     * Операция сложения матрицы
     */
    public Matrix4D sumMatrix(Matrix4D matrix4D) {
        if (matrix4D.getMatrix().length != 4 || matrix4D.getMatrix()[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }
        float[][] values = new float[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][j] = matrix[i][j] + matrix4D.getCell(i, j);
            }
        }
        return new Matrix4D(values);
    }


    /**
     * Операция вычитания матрицы
     */
    public Matrix4D subtractMatrix(Matrix4D matrix4D) {
        if (matrix4D.getMatrix().length != 4 || matrix4D.getMatrix()[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }
        float[][] values = new float[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][j] = matrix[i][j] - matrix4D.getCell(i, j);
            }
        }
        return new Matrix4D(values);
    }


    /**
     * Операция умножения на соответствующий вектор-столбец
     */
    public Vector4D multiplyVector(Vector4D vectorCol) {
        if (vectorCol == null) {
            throw new NullPointerException("Предоставленный вектор не может быть нулевым");
        }

        float[] values = new float[4];
        for (int i = 0; i < matrix.length; i++) {
            values[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                values[i] += matrix[i][j] * vectorCol.get(j);
            }
        }
        return new Vector4D(values[0], values[1], values[2], values[3]);
    }
    public Vector3D multiplyVectorDivW(Vector3D vectorCol3D) {
        if (vectorCol3D == null) {
            throw new NullPointerException("Предоставленный вектор не может быть нулевым");
        }
        Vector4D vector4DCol = vectorCol3D.translationToVector4D();

        float[] values = new float[4];
        for (int i = 0; i < matrix.length; i++) {
            values[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                values[i] += matrix[i][j] * vector4DCol.get(j);
            }
        }
        return new Vector3D(values[0]/values[3], values[1]/values[3], values[2]/values[3]);
    }


    /**
     * Операция перемножения матриц
     */
    public Matrix4D multiplyMatrix(Matrix4D matrix4D) {
        if (matrix4D.getMatrix().length != 4 || matrix4D.getMatrix()[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }
        float[][] values = new float[4][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    values[i][j] += matrix[i][k] * matrix4D.getCell(k, j);
                }
            }
        }
        return new Matrix4D(values);
    }


    /**
     * Операция транспонирования
     */
    public Matrix4D transpose() {
        if (matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }
        float[][] transposed = new float[4][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return new Matrix4D(transposed);
    }

    public float determinant() {
        if (matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }

        return matrix[0][0] * (matrix[1][1] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2])
                - matrix[1][2] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1])
                + matrix[1][3] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1]))
                - matrix[0][1] * (matrix[1][0] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2])
                - matrix[1][2] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0])
                + matrix[1][3] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0]))
                + matrix[0][2] * (matrix[1][0] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1])
                - matrix[1][1] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0])
                + matrix[1][3] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0]))
                - matrix[0][3] * (matrix[1][0] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1])
                - matrix[1][1] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0])
                + matrix[1][2] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0]));
    }

    public boolean equalsAns(Matrix4D matrix4D) {
        boolean flag = false;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == matrix4D.getCell(i, j)) {
                    flag = true;
                } else return false;
            }
        }
        return flag;
    }

}
