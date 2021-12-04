package com.rossedwards.nsdassignment;

import org.json.simple.*;

public abstract class Response implements JSONAware {

    public abstract Object toJSON();

    public String toJSONString() {
        return toJSON().toString();
    }

    public String toString() {
        return toJSONString();
    }
}
