package real.droid.livebus;

public class ObserverWrapper<T> implements IObserver<T> {

    private final ThreadMode mode;
    private BusLifecycle lifecycle;
    private final IObserver<T> observer;
    private LiveEvent<T> liveEvent;

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

    public void setLiveEvent(LiveEvent liveEvent) {
        this.liveEvent = liveEvent;
    }

    public LiveEvent<T> getLiveEvent() {
        return liveEvent;
    }

    public IObserver<T> getObserver() {
        return observer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObserverWrapper)) return false;
        ObserverWrapper<?> wrapper = (ObserverWrapper<?>) o;
        return getObserver().equals(wrapper.getObserver());
    }

    @Override
    public int hashCode() {
        return observer.hashCode();
    }
}
