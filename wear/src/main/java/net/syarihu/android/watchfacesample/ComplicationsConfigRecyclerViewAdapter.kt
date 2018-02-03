package net.syarihu.android.watchfacesample

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import net.syarihu.android.watchfacesample.databinding.ViewholderPreviewAndComplicationsBinding

class ComplicationsConfigRecyclerViewAdapter(
        private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return PreviewAndComplicationsViewHolder(context, parent)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PreviewAndComplicationsViewHolder) {
            val defaultDrawable = ContextCompat.getDrawable(context, R.drawable.add_complication)
            DataBindingUtil.bind<ViewholderPreviewAndComplicationsBinding>(holder.itemView).run {
                leftComplication.setImageDrawable(defaultDrawable)
                leftComplicationBackground.visibility = View.INVISIBLE
                rightComplication.setImageDrawable(defaultDrawable)
                rightComplicationBackground.visibility = View.INVISIBLE
            }
        }
    }

}