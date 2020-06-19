import java.util.LinkedList;
public class CrawlerTask implements Runnable {
    // поле для заданной пары глубин
    public URLDepthPair depthPair;
    // поле для URL пула
    public URLPool pool;
    // конструктор для инициализации пула
    public CrawlerTask (URLPool newPool) {
        pool = newPool;
    }
    // метод для запуска задач в CrawlerTask
    public void run() {
        // достаём из пула следующую пару
        depthPair = pool.get();
        if (depthPair != null)
        {
            int depth = depthPair.getDepth();
            // поиск всех ссылок на рассматриваемом сайте и сохранение их в linksList
            LinkedList<String> linksList;
            linksList = Crawler.getAllLinks(depthPair);
            // перебираем ссылки с сайта
            for (int i = 0; i < linksList.size(); i++) {
                String newURL = linksList.get(i);
                // создаем новую пару для каждой ссылки и добавляем её в пул
                URLDepthPair newDepthPair = new URLDepthPair(newURL, depth + 1);
                pool.put(newDepthPair);
            }
        }
    }
}