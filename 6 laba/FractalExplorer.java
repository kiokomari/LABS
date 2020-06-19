import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.*;
import java.awt.image.*;

public class FractalExplorer {
    private JButton saveButton;
    private JButton resetButton;
    private JComboBox ComboBox;
    private int rowsRemaining;
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
        // позиция center
        frame.add(display, BorderLayout.CENTER);
        // кнопка сброса
        resetButton = new JButton("Reset");
        // обработчик кнопки сброс
        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);
        // обработчик для мыши
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        // закрытие окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // поле со списком
        ComboBox = new JComboBox();
        // добавляем объекты фрактала в поле со списком
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        ComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        ComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        ComboBox.addItem(burningShipFractal);
        // обработчик кнопок в поле со списком
        ButtonHandler fractalChooser = new ButtonHandler();
        ComboBox.addActionListener(fractalChooser);
        // верхняя панель
        JPanel Panel = new JPanel();
        JLabel Label = new JLabel("Fractal:");
        Panel.add(Label);
        Panel.add(ComboBox);
        frame.add(Panel, BorderLayout.NORTH);
        // нижняя панель
        saveButton = new JButton("Save Image");
        JPanel BottomPanel = new JPanel();
        BottomPanel.add(saveButton);
        BottomPanel.add(resetButton);
        frame.add(BottomPanel, BorderLayout.SOUTH);
        // обработчик кнопки save
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);
        frame.pack();
        // видимость изображения
        frame.setVisible(true);
        // запрет изменения размера
        frame.setResizable(false);
    }
    private void drawFractal() {
        // отключаем все элементы управления пользовательского интерфейса во время рисования
        enableUI(false);
        rowsRemaining = displaySize;
        // перебираем каждую строку на дисплее и рисуем
        for (int x=0; x < displaySize; x++) {
            FractalWorker drawRow = new FractalWorker(x);
            drawRow.execute();
        }
    }
    // Включаем или отключаем кнопки интерфейса и поля со списком
    private void enableUI(boolean val) {
        ComboBox.setEnabled(val);
        resetButton.setEnabled(val);
        saveButton.setEnabled(val);
    }
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (e.getSource() instanceof JComboBox) {
                // выводим фракталы
                JComboBox Source = (JComboBox) e.getSource();
                fractal = (FractalGenerator) Source.getSelectedItem();
                fractal.getInitialRange(range);
                drawFractal();
            }
            // сбрасываем
            else if (command.equals("Reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            }
            // сохраняем
            else if (command.equals("Save Image")) {
                // выбираем файл для сохранения
                JFileChooser FileChooser = new JFileChooser();
                // только png
                FileFilter Filter = new FileNameExtensionFilter("PNG Images", "png");
                FileChooser.setFileFilter(Filter);
                FileChooser.setAcceptAllFileFilterUsed(false);
                // выбор директории
                int userSelection = FileChooser.showSaveDialog(display);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // доступ к файлу и имени файла
                    File file = FileChooser.getSelectedFile();
                    String file_name = file.toString();
                    // сохраняем фрактальное изображение
                    try {
                        BufferedImage image = display.getImage();
                        ImageIO.write(image, "png", file);
                    }
                    // исключения
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display,
                                exception.getMessage(), "Cannot Save Image",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else return;
            }
        }
    }
    private class MouseHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (rowsRemaining != 0) {
                return;
            }
            // получение координат в области щелчка мыши
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            // перерисовываем фрактал
            drawFractal();
        }
    }
    // вычисляем значения цвета для одного ряда фрактала.
    private class FractalWorker extends SwingWorker<Object, Object> {
        int yCoordinate;
        int[] computedRGBValues;
        private FractalWorker(int row) {
            yCoordinate = row;
        }
        protected Object doInBackground() {
            computedRGBValues = new int[displaySize];
            // перебираем все пиксели в строке
            for (int i = 0; i < computedRGBValues.length; i++) {
                // Находим соответствующие координаты x и y
                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, i);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, yCoordinate);
                // вычисляем количество итераций
                int iterations = fractal.numIterations(xCoord, yCoord);
                // вычисляем значения RGB массива для черного
                if (iterations == -1) {
                    computedRGBValues[i] = 0;
                }
                else {
                    // выбираем значение цвета в зависимости от итерации
                    float hue = 0.7f + (float) iterations / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    // обновляем массив
                    computedRGBValues[i] = rgbColor;
                }
            }
            return null;
        }
        protected void done() {
            /*Перебираем массив, рисуем в пикселях, которые были вычислены в doInBackground().
              Перерисовываем строку, которая была изменена.*/
            for (int i = 0; i < computedRGBValues.length; i++) {
                display.drawPixel(i, yCoordinate, computedRGBValues[i]);
            }
            display.repaint(0, 0, yCoordinate, displaySize, 1);
            // уменьшаем оставшиеся строки
            rowsRemaining--;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }
    }
    public static void main(String[] args) {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}