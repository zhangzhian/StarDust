package com.zza.library.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zza.library.R;
import com.zza.library.utils.DisplayUtil;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * <p>
 * 自定义的TitleLayout，中间标题，左右两侧可选设置图片或者是文字，左右两侧可以点击
 * 初步完善和测试完成
 * 开放view
 *
 * 使用例子
 *  <com.zza.library.weight.TitleLayout
 *         android:id="@+id/tl_title"
 *         android:layout_width="match_parent"
 *         android:layout_height="70dp"
 *         android:layout_alignParentTop="true"
 *         android:background="@color/white"
 *         android:fitsSystemWindows="true"
 *
 *         app:bottom_padding="1dp"
 *         app:center_text="@string/change_username"
 *         app:center_text_color="@color/black"
 *         app:center_text_size="16sp"
 *         app:left_image="@drawable/back_black"
 *         app:left_image_size="20dp"
 *
 *         app:left_padding="10dp"
 *         app:left_type="2"
 *         app:line_color="@color/lined7d7d7"
 *
 *         app:line_show="true"
 *
 *         app:line_size="1dp"
 *         app:right_padding="10dp"
 *         app:right_type="1"
 *         app:right_text="保存"
 *         app:right_text_size="16dp"
 *         app:top_padding="1dp"
 *         >
 * </com.zza.library.weight.TitleLayout>
 */

public class TitleLayout extends RelativeLayout implements View.OnClickListener {

    private ImageView ivLeft;
    private TextView tvLeft;

    private TextView tvTitle;

    private ImageView ivRight;
    private TextView tvRight;

    private View viewLine;

    private RelativeLayout rlView;
    //右侧类型 0：隱藏 1：文字 2：图片
    private int rightType = 0;
    //左侧类型 0：隱藏 1：文字 2：图片
    private int leftType = 0;

    private onTitleClickListener mListener;

    private Context context;


    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);

    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_title, this);

        ivLeft = findViewById(R.id.iv_left);
        tvLeft = findViewById(R.id.tv_left);

        tvTitle = findViewById(R.id.tv_title);

        ivRight = findViewById(R.id.iv_right);
        tvRight = findViewById(R.id.tv_right);

        rlView = findViewById(R.id.rl);
        viewLine = findViewById(R.id.view_line);

        ivLeft.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        tvRight.setOnClickListener(this);


        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout);

        //设置右侧按钮类型
        if (ta.getString(R.styleable.TitleLayout_right_type) != null)
            setRightType(Integer.parseInt(ta.getString(R.styleable.TitleLayout_right_type)));
        //设置右侧文字
        String msgRight = ta.getString(R.styleable.TitleLayout_right_text);
        if (!TextUtils.isEmpty(msgRight) && rightType == 1) {
            tvRight.setText(msgRight);
        }
        //设置右侧图片
        Drawable imageRight = ta.getDrawable(R.styleable.TitleLayout_right_image);
        if (imageRight != null && rightType == 2) {
            ivRight.setImageDrawable(imageRight);
        }
        //设置右侧文字颜色
        int rightColor = ta.getColor(R.styleable.TitleLayout_right_text_color, -1);
        if (rightColor != -1 && rightType == 1) {
            tvRight.setTextColor(rightColor);
        }
        //设置右侧文字大小
        float rightTextSize = ta.getDimension(R.styleable.TitleLayout_right_text_size,
                getResources().getDimension(R.dimen.default_title_text_size));
        if (rightType == 1)
            tvRight.setTextSize(DisplayUtil.px2sp(context, rightTextSize));

        //设置图片大小
        int rightImageSize = ta.getLayoutDimension(R.styleable.TitleLayout_right_image_size,
                getResources().getDimensionPixelSize(R.dimen.default_title_image_size));
        if (rightType == 2) {
            ViewGroup.LayoutParams ivrightParams = ivLeft.getLayoutParams();
            ivrightParams.width = rightImageSize;
            ivrightParams.height = rightImageSize;
            ivRight.setLayoutParams(ivrightParams);
        }

        //-----------------------------------------------------
        //设置中间title
        String title = ta.getString(R.styleable.TitleLayout_center_text);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        //设置中间文字颜色
        int centerColor = ta.getColor(R.styleable.TitleLayout_center_text_color, -1);
        if (centerColor != -1) {
            tvTitle.setTextColor(centerColor);
        }

        //设置中间文字大小
        float centerTextSize = ta.getDimension(R.styleable.TitleLayout_center_text_size,
                getResources().getDimension(R.dimen.default_title_text_size));
        tvTitle.setTextSize(DisplayUtil.px2sp(context, centerTextSize));

        //-----------------------------------------------------
        //设置左侧按钮类型
        if (ta.getString(R.styleable.TitleLayout_left_type) != null)
            setLeftType(Integer.parseInt(ta.getString(R.styleable.TitleLayout_left_type)));

        //设置左侧图片
        Drawable imageLeft = ta.getDrawable(R.styleable.TitleLayout_left_image);
        if (imageLeft != null && leftType == 2) {
            ivLeft.setImageDrawable(imageLeft);
        }

        //设置左侧文字
        String msgLeft = ta.getString(R.styleable.TitleLayout_left_text);
        if (!TextUtils.isEmpty(msgLeft) && leftType == 1) {
            tvLeft.setText(msgLeft);
        }

        //设置左侧文字颜色
        int leftColor = ta.getColor(R.styleable.TitleLayout_left_text_color, -1);
        if (leftColor != -1 && leftType == 1) {
            tvLeft.setTextColor(leftColor);
        }

        //设置左侧文字大小
        float lefeTextSize = ta.getDimension(R.styleable.TitleLayout_left_text_size,
                getResources().getDimension(R.dimen.default_title_text_size));
        if (leftType == 1) {
            tvLeft.setTextSize(DisplayUtil.px2sp(context, lefeTextSize));
        }

        int leftImageSize = ta.getLayoutDimension(R.styleable.TitleLayout_left_image_size,
                getResources().getDimensionPixelSize(R.dimen.default_title_image_size));
        if (leftType == 2) {
            ViewGroup.LayoutParams ivLeftParams = ivLeft.getLayoutParams();
            ivLeftParams.width = leftImageSize;
            ivLeftParams.height = leftImageSize;
            ivLeft.setLayoutParams(ivLeftParams);
        }

        //-----------------------------------------------------

        //设置是否显示line
        Boolean showLine = ta.getBoolean(R.styleable.TitleLayout_line_show, true);
        setViewLine(showLine);
        //line 的颜色
        Drawable lineColor = ta.getDrawable(R.styleable.TitleLayout_line_color);
        if (lineColor != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewLine.setBackground(lineColor);
            } else {
                viewLine.setBackgroundDrawable(lineColor);
            }
        }

        //line的尺寸 如果你设置为DP等单位，会做像素转换
        int lineSize = ta.getLayoutDimension(R.styleable.TitleLayout_line_size,
                getResources().getDimensionPixelSize(R.dimen.default_title_line_size));
        ViewGroup.LayoutParams params = viewLine.getLayoutParams();
        params.height = lineSize;
        viewLine.setLayoutParams(params);

        //-----------------------------------------------------
        //左右侧距离
        int rlRight = ta.getLayoutDimension(R.styleable.TitleLayout_right_padding,
                getResources().getDimensionPixelSize(R.dimen.default_title_padding_size));
        int rlLeft = ta.getLayoutDimension(R.styleable.TitleLayout_left_padding,
                getResources().getDimensionPixelSize(R.dimen.default_title_padding_size));
        int rlTop = ta.getLayoutDimension(R.styleable.TitleLayout_top_padding,
                getResources().getDimensionPixelSize(R.dimen.default_title_padding_size));
        int rlBottom = ta.getLayoutDimension(R.styleable.TitleLayout_bottom_padding,
                getResources().getDimensionPixelSize(R.dimen.default_title_padding_size));
        rlView.setPadding(rlLeft, rlTop, rlRight, rlBottom);


        ta.recycle();
    }

    public int getRightType() {
        return rightType;
    }


    public void setRightText(String content) {
        tvRight.setText(content);
    }

    /**
     * 设置右侧类型，默认为0
     * 0：隱藏 1：文字 2：圖片
     */

    public void setRightType(int rightType) {
        this.rightType = rightType;
        if (rightType == 0) {
            tvRight.setVisibility(View.GONE);
            ivRight.setVisibility(View.GONE);
        } else if (rightType == 1) {
            tvRight.setVisibility(View.VISIBLE);
            ivRight.setVisibility(View.GONE);
        } else if (rightType == 2) {
            tvRight.setVisibility(View.GONE);
            ivRight.setVisibility(View.VISIBLE);
        }

    }


    public int getLeftType() {
        return leftType;

    }


    public void setLeftType(int leftType) {
        this.leftType = leftType;
        if (leftType == 0) {
            ivLeft.setVisibility(View.GONE);
            tvLeft.setVisibility(View.GONE);
        } else if (leftType == 1) {
            ivLeft.setVisibility(View.GONE);
            tvLeft.setVisibility(View.VISIBLE);
        } else if (leftType == 2) {
            ivLeft.setVisibility(View.VISIBLE);
            tvLeft.setVisibility(View.GONE);
        }
    }


    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        //this.title = title;
        tvTitle.setText(title);
    }

    /**
     * 设置右侧文字的颜色
     *
     * @param color
     */
    public void setRightTextColor(int color) {
        tvRight.setTextColor(getResources().getColor(color));
    }


    /**
     * 设置是否显示line
     *
     * @param type
     */
    public void setViewLine(boolean type) {
        if (type == true) {
            viewLine.setVisibility(View.VISIBLE);
        } else {
            viewLine.setVisibility(View.GONE);
        }
    }


    /**
     * 设置点击监听
     *
     * @param mListener
     */
    public void setOnTitleClickListener(onTitleClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_left) {
            if (mListener != null) {
                mListener.leftClick(v);
            }

        } else if (i == R.id.tv_left) {
            if (mListener != null) {
                mListener.leftClick(v);
            }

        } else if (i == R.id.tv_title) {
            // if (mListener != null) {
            //    mListener.centerClick(v);
            // }

        } else if (i == R.id.iv_right) {
            if (mListener != null) {
                mListener.rightClick(v);
            }

        } else if (i == R.id.tv_right) {
            if (mListener != null) {
                mListener.rightClick(v);
            }

        }
    }


    public interface onTitleClickListener {
        //左面点击事件
        void leftClick(View view);

        //右侧单击事件
        void rightClick(View view);

    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public View getViewLine() {
        return viewLine;
    }

    public RelativeLayout getRlView() {
        return rlView;
    }
}
