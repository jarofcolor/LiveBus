package com.jarofcolor.livebus;

public class BusLifecycle {
    private volatile boolean isDestroy;

    public void destroy() {
        isDestroy = true;
    }

    boolean isDestroy() {
        return isDestroy;
    }
}
