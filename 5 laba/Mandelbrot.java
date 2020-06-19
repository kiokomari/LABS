import java.awt.geom.Rectangle2D;
// подкласс FractalGenerator
public class Mandelbrot extends FractalGenerator {
    // константа с максимальным количеством итераций
    public static final int MAX_ITERATIONS = 2000;
    // начальный диапазон
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }
    //Вычисляем количество итераций с помощью мнимой и реальной части
    public int numIterations(double x, double y) {
        int iterations = 0;
        double zreal = 0;
        double zimaginary = 0;

        while (iterations < MAX_ITERATIONS && zreal * zreal + zimaginary * zimaginary < 4) {
            double zreal1 = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginary1 = 2 * zreal * zimaginary + y;
            zreal = zreal1;
            zimaginary = zimaginary1;
            iterations += 1;
        } // Если достигнут максимум, вернуть -1 (точка не вышла за пределы границы)
        if (iterations == MAX_ITERATIONS) {
            return -1;
        }
        return iterations;
    }
    // возвращаем имя фрактала
    public String toString() {
        return "Mandelbrot";
    }
}