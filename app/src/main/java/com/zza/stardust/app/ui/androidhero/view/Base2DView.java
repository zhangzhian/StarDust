package com.zza.stardust.app.ui.androidhero.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * setAntiAlias();            //设置画笔的锯齿效果
 * <p>
 * 　　setColor();                //设置画笔的颜色
 * <p>
 * 　　setARGB();                 //设置画笔的A、R、G、B值
 * <p>
 * 　　setAlpha();                //设置画笔的Alpha值
 * <p>
 * 　　setTextSize();             //设置字体的尺寸
 * <p>
 * 　　setStyle();                //设置画笔的风格（空心或实心）
 * <p>
 * 　　setStrokeWidth();          //设置空心边框的宽度
 * <p>
 * 　　getColor();                //获取画笔的颜色
 */

public class Base2DView extends View {

    private Paint paint1, paint2, paint3, paint4, paint5, paint6, paint7, paint8, paint9;

    public Base2DView(Context context) {
        super(context);
        initView();
    }

    public Base2DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public Base2DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {

        paint1 = new Paint();
        paint1.setColor(Color.BLUE);
        paint1.setAntiAlias(true);
        //空心
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(5);

        paint2 = new Paint();
        paint2.setColor(Color.BLUE);
        //实心
        paint2.setStyle(Paint.Style.FILL);

        paint3 = new Paint();
        paint3.setColor(Color.RED);
        paint3.setAntiAlias(true);
        paint3.setStyle(Paint.Style.FILL);
        paint3.setStrokeWidth(5.0f);
        //paint3.setDither(true);
        paint4 = new Paint();
        paint4.setColor(Color.GREEN);
        paint4.setTextSize(50.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(50, 100, 100, 150, paint1);
        canvas.drawRect(150, 100, 200, 150, paint2);
        canvas.drawPoint(250,150,paint3);
        canvas.drawLine(300,150,500,100,paint3);

        float[] pts = {50,250,150,200,150,200,250,250};
        canvas.drawLines(pts,paint3);
        canvas.drawRoundRect(50, 300, 100, 350, 5,5,paint1);
        canvas.drawCircle(100,450, 50,paint3);

        canvas.drawArc(100,500,150,550,90,-90,true,paint1);
        canvas.drawArc(200,500,250,550,90,-90,false,paint1);
        canvas.drawArc(300,500,350,550,90,-180,true,paint2);
        canvas.drawArc(400,500,450,550,90,-180,false,paint2);

        canvas.drawOval(100,600,300,700,paint1);

        canvas.drawText("Hello World",100,800,paint4);

        Path path  = new Path();
        path.moveTo(100,1000);
        path.lineTo(300,1000);
        path.lineTo(400,1200);
        path.lineTo(500,1100);
        canvas.drawPath(path,paint1);
    }
}
