package UI_Elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Option extends JPanel {
    private JComponent component;
    private JLabel lbl_option_text;
    private JLabel lbl_help_icon;
    private JLabel lbl_description;
    private Popup pop_description;

    private JFrame parent;

    public Option(String pText, JComponent pComp, String description) {
        // initialises UI elements
        lbl_description = new JLabel(description);
        lbl_description.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));
        lbl_option_text = new JLabel(pText);
        try {
            BufferedImage img = ImageIO.read(new File("src/UI_Elements/Question-mark_icon.png"));
            lbl_help_icon = new JLabel(new ImageIcon(img));
        }
        catch (Exception e) { lbl_help_icon = new JLabel("failed"); }
        component = pComp;

        // Resizes the fonts
        lbl_option_text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,12));
        component.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,12));
        lbl_description.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,12));

        lbl_help_icon.addMouseListener(new MouseListener() {
            // not needed
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {
                int pos_x = e.getXOnScreen() - lbl_description.getWidth();
                int pos_y = e.getYOnScreen() + 1;
                pop_description = PopupFactory.getSharedInstance().getPopup(parent,lbl_description,pos_x,pos_y);
                pop_description.show();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pop_description.hide();
            }
        });

        // Adds the components
        add(lbl_option_text);
        add(component);
        add(lbl_help_icon);
    }

    public Option(String pText, JComponent pComp)
    {
        // initialises UI elements
        lbl_option_text = new JLabel(pText);
        component = pComp;

        // Resizes the fonts
        lbl_option_text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,12));
        component.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,12));

        // Adds the components
        add(lbl_option_text);
        add(component);
    }

    /** Returns the value set in the component, if it matches one of the preset classes, else returns null.
     * @return specific component's value
     */
    public Object get_value() {
        if (component.getClass().equals(JSpinner.class)) {
            JSpinner t_spinner = (JSpinner) component;
            return t_spinner.getValue();
        } else if (component.getClass().equals(JComboBox.class)) {
            JComboBox t_comboBox = (JComboBox) component;
            return t_comboBox.getSelectedItem();
        } else if (component.getClass().equals(JColorChooser.class)) {
            JColorChooser t_colorChooser = (JColorChooser) component;
            return t_colorChooser.getColor().getRGB();
        }
        return null;
    }

    public void setParent(JFrame pParent)
    {
        parent = pParent;
    }
}
