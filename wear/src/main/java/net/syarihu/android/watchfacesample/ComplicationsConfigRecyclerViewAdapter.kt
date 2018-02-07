package net.syarihu.android.watchfacesample

import android.content.ComponentName
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.wearable.complications.ComplicationProviderInfo
import android.support.wearable.complications.ProviderInfoRetriever
import android.view.ViewGroup
import java.util.concurrent.Executors

class ComplicationsConfigRecyclerViewAdapter(
        private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val providerInfoRetriever: ProviderInfoRetriever =
            ProviderInfoRetriever(context, Executors.newCachedThreadPool()).apply {
                init()
            }
    private var selectedComplicationLocation: ComplicationLocation? = null
    private var previewAndComplicationsViewHolder: PreviewAndComplicationsViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = PreviewAndComplicationsViewHolder(context, parent)
        previewAndComplicationsViewHolder = viewHolder
        return viewHolder
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PreviewAndComplicationsViewHolder) {
            holder.bind(context, { selectedComplicationLocation = it })
            providerInfoRetriever.retrieveProviderInfo(
                    object : ProviderInfoRetriever.OnProviderInfoReceivedCallback() {
                        override fun onProviderInfoReceived(
                                watchFaceComplicationId: Int,
                                complicationProviderInfo: ComplicationProviderInfo?
                        ) {
                            complicationProviderInfo?.let { providerInfo ->
                                ComplicationLocation.valueOf(watchFaceComplicationId)?.let { complicationLocation ->
                                    updateComplicationView(providerInfo, complicationLocation)
                                }
                            }
                        }
                    },
                    ComponentName(context, DigitalWatchFaceService::class.java),
                    *ComplicationLocation.getComplicationIds())
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        providerInfoRetriever.release()
    }

    fun updateComplicationView(
            complicationProviderInfo: ComplicationProviderInfo?,
            complicationLocation: ComplicationLocation
    ) {
        previewAndComplicationsViewHolder?.updateComplicationViews(
                complicationProviderInfo, complicationLocation
        )
    }

    fun updateSelectedComplication(complicationProviderInfo: ComplicationProviderInfo?) {
        val complicationLocation = selectedComplicationLocation ?: return
        updateComplicationView(complicationProviderInfo, complicationLocation)
    }
}
