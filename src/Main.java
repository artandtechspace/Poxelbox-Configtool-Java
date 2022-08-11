import JSON_Parts.JSON_Interpreter;
import UI_Elements.Category;
import UI_Elements.MainWindow;
import UI_Elements.Option;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main
{
    public static void main(String[] args) {
        // initialising UI
        JFrame frame = new JFrame();
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // requesting data
        Web_Stuff http_stuff = new Web_Stuff("http://127.0.0.1:5000");

        // shows loading screen
        JLabel lbl_loading_message = new JLabel("send http request; waiting for responses.");
        lbl_loading_message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        JPanel t_panel = new JPanel();
        t_panel.add(lbl_loading_message);
        frame.add(t_panel);
        t_panel.updateUI();

        String http_response = http_stuff.get_response().body();
        //http_response = "{\"abc\":{\"APP_SPEED\":{\"value\":1,\"title\":\"Game-Speed\",\"desc\":\"How fast the game is running. Between 1 and 10\",\"type\":\"int\",\"min\":1,\"max\":10},\"LED_PIXEL_SCALE\":{\"value\":30,\"title\":null,\"desc\":null,\"type\":\"int\",\"min\":5,\"max\":null}},\"def\":{\"ESP_BAUD\":{\"value\":9600,\"title\":null,\"desc\":null,\"type\":\"int_preset\",\"presets\":[115200,9600]}}}";
        frame.remove(t_panel);
        JSON_Interpreter test = new JSON_Interpreter(http_response);

        MainWindow window = new MainWindow(frame);
        window.btn_save_settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Category t_cat: window.all_categories)
                    for (Option t_opt : t_cat.all_options)
                        System.out.println(t_opt.get_value());
                http_stuff.send_data("{\"Settings\": {\"APP_SPEED\": 3, \"LED_PIXEL_SCALE\": 69, \"ESP_BAUD\": 115200}}");
            }
        });
        for (Category t_cat: test.generate_components()) {
            window.add_category(t_cat);
        }

    }
}
