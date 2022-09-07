import JSON_Parts.JSON_Interpreter;
import UI_Elements.Category;
import UI_Elements.MainWindow;
import UI_Elements.Option;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main {
    public static JFrame frame;
    public static MainWindow window;
    public static boolean retry = true;
    private static Web_Stuff http_stuff;

    public static void main(String[] args) {
        // initialising UI
        frame = new JFrame();
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // shows loading screen
        JPanel start_panel = new JPanel();

        JLabel lbl_loading_message = new JLabel("send http request; waiting for responses.");
        lbl_loading_message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        JButton retry_connection = new JButton("retry ?");
        retry_connection.addActionListener(e -> retry = true);
        retry_connection.setVisible(false);

        start_panel.add(lbl_loading_message);
        start_panel.add(retry_connection);
        frame.add(start_panel);
        start_panel.updateUI();

        window = new MainWindow(frame);
        window.btn_reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retry = true;
                window.reset();
            }
        });
        window.btn_save_settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Object> output = new ArrayList<>();
                for (Category t_cat : window.all_categories)
                    for (Option t_opt : t_cat.all_options) {
                        output.add(t_opt.get_value());
                    }
                JSON_Interpreter.build_answer(output.toArray());
                http_stuff.send_data(JSON_Interpreter.get_answer());
            }
        });

        while(true) {
            if (retry) {
                try {
                    build_menu();
                    retry = false;
                    frame.remove(start_panel);
                    window.show();
                } catch (Exception exception) {
                    retry = false;
                    lbl_loading_message.setText("Could not get http response, due to: " + exception.getMessage());
                    retry_connection.setVisible(true);
                    start_panel.updateUI();
                }
            }
        }
    }

    public static void build_menu() throws ExecutionException, InterruptedException {
        // requesting data
        http_stuff = new Web_Stuff("http://10.100.14.161:5555");

        String http_response = http_stuff.get_response().body();
        //http_response = "{\"abc\":{\"APP_SPEED\":{\"value\":1,\"title\":\"Game-Speed\",\"desc\":\"How fast the game is running. Between 1 and 10\",\"type\":\"int\",\"min\":1,\"max\":10},\"LED_PIXEL_SCALE\":{\"value\":30,\"title\":null,\"desc\":null,\"type\":\"int\",\"min\":5,\"max\":null}},\"def\":{\"ESP_BAUD\":{\"value\":9600,\"title\":null,\"desc\":null,\"type\":\"int_preset\",\"presets\":[115200,9600]}}}";
        JSON_Interpreter interpreter = new JSON_Interpreter(http_response);

        for (Category t_cat : interpreter.generate_components()) {
            window.add_category(t_cat);
        }
    }
}
