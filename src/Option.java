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

    private JFrame parent;

    public Option(JFrame pParent, String pText, JComponent pComp, String description) {
        // initialises UI elements
        lbl_description = new JLabel(description);
        lbl_description.setBorder(BorderFactory.createLineBorder(Color.BLACK,3,true));
        lbl_option_text = new JLabel(pText);
        final JPanel description_mouse_panel = new JPanel();
        description_mouse_panel.setBackground(Color.cyan);
        try {
            BufferedImage img = ImageIO.read(new File("src/Question-mark_icon.png"));
            lbl_help_icon = new JLabel(new ImageIcon(img));
        }
        catch (Exception e) { lbl_help_icon = new JLabel("failed"); }
        component = pComp;
        parent = pParent;

        // Resizes the fonts
        lbl_option_text.setFont(new Font(lbl_option_text.getFont().getName(),lbl_option_text.getFont().getStyle(),32));
        component.setFont(new Font(component.getFont().getName(), component.getFont().getStyle(), 32));
        lbl_description.setFont(new Font(component.getFont().getName(), component.getFont().getStyle(), 32));

        description_mouse_panel.addMouseListener(new MouseListener() {
            // not needed
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {
                description_mouse_panel.add(lbl_description);
                description_mouse_panel.updateUI();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                description_mouse_panel.remove(lbl_description);
                description_mouse_panel.updateUI();
            }
        });

        // Adds the components
        add(lbl_option_text);
        add(component);
        description_mouse_panel.add(lbl_help_icon);
        add(description_mouse_panel);
    }

    public Option(String pText, JComponent pComp)
    {
        // initialises UI elements
        lbl_option_text = new JLabel(pText);
        component = pComp;

        // Resizes the fonts
        lbl_option_text.setFont(new Font(lbl_option_text.getFont().getName(),lbl_option_text.getFont().getStyle(),32));
        component.setFont(new Font(component.getFont().getName(), component.getFont().getStyle(), 32));

        // Adds the components
        add(lbl_option_text);
        add(component);
    }
}
