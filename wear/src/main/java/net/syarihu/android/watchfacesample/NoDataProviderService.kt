package net.syarihu.android.watchfacesample

import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationManager
import android.support.wearable.complications.ComplicationProviderService
import android.util.Log

class NoDataProviderService : ComplicationProviderService() {
    override fun onComplicationUpdate(complicationId: Int, type: Int, manager: ComplicationManager) {
        Log.d(NoDataProviderService::class.java.simpleName,
                "complicationId=$complicationId, type=$type")
        val data = ComplicationData.Builder(ComplicationData.TYPE_NO_DATA).build()
        manager.updateComplicationData(complicationId, data)
    }
}