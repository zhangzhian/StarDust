package com.zza.library.weight;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.zza.library.R;

/**
 *
 *         用例：
 *
 *         InputPasswordDialog dialog = new InputPasswordDialog(getActivity()) {
 *             @Override
 *             public void inputFinish(String str) {
 *
 *             }
 *         };
 *         dialog.showDialog();
 *
 */
public abstract class InputPasswordDialog extends Dialog {


    PayPwdEditText ppePwd;
    private Context context;

    public InputPasswordDialog(@NonNull Context context) {
//        super(context);
        super(context, R.style.ActionDialogStyle);
        this.context = context;
        setContentView(R.layout.dialog_input_password);
        initView();
    }

    protected void initView() {
        ppePwd = findViewById(R.id.ppe_pwd);
        ppePwd.initStyle(R.drawable.edit_num_bg, 6, 0.5f, R.color.text333333, R.color.text333333, 25);
        ppePwd.setFocus();
        ppePwd.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                inputFinish(str);
            }
        });
    }
    public void showDialog() {
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels*0.9);
        //layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels);
        layoutParams.y = 0; //距离屏幕底部的距离
        dialogWindow.setAttributes(layoutParams);
        show();
    }

    /**
     * 输入完成
     *
     * @param str
     */
    public abstract void inputFinish(String str);

}

