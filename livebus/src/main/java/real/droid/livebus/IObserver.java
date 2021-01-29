package real.droid.livebus;

public interface IObserver<T> {
    void onChanged(T value);
}
