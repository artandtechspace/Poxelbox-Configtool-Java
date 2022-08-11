package UI_Elements;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Category {
    private JPanel main_panel;
    private JLabel lbl_category_name;
    private JPanel options_panel;
    public ArrayList<Option> all_options;

    public Category(String pName)
    {
        // Makes this category distinguishable in the GUI
        main_panel.setBackground(new Color(155,150,255));

        // Initialise objects
        all_options = new ArrayList<>();
        options_panel.setLayout(new BoxLayout(options_panel,BoxLayout.Y_AXIS));
        options_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,false));

        // sets the lable of the category to its name
        lbl_category_name.setText(pName);
    }

    /**
     *
     * @return JPanel of the UI
     */
    public JPanel get_content() {
        return main_panel;
    }

    /**
     * Appends a new option to this control element.
     * Creates a new Option object, adds it to its UI and to the ArrayList for all options
     * @param pText Text standing before the option
     */
    public Option add_option(String pText, JComponent pComp) {
        Option t_option = new Option(pText, pComp);
        options_panel.add(t_option);
        all_options.add(t_option);
        return t_option;
    }

    /**
     * Appends a new option to this control element.
     * Creates a new Option object, adds it to its UI and to the ArrayList for all options
     * @param pText Text standing before the option
     */
    public Option add_option(String pText, JComponent pComp, String description) {
        Option t_option = new Option(pText, pComp, description);
        options_panel.add(t_option);
        all_options.add(t_option);
        return t_option;
    }

    public void setParent(JFrame pParent)
    {
        for (Option tOption: all_options) {
            tOption.setParent(pParent);
            tOption.get_value();
        }
    }
}
