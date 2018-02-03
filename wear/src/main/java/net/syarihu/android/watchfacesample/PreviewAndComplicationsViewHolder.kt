package net.syarihu.android.watchfacesample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.syarihu.android.watchfacesample.databinding.ViewholderPreviewAndComplicationsBinding

class PreviewAndComplicationsViewHolder(
        context: Context, parent: ViewGroup?
) : RecyclerView.ViewHolder(
        ViewholderPreviewAndComplicationsBinding.inflate(
                LayoutInflater.from(context), parent, false
        ).root
)