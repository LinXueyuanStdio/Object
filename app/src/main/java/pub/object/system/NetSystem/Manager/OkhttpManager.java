package pub.object.system.NetSystem.Manager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.xylibrary.bone.util.Log;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.surface.fragment.
 * @date 17-7-19
 * @discription 网络响应
 */
public class OkhttpManager {
    @SuppressWarnings("unused")
    private static final String TAG = "OkhttpManager";

    public static OkHttpClient client = new OkHttpClient();

    public static String get(String url){
        try {
            client.newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS);
            Request request = new Request.Builder().url(url).build();
            Log.i(TAG, "request = " + request);

            Response response = client.newCall(request).execute();
            Log.i(TAG, "\nresponse = " + response);

            Log.i(TAG, "response.isSuccessful() = " + response.isSuccessful());
            if (response.isSuccessful()) {
                // 日了狗了, response.body().string() 只能调用一次
                // 在Log里面打印后就消耗掉了 消耗掉了 耗掉了 掉了 了
                // 后面 length() 就是0啦, 而且 return 返回是 "", "".length() = 0
                // 此时娃的心情是何等的握草
//                Log.i(TAG, "response.body().string() = " + response.body().string() +
//                        "\n length = " + response.body().string().length());
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            Log.e(TAG, "error");
            e.printStackTrace();
        }
        return null;
    }


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String post(String url, String json) throws IOException {
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            Log.e(TAG, "error");
            e.printStackTrace();
        }
        return null;
    }

}
