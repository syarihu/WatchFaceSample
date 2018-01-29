package net.syarihu.android.watchfacesample

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.wearable.watchface.CanvasWatchFaceService
import android.text.format.DateUtils
import android.view.SurfaceHolder

import java.util.Calendar

class DigitalWatchFaceService : CanvasWatchFaceService() {

    override fun onCreateEngine(): Engine {
        return Engine()
    }

    inner class Engine : CanvasWatchFaceService.Engine() {
        // 時間を取得するためのCalendar
        private lateinit var calendar: Calendar
        // 時計の背景色
        private var backgroundColor = Color.BLACK
        // 時間を描画するためのPaint
        private lateinit var timePaint: Paint
        // 日付を描画するためのPaint
        private lateinit var datePaint: Paint
        // 時間の位置を保持するためのPoint
        private lateinit var timePosition: Point
        // 日付の位置を保持するためのPoint
        private lateinit var datePosition: Point
        // 時間の文字の大きさを保持するためのRect
        private lateinit var timeBounds: Rect
        // 日付の文字の大きさを保持するためのRect
        private lateinit var dateBounds: Rect

        override fun onCreate(holder: SurfaceHolder?) {
            super.onCreate(holder)
            // 時間の文字色やサイズをセット
            timePaint = Paint()
            timePaint.color = Color.WHITE
            timePaint.textSize = 75f
            timePaint.isAntiAlias = true

            // 日付の文字色やサイズをセット
            datePaint = Paint()
            datePaint.color = Color.WHITE
            datePaint.textSize = 20f
            datePaint.isAntiAlias = true

            // Calendarを初期化
            calendar = Calendar.getInstance()

            // 位置を初期化
            timePosition = Point()
            datePosition = Point()
            // 大きさの初期化
            timeBounds = Rect()
            dateBounds = Rect()
        }

        override fun onAmbientModeChanged(inAmbientMode: Boolean) {
            super.onAmbientModeChanged(inAmbientMode)
            backgroundColor = if (inAmbientMode)
                Color.BLACK
            else
                ContextCompat.getColor(
                        applicationContext,
                        android.R.color.holo_blue_light
                )
            invalidate()
        }

        override fun onTimeTick() {
            super.onTimeTick()
            // 分が変わった時
            invalidate()
        }

        override fun onDraw(canvas: Canvas, bounds: Rect) {
            super.onDraw(canvas, bounds)
            // 時間を更新
            calendar.timeInMillis = System.currentTimeMillis()
            // 時間
            val s_time = DateUtils.formatDateTime(
                    this@DigitalWatchFaceService,
                    calendar.timeInMillis,
                    DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_24HOUR
            )
            // 時間のテキストの大きさを再取得
            timePaint.getTextBounds(s_time, 0, s_time.length, timeBounds)
            // 中央に配置するための座標を計算する
            timePosition.set(
                    canvas.width / 2 - timeBounds.width() / 2,
                    canvas.height / 2
            )
            // 日付
            val s_date = DateUtils.formatDateTime(
                    this@DigitalWatchFaceService,
                    calendar.timeInMillis,
                    DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY
            )
            // 日付のテキストの大きさを取得
            datePaint.getTextBounds(s_date, 0, s_date.length, dateBounds)
            datePosition.set(canvas.width / 2 - dateBounds.width() / 2, canvas.height / 2 + timeBounds.height() / 2 + (10 / resources.displayMetrics.density).toInt())

            canvas.drawColor(backgroundColor)
            canvas.drawText(s_time, timePosition.x.toFloat(), timePosition.y.toFloat(), timePaint)
            canvas.drawText(s_date, datePosition.x.toFloat(), datePosition.y.toFloat(), datePaint)
        }
    }
}
