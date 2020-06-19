import java.util.Arrays;
public class Tasks2 {
    /*1. Создайте функцию, которая повторяет каждый символ в строке n раз.*/
    public static String repeat ( String str, int n) {
        String str1 = "";
        for (int i = 0; i < str.length(); i++)
            for (int j = 0; j < n; j++)
                str1+=str.charAt(i);
        return str1;
    }
    /*2. Создайте функцию, которая принимает массив и возвращает разницу между
    самыми большими и самыми маленькими числами.*/
    public static int differenceMaxMin ( int [] mass) {
        int max = mass[0];
        int min = mass[0];
        for (int i = 0; i < mass.length;i++) {
            max = Math.max(max, mass[i]);
            min = Math.min(min, mass[i]);
        }
        return max-min;
    }
    /*3. Создайте функцию, которая принимает массив в качестве аргумента и возвращает true или false
     в зависимости от того, является ли среднее значение всех элементов массива целым числом или нет.*/
    public static boolean isAvgWhole ( int [] mass) {
        double sum = 0;
        double sr;
        for (int i = 0; i < mass.length; i++) {
            sum += mass[i];
        }
        sr = sum/mass.length;
        if (sr%1 == 0)
           return true;
        else
            return false;
    }
    /*4. Создайте метод, который берет массив целых чисел и возвращает массив,
    в котором каждое целое число является суммой самого себя + всех предыдущих чисел в массиве.*/
    public static int[] cumulativeSum (int [] mass) {
        for (int i = 1; i < mass.length; i++) {
            mass[i] = mass[i] + mass[i - 1];
        }
        return mass;
    }
    /*5. Создайте функцию, которая возвращает число десятичных знаков, которое имеет число (заданное в виде строки).
    Любые нули после десятичной точки отсчитываются в сторону количества десятичных знаков.*/
    public static int getDecimalPlaces (String a) {
        if (a.indexOf(".") != -1)
            return a.length()-(a.indexOf(".") + 1);
        else
            return 0;
    }
    /*6. Создайте функцию, которая при заданном числе возвращает соответствующее число Фибоначчи.*/
    public static int Fibonacci (int a) {
        int[] mass = new int[a];
        mass[0]=1;
        mass[1]=2;
        for (int i=2; i < a; i++) {
            mass[i] = mass[i-1] + mass[i-2];
        }
        return mass[a-1];
    }
    /*7. Почтовые индексы состоят из 5 последовательных цифр. Учитывая строку, напишите функцию, чтобы определить,
     является ли вход действительным почтовым индексом. Действительный почтовый индекс выглядит следующим образом:
    – Должно содержать только цифры (не допускается использование нецифровых цифр).
    – Не должно содержать никаких пробелов.
    – Длина не должна превышать 5 цифр.*/
    public static boolean isValid (String a) {
        if (a.length() == 5)
            try {
                Integer.parseInt(a);
                return true;
            }
            catch (Exception e) {
                return false;
            }
        return false;
    }
    /*8. Пара строк образует странную пару, если оба из следующих условий истинны:
    – Первая буква 1-й строки = последняя буква 2-й строки.
    – Последняя буква 1-й строки = первая буква 2-й строки.
    – Создайте функцию, которая возвращает true, если пара строк представляет собой странную пару,
     и false в противном случае.*/
    public static boolean isStrangePair (String str1, String str2) {
        if (str1.equals("") && str2.equals(""))
            return true;
        else
        if (!(str1.equals("") || str2.equals("")))
            if (str1.charAt(0) == str2.charAt(str2.length() - 1) && str1.charAt(str1.length() - 1) == str2.charAt(0))
                return true;
            else
                return false;
        return false;
    }
    /*9. Создайте две функции: isPrefix(word, prefix-) и isSuffix (word, -suffix).
    – isPrefix должен возвращать true, если он начинается с префиксного аргумента.
    – isSuffix должен возвращать true, если он заканчивается аргументом суффикса.
    – В противном случае верните false. */
    public static boolean isPrefix (String word, String pref) {
        pref =  pref.substring(0,  pref.length()-1);
        return word.startsWith(pref);
    }
    public static boolean isSuffix (String word, String suf) {
        suf = suf.substring(1);
        return word.endsWith(suf);
    }
    /*Создайте функцию, которая принимает число (шаг) в качестве аргумента и возвращает
    количество полей на этом шаге последовательности.
    Шаг 0: начните с 0
    Шаг 1: Добавьте 3
    Шаг 2: Вычтите 1
    Повторите Шаги 1 И 2*/
    public static int boxSeq (int a) {
        if (a >=0 && a%2==0)
             return a;
        else
            if (a >=0)
            return a+2;
            else return 0;
    }
    public static void main (String[] args) {
        // тест 1 - вернет mmmmmiiiiiccccceeeee
        System.out.println(repeat("mice", 5));
        // тест 2 - вернет 82
        System.out.println(differenceMaxMin(new int[] {10, 4, 1, 4, -10, -50, 32, 21}));
        // тест 3 - вернет true
        System.out.println(isAvgWhole(new int[] {1, 3}));
        // тест 4 - вернет 1, 3, 6
        System.out.println(Arrays.toString(cumulativeSum(new int[] {1, 2, 3})));
        // тест 5 - вернет 2
        System.out.println(getDecimalPlaces("43.20"));
        // тест 6 - вернет 3
        System.out.println(Fibonacci(3));
        // тест 7 - вернет false
        System.out.println(isValid("732 32"));
        // тест 8 - вернет true
        System.out.println(isStrangePair("ratio", "orator"));
        // тест 9 - вернет true и false
        System.out.println(isPrefix("automation", "auto-"));
        System.out.println(isSuffix("vocation", "-logy"));
        // тест 10 - вернет 5
        System.out.println(boxSeq(3));
    }
}