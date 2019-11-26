package com.jarofcolor.livebus.demo

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.jarofcolor.livebus.LiveBus
import com.jarofcolor.livebus.ThreadMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //添加观察者
        addBusLife(LiveBus.get(String::class.java).observeLife(ThreadMode.MAIN) {
            textView.text = it
        })

        editText.addTextChangedListener {
            //发送事件
            LiveBus.get(String::class.java).value = it.toString()
        }

        //每种类型的事件会缓存一个最新值
        LiveBus.get(String::class.java).value
    }
}
