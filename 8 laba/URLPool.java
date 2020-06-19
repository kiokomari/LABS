import java.util.*;
public class URLPool {
    // список для не просмотренных пар <URL, глубина>
    private LinkedList<URLDepthPair> pendingURLs;
    // список для просмотренных пар <URL, глубина>
    public LinkedList<URLDepthPair> processedURLs;
    // список для просмотренных URL
    private ArrayList<String> seenURLs = new ArrayList<>();
    // количество потоков, которые ожидают обработки
    public int waitingThreads;
    int maxDepth;
    // конструктор для инициализации waitingThreads, processedURLs и pendingURLs
    public URLPool(int depthPair) {
        maxDepth = depthPair;
        waitingThreads = 0;
        pendingURLs = new LinkedList<>();
        processedURLs = new LinkedList<>();
    }
    // метод для доступа к waitingThreads
    public synchronized int getWaitThreads() {
        return waitingThreads;
    }
    // метод для получения размера pendingURLs
    public synchronized int size() {
        return pendingURLs.size();
    }
    // метод для добавления новой пары <URL, глубина>
    public synchronized void put(URLDepthPair depthPair) {
        // если был вызван put и есть потоки, находящиеся в ожидании, то надо вызвать эти потоки и уменьшить их счётчик
        if (waitingThreads != 0) {
            --waitingThreads;
            this.notify();
        }
        if (!seenURLs.contains(depthPair.getURL()) &
                !pendingURLs.contains(depthPair)) {
            if (depthPair.getDepth() < maxDepth) {
                pendingURLs.add(depthPair);
            }
            else {
                processedURLs.add(depthPair);
                seenURLs.add(depthPair.getURL());
            }
        }
    }
    // метод для получения следующей пары из пула
    public synchronized URLDepthPair get() {
        URLDepthPair DepthPair = null;
        // пока пул пуст, ждем
        if (pendingURLs.size() == 0) {
            waitingThreads++;
            try {
                this.wait();
            }
            // ловим исключение для прерывания потока
            catch (InterruptedException e) {
                System.err.println("MalformedURLException: " + e.getMessage());
                return null;
            }
        }
        // удаляем первую пару, добавляем к просмотренным и обработанным URL, и возвращаем
        if (!pendingURLs.isEmpty()) {
            DepthPair = pendingURLs.getFirst();
            pendingURLs.removeFirst();
            seenURLs.add(DepthPair.getURL());
            processedURLs.add(DepthPair);
        }
        return DepthPair;
    }
}