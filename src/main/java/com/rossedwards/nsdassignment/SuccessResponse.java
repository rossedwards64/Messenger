package com.rossedwards.nsdassignment;

import org.json.simple.JSONObject;

public class SuccessResponse extends Response {
    private static final String _class = SuccessResponse.class.getSimpleName();
    public SuccessResponse() {
    }

    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        return obj;
    }

    public static SuccessResponse fromJSON(Object value) {
        try {
            JSONObject obj = (JSONObject) value;
            if (!_class.equals(obj.get("_class")))
                return null;

            return new SuccessResponse();
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
