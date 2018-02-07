package net.syarihu.android.watchfacesample

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.support.wearable.complications.ComplicationHelperActivity
import android.support.wearable.complications.ComplicationProviderInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import net.syarihu.android.watchfacesample.databinding.ViewholderPreviewAndComplicationsBinding

class PreviewAndComplicationsViewHolder(
        context: Context, parent: ViewGroup?
) : RecyclerView.ViewHolder(
        ViewholderPreviewAndComplicationsBinding.inflate(
                LayoutInflater.from(context), parent, false
        ).root
) {
    private val binding: ViewholderPreviewAndComplicationsBinding = DataBindingUtil.bind(itemView)

    fun bind(
            context: Context,
            complicationClickListener: (complicationLocation: ComplicationLocation) -> Unit
    ) {
        val defaultDrawable = context.getDrawable(R.drawable.add_complication)
        binding.run {
            leftComplication.run {
                setImageDrawable(defaultDrawable)
                setOnClickListener { view ->
                    onClickComplication(view, complicationClickListener)
                }
            }
            leftComplicationBackground.visibility = View.INVISIBLE
            rightComplication.run {
                setImageDrawable(defaultDrawable)
                setOnClickListener { view ->
                    onClickComplication(view, complicationClickListener)
                }
            }
            rightComplicationBackground.visibility = View.INVISIBLE
        }
    }

    private fun onClickComplication(
            view: View, complicationClickListener: (complicationLocation: ComplicationLocation) -> Unit
    ) {
        val complicationLocation: ComplicationLocation? = when (view.id) {
            R.id.left_complication -> ComplicationLocation.LEFT
            R.id.right_complication -> ComplicationLocation.RIGHT
            else -> null
        }
        complicationLocation?.let {
            complicationClickListener(it)
            launchComplicationHelperActivity(view.context, it)
        }
    }

    fun updateComplicationViews(
            complicationProviderInfo: ComplicationProviderInfo?,
            complicationLocation: ComplicationLocation
    ) {
        val button: ImageButton
        val background: ImageView
        when (complicationLocation) {
            ComplicationLocation.LEFT -> {
                button = binding.leftComplication
                background = binding.leftComplicationBackground
            }
            ComplicationLocation.RIGHT -> {
                button = binding.rightComplication
                background = binding.rightComplicationBackground
            }
        }
        if (complicationProviderInfo == null) {
            button.setImageDrawable(
                    button.context.getDrawable(R.drawable.add_complication))
            background.visibility = View.INVISIBLE
            return
        }
        button.setImageIcon(complicationProviderInfo.providerIcon)
        background.visibility = View.VISIBLE
    }

    private fun launchComplicationHelperActivity(
            context: Context, complicationLocation: ComplicationLocation
    ) {
        val activity = context as? Activity ?: return
        val watchFace = ComponentName(activity, DigitalWatchFaceService::class.java)
        activity.startActivityForResult(
                ComplicationHelperActivity.createProviderChooserHelperIntent(
                        activity,
                        watchFace,
                        complicationLocation.complicationId,
                        *complicationLocation.complicationSupportedTypes
                ),
                ComplicationsConfigActivity.COMPLICATION_CONFIG_REQUEST_CODE
        )
    }
}