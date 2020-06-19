import java.util.*;
public class Tasks6 {
/*1. Число Белла – это количество способов, которыми массив из n элементов может быть разбит на непустые подмножества.
Создайте функцию, которая принимает число n и возвращает соответствующее число Белла.*/
    public static int bell(int n) {
        int sum = 0;
        int[][] mass = new int[n + 1][n + 1];
        mass[0][0] = 1;
        mass[n][n] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < n; j++) {
                mass[i][j] = mass[i - 1][j - 1] + j*mass[i - 1][j];
            }
        }
        for (int i = 0; i <= n; i++)
            sum += mass[n][i];
        return sum;
    }
/*2. В «поросячьей латыни» (свинский латинский) есть два очень простых правила:
– Если слово начинается с согласного, переместите первую букву (буквы) слова до гласного до конца слова и добавьте «ay» в конец:
have ➞ avehay, cram ➞ amcray, take ➞ aketay, cat ➞ atcay, shrimp ➞ impshray, trebuchet ➞ ebuchettray.
– Если слово начинается с гласной, добавьте "yay" в конце слова: ate ➞ ateyay, apple ➞ appleyay, oaken ➞ oakenyay.
Напишите две функции, чтобы сделать переводчик с английского на свинский латинский. Первая функция translateWord
получает слово на английском и возвращает это слово, переведенное на латинский язык. Вторая функция translateSentence
берет английское предложение и возвращает это предложение, переведенное на латинский язык. Примечание:
– Регулярные выражения помогут вам не исказить пунктуацию в предложении.
– Если исходное слово или предложение начинается с заглавной буквы, перевод должен сохранить свой регистр.*/
    public static String translateWord(String str) {
        if (str.isEmpty())
            return "";
        else if (str.matches("[aeiyouAEIYOU].*"))
            return str + "yay";
        else {
            String str1 = str.split("[aeiyouAEIYOU]")[0];
            return str.replaceFirst(str1 ,"") + str1 + "ay";
        }
    }
    public static String translateSentence(String str) {
        String res = "";
        String str1 = "";
        for (char a: str.toCharArray()){
            if (Character.isLetter(a))
                str1 += a;
            else {
                res += translateWord(str1);
                res += a;
                str1 = "";
            }
        }
        return res;
    }
/*3. Учитывая параметры RGB (A) CSS, определите, является ли формат принимаемых значений допустимым или нет.
Создайте функцию, которая принимает строку (например, " rgb(0, 0, 0)") и возвращает true, если ее формат правильный,
в противном случае возвращает false.*/
    public static boolean validColor(String str) {
            if (!str.contains("rgb"))
                return false;
            String str1 = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
            String[] s = str1.split(",");
            if (str.contains("rgba") && s.length != 4)
                return false;
            if (str.contains("rgb") && !str.contains("rgba") && s.length != 3)
                return false;
            try {
            for (int i = 0; i < 3; i++) {
                if (Integer.parseInt(s[i]) < 0 || Integer.parseInt(s[i]) > 255)
                    return false;
            }
            if (s.length == 4) {
                if (Double.parseDouble(s[3]) < 0 || Double.parseDouble(s[3]) > 1)
                    return false;
            }
            }
            catch (Exception e) {
                return false;
            }
            return true;
    }
/*4. Создайте функцию, которая принимает URL (строку), удаляет дублирующиеся параметры запроса и параметры,
указанные во втором аргументе (который будет необязательным массивом). Примечание:
– Второй аргумент paramsToStrip является необязательным.
– paramsToStrip может содержать несколько параметров.
– Если есть повторяющиеся параметры запроса с разными значениями, используйте значение последнего встречающегося параметра.*/
    public static String stripUrlParams(String URL, String ...paramsToStrip) {
        if (!URL.contains("?"))
            return URL;
        String res = "";
        Map<String, String> map = new HashMap<>();
        String[] str = URL.split("\\?");
        res += str[0];
        if (str.length != 1) {
            str = str[1].split("&");
            for (int i = 0; i < str.length; i++) {
                String[] str1 = str[i].split("=");
                 map.put(str1[0], str1[1]);
            }
            res += "?";
        }
        if (paramsToStrip != null) {
            for (String param : paramsToStrip) {
                map.remove(param);
            }
        }
        for (String k : map.keySet()) {
            res += k + "=" + map.get(k) + "&";
        }
        return res.substring(0,res.length()-1);
    }
/*5. Напишите функцию, которая извлекает три самых длинных слова из заголовка газеты и преобразует их в хэштеги.
Если несколько слов одинаковой длины, найдите слово, которое встречается первым. Примечание:
– Если заголовок содержит менее 3 слов, просто расположите слова в заголовке по длине в порядке убывания.
– Пунктуация не считается с длиной слова.*/
    public static String[] getHashTags (String str) {
        String[] res = { "", "", ""};
        String[] str1 = str.split(" ");
        String max = "", max1 = "";
        int k = 0;
        while (k < 3) {
            for (String s : str1) {
                if (!Character.isLetter(s.charAt(s.length() - 1)))
                    s = s.substring(0, s.length() - 1);
                if (max.length() < s.length() && !max1.contains(s))
                    max = s;
            }
            if (max != "")
                res[k] = "#" + max.toLowerCase();
            max1 += max;
            max = "";
            k++;
        }
        return res;
    }
/*6. Последовательность Улама начинается с: ulam = [1, 2]. Следующее число в последовательности — это наименьшее
положительное число, равное сумме двух разных чисел (которые уже есть в последовательности) ровно одним способом.
Тривиально, это 3, так как в стартовой последовательности есть только 2 числа ulam = [1, 2, 3]. Следующее число 4,
которое является суммой 3 + 1. 4 также равно 2 + 2, но это уравнение не учитывается, так как 2 добавления должны быть
различны ulam = [1, 2, 3, 4]. Следующее число не может быть 5, так как 5 = 1 + 4, но также и 5 = 2 + 3. Должен быть
только один способ сделать число Улама из 2 различных добавлений, найденных в последовательности. Следующее число
6 = 2 + 4. Есть 2 способа сделать 7 (1 + 6 или 3 + 4), поэтому дальше следует – 8 = 2 + 6. И так далее
ulam = [1, 2, 3, 4, 6, 8, 11, 13, 16, 18, 26, 28, 36, 38, 47, 48, 53, …]. Создайте функцию, которая принимает число n
и возвращает n-е число в последовательности Улама.*/
    public static int ulam(int n) {
        Vector<Integer> mass = new Vector<>();
        mass.add(1);
        mass.add(2);
        for (int i = 3; i < 2000; i++) {
            int k = 0;
            for (int j = 0; j < mass.size() - 1; j++) {
                for (int m = j + 1; m < mass.size(); m++) {
                    if (mass.get(j) + mass.get(m) == i) k++;
                    else if (k > 1)
                        break;
                }
                if (k > 1)
                    break;
            }
            if (k == 1)
                mass.add(i);
        }
        return mass.get(n - 1);
    }
/*7. Напишите функцию, которая возвращает самую длинную неповторяющуюся подстроку для строкового ввода. Примечание:
– Если несколько подстрок связаны по длине, верните ту, которая возникает первой.*/
    public static String longestNonrepeatingSubstring(String str) {
        String max = "", res = "";
        for (int i = 0; i < str.length(); i++) {
            String str1 = Character.toString(str.charAt(i));
            if (!max.contains(str1))
                max += str1;
            else {
                if (res.length() < max.length())
                    res = max;
                max = str1;
            }
            if (res.length() < max.length())
                res = max;
        }
        return res;
    }
/*8. Создайте функцию, которая принимает арабское число и преобразует его в римское число. Примечание:
– Все римские цифры должны быть возвращены в верхнем регистре.
– Самое большое число, которое может быть представлено в этой нотации, - 3,999.*/
    public static String convertToRoman(int n) {
        if (n < 1 || n > 3999)
            return "Недопустимое значение";
        String res = "";
        while (n >= 1000) {
            res += "M";
            n -= 1000;
        }
        while (n >= 900) {
            res += "CM";
            n -= 900;
        }
        while (n >= 500) {
            res += "D";
            n -= 500;
        }
        while (n >= 400) {
            res += "CD";
            n -= 400;
        }
        while (n >= 100) {
            res += "C";
            n -= 100;
        }
        while (n >= 90) {
            res += "XC";
            n -= 90;
        }
        while (n >= 50) {
            res += "L";
            n -= 50;
        }
        while (n >= 40) {
            res += "XL";
            n -= 40;
        }
        while (n >= 10) {
            res += "X";
            n -= 10;
        }
        while (n >= 9) {
            res += "IX";
            n -= 9;
        }
        while (n >= 5) {
            res += "V";
            n -= 5;
        }
        while (n >= 4) {
            res += "IV";
            n -= 4;
        }
        while (n >= 1) {
            res += "I";
            n -= 1;
        }
        return res;
    }
/*9. Создайте функцию, которая принимает строку и возвращает true или false в зависимости от того,
является ли формула правильной или нет.*/
    public static boolean formula(String str) {
        if (!(str.contains("=")))
            return false;
        String[] form = str.split("=");
        double[] res = new double[form.length];
        for (int j =0; j < form.length; j++) {
            if (form[j].length() == 1)
                res[j] = Double.parseDouble(form[j]);
            else {
                String[] str1 = form[j].split(" ");
                for (int i = 0; i < str1.length; i++) {
                    switch (str1[i]) {
                        case "+":
                            str1[i+1] = String.valueOf(Double.parseDouble(str1[i-1]) + Double.parseDouble(str1[i+1]));
                            break;
                        case "-":
                            str1[i+1] = String.valueOf(Double.parseDouble(str1[i-1]) - Double.parseDouble(str1[i+1]));
                            break;
                        case "*":
                            str1[i+1] = String.valueOf(Double.parseDouble(str1[i-1]) * Double.parseDouble(str1[i+1]));
                            break;
                        case "/":
                            str1[i+1] = String.valueOf(Double.parseDouble(str1[i-1]) / Double.parseDouble(str1[i+1]));
                            break;
                        default:
                            break;
                    }
                }
                res[j] = Double.parseDouble(str1[str1.length - 1]);
            }
        }
        double check = res[0];
        for (int i = 1; i < res.length; i++) {
            if (res[i] != check)
                return false;
        }
        return true;
    }
/*10. Число может не быть палиндромом, но его потомком может быть. Прямой потомок числа создается путем суммирования
каждой пары соседних цифр, чтобы создать цифры следующего числа. Например, 123312 – это не палиндром, а его следующий
потомок 363, где: 3 = 1 + 2; 6 = 3 + 3; 3 = 1 + 2. Создайте функцию, которая возвращает значение true, если само число
является палиндромом или любой из его потомков вплоть до 2 цифр (однозначное число — тривиально палиндром). Примечание:
– Числа всегда будут иметь четное число цифр.*/
    public static boolean palindromedescendant(long num) {
        String n = Long.toString(num);
        while (true) {
            String rev = new StringBuffer(n).reverse().toString();
            if (n.equals(rev)) {
                return true;
            }
            else {
                if (n.length() % 2 != 0)
                    return false;
                String sum = "";
                for (int i = 1; i < n.length(); i += 2) {
                    sum += Long.parseLong(String.valueOf(n.charAt(i))) + Long.parseLong(String.valueOf(n.charAt(i-1)));
                }
                n = sum;
            }
        }
    }
    public static void main (String[] args) {
        //тест 1 - вернет 5
        System.out.println(bell(3));
        // тест 2 - вернет agflay и Iyay ikelay otay eatyay oneyhay afflesway.
        System.out.println(translateWord("flag"));
        System.out.println(translateSentence("I like to eat honey waffles."));
        // тест 3 - вернет true
        System.out.println(validColor("rgba(0,0,0,0.123456789)"));
        // тест 4 - вернет https://edabit.com?a=2&b=2
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2"));
        // тест 5 - вернет [#avocado, #became, #global]
        System.out.println(Arrays.toString(getHashTags("How the Avocado Became the Fruit of the Global Trade")));
        // тест 6 - вернет 1856
        System.out.println(ulam(206));
        // тест 7 - вернет abc
        System.out.println(longestNonrepeatingSubstring("abcabcbb"));
        // тест 8 - вернет XVI
        System.out.println(convertToRoman(16));
        // тест 9 - вернет true
        System.out.println(formula("6 * 4 + 3.2 = 27.2"));
        // тест 10 - вернет true
        System.out.println(palindromedescendant(11211230));
    }
}
