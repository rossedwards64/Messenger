package com.rossedwards.nsdassignment;

import org.json.simple.JSONObject;

public class ReadRequest extends Request {
    private static final String _class = ReadRequest.class.getSimpleName();

    public ReadRequest() { /* empty because there are no instance fields */ }

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        return obj;
    }

    public static ReadRequest fromJSON(Object value) {
        try {
            JSONObject obj = (JSONObject) value;
            if(!_class.equals(obj.get("_class")))
                return null;

            return new ReadRequest();
        } catch(ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
