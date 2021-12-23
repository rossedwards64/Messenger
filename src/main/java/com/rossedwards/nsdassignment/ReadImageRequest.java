package com.rossedwards.nsdassignment;

import org.json.simple.JSONObject;

public class ReadImageRequest extends Request {
    private static final String _class = ReadImageRequest.class.getSimpleName();

    public ReadImageRequest() { /* empty because there are no instance fields */ }

    public static ReadImageRequest fromJSON(Object value) {
        try {
            JSONObject obj = (JSONObject) value;
            if (!_class.equals(obj.get("_class")))
                return null;

            return new ReadImageRequest();
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        return obj;
    }
}
