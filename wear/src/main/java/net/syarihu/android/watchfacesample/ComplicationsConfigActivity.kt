package net.syarihu.android.watchfacesample

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import net.syarihu.android.watchfacesample.databinding.ActivityComplicationsConfigBinding

class ComplicationsConfigActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityComplicationsConfigBinding>(
                this, R.layout.activity_complications_config
        )
        binding.wearableRecyclerView.run {
            layoutManager = LinearLayoutManager(this@ComplicationsConfigActivity)
            adapter = ComplicationsConfigRecyclerViewAdapter(this@ComplicationsConfigActivity)
        }
    }
}
