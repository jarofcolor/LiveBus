package real.droid.livebus.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import real.droid.livebus.BusLifecycle
import real.droid.livebus.LiveBus
import real.droid.livebus.ThreadMode
import real.droid.livebus.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    data class Msg(val text:String);
    private  val busLifecycle = BusLifecycle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LiveBus.get(Msg::class.java).observe(ThreadMode.MAIN,busLifecycle){
            binding.text.text = it.text
        }

        binding.button.setOnClickListener {
            LiveBus.get(Msg::class.java).value = Msg("Receiving a test msg,ha ha ha")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        busLifecycle.destroy()
    }
}