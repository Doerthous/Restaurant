package restaurant.communication.core.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Bundle implements Serializable {
    private Map<String, Object> data;

    public Bundle(){
        data = new HashMap<>();
    }
    public Bundle putString(String key, String value){
        return put(key, value);
    }
    public Bundle putInteger(String key, Integer value){
        return put(key, value);
    }
    public Bundle putBundle(String key, Bundle value) {
        return put(key, value);
    }
    private Bundle put(String key, Object value){
        data.put(key, value);
        return this;
    }

    public String getString(String key){
        if(data.keySet().contains(key)) {
            Object d = data.get(key);
            if(d instanceof String){
                return (String) d;
            }
        }
        return null;
    }
    public Integer getInteger(String key) {
        if(data.keySet().contains(key)) {
            Object d = data.get(key);
            if(d instanceof Integer){
                return (Integer) d;
            }
        }
        return null;
    }
    public Bundle getBundle(String key){
        if(data.keySet().contains(key)) {
            Object d = data.get(key);
            if(d instanceof Bundle){
                return (Bundle) d;
            }
        }
        return null;
    }
}