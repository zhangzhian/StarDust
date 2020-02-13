package com.zza.stardust.app.ui.androidart.SerializableParcelable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static com.zza.library.utils.LogUtil.TAG;

public class SerializableParcelableActivity2 extends Activity {

    private TextView textView;
    private User userS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable_parcelable2);
        textView = findViewById(R.id.tv_show);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userS = (User) getIntent().getSerializableExtra("extra_user");
        LogUtil.d("user:" + userS.toString());

        // Log.d(TAG, "UserManage.sUserId=" + UserManager.sUserId);
        recoverFromFile();
    }

    private void recoverFromFile() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                User user = null;
                File cachedFile = new File(Environment
                        .getExternalStorageDirectory().getPath()
                        + "/StarDust/ParcelableToFile/cache");
                if (cachedFile.exists()) {
                    ObjectInputStream objectInputStream = null;
                    try {
                        objectInputStream = new ObjectInputStream(
                                new FileInputStream(cachedFile));
                        user = (User) objectInputStream.readObject();
                        LogUtil.d("recover user:" + user);

                        User finalUser = user;
                        runOnUiThread(() -> textView.setText(userS.toString() + "\r\n"+ finalUser.toString()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (objectInputStream != null) {
                                objectInputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
