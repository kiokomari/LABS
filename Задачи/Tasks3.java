import java.util.Arrays;
public class Tasks3 {
    /*1. Квадратное уравнение ax2 + bx + c = 0 имеет либо 0, либо 1, либо 2 различных решения
     для действительных значений x. Учитывая a, b и c, вы должны вернуть число решений в уравнение.*/
    public static int solutions (double a, double b, double c) {
        double D=b*b - 4*a*c;
        if ((a == 0) && (b == 0))
            System.out.print("Нет уравнения. Количество решений: ");
        else
        if (D > 0)
            return 2;
        else if (D == 0)
            return 1;
        else
            return 0;
        return 0;
    }
    /*2. Напишите функцию, которая возвращает позицию второго вхождения " zip " в строку, или -1,
    если оно не происходит по крайней мере дважды. Ваш код должен быть достаточно общим,
    чтобы передать все возможные случаи, когда "zip" может произойти в строке.
    Примечание: Верхний регистр " Zip "- это не то же самое, что нижний регистр "zip".*/
    public static int findZip (String str) {
        if (str.indexOf("zip") == str.lastIndexOf("zip"))
            return -1;
        else
            return str.indexOf("zip", str.indexOf("zip") + 1);
    }
    /*3. Создайте функцию, которая проверяет, является ли целое число совершенным числом или нет.
    Совершенное число - это число, которое можно записать как сумму его множителей, исключая само число.*/
    public static boolean checkPerfect (int num) {
        int sum = 0;
        for (int i=1; i < num-1; i++) {
            if (num%i == 0)
                sum+=i;
        }
        if (num == sum)
            return true;
        else
            return false;
    }
    /*4. Создайте функцию, которая принимает строку и возвращает новую строку с
      заменой ее первого и последнего символов, за исключением трех условий:
    – Если длина строки меньше двух, верните "Несовместимо.".
    – Если первый и последний символы совпадают, верните "Два - это пара.".*/
    public static String flipEndChars (String str) {
        char begin, end;
        char [] t;
        begin = str.charAt(0);
        end = str.charAt(str.length() - 1);
        if (str.length() < 2)
            return "Несовместимо.";
        else if (begin == end)
            return "Два - это пара.";
        else
        t = str.toCharArray();
        t[0] = end;
        t[str.length()-1] = begin;
        str = new String(t);
        return str;
    }
    /*5. Создайте функцию, которая определяет, является ли строка допустимым шестнадцатеричным кодом.
    Шестнадцатеричный код должен начинаться с фунтового ключа # и иметь длину ровно 6 символов.
    Каждый символ должен быть цифрой от 0-9 или буквенным символом от A-F.
    Все буквенные символы могут быть прописными или строчными.*/
    public static boolean isValidHexCode(String str) {
        return str.matches("#[a-fA-F0-9]{6}");
    }
    /*6. Напишите функцию, которая возвращает true, если два массива имеют одинаковое количество уникальных элементов,
     и false в противном случае.*/
    public static boolean same (int [] mass, int[] mass1) {
        int count = 0;
        int count1 = 0;
        Arrays.sort(mass);
        Arrays.sort(mass1);
        for (int i=0; i < mass.length-1; i++) {
            if (mass[i] != mass[i+1])
                count ++;
        }
        for (int i=0; i < mass1.length-1; i++){
            if (mass1[i] != mass1[i+1])
                count1 ++;
        }
        if (count == count1)
            return true;
        else
            return false;
    }
    /*7. Число Капрекара - это положительное целое число, которое после возведения в квадрат и разбиения
      на две лексикографические части равно сумме двух полученных новых чисел:
    – Если количество цифр квадратного числа четное, то левая и правая части будут иметь одинаковую длину.
    – Если количество цифр квадратного числа нечетно, то правая часть будет самой длинной половиной,
      а левая - самой маленькой или равной нулю, если количество цифр равно 1.
   – Учитывая положительное целое число n, реализуйте функцию, которая возвращает true,
     если это число Капрекара, и false, если это не так. Примечание: Тривиально, 0 и 1 - это числа Капрекара,
     являющиеся единственными двумя числами, равными их квадрату.*/
    public static boolean isKaprekar (int num) {
        int a = num*num;
        String str=String.valueOf(a);
        if (str.length() == 1)
           if (a == num)
                return true;
            else
                return false;
        else
        if ((Integer.parseInt(str.substring(0, str.length()/2)) + Integer.parseInt(str.substring(str.length()/2))) == num)
            return true;
        else
            return false;
}
    /*8. Напишите функцию, которая возвращает самую длинную последовательность последовательных нулей в двоичной строке.*/
    public static String longestZero (String str) {
        String max = "";
        String count = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '0')
                count += "0";
            else if (str.charAt(i) == '1')
                count = "";
            if (max.length() < count.length())
                max = count;
        }
        return max;
    }
    /*9. Если задано целое число, создайте функцию, которая возвращает следующее простое число.
    Если число простое, верните само число.*/
    public static int nextPrime (int a) {
        for (int i = 2; i < a; i++ )
            if (a % i == 0) {
                a ++;
                i = 2;
            }
        return a;
    }
    /*10. Учитывая три числа, x, y и z, определите, являются ли они ребрами прямоугольного треугольника.*/
    public static boolean rigthTriangle (double a, double b, double c) {
        if (a == 0 || b == 0 || c == 0)
            return false;
        if (a*a + b*b == c*c || a*a + c*c == b*b || b*b + c*c == a*a) {
        return true;
        }
        return false;
    }
    public static void main (String[] args) {
        //тест 1 - вернет 2
        System.out.println(solutions(1, 0, -1));
        // тест 2 - вернет 18
        System.out.println(findZip("all zip files are zipped"));
        // тест 3 - вернет true
        System.out.println(checkPerfect(6));
        // тест 4 - вернет .at, dog, and mouseC
        System.out.println(flipEndChars("Cat, dog, and mouse."));
        // тест 5 - вернет true
        System.out.println(isValidHexCode("#CD5C5C"));
        // тест 6 - вернет true
        System.out.println(same(new int []{1,3,4,4,4,}, new int []{2,5,7}));
        // тест 7 - вернет false
        System.out.println(isKaprekar(3));
        // тест 8 - вернет 0000
        System.out.println(longestZero("01100001011000"));
        // тест 9 - вернет 29
        System.out.println(nextPrime(24));
        // тест 10 - вернет true
        System.out.println(rigthTriangle(4,3,5));
    }
}
