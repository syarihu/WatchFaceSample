package net.syarihu.android.watchfacesample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import java.util.*

class ComplicationsConfigPreviewView : View {
    private var calendar: Calendar = Calendar.getInstance()
    private var timePaint: Paint = Paint().apply {
        color = Color.WHITE
        textSize = 75f
        isAntiAlias = true
    }
    private var datePaint: Paint = Paint().apply {
        color = Color.WHITE
        textSize = 20f
        isAntiAlias = true
    }
    private var timePosition: Point = Point()
    private var datePosition: Point = Point()
    private var timeBounds: Rect = Rect()
    private var dateBounds: Rect = Rect()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        calendar.timeInMillis = System.currentTimeMillis()
        val s_time = DateUtils.formatDateTime(
                context,
                calendar.timeInMillis,
                DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_24HOUR
        )
        timePaint.getTextBounds(s_time, 0, s_time.length, timeBounds)
        timePosition.set(
                canvas.width / 2 - timeBounds.width() / 2,
                canvas.height / 2
        )
        val s_date = DateUtils.formatDateTime(
                context,
                calendar.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY
        )
        datePaint.getTextBounds(s_date, 0, s_date.length, dateBounds)
        datePosition.set(canvas.width / 2 - dateBounds.width() / 2, canvas.height / 2 + timeBounds.height() / 2 + (10 / resources.displayMetrics.density).toInt())

        canvas.run {
            drawColor(Color.BLACK)
            drawText(s_time, timePosition.x.toFloat(), timePosition.y.toFloat(), timePaint)
            drawText(s_date, datePosition.x.toFloat(), datePosition.y.toFloat(), datePaint)
        }
    }
}