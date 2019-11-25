package com.jarofcolor.livebus;

public interface IObserver<T> {
    void onChanged(T value);
}
