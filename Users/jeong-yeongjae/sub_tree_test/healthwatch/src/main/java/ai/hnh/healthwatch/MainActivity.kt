package ai.hnh.healthwatch

import ai.hnh.healthwatch.databinding.ActivityMainBinding
import android.content.Context
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.wear.ambient.AmbientModeSupport
import androidx.wear.ambient.AmbientModeSupport.AmbientCallback
import com.google.android.gms.wearable.*
import androidx.annotation.RequiresApi


class MainActivity : FragmentActivity(), AmbientModeSupport.AmbientCallbackProvider,
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enables Always-on
        ambientController = AmbientModeSupport.attach(this)

        initView()

    }

    private fun initView() {
        var count = 0
        binding.buttonStartExercise.setOnClickListener {
            val putDataMapRequest = PutDataMapRequest.create(START_PATH).apply {
                count++
                dataMap.putString(START_KEY, "" + count)
            }
            val request = putDataMapRequest.asPutDataRequest().apply {
                setUrgent()
            }
            Wearable.getDataClient(this).putDataItem(request)
        }

        binding.buttonEndExercise.setOnClickListener {
            val putDataMapRequest = PutDataMapRequest.create(END_PATH).apply {
                count++
                dataMap.putString(END_KEY, "" + count)
            }
            val request = putDataMapRequest.asPutDataRequest().apply {
                setUrgent()
            }
            Wearable.getDataClient(this).putDataItem(request)
        }

        binding.textCountExercise.text = "개수 : 0"
    }

    override fun onResume() {
        super.onResume()
        Wearable.getMessageClient(this).addListener(this)
        Wearable.getDataClient(this).addListener(this)
        Wearable.getCapabilityClient(this)
            .addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE);
    }

    override fun onPause() {
        super.onPause()
        Wearable.getMessageClient(this).removeListener(this)
        Wearable.getDataClient(this).removeListener(this)
        Wearable.getCapabilityClient(this).removeListener(this)
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        Log.d(TAG, "data received")

        dataEvents.forEach { event ->
            when (event.dataItem.uri.path) {
                COUNT_PATH -> DataMapItem.fromDataItem(event.dataItem).dataMap.apply {
                    Log.d(TAG, "count key : " + getInt(COUNT_KEY))
                    updateCount(getInt(COUNT_KEY))
                }
            }
        }

    }

    private fun updateCount(count: Int) {
        if (count < 0) {
            binding.textCountExercise.text = "개수 : 0"
        } else {
            binding.textCountExercise.text = "개수 : $count"
        }

        val vibrator  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =  this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator;
        } else {
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        val effect = VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(effect)
    }

    override fun onMessageReceived(message: MessageEvent) {
        Log.d(TAG, "message received")
    }

    override fun onCapabilityChanged(p0: CapabilityInfo) {
        Log.d(TAG, "onCapabilityChanged : $p0")
    }

    /** wear 대기모드 - 앱 표시 유지 */
    override fun getAmbientCallback(): AmbientCallback = MyAmbientCallback()

    private inner class MyAmbientCallback : AmbientCallback() {
        override fun onEnterAmbient(ambientDetails: Bundle) {
            super.onEnterAmbient(ambientDetails)
        }

        override fun onUpdateAmbient() {
            super.onUpdateAmbient()
        }

        override fun onExitAmbient() {
            super.onExitAmbient()
        }
    }

    companion object {
        const val TAG = "Wear MainActivity"
    }

}
