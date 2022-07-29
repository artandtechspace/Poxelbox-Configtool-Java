import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Json_Category_Element {
    public String category_name;
    public ArrayList<Json_Option_Element> all_options;

    public Json_Category_Element(JSONObject pContent, String pKey)
    {
        all_options = new ArrayList<>();
        category_name = pKey;

        /** Splits a JSON-Object into smaller JSON-Object, saves and creates a Json_Option_Element
         * out of the contained JSON-Object
        */
        for (int i = 0; i < pContent.size(); i++)
        {
            String json_key = (String) pContent.keySet().toArray()[i];
            JSONObject current_json = (JSONObject) pContent.get(json_key);
            all_options.add(new Json_Option_Element(current_json, json_key));
        }
    }
}
