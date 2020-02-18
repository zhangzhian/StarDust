package com.zza.stardust.app.ui.androidart.SerializableParcelable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.androidart.aidl.Book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableParcelableActivity1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable_parcelable1);
        findViewById(R.id.button1).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(SerializableParcelableActivity1.this, SerializableParcelableActivity2.class);
            User user = new User(0, "jake", true);
            user.book = new Book();
            intent.putExtra("extra_user", (Serializable) user);
            startActivity(intent);
        });
        persistToFile();
    }

    private void persistToFile() {
        new Thread(() -> {
            User user = new User(1, "hello world", false);
            File dir = new File(Environment
                    .getExternalStorageDirectory().getPath()
                    + "/StarDust/ParcelableToFile/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File cachedFile = new File(Environment
                    .getExternalStorageDirectory().getPath()
                    + "/StarDust/ParcelableToFile/cache");
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = new ObjectOutputStream(
                        new FileOutputStream(cachedFile));
                objectOutputStream.writeObject(user);
                LogUtil.d("persist user:" + user);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
