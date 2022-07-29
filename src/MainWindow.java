import javax.swing.*;

public class MainWindow {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    public JButton button1;
    public JButton button2;
    public JButton button3;
    public JButton button4;
    public JButton button5;
    public JButton[] buttons = {button1, button2, button3, button4, button5};
    private JPanel panel_settings;
    private JPanel button_panel;
    private JFrame parent;

    public MainWindow(JFrame pParent)
    {
        parent = pParent;
        parent.add(panel1);
        panel_settings.setLayout(new BoxLayout(panel_settings,BoxLayout.Y_AXIS));
    }

    public void add_category(Category t_category) {
        t_category.set_parent(parent);
        panel_settings.add(t_category.get_content());
    }
}
