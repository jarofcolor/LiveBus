package com.jarofcolor.livebus;

import java.util.ArrayList;
import java.util.List;

public class LiveEvent<T> {
    private List<IObserver<T>> observers = new ArrayList<>();
    private T value;

    synchronized public void setValue(T value) {
        this.value = value;

        if (observers == null) {
            return;
        }

        for (IObserver<T> observer : observers) {
            try {
                if (observer instanceof ObserverWrapper) {
                    ObserverWrapper wrapper = (ObserverWrapper) observer;
                    BusLifecycle lifecycle = wrapper.getLifecycle();
                    if (lifecycle != null && lifecycle.isDestroy()) {
                        continue;
                    }

                    ThreadHandler.get().handle(wrapper.getMode(), lifecycle,observer, value);
                } else
                    observer.onChanged(value);
            } catch (Exception ignored) {
            }
        }
    }

    synchronized public T getValue() {
        return value;
    }


    synchronized public void observe(IObserver<T> observer) {
        if (observer == null) return;
        observers.add(observer);
    }


    public void observe(ThreadMode mode, IObserver<T> observer) {
        this.observe(new ObserverWrapper<>(mode, observer));
    }


    public BusLifecycle observeLife(ThreadMode mode, IObserver<T> observer) {
        final ObserverWrapper<T> wrapper = new ObserverWrapper<>(mode, observer);
        BusLifecycle lifecycle = new BusLifecycle() {
            @Override
            public void destroy() {
                synchronized (LiveEvent.this){
                    removeObserver(wrapper);
                }
            }
        };
        wrapper.setLifecycle(lifecycle);
        this.observe(wrapper);
        return lifecycle;
    }

    /**
     * 删除Observer
     */
    synchronized public void removeObserver(IObserver observer) {
        if (observers != null) {
            observers.remove(observer);
        }
    }
}
