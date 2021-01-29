package real.droid.livebus;

import java.util.ArrayList;
import java.util.List;

public class BusLifecycle {

    private boolean isDestroy = false;
    //缓存受生命周期控制的observers
    private final List<ObserverWrapper<?>> observers = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public synchronized final void destroy() {
        isDestroy = true;
        for (ObserverWrapper wrapper : observers) {
            wrapper.getLiveEvent().removeObserver(wrapper);
        }
    }

    final synchronized void add(ObserverWrapper observer) {
        observers.add(observer);
    }

    public final boolean isDestroy() {
        return isDestroy;
    }
}
