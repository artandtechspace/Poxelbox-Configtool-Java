import JSON_Parts.JSON_Interpreter;
import UI_Elements.Category;
import UI_Elements.MainWindow;
import UI_Elements.Option;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Main {
    public static JFrame frame;
    public static MainWindow window;
    public static boolean retry = true;
    private static Web_Stuff http_stuff;
    private static String ip_adresse;
    private static JTextField ip_field;

    public static void main(String[] args) {
        // initialising UI
        frame = new JFrame();
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // gets ip adresse
        JPanel get_ip_adresse = new JPanel();
        ip_field = new JTextField("192.168.0.0:5555");
        ip_field.setToolTipText("enter IP Address for py");
        JButton ip_confirm_button = new JButton("confirm");

        ip_adresse = null;

        ip_confirm_button.addActionListener(e -> {
            ip_adresse = "http://" + ip_field.getText();
            get_ip_adresse.setVisible(false);
        });

        get_ip_adresse.add(new JLabel("enter IP Address: "));
        get_ip_adresse.add(ip_field);
        get_ip_adresse.add(ip_confirm_button);

        // loading screen
        JPanel start_panel = new JPanel();

        JLabel lbl_loading_message = new JLabel("send http request; waiting for responses.");
        lbl_loading_message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        lbl_loading_message.setVisible(false);

        JButton retry_connection = new JButton("retry ?");
        retry_connection.addActionListener(e -> retry = true);
        retry_connection.setVisible(false);

        start_panel.add(get_ip_adresse);
        start_panel.add(lbl_loading_message);
        start_panel.add(retry_connection);
        frame.add(start_panel);
        start_panel.updateUI();

        // Main-window button-functions
        window = new MainWindow(frame);
        window.btn_reload.addActionListener(e -> {
            retry = true;
            window.reset();
        });
        window.btn_save_settings.addActionListener(e -> {
            ArrayList<Object> output = new ArrayList<>();
            for (Category t_cat : window.all_categories)
                for (Option t_opt : t_cat.all_options) {
                    output.add(t_opt.get_value());
                }
            JSON_Interpreter.build_answer(output.toArray());
            http_stuff.send_data(JSON_Interpreter.get_answer());
        });

        while(true) {
            if (retry && !Objects.equals(ip_adresse, null)) {
                try {
                    lbl_loading_message.setVisible(true);
                    build_menu();
                    retry = false;
                    frame.remove(start_panel);
                    window.show();
                } catch (Exception exception) {
                    retry = false;
                    lbl_loading_message.setText("Could not get http response, due to: " + exception.getMessage());
                    retry_connection.setVisible(true);
                    get_ip_adresse.setVisible(true);
                    start_panel.updateUI();
                }
            }
        }
    }

    public static void build_menu() throws ExecutionException, InterruptedException {
        // requesting data
        http_stuff = new Web_Stuff(ip_adresse);

        String http_response = http_stuff.get_response().body();
        //http_response = "{\"Settings\": {\"LED_PIXEL_SCALE\": {\"value\": 35, \"title\": \"PyGame-Pixel-Scale\", \"desc\": \"How many Screen-Pixel one Game-Pixel uses\", \"type\": \"int\", \"min\": 5, \"max\": 100}, \"ESP_BAUD\": {\"value\": 9600, \"title\": \"Esp-Baud\", \"desc\": \"Baud-rate that is used to communicate with the Esp32\", \"type\": \"int_preset\", \"presets\": [115200, 9600]}, \"WALL_SIZE_X\": {\"value\": 5, \"title\": \"Wall-Size (X)\", \"desc\": \"Of how many cubes (on the x axis) does the wall consist?\", \"type\": \"int\", \"min\": 1, \"max\": 10}, \"WALL_SIZE_Y\": {\"value\": 5, \"title\": \"Wall-Size (Y)\", \"desc\": \"Of how many cubes (on the y axis) does the wall consist?\", \"type\": \"int\", \"min\": 1, \"max\": 10}, \"IS_DEVELOPMENT_ENVIRONMENT\": {\"value\": false, \"title\": \"Is Dev-Environment?\", \"desc\": \"Is the software running in development (True) or production (False) mode?\", \"type\": \"bool\"}, \"USE_TEST_SCENE\": {\"value\": true, \"title\": \"Use test scene?\", \"desc\": \"Shall the test-screen-scene be loaded instead of the normal start scene?\", \"type\": \"bool\"}, \"TETRIS_SPEED\": {\"value\": 0.2, \"title\": \"Speed (Tetris)\", \"desc\": \"Delay in ms between frames in tetris.\", \"type\": \"float\", \"min\": 0, \"max\": 1}, \"SNAKE_SPEED\": {\"value\": 0.15, \"title\": \"Speed (Snake)\", \"desc\": \"Delay in ms between frames in snake.\", \"type\": \"float\", \"min\": 0, \"max\": 1}, \"PONG_SPEED\": {\"value\": 0.1, \"title\": \"Speed (Pong)\", \"desc\": \"Delay in ms between frames in pong.\", \"type\": \"float\", \"min\": 0, \"max\": 1}}}";
        JSON_Interpreter interpreter = new JSON_Interpreter(http_response);

        for (Category t_cat : interpreter.generate_components()) {
            window.add_category(t_cat);
        }
    }
}
