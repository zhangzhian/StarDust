package com.zza.stardust.app.ui.tboxupgrade.upgrade.impl;

import com.zza.stardust.callback.TransFileCallBack;
import com.zza.stardust.callback.UpgradeCallBack;

public interface YodeTBoxUpgrdeImpl {
    void TransFile(String host, int post, String filePath, TransFileCallBack callBack);
    void UpgradeTBox(String host, int post, String softVersion, String hardVersion, UpgradeCallBack callBack);
}
