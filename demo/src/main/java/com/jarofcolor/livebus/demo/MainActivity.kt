package com.jarofcolor.livebus.demo

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.jarofcolor.livebus.BusLifecycle
import com.jarofcolor.livebus.IObserver
import com.jarofcolor.livebus.LiveBus
import com.jarofcolor.livebus.ThreadMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var noLifeObserver:IObserver<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //添加观察者
        LiveBus.get(String::class.java).observe(ThreadMode.MAIN, busLifecycle) {
            textView.text = it
        }

        //另外一种方式
        noLifeObserver = IObserver {
            textView2.text = it
        }

        LiveBus.get(String::class.java).observe(ThreadMode.MAIN,noLifeObserver)

        editText.addTextChangedListener {
            //发送事件
            LiveBus.get(String::class.java).value = it.toString()
        }

        //每种类型的事件会缓存一个最新值
        LiveBus.get(String::class.java).value
    }

    override fun onDestroy() {
        super.onDestroy()
        LiveBus.get(String::class.java).removeObserver(noLifeObserver)
    }
}
