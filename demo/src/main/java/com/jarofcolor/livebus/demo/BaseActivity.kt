package com.jarofcolor.livebus.demo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.jarofcolor.livebus.BusLifecycle

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    private val busLifecycleList = arrayListOf<BusLifecycle>()

    @Synchronized
    fun addBusLife(busLifecycle: BusLifecycle) {
        busLifecycleList.add(busLifecycle)
    }

    @Synchronized
    override fun onDestroy() {
        super.onDestroy()

        busLifecycleList.forEach {
            it.destroy()
        }

        busLifecycleList.clear()
    }
}