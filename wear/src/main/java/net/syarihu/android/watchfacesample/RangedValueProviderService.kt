package net.syarihu.android.watchfacesample

import android.content.ComponentName
import android.graphics.drawable.Icon
import android.preference.PreferenceManager
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationManager
import android.support.wearable.complications.ComplicationProviderService
import android.support.wearable.complications.ComplicationText

class RangedValueProviderService : ComplicationProviderService() {
    override fun onComplicationUpdate(complicationId: Int, type: Int, manager: ComplicationManager) {
        if (type != ComplicationData.TYPE_RANGED_VALUE) {
            manager.noUpdateRequired(complicationId)
            return
        }

        val thisProvider = ComponentName(this, javaClass)
        val complicationPendingIntent =
                ComplicationReceiver.getIntent(this, thisProvider, complicationId)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val state = preferences.getFloat(
                ComplicationReceiver.getPreferenceKey(thisProvider, complicationId),
                0f)

        val complicationData = ComplicationData.Builder(type)
                .setMinValue(0f)
                .setMaxValue(10f)
                .setValue(state)
                .setShortText(ComplicationText.plainText(state.toInt().toString()))
                .setIcon(Icon.createWithResource(this, R.drawable.ic_cc_settings_button_bottom))
                .setTapAction(complicationPendingIntent)
                .build()
        manager.updateComplicationData(complicationId, complicationData)
    }
}