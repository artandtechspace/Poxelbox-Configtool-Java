package JSON_Parts;

import UI_Elements.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JSON_Interpreter {
    private static JSONObject jsonObject;
    private static JSONObject return_val;
    private ArrayList<Json_Category_Element> all_data;

    public JSON_Interpreter(String p_str_Json)
    {
        // initializes the ArrayList
        all_data = new ArrayList<>();

        // parses the JSON-Object
        try {
            System.out.println("JSON Input: ");
            System.out.print(p_str_Json);
            System.out.println("");
            jsonObject = (JSONObject) JSONValue.parse(p_str_Json);
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
                else if (opt_element.get("type").equals("float")) {
                    // default values
                    double min = Double.MIN_VALUE;
                    double max = Double.MAX_VALUE;
                    double step = .001;

                    // uses value if given
                    if (opt_element.get("min") != null)
                        min = ((Number) opt_element.get("min")).floatValue();
                    if (opt_element.get("max") != null)
                        max = ((Number) opt_element.get("max")).floatValue();
                    if (opt_element.get("step_size") != null)
                        step = ((Number) opt_element.get("step_size")).floatValue();

                    // calculates the middle value between min and max as default value
                    double val = max - min / 2 + min;

                    // uses value if given
                    if (opt_element.get("value") != null){
                        val = ((Number) opt_element.get("value")).floatValue();
                    }

                    // initials the component
                    t_comp = new JSpinner(new SpinnerNumberModel(val, min, max, step));
                }
                else if (opt_element.get("type").equals("int_preset")) {
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
                else if (opt_element.get("type").equals("string_preset")) {
                    // Creates a new JComboBox (setting t_comp to be one does not enable the needed functions)
                    JComboBox<String> t_dropdown = new JComboBox<>();

                    // Add the elements of "presets" as Items for the JComboBox
                    for (Object t_obj : (JSONArray) opt_element.get("presets")) {
                        t_dropdown.addItem((String) t_obj);
                    }

                    // Sets the default value, if existing
                    if (opt_element.get("value") != null) {
                        t_dropdown.setSelectedItem(opt_element.get("value"));
                    }

                    // Sets t_comp to be the JComboBox
                    t_comp = t_dropdown;
                }
                else if (opt_element.get("type").equals("color")) {
                    Color start_color = Color.WHITE;
                    if (opt_element.get("value") != null)
                        start_color = Color.getColor( (String) opt_element.get("value"));
                    t_comp = new JColorChooser(start_color);
                }
                else if (opt_element.get("type").equals("bool")) {
                    JCheckBox t_checkbox = new JCheckBox();
                    if (opt_element.get("value") != null)
                        t_checkbox.setSelected((boolean) opt_element.get("value"));
                    t_comp = t_checkbox;
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

    public static void build_answer(Object[] input) {
        if (input.length != ((JSONObject) jsonObject.get("Settings")).size()) {
            System.out.println("UI output and jsonObject category Settings from http request don't have equal length; " + input.length + ", " + ((JSONObject) jsonObject.get("Settings")).size());
            System.out.println("UI outputs are: ");
            for (Object in : input) {
                System.out.println("\t " + in.toString());
            }
        }

        int counter = 0;
        return_val = new JSONObject();
        for (int i = 0; i < jsonObject.size(); i++) {
            String json_category_key = (String) jsonObject.keySet().toArray()[i];
            JSONObject t_json_category = (JSONObject) jsonObject.get(json_category_key);
            JSONObject t_return_category = new JSONObject();
            for (int j = 0; i < t_json_category.size(); i++) {
                String json_option_key = (String) t_json_category.keySet().toArray()[i];
                t_return_category.put(json_option_key, input[counter]);
                counter++;
            }
            return_val.put(json_category_key, t_return_category);
        }
    }

    public static String get_answer()
    {
        return return_val.toJSONString();
    }
}
