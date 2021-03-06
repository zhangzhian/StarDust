package com.zza.library.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * <p>
 * 带圆角的矩形ImageView
 * TODO 修改为可以选择圆角位置的模式
 */

public class RoundRectImageView extends AppCompatImageView {

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {10.0f, 10.0f, 10.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f,};


    public RoundRectImageView(Context context) {
        super(context);
    }

    public RoundRectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RoundRectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 画图
     * by Hankkin at:2015-08-30 21:15:53
     *
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
