package real.droid.livebus;

import java.util.HashMap;
import java.util.Map;

class LiveBusCore {

    private final Map<Class<?>, LiveEvent<?>> eventMap = new HashMap<>();

    LiveBusCore() {
    }

    @SuppressWarnings("unchecked")
    synchronized <T> LiveEvent<T> get(Class<T> type) {
        LiveEvent<T> event = (LiveEvent<T>) eventMap.get(type);
        if (event == null) {
            event = new LiveEvent<>();
            eventMap.put(type, event);
        }
        return event;
    }
}
