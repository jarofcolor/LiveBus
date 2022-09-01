package real.droid.livebus;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class ThreadHandler {
    private final static ThreadHandler threadHandle = new ThreadHandler();

    private ThreadHandler() {
    }

    private final Executor background = Executors.newSingleThreadExecutor();
    private final Executor async = Executors.newCachedThreadPool();

    private final Handler handler = new Handler(Looper.getMainLooper());

    static ThreadHandler get() {
        return threadHandle;
    }

    <T> void handle(ThreadMode mode, final BusLifecycle lifecycle, final IObserver<T> observer, final T value) {
        Runnable call = new Runnable() {
            @Override
            public void run() {
                if (lifecycle == null || !lifecycle.isDestroy()) {
                    observer.onChanged(value);
                }
            }
        };

        switch (mode) {
            case POSTING:
                call.run();
                break;
            case MAIN:
                if (isMainThread()) {
                    call.run();
                } else {
                    handler.post(call);
                }
                break;
            case MAIN_ORDERED:
                handler.post(call);
                break;
            case BACKGROUND:
                background.execute(call);
                break;
            case ASYNC:
                async.execute(call);
                break;
        }
    }

    private boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
