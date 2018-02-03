package net.syarihu.android.watchfacesample

import android.support.wearable.complications.ComplicationData

enum class ComplicationLocation(
        val complicationId: Int,
        val complicationSupportedTypes: IntArray
) {
    LEFT(101, intArrayOf(
            ComplicationData.TYPE_RANGED_VALUE,
            ComplicationData.TYPE_ICON,
            ComplicationData.TYPE_SHORT_TEXT,
            ComplicationData.TYPE_SMALL_IMAGE
    )),
    RIGHT(102, intArrayOf(
            ComplicationData.TYPE_RANGED_VALUE,
            ComplicationData.TYPE_ICON,
            ComplicationData.TYPE_SHORT_TEXT,
            ComplicationData.TYPE_SMALL_IMAGE
    ));

    companion object {
        fun getComplicationIds(): IntArray =
                values().map { it.complicationId }.toIntArray()

        fun valueOf(complicationId: Int): ComplicationLocation? =
                values().firstOrNull { it.complicationId == complicationId }
    }
}
