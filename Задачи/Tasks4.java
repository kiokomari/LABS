import java.util.*;
public class Tasks4 {
/*1. Бесси работает над сочинением для своего класса писателей. Поскольку ее почерк довольно плох, она решает напечатать
эссе с помощью текстового процессора. Эссе содержит N слов (1≤N≤100), разделенных пробелами. Каждое слово имеет длину
от 1 до 15 символов включительно и состоит только из прописных или строчных букв. Согласно инструкции к заданию,
эссе должно быть отформатировано очень специфическим образом: каждая строка должна содержать не более K (1≤K≤80) символов,
не считая пробелов. К счастью, текстовый процессор Бесси может справиться с этим требованием, используя следующую стратегию:
– Если Бесси набирает Слово, и это слово может поместиться в текущей строке, поместите его в эту строку.
В противном случае поместите слово на следующую строку и продолжайте добавлять к этой строке. Конечно, последовательные
слова в одной строке все равно должны быть разделены одним пробелом. В конце любой строки не должно быть места.
– К сожалению, текстовый процессор Бесси только что сломался. Пожалуйста, помогите ей правильно оформить свое эссе!
Вам будут даны n, k и строка.*/
    public static String BessieEssay(int n, int k , String txt) {
        String[] text = txt.split(" ");
        txt = "";
        String essay = "";
        for (int i = 0; i < n; i++) {
            if (txt.length() + text[i].length() > k) {
                essay = essay.trim() + "\r\n" + text[i] + " ";
                txt = text[i];
            } else {
                essay += text[i] + " ";
                txt += text[i];
            }
        }
        return essay.trim();
    }
/*2. Напишите функцию, которая группирует строку в кластер скобок. Каждый кластер должен быть сбалансирован.*/
    public static List<String> split(String klast) {
        List<String> res = new LinkedList<String>();
        int k = 0;
        String buf = "";
        for(int i = 0; i < klast.length(); i++) {
            if (klast.charAt(i) == '(')
                k++;
            else if (klast.charAt(i) == ')')
                k--;
            buf += klast.charAt(i);
            if (k == 0) {
                res.add(buf);
                buf = "";
            }
        }
        return res;
    }
/*3. Создайте две функции toCamelCase () и toSnakeCase (), каждая из которых берет одну строку и преобразует ее
либо в camelCase, либо в SnakeCase. Примечание:
– SnakeCase — стиль написания составных слов, при котором несколько слов разделяются символом подчеркивания (_),
и не имеют пробелов в записи, причём каждое слово обычно пишется с маленькой буквы — «foo_bar», «hello_world» и т. д.
– CamelCase — стиль написания составных слов, при котором несколько слов пишутся слитно без пробелов,
при этом каждое слово внутри фразы пишется с прописной буквы. Стиль получил название CamelCase, поскольку
прописные буквы внутри слова напоминают горбы верблюда.*/
    public static String toCamelCase(String camel) {
        String cam = "";
        for (int i = 0; i < camel.length(); i++) {
            if (camel.charAt(i) != '_')
                cam += camel.charAt(i);
            if (camel.charAt(i) == '_') {
                i++;
                String cam1 = Character.toString(camel.charAt(i));
                cam += cam1.toUpperCase();
            }
        }
        return cam;
    }
    public static String toSnakeCase(String snake) {
        String snak = "";
        for (int i = 0; i < snake.length(); i++) {
            if ((snake.charAt(i) >= 'A') && (snake.charAt(i) <= 'Z')) {
                snak += '_';
                String snak1 = Character.toString(snake.charAt(i));
                snak += snak1.toLowerCase();
            } else
                snak += snake.charAt(i);
        }
        return snak;
    }
/*4. Напишите функцию, которая вычисляет сверхурочную работу и оплату, связанную со сверхурочной работой. Работа с 9 до 5:
обычные часы работы, после 5 вечера это сверхурочная работа. Ваша функция получает массив с 4 значениями:
– Начало рабочего дня, в десятичном формате, (24-часовая дневная нотация)
– Конец рабочего дня.(Тот же формат)
– Почасовая ставка
– Множитель сверхурочных работ.
Ваша функция должна возвращать: $ + заработанные в тот день (округлены до ближайшей сотой).
Примечание: С 16 до 17 регулярно, поэтому 1 * 30 = 30, с 17 до 18 сверхурочно, поэтому 1 * 30 * 1,8 = 54 30 + 54 = 84,00$.*/
    public static String overTime(double[] work) {
        double sum = 0;
        if (work[1] <= 17)
            sum += (work[1] - work[0]) * work[2];
        else
            sum += ((17 - work[0]) * work[2]) + ((work[1] - 17) * work[2] * work[3]);
        return ('$' + String.valueOf(sum));
    }
/*5. Индекс массы тела (ИМТ) определяется путем измерения вашего веса в килограммах и деления на квадрат вашего роста в метрах.
Категории ИМТ таковы: Недостаточный вес: <18,5; нормальный вес: 18.5-24.9, избыточный вес: 25 и более. Создайте функцию,
которая будет принимать вес и рост (в килограммах, фунтах, метрах или дюймах) и возвращать ИМТ и связанную с ним категорию.
Округлите ИМТ до ближайшей десятой.*/
    public static String BMI(String[] fat) {
        double mass = Double.parseDouble(fat[0].split(" ")[0]);
        double height = Double.parseDouble(fat[1].split(" ")[0]);
        String category = " ";
        if (fat[0].contains("pounds"))
            mass = mass * 0.453592;
        if (fat[1].contains("inches"))
            height *= 0.0254;
        double BMI = Math.round((mass/(height * height)) * 10.0)/10.0;
        if (BMI < 18.5)
            category = BMI + " Недостаточный вес";
        if (BMI >= 18.5 && BMI <= 24.9)
            category = BMI + " Нормальный вес";
        if (BMI > 25)
            category = BMI + " Избыточный вес";
        return category;
    }
/*6. Создайте функцию, которая принимает число и возвращает его мультипликативное постоянство, которое представляет
собой количество раз, которое вы должны умножать цифры в num, пока не достигнете одной цифры.*/
    public static int bugger(int num) {
        int k = 0;
        while (num > 9) {
            int num1 = 1;
            while (num > 0) {
                num1 *= num % 10;
                num /= 10;
            }
            num = num1;
            k++;
        }
        return k;
    }
/*7. Напишите функцию, которая преобразует строку в звездную стенографию. Если символ повторяется n раз, преобразуйте его в символ*n.*/
    public static String toStarShorthand(String str) {
        if (str.length() == 0)
            return "";
        int k = 1;
        char n = str.charAt(0);
        String Str = "";
        for(int i=1; i < str.length(); i++) {
            if (str.charAt(i) == n) k++;
            else {
                if (k > 1)
                Str += n  +"*" + k;
            else
                Str += n;
                n = str.charAt(i);
                k = 1;
            }
        }
        if (k > 1)
            Str += n + "*" + k;
        else Str += n;
        return Str;
    }
/*8. Создайте функцию, которая возвращает true, если две строки рифмуются, и false в противном случае. Для целей этого
упражнения две строки рифмуются, если последнее слово из каждого предложения содержит одни и те же гласные. Примечание:
– Без учета регистра.
– Здесь мы не обращаем внимания на такие случаи, как "thyme" и "lime".
– Мы также игнорируем такие случаи, как "away" и "today" (которые технически рифмуются, хотя и содержат разные гласные).*/
    public static boolean doesRhyme(String str, String str1) {
        str = str.substring(str.lastIndexOf(" ") + 1);
        str1 = str1.substring(str1.lastIndexOf(" ") + 1);
        String a = "aeiouyAEIOUY";
        String res = "", res1 = "";
        for (int i = 0; i < str.length(); i++) {
            if (a.indexOf(str.charAt(i)) != -1)
                res += str.charAt(i);
        }
        for (int i = 0; i < str1.length(); i++) {
            if (a.indexOf(str1.charAt(i)) != -1)
                res1 += str1.charAt(i);
        }
        if (res.toLowerCase().equals(res1.toLowerCase()))
            return true;
        else
            return false;
    }
/*9. Создайте функцию, которая принимает два целых числа и возвращает true, если число повторяется три раза подряд
в любом месте в num1 и то же самое число повторяется два раза подряд в num2.*/
    public static boolean trouble(long num1, long num2) {
        String a = Long.toString(num1);
        String b = Long.toString(num2);
        for(int i=0; i < 10; i++) {
            if (a.contains(Integer.toString(i) + i + i ) && b.contains(Integer.toString(i) + i))
                return true;
        }
        return false;
    }
/*10. Предположим, что пара одинаковых символов служит концами книги для всех символов между ними. Напишите функцию,
которая возвращает общее количество уникальных символов (книг, так сказать) между всеми парами концов книги.
Эта функция будет выглядеть следующим образом: countUniqueBooks("stringSequence", "bookEnd")*/
    public static  int countUniqueBooks(String stringSequence, char bookEnd) {
        char[] n = stringSequence.toCharArray();
        int k = 0;
        String str = "";
        int open = -1;
        for (int i = 0; i< n.length-1; i++) {
            if (n[i] == bookEnd) {
                open *= -1;
            }
            else {
                if (open == 1) {
                    if (str.contains(String.valueOf(n[i]))) { }
                        else k++;
                    str += String.valueOf(n[i]);
                }
            }
        }
        return k;
    }
    public static void main (String[] args) {
        /* тест 1 - вернет hello my
                           name is
                           Bessie
                           and this
                           is my
                           essay */
        System.out.println(BessieEssay(10,7, "hello my name is Bessie and this is my essay"));
        // тест 2 - вернет [(), (), ()]
        System.out.println(split("()()()"));
        /* тест 3 - вернет helloEdabit, hello_edabit */
        System.out.println(toCamelCase("hello_edabit"));
        System.out.println(toSnakeCase("helloEdabit"));
        // тест 4 - вернет $240.0
        System.out.println(overTime(new double[]{9, 17, 30, 1.5}));
        // тест 5 - вернет 27.0 Избыточный вес
        System.out.println(BMI(new String[]{"205 pounds", "73 inches"}));
        // тест 6 - вернет 3
        System.out.println(bugger(39));
        // тест 7 - вернет ab*2c*3
        System.out.println(toStarShorthand("abbccc"));
        // тест 8 - вернет true
        System.out.println(doesRhyme("Sam I am!", "Green eggs and ham."));
        // тест 9 - вернет true
        System.out.println(trouble(451999277,41177722899L));
        // тест 10 - вернет 4
        System.out.println(countUniqueBooks("AZYWABBCATTTA", 'A'));
    }
}
