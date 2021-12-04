package com.rossedwards.nsdassignment;

import org.json.simple.*;

public abstract class Request implements JSONAware {

    // adds data fields to a JSON file in key/value pairs
    public abstract Object toJSON();

    public String toJSONString() {
        return toJSON().toString();
    }

    public String toString() {
        return toJSONString();
    }
}
