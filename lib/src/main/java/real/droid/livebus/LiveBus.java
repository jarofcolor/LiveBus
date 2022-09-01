package real.droid.livebus;

public class LiveBus {
    private final static LiveBusCore core = new LiveBusCore();

    public static <T> LiveEvent<T> get(Class<T> type) {
        return core.get(type);
    }
}
