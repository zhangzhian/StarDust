package com.zza.library.weight;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zza.library.R;

import static com.zza.library.utils.ImageUtil.decodeSampledBitmapFromResource;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * @Date: 2018/9/27 20:55
 * <p>
 * 初步完善完善测试AlertChooseDialog
 * 可对dialog的图片、提示、按钮进行设置
 * <p>
 * 开放了所有组件的View
 *
 * TODO 优化
 *
 */
public abstract class AlertChooseDialog extends Dialog implements View.OnClickListener {

    private TextView tvContent;
    private Button btLeft;
    private Button btRight;
    private ImageView ivIcon;
    private Context context;

    public AlertChooseDialog(Context context) {
        super(context, R.style.ActionDialogStyle);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alert);
        initView();

    }

    private void initView() {
        tvContent = findViewById(R.id.tv_content);
        btLeft = findViewById(R.id.bt_left);
        btRight = findViewById(R.id.bt_right);
        ivIcon = findViewById(R.id.iv_icon);
        //tvContent.setOnClickListener(this);
        btLeft.setOnClickListener(this);
        btRight.setOnClickListener(this);


        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.6);
        //layoutParams.height = (int) (context.getResources().getDisplayMetrics().heightPixels*0.5);
        //layoutParams.y = 100; //距离屏幕底部的距离
        dialogWindow.setAttributes(layoutParams);
    }


    /**
     * 显示Dialog
     */
    public void showDialog() {
        show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_content) {
        } else if (i == R.id.bt_left) {
            onLeft(v);

        } else if (i == R.id.bt_right) {
            onRight(v);

        }
    }

    /**
     * 设置布局
     *
     * @param gravity   dialog的权重,建议设置为Gravity.TOP / Gravity.BOTTOM / Gravity.CENTER
     * @param widthPer  距离两侧距离所占比例
     * @param heightPer 距离底部或者是顶部距离所占比例
     */
    public void setLayout(int gravity, float widthPer, float heightPer) {
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(gravity);
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        //layoutParams.x = (int) (context.getResources().getDisplayMetrics().widthPixels*0.1);
        layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * (1 - widthPer));
        //layoutParams.height = (int) (context.getResources().getDisplayMetrics().heightPixels*0.7);
        layoutParams.y = (int) (context.getResources().getDisplayMetrics().heightPixels * heightPer); //距离屏幕底部的距离

        dialogWindow.setAttributes(layoutParams);
    }

    /**
     * 设置按钮的数量
     *
     * @param type 0 没有按钮 1 显示1个左按钮 2 显示1个右按钮 3 显示2个按钮
     */

    public void setButtonNum(int type) {
        if (type == 0) {
            btLeft.setVisibility(View.GONE);
            btRight.setVisibility(View.GONE);
        } else if (type == 1) {
            btLeft.setVisibility(View.VISIBLE);
            btRight.setVisibility(View.GONE);
        } else if (type == 2) {
            btLeft.setVisibility(View.GONE);
            btRight.setVisibility(View.VISIBLE);
        } else if (type == 3) {
            btLeft.setVisibility(View.VISIBLE);
            btRight.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置中间的提示信息
     *
     * @param tvContent 提示内容
     * @param color     颜色
     * @param size      字体
     */
    public void setTvContent(String tvContent, int color, float size) {
        this.tvContent.setText(tvContent);
        if (size != 0) this.tvContent.setTextSize(size);
        if (color != 0)
            this.tvContent.setTextColor(context.getResources().getColor(color));
    }

    /**
     * 设置左边按钮的提示信息
     *
     * @param btLeftContent 左边按钮的文字
     * @param color         颜色
     * @param size          大学
     */
    public void setBtLeftText(String btLeftContent, int color, float size) {
        this.btLeft.setText(btLeftContent);
        if (size != 0) this.btLeft.setTextSize(size);
        if (color != 0)
            this.btLeft.setTextColor(context.getResources().getColor(color));
    }

    /**
     * 设置又边按钮的提示信息
     *
     * @param btRightContent 又边按钮的文字
     * @param color          颜色
     * @param size           大学
     */
    public void setBtRightText(String btRightContent, int color, float size) {
        this.btRight.setText(btRightContent);
        if (size != 0) this.btRight.setTextSize(size);
        if (color != 0)
            this.btRight.setTextColor(context.getResources().getColor(color));
    }

    /**
     * 设置中间的图片
     *
     * @param drawable 图片id
     * @param width    宽
     * @param height   高
     */
    public void setCenterIcon(int drawable, int width, int height) {
        this.ivIcon.setImageDrawable(context.getResources().getDrawable(drawable));
        //将任意一张图片压缩成width*height的缩略图
        ivIcon.setImageBitmap(
                decodeSampledBitmapFromResource(context.getResources(), drawable, width, height));

    }

    /**
     * 获取中间信息view
     *
     * @return
     */
    public TextView getTvContent() {
        return tvContent;
    }

    /**
     * 获取左边按钮view
     *
     * @return
     */
    public Button getBtLeft() {
        return btLeft;
    }

    /**
     * 获取右边按钮view
     */
    public Button getBtRight() {
        return btRight;
    }

    /**
     * 获取中间图片view
     *
     * @return
     */
    public ImageView getIvIcon() {
        return ivIcon;
    }


    /**
     * 获取context
     *
     * @return
     */
    public Context getAlertChooseDialogContext() {
        return context;
    }

    public abstract void onRight(View view);

    public abstract void onLeft(View view);


}
