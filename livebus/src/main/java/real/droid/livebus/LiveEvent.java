package real.droid.livebus;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LiveEvent<T> {
    private final List<IObserver<T>> observers = new ArrayList<>();
    private T value;

    synchronized public void setValue(T value) {
        this.value = value;

        for (IObserver<T> observer : observers) {
            try {
                if (observer instanceof ObserverWrapper) {
                    ObserverWrapper<T> wrapper = (ObserverWrapper<T>) observer;
                    BusLifecycle lifecycle = wrapper.getLifecycle();
                    if (lifecycle != null && lifecycle.isDestroy()) {
                        continue;
                    }

                    ThreadMode mode = wrapper.getMode() == null ? ThreadMode.POSTING : wrapper.getMode();
                    ThreadHandler.get().handle(mode, lifecycle, observer, value);
                } else
                    observer.onChanged(value);
            } catch (Exception ignored) {
            }
        }
    }

    synchronized public T getValue() {
        return value;
    }

    public synchronized void observe(IObserver<T> observer) {
        this.observe(ThreadMode.POSTING, observer);
    }

    public synchronized void observe(ThreadMode mode, IObserver<T> observer) {
        this.observe(mode, null, observer);
    }

    public synchronized void observe(ThreadMode mode, BusLifecycle lifecycle, IObserver<T> observer) {
        if (observer == null) return;
        final ObserverWrapper<T> wrapper = new ObserverWrapper<>(mode, observer);
        if (observers.contains(wrapper)) {
            return;
        }

        if (lifecycle != null)
            lifecycle.add(wrapper);
        wrapper.setLifecycle(lifecycle);
        wrapper.setLiveEvent(this);
        observers.add(wrapper);
    }

    /**
     * 删除Observer
     */
    synchronized public void removeObserver(IObserver<T> observer) {
        boolean removed;
        if (observer instanceof ObserverWrapper) {
            removed = observers.remove(observer);
        } else
            removed = observers.remove(new ObserverWrapper<>(null, observer));
        Log.i("LiveBus", "Observer removed:" + removed);
    }
}
