package cn.allen.medical.data;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import allen.frame.AllenManager;
import allen.frame.tools.Logger;
import cn.allen.medical.utils.Constants;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class HttpRequest {
    private static final long TIME_OUT = 120;//单位秒S
    private String token;
    public HttpRequest() {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.User_Token,"");
    }
    public <T>void okhttppost(String mothed, String json,HttpCallBack<T> callBack) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS).authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return response.request().newBuilder().header("Authorization", token).build();
                    }
                })
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(Constants.url + mothed)
                .addHeader("keep-alive","false").post(body)
                .build();
        Logger.http("header",request.headers().toString());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailed(new MeRespone(-1,"网络请求错误!","网络请求错误!"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    Logger.http("data",">>"+data);
                    MeRespone respone = new Gson().fromJson(data,MeRespone.class);
                    if(respone.getCode()==200){
                        callBack.onSuccess(new Gson().fromJson(respone.getData().toString(),callBack.getGenericityType()));
                        callBack.onTodo(respone);
                    }else{
                        callBack.onFailed(respone);
                    }
                }
            }
        });
    }
    private Handler handler = new Handler();
}