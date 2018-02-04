package net.syarihu.android.watchfacesample

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.support.wearable.complications.ProviderUpdateRequester

class ComplicationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras ?: return
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val provider = extras.getParcelable<ComponentName>(EXTRA_PROVIDER_COMPONENT)
        val complicationId = extras.getInt(EXTRA_COMPLICATION_ID)

        val preferenceKey = getPreferenceKey(provider, complicationId)
        val value = pref.getFloat(preferenceKey, 0f)
        val editor = pref.edit().apply {
            putFloat(preferenceKey, if (value + 1f < 11f) {
                value + 1f
            } else {
                0f
            })
        }
        editor.apply()

        val requester = ProviderUpdateRequester(context, provider)
        requester.requestUpdate(complicationId)
    }

    companion object {
        private const val EXTRA_PROVIDER_COMPONENT = "providerComponent"
        private const val EXTRA_COMPLICATION_ID = "complicationId"

        internal fun getPreferenceKey(provider: ComponentName, complicationId: Int): String {
            return provider.className + complicationId
        }

        internal fun getIntent(
                context: Context, provider: ComponentName, complicationId: Int): PendingIntent {
            val intent = Intent(context, ComplicationReceiver::class.java)
            intent.putExtra(EXTRA_PROVIDER_COMPONENT, provider)
            intent.putExtra(EXTRA_COMPLICATION_ID, complicationId)
            return PendingIntent.getBroadcast(
                    context, complicationId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

}