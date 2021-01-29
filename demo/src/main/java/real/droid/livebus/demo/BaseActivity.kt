package real.droid.livebus.demo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import real.droid.livebus.BusLifecycle

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    val busLifecycle = BusLifecycle()
    @Synchronized
    override fun onDestroy() {
        super.onDestroy()
        busLifecycle.destroy()
    }


}