package com.zza.stardust.app.ui.tboxprotobuf;

import android.os.Bundle;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.*;
/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * java api: https://developers.google.cn/protocol-buffers/
 * github: https://github.com/protocolbuffers/protobuf
 * 资料: https://developers.google.cn/protocol-buffers/
 * @CreateDate: 2020/2/5 17:47
 * @UpdateDate: 2020/2/5 17:47
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ProtobufActivity extends MActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_protobuf;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
    }
}
