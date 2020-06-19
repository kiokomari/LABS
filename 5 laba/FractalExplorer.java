import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.*;
import java.awt.image.*;

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
        // позиция center
        frame.add(display, BorderLayout.CENTER);
        // кнопка сброса
        JButton resetButton = new JButton("Reset");
        // обработчик кнопки сброс
        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);
        // обработчик для мыши
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        // закрытие окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // поле со списком
        JComboBox ComboBox = new JComboBox();
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
        JButton saveButton = new JButton("Save Image");
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
        // проходим через каждый пиксель на дисплее
        for (int x=0; x<displaySize; x++) {
            for (int y=0; y<displaySize; y++) {
                // находим координаты
                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);
                // количество итерации
                int iterations = fractal.numIterations(xCoord, yCoord);
                // черный цвет
                if (iterations == -1) {
                    display.drawPixel(x, y, 0);
                }
                else { // выбрать цвет в зависимости от итерации
                    float hue = 0.7f + (float) iterations / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    // обновляем цвет
                    display.drawPixel(x, y, rgbColor);
                }
            }
        } // обновляем изображение
        display.repaint();
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
    public static void main(String[] args) {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}