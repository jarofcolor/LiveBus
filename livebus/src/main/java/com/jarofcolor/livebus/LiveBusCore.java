package com.jarofcolor.livebus;

import java.util.HashMap;
import java.util.Map;

class LiveBusCore {

    private Map<Class,LiveEvent> eventMap = new HashMap<>();

    LiveBusCore(){}

    <T> LiveEvent<T> get(Class<T> type){
        LiveEvent<T> event = eventMap.get(type);
        if(event == null) {
            event = new LiveEvent<T>();
            eventMap.put(type,event);
        }
        return event;
    }
}
