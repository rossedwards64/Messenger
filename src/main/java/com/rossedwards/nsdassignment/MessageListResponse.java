package com.rossedwards.nsdassignment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MessageListResponse extends Response {
    private static final String _class = MessageListResponse.class.getSimpleName();
    private final List<Message> messages;

    public MessageListResponse(List<Message> messages) {
        if(messages == null || messages.contains(null))
            throw new NullPointerException();
        this.messages = messages;
    }

    List<Message> getMessages() {
        return messages;
    }

    public Object toJSON() {
        JSONArray arr = new JSONArray();
        for(Message message : messages)
            arr.add(message.toJSON());

        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("messages", arr);
        return obj;
    }

    public static MessageListResponse fromJSON(Object value) {
        try {
            JSONObject obj = (JSONObject) value;
            if(!_class.equals(obj.get("_class")))
                return null;

            JSONArray arr = (JSONArray) obj.get("messages");
            List<Message> messages = new ArrayList<>();

            for(Object message : arr)
                messages.add(Message.fromJSON(message));

            return new MessageListResponse(messages);
        } catch(ClassCastException | NullPointerException e) {
            return null;
        }
    }

}
