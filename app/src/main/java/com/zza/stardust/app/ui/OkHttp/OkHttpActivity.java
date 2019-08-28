package com.zza.stardust.app.ui.OkHttp;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
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
    @BindView(R.id.bt_post_7)
    Button btPost7;
    @BindView(R.id.bt_post_8)
    Button btPost8;
    @BindView(R.id.bt_post_9)
    Button btPost9;
    @BindView(R.id.bt_post_10)
    Button btPost10;
    @BindView(R.id.bt_post_11)
    Button btPost11;

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

    @OnClick({R.id.bt_get_1, R.id.bt_get_2, R.id.bt_post_1, R.id.bt_post_2, R.id.bt_post_3, R.id.bt_post_4, R.id.bt_post_5, R.id.bt_post_6, R.id.bt_post_7, R.id.bt_post_8, R.id.bt_post_9, R.id.bt_post_10, R.id.bt_post_11})
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
            case R.id.bt_post_7:
                responseCaching();
                break;
            case R.id.bt_post_8:
                cancelCall();
                break;
            case R.id.bt_post_9:
                timeout();
                break;
            case R.id.bt_post_10:
                perCallConfiguration();
                break;
            case R.id.bt_post_11:
                handlingAuthentication();
                break;
        }
    }

    /**
     * 处理身份验证
     */
    private void handlingAuthentication() {
        StringBuffer buffer = new StringBuffer();

        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {

                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        if (response.request().header("Authorization") != null) {
                            return null; // Give up, we've already attempted to authenticate.
                        }
                        buffer.append("Authenticating for response: " + response + "\r\n");
                        buffer.append("Challenges: " + response.challenges() + "\r\n");

                        String credential = Credentials.basic("jesse", "password1");
                        return response.request().newBuilder()
                                .header("Authorization", credential)
                                .build();
                    }
                })
                .build();

        Request request = new Request.Builder()
                .url("http://publicobject.com/secrets/hellosecret.txt")
                .get()
                .build();

        final Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("handlingAuthentication onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                buffer.append("handlingAuthentication onResponse:   " + result + "\r\n");
                runOnUiThread(() -> tvContent.setText(buffer));

            }
        });
    }

    /**
     * 每个call配置
     */
    private void perCallConfiguration() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url("https://httpbin.org/delay/1")
                .get()
                .build();

        OkHttpClient clientCopy = client.newBuilder()
                .readTimeout(500, TimeUnit.MILLISECONDS)
                .build();

        final Call call = clientCopy.newCall(request);
        StringBuffer buffer = new StringBuffer();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("perCallConfiguration onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                buffer.append("perCallConfiguration onResponse:   " + result + "\r\n");
                runOnUiThread(() -> tvContent.setText(buffer));

            }
        });
    }

    /**
     *  超时
     */
    private void timeout() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

         Request request = new Request.Builder()
                .url("https://httpbin.org/delay/12")
                .get()
                .build();
        final Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvContent.setText("timeout onFailure: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                StringBuffer buffer = new StringBuffer();
                buffer.append("timeout onResponse:   " + result + "\r\n");
                runOnUiThread(() -> tvContent.setText(buffer));

            }
        });
    }

    /**
     * 取消请求
     */
    private void cancelCall() {
        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                .build();

        final long startNanos = System.nanoTime();
        final Call call = client.newCall(request);

        StringBuffer buffer = new StringBuffer();
        // Schedule a job to cancel the call in 1 second.
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                buffer.append(String.format("%.2f Canceling call.%n",
                        (System.nanoTime() - startNanos) / 1e9f));
                call.cancel();
                buffer.append(String.format("%.2f Canceled call.%n",
                        (System.nanoTime() - startNanos) / 1e9f) );
                runOnUiThread(() -> tvContent.setText(buffer));
            }
        }, 1, TimeUnit.SECONDS);

        executor.schedule(new Runnable() {
            @Override
            public void run() {
                buffer.append(String.format("%.2f Executing call.%n", (System.nanoTime() - startNanos) / 1e9f) );
                try (Response response = call.execute()) {
                    buffer.append(String.format("%.2f Call was expected to fail, but completed: %s%n",
                            (System.nanoTime() - startNanos) / 1e9f, response));
                } catch (IOException e) {
                    buffer.append(String.format("%.2f Call failed as expected: %s%n",
                            (System.nanoTime() - startNanos) / 1e9f, e) );
                }
            }
        }, 0, TimeUnit.SECONDS);
    }

    /**
     * 支持缓存
     */
    private void responseCaching() {
        String url = "https://publicobject.com/helloworld.txt";
        File cacheDirectory = new File(Environment.getExternalStorageDirectory() + "/cache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(cacheDirectory, cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求
                .build();
        final Call call1 = okHttpClient.newCall(request);
        final Call call2 = okHttpClient.newCall(request);
        new Thread(() -> {
            try {
                StringBuffer buffer = new StringBuffer();
                //直接execute call
                Response response1 = call1.execute();
                if (!response1.isSuccessful()) {
                    runOnUiThread(() -> tvContent.setText("responseCaching onFailure"));
                } else {
                    String result = response1.body().string();

                    buffer.append("responseCaching1 onResponse:   " + result + "\r\n");
                    buffer.append("responseCaching1 cache response:    " + response1.cacheResponse() + "\r\n");
                    buffer.append("responseCaching1 network response:  " + response1.networkResponse() + "\r\n");
                    runOnUiThread(() -> tvContent.setText(buffer));
                }
                Response response2 = call2.execute();
                if (!response2.isSuccessful()) {
                    runOnUiThread(() -> tvContent.setText("responseCaching onFailure"));
                } else {
                    String result = response2.body().string();
                    buffer.append("responseCaching2 onResponse:   " + result + "\r\n");
                    buffer.append("responseCaching2 cache response:    " + response2.cacheResponse() + "\r\n");
                    buffer.append("responseCaching2 network response:  " + response2.networkResponse() + "\r\n");
                    runOnUiThread(() -> tvContent.setText(buffer));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 拦截器
     * <p>
     * 请查看打印输出
     */
    private void testInterceptor() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        Request request = new Request.Builder()
                .url("https://www.publicobject.com/helloworld.txt")
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
                .url("https://www.baidu.com")
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
        String url = "https://wwww.baidu.com";
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
        String url = "https://wwww.baidu.com";
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
