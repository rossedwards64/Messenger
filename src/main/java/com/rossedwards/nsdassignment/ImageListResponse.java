package com.rossedwards.nsdassignment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImageListResponse extends Response {
    private static final String _class = ImageListResponse.class.getSimpleName();
    private final List<Message> images;

    public ImageListResponse(List<Message> images) {
        if (images == null || images.contains(null))
            throw new NullPointerException();
        this.images = images;
    }

    public static ImageListResponse fromJSON(Object value) {
        try {
            JSONObject obj = (JSONObject) value;
            if (!_class.equals(obj.get("_class")))
                return null;

            JSONArray arr = (JSONArray) obj.get("images");
            List<Message> images = new ArrayList<>();

            for (Object image : arr)
                images.add(Message.fromJSON(image));

            return new ImageListResponse(images);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }

    List<Message> getImages() {
        return images;
    }

    public Object toJSON() {
        JSONArray arr = new JSONArray();
        for (Message image : images)
            arr.add(image.toJSON());

        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("images", arr);
        return obj;
    }

}

