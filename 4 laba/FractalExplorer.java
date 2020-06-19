import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        displaySize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }
    public void createAndShowGUI() {
        display.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Fractal Explorer");
        //позиция center
        frame.add(display, BorderLayout.CENTER);
        //Кнопка сброса
        JButton resetButton = new JButton("Reset");
        //обработчик сброса
        ResetHandler handler = new ResetHandler();
        resetButton.addActionListener(handler);
        // позиция south
        frame.add(resetButton, BorderLayout.SOUTH);
        //обработчик для мыши
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        //закрытие окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //видимость изображения
        frame.setVisible(true);
        //запрет изменения размера
        frame.setResizable(false);
    }
    private void drawFractal() {
        //проходим через каждый пиксель на дисплее
        for (int x=0; x<displaySize; x++) {
            for (int y=0; y<displaySize; y++) {
                //находим координаты
                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);
                //количество итерации
                int iterations = fractal.numIterations(xCoord, yCoord);
                //черный цвет
                if (iterations == -1) {
                    display.drawPixel(x, y, 0);
                }
                else { //выбрать цвет в зависимости от итерации
                    float hue = 0.7f + (float) iterations / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    //обновляем цвет
                    display.drawPixel(x, y, rgbColor);
                }
            }
        } //обновляем изображение
        display.repaint();
    }
    private class ResetHandler implements ActionListener {
        //сбрасываем в начало и рисуем фрактал
        public void actionPerformed(ActionEvent e) {
            fractal.getInitialRange(range);
            drawFractal();
        }
    }
    private class MouseHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            //Получение координат в области щелчка мыши
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            //перерисовываем фрактал
            drawFractal();
        }
    }
    public static void main(String[] args) {
        FractalExplorer displayExplorer = new FractalExplorer(800);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}