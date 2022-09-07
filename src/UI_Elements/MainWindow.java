package UI_Elements;

import javax.swing.*;
import java.util.ArrayList;

public class MainWindow {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    public JButton btn_start_game;
    public JButton btn_stop_game;
    public JButton btn_restart_game;
    public JButton btn_restart_pi;
    public JButton btn_save_settings;
    public JButton btn_reload;
    private JPanel panel_settings;
    private JPanel button_panel;

    private JFrame parent;
    public JButton[] buttons = {btn_start_game, btn_stop_game, btn_restart_game, btn_restart_pi, btn_save_settings, btn_reload};
    public ArrayList<Category> all_categories;

    public MainWindow(JFrame pParent)
    {
        // initiates the UI Components
        panel_settings.setLayout(new BoxLayout(panel_settings,BoxLayout.Y_AXIS));
        parent = pParent;

        all_categories = new ArrayList<>();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setVisible(false);
        }
        btn_save_settings.setVisible(true);
        btn_reload.setVisible(true);
    }

    public void add_category(Category t_category) {
        t_category.setParent(parent);
        all_categories.add(t_category);
        panel_settings.add(new JPanel());
        panel_settings.add(t_category.get_content());
        panel1.updateUI();
    }

    public void show() {
        parent.add(panel1);
        panel1.updateUI();
    }

    public void reset() {
        all_categories = new ArrayList<>();
        panel_settings.removeAll();
    }
}
