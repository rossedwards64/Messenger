package com.rossedwards.nsdassignment;
// needs compiling
// javac -cp json-simple-1.1.1.jar;. Request.java
import org.json.simple.*;

public abstract class Request implements JSONAware {

    public abstract Object toJSON();

    public String toJSONString() {
        return toJSON().toString();
    }

    public String toString() {
        return toJSONString();
    }
}
