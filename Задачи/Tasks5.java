import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
public class Tasks5 {
/*1. Пришло время отправлять и получать секретные сообщения. Создайте две функции, которые принимают строку и массив
и возвращают закодированное или декодированное сообщение. Первая буква строки или первый элемент массива представляет
собой символьный код этой буквы. Следующие элементы — это различия между символами: например, A + 3 --> C или z - 1 --> y.*/
    public static int[] encrypt(String str) {
        int [] res = new int [str.length()];
        for (int i = 0; i < str.length(); i++) {
            if (i == 0)
                res[i] = str.charAt(i);
            else
                res[i] = str.charAt(i) - str.charAt(i-1);
        }
        return res;
    }
    public static String decrypt(int [] mass) {
        String res = "";
        for (int i = 0; i < mass.length; i++){
            if (i == 0)
                res += (char) mass[i];
            else {
                res += (char) (mass[i] + mass[i-1]);
                mass[i] += mass[i-1];
            }
        }
        return res;
    }
/*2. Создайте функцию, которая принимает имя шахматной фигуры, ее положение и целевую позицию. Функция должна возвращать
true, если фигура может двигаться к цели, и false, если она не может этого сделать. Возможные входные данные:
- "Пешка", "Конь", "Слон", "Ладья", "Ферзь" и "Король".*/
    public static boolean canMove(String name,String start, String end) {
        int s = Integer.parseInt(String.valueOf(start.charAt(1)));
        int e = Integer.parseInt(String.valueOf(end.charAt(1)));
        if (start.charAt(0) == end.charAt(0) && s == e)
            return false;
        switch (name) {
            case "Pawn":
            case "Пешка": {
                if (start.charAt(0) == end.charAt(0) && s == 2 && e == 4)
                    return true;
                else if (start.charAt(0) == end.charAt(0) && s == (e-1))
                    return true;
                else
                return false;
            }
            case "Knight":
            case "Конь": {
                if ((Math.abs(start.charAt(0) - end.charAt(0)) == 2 && Math.abs(s-e) == 1) ||
                        (Math.abs(start.charAt(0) - end.charAt(0)) == 1 && Math.abs(start.charAt(0) - e) == 2))
                    return true;
                else
                return false;
            }
            case "Bishop":
            case "Слон": {
                if (Math.abs(start.charAt(0) - end.charAt(0)) == Math.abs(s-e))
                    return true;
                else
                return false;
            }
            case "Rook":
            case "Ладья": {
                if ((start.charAt(0) == end.charAt(0) && s != e) || (start.charAt(0) != end.charAt(0) && s == e))
                    return true;
                else
                return false;
            }
            case "Queen":
            case "Ферзь": {
                if ((start.charAt(0) == end.charAt(0) && s != e) || (start.charAt(0) != end.charAt(0) && s == e))
                    return true;
                else if (Math.abs(start.charAt(0) - end.charAt(0)) == Math.abs(s-e))
                    return true;
                break;
            }
            case "King":
            case "Король": {
                if (Math.abs(start.charAt(0) - end.charAt(0)) < 2 && Math.abs(s-e) < 2)
                    return true;
                else
                return false;
            }
        }
        return false;
    }
/*3. Входная строка может быть завершена, если можно добавить дополнительные буквы, и никакие буквы не должны быть удалены,
чтобы соответствовать слову. Кроме того, порядок букв во входной строке должен быть таким же, как и порядок букв в последнем слове.
Создайте функцию, которая, учитывая входную строку, определяет, может ли слово быть завершено.*/
    public static boolean canComplete(String str, String full) {
        int k = 0;
        for (int i = 0; i < full.length(); i++) {
            if (full.charAt(i) == str.charAt(k)) {
                k++;
                if (k == str.length())
                    return true;
            }
        }
        return false;
    }
/*4. Создайте функцию, которая принимает числа в качестве аргументов, складывает их вместе и возвращает произведение
цифр до тех пор, пока ответ не станет длиной всего в 1 цифру.*/
    public static int sumDigProd(int [] mass){
        int sum = 0;
        for (int i = 0; i < mass.length; i++ )
            sum += mass[i];
        int mul = 1;
        while (sum > 9) {
            for (int i = 0; i < sum; i++) {
                mul *= sum % 10;
                sum = sum/10;
            }
            sum = mul;
            mul = 1;
        }
        return sum;
    }
/*5. Напишите функцию, которая выбирает все слова, имеющие все те же гласные (в любом порядке и/или количестве),
что и первое слово, включая первое слово.*/
    public static String[] sameVowelGroup(String[] str) {
        List<String> word = new ArrayList<String>();
        word.add(str[0]);
        String vowels = "aeiyouAEIOUY";
        for (int i = 1; i < str.length; i++) {
            boolean log = true;
            for (char a: vowels.toCharArray()) {
                if (str[0].contains(String.valueOf(a)) != str[i].contains(String.valueOf(a)))
                    log = false;
            }
            if (log) word.add(str[i]);
        }
        return word.toArray(new String[word.size()]);
    }
/*6. Создайте функцию, которая принимает число в качестве аргумента и возвращает true, если это число является
действительным номером кредитной карты, а в противном случае - false. Номера кредитных карт должны быть длиной
от 14 до 19 цифр и проходить тест Луна, описанный ниже:
– Удалите последнюю цифру (это"контрольная цифра").
– Переверните число.
– Удвойте значение каждой цифры в нечетных позициях. Если удвоенное значение имеет более 1 цифры, сложите цифры вместе
(например, 8 x 2 = 16 ➞ 1 + 6 = 7).
– Добавьте все цифры.
– Вычтите последнюю цифру суммы (из шага 4) из 10. Результат должен быть равен контрольной цифре из Шага 1.*/
    public static boolean validateCard(long num) {
        String n = Long.toString(num);
        if (n.length() < 14 || n.length() > 19)
            return false;
        // шаг 1-2
        long del = num%10;
        num = num/10;
        StringBuilder revers = new StringBuilder();
        num = Long.parseLong(revers.append(num).reverse().toString());
        // шаг 3-4
        int sum = 0;
        String str = String.valueOf(num);
        for (int i = 0; i < str.length(); i++) {
            if (i%2 == 0) {
                int mul = Integer.parseInt(String.valueOf(str.charAt(i))) * 2;
                sum += mul/10;
                sum += mul%10;
            }
            else
                sum += Integer.parseInt(String.valueOf(str.charAt(i)));
        }
        // шаг 5
        if ((10 - sum%10) == del)
            return true;
        else
        return false;
    }
/*7. Напишите функцию, которая принимает положительное целое число от 0 до 999 включительно и возвращает строковое
представление этого целого числа, написанное на английском языке. То же самое нужно сделать и для русского языка.*/
    public static String numToEng(int num) {
        String str = "";
        if (num == 0)
            return "zero";
        switch (num/100) {
            case 1: str += "one hundred ";
            break;
            case 2: str += "two hundred ";
            break;
            case 3: str += "three hundred ";
            break;
            case 4: str += "four hundred ";
            break;
            case 5: str += "five hundred ";
            break;
            case 6: str += "six hundred ";
            break;
            case 7: str += "seven hundred ";
            break;
            case 8: str += "eight hundred ";
            break;
            case 9: str += "nine hundred ";
            break;
        }
        switch (num/10%10) {
            case 1: switch (num%100) {
                case 10: str += "ten";
                    return str;
                case 11: str += "eleven";
                return str;
                case 12: str += "twelve";
                return str;
                case 13: str += "thirteen";
                return str;
                case 14: str += "fourteen";
                return str;
                case 15: str += "fifteen";
                return str;
                case 16: str += "sixteen";
                return str;
                case 17: str += "seventeen";
                return str;
                case 18: str += "eighteen";
                return str;
                case 19: str += "nineteen";
                return str;
            }
            case 2: str += "twenty ";
            break;
            case 3: str += "thirty ";
            break;
            case 4: str += "forty ";
            break;
            case 5: str += "fifty ";
            break;
            case 6: str += "sixty ";
            break;
            case 7: str += "seventy ";
            break;
            case 8: str += "eighty ";
            break;
            case 9: str += "ninety ";
            break;
        }
        switch (num%10) {
            case 1: str += "one";
            break;
            case 2: str += "two";
            break;
            case 3: str += "three";
            break;
            case 4: str += "four";
            break;
            case 5: str += "five";
            break;
            case 6: str += "six";
            break;
            case 7: str += "seven";
            break;
            case 8: str += "eight";
            break;
            case 9: str += "nine";
            break;
        }
        return str;
    }
    public static String numToRus(int num) {
        String str = "";
        if (num == 0)
            return "ноль";
        switch (num/100) {
            case 1: str += "сто ";
            break;
            case 2: str += "двести ";
            break;
            case 3: str += "триста ";
            break;
            case 4: str += "четыреста ";
            break;
            case 5: str += "пятьсот ";
            break;
            case 6: str += "шестьсот ";
            break;
            case 7: str += "семьсот ";
            break;
            case 8: str += "восемьсот ";
            break;
            case 9: str += "девятьсот ";
            break;
        }
        switch (num/10%10) {
            case 1: switch (num%100) {
                case 10: str += "десять";
                    return str;
                case 11: str += "одиннадцать";
                    return str;
                case 12: str += "двенадцать";
                    return str;
                case 13: str += "тринадцать";
                    return str;
                case 14: str += "четырнадцать";
                    return str;
                case 15: str += "пятнадцать";
                    return str;
                case 16: str += "шестнадцать";
                    return str;
                case 17: str += "семнадцать";
                    return str;
                case 18: str += "восемнадцать";
                    return str;
                case 19: str += "девятнадцать";
                    return str;
            }
            case 2: str += "двадцать ";break;
            case 3: str += "тридцать ";break;
            case 4: str += "сорок ";break;
            case 5: str += "пятьдесят ";break;
            case 6: str += "шестьдесят ";break;
            case 7: str += "семьдесят ";break;
            case 8: str += "восемьдесят ";break;
            case 9: str += "девяносто ";break;
        }
        switch (num%10) {
            case 1: str += "один";
            break;
            case 2: str += "два";
            break;
            case 3: str += "три";
            break;
            case 4: str += "четыре";
            break;
            case 5: str += "пять";
            break;
            case 6: str += "шесть";
            break;
            case 7: str += "семь";
            break;
            case 8: str += "восемь";
            break;
            case 9: str += "девять";
            break;
        }
        return str;
    }
/*8. Хеш-алгоритмы легко сделать одним способом, но по существу невозможно сделать наоборот. Например,
если вы хешируете что-то простое, например, password123, это даст вам длинный код, уникальный для этого слова или фразы.
В идеале, нет способа сделать это в обратном порядке. Вы не можете взять хеш-код и вернуться к слову или фразе,
с которых вы начали. Создайте функцию, которая возвращает безопасный хеш SHA-256 для данной строки. Хеш должен быть
отформатирован в виде шестнадцатеричной цифры.*/
    public static String getSha256Hash(String str) {
        StringBuilder res = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            for (byte a: hash) {
                res.append(String.format("%02x", a));
            }
            return res.toString();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
/*9. Напишите функцию, которая принимает строку и возвращает строку с правильным регистром для заголовков символов
в серии "Игра престолов". Слова and, the, of и in должны быть строчными. Все остальные слова должны иметь первый символ
в верхнем регистре, а остальные - в нижнем. Примечания:
– Знаки препинания и пробелы должны оставаться в своих первоначальных положениях.
– Дефисные слова считаются отдельными словами.
– Будьте осторожны со словами, которые содержат and, the, of или in.*/
    public static String correctTitle(String str) {
        String[] res = str.split(" ");
        str = "";
        for (int i = 0; i < res.length; i++) {
            if (i == 0 || i == 1 || i == 2 || i == res.length - 1) {
                res[i] = res[i].substring(0, 1).toUpperCase() + res[i].substring(1).toLowerCase();
                str += res[i] + " ";
            }
            else str += res[i].toLowerCase() + " ";
        }
        return str;
    }
/*10. Как указано в онлайн-энциклопедии целочисленных последовательностей:
Гексагональная решетка — это привычная двумерная решетка, в которой каждая точка имеет 6 соседей.
Центрированное шестиугольное число — это центрированное фигурное число, представляющее шестиугольник с точкой в центре
и всеми другими точками, окружающими центральную точку в шестиугольной решетке. Иллюстрация начальных условий:
                             o o o o
                o o o       o o o o o
       o o     o o o o     o o o o o o   Напишите функцию, которая принимает целое число n и возвращает "недопустимое",
  o   o o o   o o o o o   o o o o o o o  если n не является центрированным шестиугольным числом или его иллюстрацией
       o o     o o o o     o o o o o o   в виде многострочной прямоугольной строки в противном случае.
                o o o       o o o o o
                             o o o o
  1     7        19            37 */
    public static String hexLattice(int num) {
        String res = "";
        int k = 1, i;
        for (i = 1; k < num; i++) {
            k += i*6;
        }
        if (num != k) {
            return "Недопустимое";
        }
        for ( int j = 0; j < i*2-1; j++) {
            res += "o ";
        }
        String aux = res;
        for ( int j = 1; j < i; j++) {
            aux = " " + aux.substring(0, aux.length()-2);
            res = aux  + "\n" +  res + "\n" + aux;
        }
        return res;
    }
        public static void main (String[] args) {
        //тест 1 - вернет [72, 29, 7, 0, 3] и Hi there!
        System.out.println(Arrays.toString(encrypt("Hello")));
        System.out.println(decrypt(new int []{72, 33, -73, 84, -12, -3, 13, -13, -68}));
        // тест 2 - вернет true
        System.out.println(canMove("Rook", "A8", "H8"));
        // тест 3 - вернет false
        System.out.println(canComplete("butlz", "beautiful"));
        // тест 4 - вернет 6
        System.out.println(sumDigProd((new int []{16, 28})));
        // тест 5 - вернет [toe, ocelot]
        System.out.println(Arrays.toString(sameVowelGroup(new String[]{"toe", "ocelot", "maniac"})));
        // тест 6 - вернет true
        System.out.println(validateCard(1234567890123452L));
        // тест 7 - вернет one hundred twenty six и сто восемнадцать
        System.out.println(numToEng(126));
        System.out.println(numToRus(118));
        // тест 8 - вернет ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f
        System.out.println(getSha256Hash("password123"));
        // тест 9 - вернет Jon Snow, King in the North.
        System.out.println(correctTitle("jOn SnoW, kINg IN thE noRth."));
        /* тест 10 - вернет o o o
                           o o o o
                          o o o o o
                           o o o o
                            o o o */
        System.out.println(hexLattice(19));
    }
}
