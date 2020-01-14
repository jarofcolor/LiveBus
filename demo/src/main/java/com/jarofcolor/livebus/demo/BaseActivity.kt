package com.jarofcolor.livebus.demo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.jarofcolor.livebus.BusLifecycle

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    val busLifecycle = BusLifecycle()
    @Synchronized
    override fun onDestroy() {
        super.onDestroy()
        busLifecycle.destroy()
    }


}