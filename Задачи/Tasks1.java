public class Tasks1 {
    /*1. В Java есть единственный оператор, способный обеспечить остаток от операции деления.
     Два числа передаются в качестве параметров. Первый параметр,разделенный на второй параметр,
     будет иметь остаток, возможно, ноль. Верните это значение. */
    public static int remainder (int a, int b) {
        return a % b ;
    }
    /*2. Напишите функцию, которая принимает основание и высоту треугольника и возвращает его площадь.*/
    public static double triArea (int a, int h) {
        return a*h/2;
    }
    /*3. В этой задаче фермер просит вас сказать ему, сколько ног можно сосчитать среди всех его животных.
    Фермер разводит три вида: chickens = 2 legs, cows = 4 legs, pigs = 4 legs. Фермер подсчитал своих животных,
    и он дает вам промежуточный итог для каждого вида. Вы должны реализовать функцию, которая возвращает общее
    количество ног всех животных.*/
    public static double animals (int chickens, int cows, int pigs) {
        return chickens*2 + (cows + pigs)*4;
    }
    /*Создайте функцию, которая принимает три аргумента (prob, prize, pay) и
    возвращает true, если prob * prize > pay; в противном случае возвращает false.*/
    public static boolean profitableGamble ( double prob, double prize, double pay) {
        if (prob*prize > pay)
            return true;
        else
            return false;
    }
    /*5. Напишите функцию, которая принимает 3 числа и возвращает, что нужно сделать с a и b:
    они должны быть сложены, вычитаны, умножены или разделены, чтобы получить N. Если ни одна
    из операций не может дать N, верните "none" (3 числа – это N, a и b).*/
    public static String operation (int a, int b, int N) {
        if (a-b==N) return "added";
        else if (a+b==N) return "subtracted";
        else if (a*b==N) return "multiplied";
        else if (a/b==N) return "divided";
        else return "none";
    }
    /*6. Создайте функцию, которая возвращает значение ASCII переданного символа.*/
    public static int ctoa (char a) {
        return a;
    }
    /*7. Напишите функцию, которая берет последнее число из последовательного списка чисел и
    возвращает сумму всех чисел до него и включая его.*/
    public static int addUpTo (int a) {
        int sum=0;
        for (int i=1;i<=a;i++) {
            sum=sum+ i;
        }
        return sum;
    }
    /*8. Создайте функцию, которая находит максимальное значение третьего ребра треугольника,
    где длины сторон являются целыми числами.*/
    public static int nextEdge (int a, int b) {
        return a + b -1;
    }
    /*9. Создайте функцию, которая принимает массив чисел и возвращает сумму его кубов.*/
    public static int sumOfCubes (int [] mass) {
        int sum = 0;
        for (int i = 0 ; i < mass.length ; i++) {
            sum += Math.pow(mass[i], 3);
        }
        return sum;
    }
    /*10. Создайте функцию, которая будет для данного a, b, c выполнять следующие действия:
     – Добавьте A к себе B раз.
     – Проверьте, делится ли результат на C.*/
    public static boolean abcmath (int a, int b, int c) {
        for(int i=0;i<b;i++) {
            a += a;
        }
        if (a%c == 0)
            return true;
        else
            return false;
    }
    public static void main (String[] args) {
        //тест - вернет 1
        System.out.println(remainder(1, 3));
        // тест 2 - вернет 3
        System.out.println(triArea(3,2));
        // тест 3 - вернет 36
        System.out.println(animals(2,3,5 ));
        // тест 4 - вернет true
        System.out.println(profitableGamble(0.2,50, 9));
        // тест 5 - вернет added
        System.out.println(operation(24,15,9));
        // тест 6 - вернет 65
        System.out.println(ctoa('A'));
        // тест 7 - вернет 6
        System.out.println(addUpTo(3));
        // тест 8 - вернет 17
        System.out.println(nextEdge(8, 10));
        // тест 9 - вернет 855
        System.out.println(sumOfCubes (new int[] {1, 5 , 9}));
        // тест 10 - вернет false
        System.out.println(abcmath(42, 5 , 10));
    }
}

