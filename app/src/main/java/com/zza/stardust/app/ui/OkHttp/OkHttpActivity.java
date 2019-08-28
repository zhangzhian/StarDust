package com.zza.stardust.app.ui.OkHttp;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class OkHttpActivity extends MActivity {

    @BindView(R.id.bt_get_1)
    Button btGet1;
    @BindView(R.id.bt_get_2)
    Button btGet2;
    @BindView(R.id.bt_post_1)
    Button btPost1;
    @BindView(R.id.bt_post_2)
    Button btPost2;
    @BindView(R.id.bt_post_3)
    Button btPost3;
    @BindView(R.id.bt_post_4)
    Button btPost4;
    @BindView(R.id.bt_post_5)
    Button btPost5;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.bt_post_6)
    Button btPost6;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ok_http;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @OnClick({R.id.bt_get_1, R.id.bt_get_2, R.id.bt_post_1, R.id.bt_post_2, R.id.bt_post_3, R.id.bt_post_4, R.id.bt_post_5, R.id.bt_post_6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_1:
                asynchronousGetRequests();
                break;
            case R.id.bt_get_2:
                synchronizedGetRequests();
                break;
            case R.id.bt_post_1:
                postString();
                break;
            case R.id.bt_post_2:
                postStream();
                break;
            case R.id.bt_post_3:
                postFile();
                break;
            case R.id.bt_post_4:
                postForm();
                break;
            case R.id.bt_post_5:
                postMultipartBody();
                break;
            case R.id.bt_post_6:
                testInterceptor();
                break;
        }
    }

    private void testInterceptor() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        Request request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")
                .header("User-Agent", "OkHttp Example")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("testInterceptor onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String result = response.body().string();
                if (body != null) {
                    LogUtil.d("testInterceptor: " + result);
                    body.close();
                }
                runOnUiThread(() -> tvContent.setText("testInterceptor onResponse: " + result));
            }
        });
    }

    /**
     * POST方式提交分块请求
     * <p>
     * 使用公司项目的一个接口调试通过，为避免一些问题，接口和参数删掉
     */
    private void postMultipartBody() {
        OkHttpClient client = new OkHttpClient();

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/logo_star_dust.png");
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        RequestBody filebody = MultipartBody.create(MEDIA_TYPE_PNG, file);

        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("data", file.getName(), filebody)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"vin\""),
                        RequestBody.create(null, ""))
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"iccid\""),
                        RequestBody.create(null, ""))
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"type\""),
                        RequestBody.create(null, ""))
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"jobId\""),
                        RequestBody.create(null, ""))
                .build();

        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("postMultipartBody onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                runOnUiThread(() -> tvContent.setText("postMultipartBody onResponse: " + result));
            }

        });
    }

    /**
     * POST方式提交表单
     * 通过FormBody#Builder构造RequestBody
     */
    private void postForm() {
        //https://www.wanandroid.com/article/query/0/json
        //方法：POST
        //参数：
        //  页码：拼接在链接上，从0开始。
        //  k ： 搜索关键词
        RequestBody requestBody = new FormBody.Builder()
                .add("k", "okhttp")
                .build();
        Request request = new Request.Builder()
                .url("https://www.wanandroid.com/article/query/0/json")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("postForm onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                StringBuffer buffer = new StringBuffer();
                buffer.append("postForm: " + "\r\n");
                buffer.append(response.protocol() + " " + response.code() + " " + response.message() + "\r\n");
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    buffer.append(headers.name(i) + ":" + headers.value(i) + "\r\n");
                }
                buffer.append("postForm: " + response.body().string());

                runOnUiThread(() -> tvContent.setText(buffer.toString()));
            }
        });
    }

    /**
     * POST提交文件
     * 文件没有的话会失败
     * 需要在路径下添加文件
     * 还需要权限
     */
    private void postFile() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        String path = Environment.getExternalStorageDirectory().getPath() + "/zza.md";
        File file = new File(path);
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, file))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("postFile onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                StringBuffer buffer = new StringBuffer();
                buffer.append("postFile: " + "\r\n");
                buffer.append(response.protocol() + " " + response.code() + " " + response.message() + "\r\n");
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    buffer.append(headers.name(i) + ":" + headers.value(i) + "\r\n");
                }
                buffer.append("postFile: " + response.body().string());

                runOnUiThread(() -> tvContent.setText(buffer.toString()));
            }
        });
    }

    /**
     * POST方式提交流
     */
    private void postStream() {
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("I am zza.");
            }
        };

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("postStream onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                StringBuffer buffer = new StringBuffer();
                buffer.append("postStream: " + "\r\n");
                buffer.append(response.protocol() + " " + response.code() + " " + response.message() + "\r\n");
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    buffer.append(headers.name(i) + ":" + headers.value(i) + "\r\n");
                }
                buffer.append("onResponse: " + response.body().string());

                runOnUiThread(() -> tvContent.setText(buffer.toString()));
            }
        });
    }

    /**
     * POST方式提交String
     * 在构造 Request对象时，需要多构造一个RequestBody对象，携带要提交的数据。
     * 在构造 RequestBody 需要指定MediaType，用于描述请求/响应 body 的内容类型
     */
    private void postString() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am zza.";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("postString onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                StringBuffer buffer = new StringBuffer();
                buffer.append("postString: " + "\r\n");
                buffer.append(response.protocol() + " " + response.code() + " " + response.message() + "\r\n");
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    buffer.append(headers.name(i) + ":" + headers.value(i) + "\r\n");
                }
                buffer.append("onResponse: " + response.body().string());

                runOnUiThread(() -> tvContent.setText(buffer.toString()));
            }
        });
    }

    /**
     * 同步GET请求
     * new OkHttpClient;
     * 构造Request对象；
     * 通过前两步中的对象构建Call对象；
     * 在子线程中通过Call#execute()方法来提交同步请求；
     */
    private void synchronizedGetRequests() {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(() -> {
            try {
                //直接execute call
                Response response = call.execute();
                String result = response.body().string();
                runOnUiThread(() -> tvContent.setText("synchronizedGetRequests run: " + result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 异步GET请求:
     * new OkHttpClient;
     * 构造Request对象；
     * 通过前两步中的对象构建Call对象；
     * 通过Call#enqueue(Callback)方法来提交异步请求；
     */
    private void asynchronousGetRequests() {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("asynchronousGetRequests onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                runOnUiThread(() -> tvContent.setText("asynchronousGetRequests onResponse: " + result));

            }
        });
    }

}
