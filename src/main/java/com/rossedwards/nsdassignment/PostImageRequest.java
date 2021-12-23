package com.rossedwards.nsdassignment;

import org.json.simple.JSONObject;

public class PostImageRequest extends Request {
    private static final String _class = PostImageRequest.class.getSimpleName();
    private final String image;

    public PostImageRequest(String image) {
        if (image == null)
            throw new NullPointerException();
        this.image = image;
    }

    public static PostImageRequest fromJSON(Object value) {
        try {
            JSONObject obj = (JSONObject) value;
            if (!_class.equals(obj.get("_class")))
                return null;
            String image = (String) obj.get("image");
            return new PostImageRequest(image);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }

    String getImage() {
        return image;
    }

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("image", image);
        return obj;
    }
}
