package com.rossedwards.nsdassignment;

import org.json.simple.JSONObject;

public record Message(String body, String author, long timeStamp) {
    private static final String _class = Message.class.getSimpleName();

    public Message {
        if (body == null | author == null)
            throw new NullPointerException();
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    // puts the fields into message that will be displayed to the user
    public String toString() {
        return author + ": " + body + " (" + timeStamp + ")";
    }

    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("body", body);
        obj.put("author", author);
        obj.put("timeStamp", timeStamp);
        return obj;
    }

    public static Message fromJSON(Object value) {
        try {
            JSONObject obj = (JSONObject) value;
            if (!_class.equals(obj.get("_class")))
                return null;
            String body = (String) obj.get("body");
            String author = (String) obj.get("author");
            long timeStamp = (long) obj.get("timeStamp");
            return new Message(body, author, timeStamp);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }


}
