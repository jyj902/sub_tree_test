package ai.hnh.healthwatch

import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

/**
 * Created by hyerim on 2021/11/03...
 */
class DataLayerListenerService : WearableListenerService() {
    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)

        if (messageEvent.path.equals(COUNT_PATH)){
            var startIntent = Intent(applicationContext, MainActivity::class.java)
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(startIntent);
        }

    }
}