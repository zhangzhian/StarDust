package com.zza.stardust.app.ui.androidhero.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

//   通过saveLayer()方法，saveLayerAlpha()将一个图层入栈
//   使用restore(）方法，restoreToCount（）方法将一个图层出栈
public class LayerView extends View {
    private Paint mPaint;

    public LayerView(Context context) {
        super(context);
        mPaint = new Paint(Canvas.ALL_SAVE_FLAG);
    }

    public LayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Canvas.ALL_SAVE_FLAG);
    }

    public LayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Canvas.ALL_SAVE_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(150, 150, 100, mPaint);
        //canvas.saveLayerAlpha(0, 0, 400, 400, 0,Canvas.ALL_SAVE_FLAG);
        canvas.saveLayerAlpha(0, 0, 400, 400, 127,Canvas.ALL_SAVE_FLAG);
        //canvas.saveLayerAlpha(0, 0, 400, 400, 255,Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(200, 200, 100, mPaint);

        canvas.restore();
    }
}
