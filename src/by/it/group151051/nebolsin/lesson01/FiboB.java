package by.it.group151051.nebolsin.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

public class FiboB {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {

        //вычисление чисел простым быстрым методом
        FiboB fibo = new FiboB();
        int n = 55555;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    BigInteger fastB(Integer n) {
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)
        if (n == 0) {
            return BigInteger.ZERO;
        }
        if (n == 1 || n == 2) {
            return BigInteger.ONE;
        }

        BigInteger[] bigIntegers = new BigInteger[n];
        bigIntegers[0] = BigInteger.ONE;
        bigIntegers[1] = BigInteger.ONE;

        for (int i = 2; i < n; i++) {
            bigIntegers[i] = bigIntegers[i - 1].add(bigIntegers[i - 2]);
        }

        return bigIntegers[n-1];
    }

}

