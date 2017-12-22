package restaurant.communication.core.utils;

import java.util.Map;

public class MapOperation {
    public static void putIfAbsent(Map map, Object key, Object value){
        if(!map.keySet().contains(key)){
            map.put(key, value);
        }
    }
    public static Object getOrDefault(Map map, Object key, Object defaultValue){
        if(!map.keySet().contains(key)){
            return defaultValue;
        }
        return map.get(key);
    }
}
