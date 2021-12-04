package com.rossedwards.nsdassignment;

import org.json.simple.JSONObject;

public class PostRequest extends Request {
    private static final String _class = PostRequest.class.getSimpleName();
    private final String message;

    public PostRequest(String message) {
        if(message == null)
            throw new NullPointerException();
        this.message = message;
    }

    String getMessage() {
        return message;
    }

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        return obj;
    }

    public static PostRequest fromJSON(Object value) {
        try {
            JSONObject obj = (JSONObject) value;
            if(!_class.equals(obj.get("_class")))
                return null;

            String message = (String)obj.get("message");
            return new PostRequest(message);
        } catch(ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
