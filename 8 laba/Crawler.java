import java.io.*;
import java.net.*;
import java.util.*;
public class Crawler {
    public static void main(String[] args) {
        String[] arg = new String[] {"http://www.mtuci.ru/","2","3"};
        // переменные для текущей глубины и запрошенного количества потоков
        int depth = 0;
        int numThreads = 0;
        // проверяем, правильное ли количество параметров было введено
        if (arg.length != 3) {
            System.out.println("Введите URL, глубину и количество потоков сканера");
            System.exit(1);
        }
        else {
            try {
                depth = Integer.parseInt(arg[1]);
                numThreads = Integer.parseInt(arg[2]);
            } catch (NumberFormatException nfe) {
                // проверяем являются ли глубина и количество потоков цифрами
                System.out.println("Введите URL, глубину и количество потоков сканера");
                System.exit(1);
            }
        }
        // создаем глубину URL-пары для представления веб-сайта
        URLDepthPair currentDepthPair = new URLDepthPair(arg[0], 0);
        // создаем URL пул и добавляем введенный пользователем веб-сайт
        URLPool pool = new URLPool(depth);
        pool.put(currentDepthPair);
        // поле для начального количества потоков
        int initialActiveThreads = Thread.activeCount();
        /* пока количество ожидающих потоков не равно их запрошенному числу, и
        если количество всех потоков меньше их запрошенного количества, то
        создаём больше потоков и запускаем их на CrawlerTask. Иначе ждём. */
        while (pool.getWaitThreads() != numThreads) {
            if (Thread.activeCount() - initialActiveThreads < numThreads) {
                CrawlerTask crawler = new CrawlerTask(pool);
                new Thread(crawler).start();
            }
            else {
                try {
                    Thread.sleep(500); // 0,5 секунды
                }
                // ловим исключения для прерывания потока
                catch (InterruptedException ie) {
                    System.out.println("Произошло прерывание потока" + " игнорируем...");
                }
            }
        }
        // выводим все обработанные URL с глубиной
        Iterator<URLDepthPair> iter = pool.processedURLs.iterator();
        String temp = "";
        while (iter.hasNext()) {
            URLDepthPair s = iter.next();
            temp+= temp.contains(s.toString()) ? "" : s.toString() + " ";
        }
        for (int i = 0; i < depth + 1; i++)
            for (int j = 0; j < temp.length();)
            {
                if (temp.indexOf(String.valueOf(i) + "\t", j) != -1)
                {
                    System.out.println(temp.substring(temp.indexOf(String.valueOf(i) + "\t", j),
                            temp.indexOf(" ", temp.indexOf(String.valueOf(i) + "\t", j))));
                    if (j != temp.indexOf(" ", temp.indexOf(String.valueOf(i) + "\t", j)))
                        j = temp.indexOf(" ", temp.indexOf(String.valueOf(i) + "\t", j));
                    else
                        break;
                }
                else
                    break;
            }
        // выход
        System.exit(0);
    }
    public static LinkedList<String> getAllLinks(URLDepthPair DepthPair) {
        // создаем связанный список LinkedList<String>, в котором будут храниться ссылки, что мы находим
        LinkedList<String> URLs = new LinkedList<String>();
        // создаём новый сокет
        Socket sock;
        // инициализируем новый сокет из строки String, содержащей имя хоста, и из номера порта, равного 80 (http)
        try {
            sock = new Socket(DepthPair.getWebHost(), 80);
        }
        // ловим исключения неизвестного хоста и возвращаем пустой список
        catch (UnknownHostException e) {
            //System.err.println("UnknownHostException: " + e.getMessage());
            return URLs;
        }
        // ловим исключения ввода-вывода и возвращаем пустой список
        catch (IOException ex) {
            //System.err.println("IOException: " + ex.getMessage());
            return URLs;
        }
        try {
            // устанавливаем таймаут сокета на 3 секунды
            sock.setSoTimeout(3000);
        }
        // ловим исключения сокета и возвращаем пустой список
        catch (SocketException exc) {
            System.err.println("SocketException: " + exc.getMessage());
            return URLs;
        }
        // поля для хранения пути к файлу и хоста
        String docPath = DepthPair.getDocPath();
        String webHost = DepthPair.getWebHost();
        // создаем поток вывода
        OutputStream outStream;
        // возвращаем OutputStream связанный с сокетом используемый для передачи данных
        try {
            outStream = sock.getOutputStream();
        }
        // ловим исключения ввода-вывода и возвращаем пустой список
        catch (IOException exce) {
            //System.err.println("IOException: " + exce.getMessage());
            return URLs;
        }
        // создаем PrintWriter, true означает, что PrintWriter будет сбрасываться после каждого вывода
        PrintWriter pw = new PrintWriter(outStream, true);
        // отправляем запрос на сервер
        pw.println("GET " + docPath + " HTTP/1.1");
        pw.println("Host: " + webHost);
        pw.println("Connection: close");
        pw.println();
        // создаем поток ввода
        InputStream inStream;
        // возвращает InputStream, связанный с объектом Socket, используемого для приема данных
        try {
            inStream = sock.getInputStream();
        }
        // ловим исключения ввода-вывода и возвращаем пустой список
        catch (IOException excep) {
            //System.err.println("IOException: " + excep.getMessage());
            return URLs;
        }
        // создаем новый InputStreamReader и BufferedReader для чтения строк с сервера
        InputStreamReader in = new InputStreamReader(inStream);
        BufferedReader br = new BufferedReader(in);
        while (true) {
            String line;
            try {
                line = br.readLine();
            }
            // ловим исключения ввода-вывода и возвращаем пустой список
            catch (IOException except) {
                System.err.println("IOException: " + except.getMessage());
                return URLs;
            }
            // прекращаем чтения документа
            if (line == null)
                break;
            // переменные для индексов
            int beginIndex;
            int endIndex;
            int index = 0;
            while (true) {
                // константа для строки, указывающей на ссылку
                String URL_PREFIX = "<a href=\"http";
                int p = "<a href=\"".length();
                // константа для строки, указывающей конец веб-хоста
                String END_URL = "\"";
                // поиск нашего начала в текущей строке
                index = line.indexOf(URL_PREFIX, index);
                if (index == -1)
                    break;
                // двигаем текущий индекс и устанавливаем в beginIndex
                index += p;
                beginIndex = index;
                // ищем наш конец в текущей строке и устанавливаем в endIndex
                endIndex = line.indexOf(END_URL, index);
                index = endIndex;
                // устанавливаем ссылку на подстроку между начальным и конечным индексом, добавляем к нашему списку URL
                try {
                    String newLink = line.substring(beginIndex, endIndex);
                    URLs.add(newLink);
                }
                // ловим исключения несуществующего индекса в массиве
                catch (IndexOutOfBoundsException ignored)
                {
                    break;
                }
            }
        }
        // возвращаем список URL
        return URLs;
    }
}