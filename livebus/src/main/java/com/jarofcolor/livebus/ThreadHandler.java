package com.jarofcolor.livebus;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class ThreadHandler {
    private static ThreadHandler threadHandle = new ThreadHandler();
    private ThreadHandler(){}

    private Executor background = Executors.newSingleThreadExecutor();
    private Executor async = Executors.newFixedThreadPool(8);

    private Handler handler = new Handler(Looper.getMainLooper());

    static ThreadHandler get() {
        return threadHandle;
    }

    <T> void handle(ThreadMode mode,final IObserver<T> observer,final T value){
        switch (mode){
            case POSTING:
                observer.onChanged(value);
                break;
            case MAIN:
                if(isMainThread()){
                    observer.onChanged(value);
                }else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            observer.onChanged(value);
                        }
                    });
                }
                break;
            case MAIN_ORDERED:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        observer.onChanged(value);
                    }
                });
                break;
            case BACKGROUND:
                background.execute(new Runnable() {
                    @Override
                    public void run() {
                        observer.onChanged(value);
                    }
                });
                break;
            case ASYNC:
                async.execute(new Runnable() {
                    @Override
                    public void run() {
                        observer.onChanged(value);
                    }
                });
                break;
        }
    }

    private boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
