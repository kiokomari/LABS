import java.net.*;
public class URLDepthPair {
    // поля для хранения текущего URL и текущей глубины
    private String currentURL;
    private int currentDepth;
    // конструктор, который устанавливает ввод для текущих URL и глубины
    public URLDepthPair(String URL, int depth) {
        currentURL = URL;
        currentDepth = depth;
    }
    // метод, который возвращает текущий URL
    public String getURL() {
        return currentURL;
    }
    // метод, который возвращает текущую глубину
    public int getDepth() {
        return currentDepth;
    }
    // метод, который возвращает текущий URL и текущую глубину в строковом формате
    public String toString() {
        String stringDepth = Integer.toString(currentDepth);
        return stringDepth + '\t' + currentURL;
    }
    // метод, который возвращает путь документа текущего URL
    public String getDocPath() {
        try {
            URL url = new URL(currentURL);
            return url.getPath();
        }
        catch (MalformedURLException e) {
            //System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }
    // метод, который возвращает хост текущего URL
    public String getWebHost() {
        try {
            URL url = new URL(currentURL);
            return url.getHost();
        }
        catch (MalformedURLException e) {
            //System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }
}