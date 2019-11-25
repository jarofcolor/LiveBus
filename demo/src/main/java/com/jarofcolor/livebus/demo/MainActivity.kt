package com.jarofcolor.livebus.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.jarofcolor.livebus.BusLifecycle
import com.jarofcolor.livebus.LiveBus
import com.jarofcolor.livebus.ThreadMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val lifeList = arrayListOf<BusLifecycle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifeList.add(LiveBus.get(String::class.java).observeLife(ThreadMode.MAIN) {
            textView.text = it
        })

        editText.addTextChangedListener {
            LiveBus.get(String::class.java).value = it.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifeList.forEach {
            it.destroy()
        }
        lifeList.clear()
    }
}
