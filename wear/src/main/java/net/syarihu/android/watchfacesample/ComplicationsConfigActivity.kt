package net.syarihu.android.watchfacesample

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.wearable.complications.ProviderChooserIntent
import net.syarihu.android.watchfacesample.databinding.ActivityComplicationsConfigBinding

class ComplicationsConfigActivity : Activity() {
    private var complicationsConfigRecyclerViewAdapter: ComplicationsConfigRecyclerViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityComplicationsConfigBinding>(
                this, R.layout.activity_complications_config
        )
        complicationsConfigRecyclerViewAdapter =
                ComplicationsConfigRecyclerViewAdapter(this)
        binding.wearableRecyclerView.run {
            layoutManager = LinearLayoutManager(this@ComplicationsConfigActivity)
            adapter = complicationsConfigRecyclerViewAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COMPLICATION_CONFIG_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            data?.let {
                complicationsConfigRecyclerViewAdapter?.updateSelectedComplication(
                        data.getParcelableExtra(ProviderChooserIntent.EXTRA_PROVIDER_INFO))
            }
        }
    }

    companion object {
        const val COMPLICATION_CONFIG_REQUEST_CODE = 1001
    }
}
