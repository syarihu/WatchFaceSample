package net.syarihu.android.watchfacesample;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.text.format.DateUtils;
import android.view.SurfaceHolder;

import java.util.Calendar;

public class DigitalWatchFaceService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine {
        // 時間を取得するためのCalendar
        Calendar mCalendar;
        // 時計の背景色
        int mBackgroundColor = Color.BLACK;
        // 時間を描画するためのPaint
        Paint mTimePaint;
        // 日付を描画するためのPaint
        Paint mDatePaint;
        // 時間の位置を保持するためのPoint
        Point mTimePosition;
        // 日付の位置を保持するためのPoint
        Point mDatePosition;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            // 時間の文字色やサイズをセット
            mTimePaint = new Paint();
            mTimePaint.setColor(Color.WHITE);
            mTimePaint.setTextSize(75);
            mTimePaint.setAntiAlias(true);

            // 日付の文字色やサイズをセット
            mDatePaint = new Paint();
            mDatePaint.setColor(Color.WHITE);
            mDatePaint.setTextSize(20);
            mDatePaint.setAntiAlias(true);

            // Calendarを初期化
            mCalendar = Calendar.getInstance();

            // 位置を初期化
            mTimePosition = new Point();
            mDatePosition = new Point();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            mBackgroundColor = inAmbientMode ? Color.BLACK : ContextCompat.getColor(getApplicationContext(), android.R.color.holo_blue_light);
            invalidate();
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            // 分が変わった時
            invalidate();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            // 時間を更新
            mCalendar.setTimeInMillis(System.currentTimeMillis());
            // 時間
            String s_time = DateUtils.formatDateTime(
                    DigitalWatchFaceService.this,
                    mCalendar.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_24HOUR
            );
            Rect s_time_bounds = new Rect();
            mTimePaint.getTextBounds(s_time, 0, s_time.length(), s_time_bounds);
            // 中央に配置するための座標を計算する
            mTimePosition.set(canvas.getWidth() / 2 - s_time_bounds.width() / 2, canvas.getHeight() / 2);
            // 日付
            String s_date = DateUtils.formatDateTime(
                    DigitalWatchFaceService.this,
                    mCalendar.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY
            );
            // 時間のテキストの大きさを再取得
            mTimePaint.getTextBounds(s_time, 0, s_time.length(), s_time_bounds);
            Rect s_date_bounds = new Rect();
            // 日付のテキストの大きさを取得
            mDatePaint.getTextBounds(s_date, 0, s_date.length(), s_date_bounds);
            mDatePosition.set(canvas.getWidth() / 2 - s_date_bounds.width() / 2, canvas.getHeight() / 2 + s_time_bounds.height() / 2 + (int) (10 / getResources().getDisplayMetrics().density));

            canvas.drawColor(mBackgroundColor);
            canvas.drawText(s_time, mTimePosition.x, mTimePosition.y, mTimePaint);
            canvas.drawText(s_date, mDatePosition.x, mDatePosition.y, mDatePaint);
        }
    }
}
