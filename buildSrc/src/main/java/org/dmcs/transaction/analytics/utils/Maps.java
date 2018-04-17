package org.dmcs.transaction.analytics.utils;

import java.util.HashMap;
import java.util.Map;

public class Maps {

    public static <K, V> Map<K, V> map(Tuple<K, V>... content){
        Map<K, V> map = new HashMap<>();
        for(int i = 0; i < content.length; i++){
            map.put(content[i].getKey(), content[i].getValue());
        }
        return map;
    }
}
