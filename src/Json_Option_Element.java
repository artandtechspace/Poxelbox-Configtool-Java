import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Json_Option_Element extends JSONObject {
    public String name_key;

    public Json_Option_Element(JSONObject pContent, String pKey)
    {
        name_key = pKey;

        /** identifies the keys of the given JSON-Object and adds them (w/ their values) to itself
         */
        for (int i = 0; i < pContent.size(); i++)
        {
            String key = (String) pContent.keySet().toArray()[i];
            put(key, pContent.get(key));
        }

        if (pContent.get("title") != null)
            name_key = (String) pContent.get("title");
    }
}
