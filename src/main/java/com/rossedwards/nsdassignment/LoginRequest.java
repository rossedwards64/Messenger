package com.rossedwards.nsdassignment;

import org.json.simple.JSONObject;

public class LoginRequest extends Request {
    private static final String _class = LoginRequest.class.getSimpleName();
    private final String username;

    public LoginRequest(String username) {
        if(username == null)
            throw new NullPointerException();
        this.username = username;
    }

    String getUsername() {
        return username;
    }

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("username", username);
        return obj;
    }

    public static LoginRequest fromJSON(Object value) {
        try {
            JSONObject obj = (JSONObject) value;
            if(!_class.equals(obj.get("_class")))
                return null;
            String name = (String) obj.get("username");
            return new LoginRequest(name);
        } catch(ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
