package com.jarofcolor.livebus;

public class ObserverWrapper<T> implements IObserver<T> {

    private ThreadMode mode;
    private BusLifecycle lifecycle;
    private IObserver<T> observer;

    public ObserverWrapper(ThreadMode mode, IObserver<T> observer) {
        this.mode = mode == null ? ThreadMode.POSTING : mode;
        this.observer = observer;
    }

    @Override
    public void onChanged(T value) {
        observer.onChanged(value);
    }

    public ThreadMode getMode() {
        return mode;
    }

    public BusLifecycle getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(BusLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }
}
