import java.awt.geom.Rectangle2D;
// подкласс FractalGenerator
public class Tricorn extends FractalGenerator {
    // константа с максимальным количеством итераций
    public static final int MAX_ITERATIONS = 2000;
    // начальный диапазон
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }
    //Вычисляем количество итераций с помощью мнимой и реальной части
    public int numIterations(double x, double y) {
        int iterations = 0;
        double zreal = 0;
        double zimaginary = 0;

        while (iterations < MAX_ITERATIONS && zreal * zreal + zimaginary * zimaginary < 4) {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = -2 * zreal * zimaginary + y;
            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;
            iterations += 1;
        } // Если достигнут максимум, вернуть -1 (точка не вышла за пределы границы)
        if (iterations == MAX_ITERATIONS) {
            return -1;
        }
        return iterations;
    }
    // возвращаем имя фрактала
    public String toString() {
        return "Tricorn";
    }
}