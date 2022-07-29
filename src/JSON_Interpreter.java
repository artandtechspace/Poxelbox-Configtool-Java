import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.swing.*;
import java.util.ArrayList;

public class JSON_Interpreter {
    private JSONObject jsonObject;
    ArrayList<Json_Category_Element> all_data;

    public JSON_Interpreter()
    {
        // initializes the ArrayList
        all_data = new ArrayList<>();

        // parses the JSON-Object
        try {
            jsonObject = (JSONObject) JSONValue.parse("{\"abc\":{\"APP_SPEED\":{\"value\":1,\"title\":\"Game-Speed\",\"desc\":\"How fast the game is running. Between 1 and 10\",\"type\":\"int\",\"min\":1,\"max\":10},\"LED_PIXEL_SCALE\":{\"value\":30,\"title\":null,\"desc\":null,\"type\":\"int\",\"min\":5,\"max\":null}},\"def\":{\"ESP_BAUD\":{\"value\":9600,\"title\":null,\"desc\":null,\"type\":\"int_preset\",\"presets\":[115200,9600]}}}");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        /** Splits a JSON-Object into smaller JSON-Object, saves and creates a Json_Category_Element
         * out of the contained JSON-Object
         */
        for (int i = 0; i < jsonObject.size(); i++) {
            String json_key = (String) jsonObject.keySet().toArray()[i];
            JSONObject current_json = (JSONObject) jsonObject.get(json_key);
            all_data.add(new Json_Category_Element(current_json, json_key));
        }
    }

    public ArrayList<Category> generate_components() {
        ArrayList<Category> return_list = new ArrayList<>();
        for (Json_Category_Element cat_element: all_data) {
            Category t_cat = new Category(cat_element.category_name);
            for (Json_Option_Element opt_element : cat_element.all_options) {
                JComponent t_comp = null;
                if (opt_element.get("type").equals("int")) {
                    // default values
                    int min = Integer.MIN_VALUE;
                    int max = Integer.MAX_VALUE;
                    int step = 1;

                    // uses value if given
                    if (opt_element.get("min") != null)
                        min = (int) (long) opt_element.get("min");
                    if (opt_element.get("max") != null)
                        max = (int) (long) opt_element.get("max");
                    if (opt_element.get("step_size") != null)
                        step = (int) (long) opt_element.get("step_size");

                    // calculates the middle value between min and max as default value
                    int val = max - min / 2 + min;

                    // uses value if given
                    if (opt_element.get("value") != null)
                        val = (int) (long) opt_element.get("value");

                    // initials the component
                    t_comp = new JSpinner(new SpinnerNumberModel(val, min, max, step));
                }
                else if (opt_element.get("type").equals("double")) {
                    // default values
                    double min = Double.MIN_VALUE;
                    double max = Double.MAX_VALUE;
                    double step = .01;

                    // uses value if given
                    if (opt_element.get("min") != null)
                        min = (double) opt_element.get("min");
                    if (opt_element.get("max") != null)
                        max = (double) opt_element.get("max");
                    if (opt_element.get("step_size") != null)
                        step = (double) opt_element.get("step_size");

                    // calculates the middle value between min and max as default value
                    double val = max - min / 2 + min;

                    // uses value if given
                    if (opt_element.get("value") != null)
                        val = (double) opt_element.get("value");

                    // initials the component
                    t_comp = new JSpinner(new SpinnerNumberModel(val, min, max, step));
                }
                else if (opt_element.get("type").equals("int_preset"))
                {
                    // Creates a new JComboBox (setting t_comp to be one does not enable the needed functions)
                    JComboBox<Integer> t_dropdown = new JComboBox<>();

                    // Add the elements of "presets" as Items for the JComboBox
                    for (Object t_obj : (JSONArray) opt_element.get("presets")) {
                        t_dropdown.addItem((int) (long) t_obj);
                    }

                    // Sets the default value, if existing
                    if (opt_element.get("value") != null) {
                        t_dropdown.setSelectedItem((int) (long) opt_element.get("value"));
                    }

                    // Sets t_comp to be the JComboBox
                    t_comp = t_dropdown;
                }
                if (opt_element.get("desc") != null && t_comp != null)
                {
                    t_cat.add_option(opt_element.name_key, t_comp, (String) opt_element.get("desc"));
                }
                else if (t_comp != null) {
                    t_cat.add_option(opt_element.name_key, t_comp);
                }
            }
            return_list.add(t_cat);
        }
        return return_list;
    }
}
