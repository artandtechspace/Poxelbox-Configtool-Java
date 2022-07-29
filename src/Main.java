import javax.swing.*;

public class Main
{
    public static void main(String[] args) {
        JSON_Interpreter test = new JSON_Interpreter();
        test.generate_components();
        JFrame frame = new JFrame();
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainWindow window = new MainWindow(frame);
        for (Category t_cat: test.generate_components()) {
            window.add_category(t_cat);
        }
        frame.setVisible(true);
    }
}
