package com.androidweardocs.wearabledatamap;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by michaelHahn on 1/16/15.
 * Listener service or data events on the data layer
 */
public class ListenerService extends WearableListenerService{

    private static final String WEARABLE_DATA_PATH = "/wearable_data";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        DataMap dataMap;
        for (DataEvent event : dataEvents) {

            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(WEARABLE_DATA_PATH)) {}
                dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                Log.v("myTag", "DataMap received on watch: " + dataMap);

                // Broadcast DataMap contents to wearable activity for display
                // The content has the golf hole number and distances to the front,
                // middle and back pin placements.

                Intent messageIntent = new Intent();
                messageIntent.setAction(Intent.ACTION_SEND);
                messageIntent.putExtra("hole", dataMap.getString("hole"));
                messageIntent.putExtra("front", dataMap.getString("front"));
                messageIntent.putExtra("middle", dataMap.getString("middle"));
                messageIntent.putExtra("back", dataMap.getString("back"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);

            }
        }
    }
}
