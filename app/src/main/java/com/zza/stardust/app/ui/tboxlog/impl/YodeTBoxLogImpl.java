package com.zza.stardust.app.ui.tboxlog.impl;

import com.zza.stardust.callback.TransFileCallBack;
import com.zza.stardust.callback.UpgradeCallBack;

import java.util.ArrayList;

public interface YodeTBoxLogImpl {
    void TransFiles(String host, int post, String localDir, ArrayList<String> remoteFilenames,
                    TransFileCallBack callBack);

    void TransFile(String host, int post, String localDir, String remoteFilename,
                   TransFileCallBack callBack);
}
