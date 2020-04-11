package ru.home.lesson2;

import com.sun.jdi.ObjectReference;
import ru.home.lesson2.exceptions.ArrayToBig;
import ru.home.lesson2.exceptions.StringNotInt;

public class Main {
    public static void main(String[] args) {
        String str = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
        try {
            String[][] strArr = getArrayFromString(str);
            System.out.println(getMatrixHalSum(strArr));
        } catch (ArrayToBig | StringNotInt e) {
            e.printStackTrace();
        }
    }

    /**
     * Преобразовать строку в двумерный массив
     *
     * @param in Исходная строка
     * @return Думерный массив строк
     * @throws ArrayToBig
     */
    public static String[][] getArrayFromString(String in) throws ArrayToBig {
        String[] inArr = in.split("\n");
        if (inArr.length > 4) {
            throw new ArrayToBig();
        }
        String[][] result = new String[4][4];
        for (int i = 0; i < inArr.length; i++) {
            String[] tmpArr = inArr[i].split(" ");
            if (tmpArr.length > 4) {
                throw new ArrayToBig();
            }
            result[i] = tmpArr;
        }
        return result;
    }

    /**
     * Получить половину от суммы всех элементов двумерного массива
     * @param in
     * @return float
     * @throws StringNotInt
     */
    public static float getMatrixHalSum (String[][] in) throws StringNotInt {
        float result = 0;
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[i].length; j++) {
                if (!in[i][j].matches("[+|-]?\\d+")) {
                    throw new StringNotInt();
                }
                result += Integer.parseInt(in[i][j]);
            }
        }
        return result/2;
    }
}
