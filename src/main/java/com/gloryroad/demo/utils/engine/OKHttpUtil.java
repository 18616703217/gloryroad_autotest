package com.gloryroad.demo.utils.engine;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class OKHttpUtil {
    private static Logger logger = LoggerFactory.getLogger(OKHttpUtil.class);

    private static OKHttpUtil okHttpUtils = null;

    private OKHttpUtil() {
    }

    public static OKHttpUtil getInstance() {
        if (okHttpUtils == null) {
            //加同步安全
            synchronized (OKHttpUtil.class) {
                if (okHttpUtils == null) {
                    okHttpUtils = new OKHttpUtil();
                }
            }
        }
        return okHttpUtils;
    }

    private static OkHttpClient okHttpClient = null;


    public synchronized static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            //判空 为空创建实例
            okHttpClient = new OkHttpClient();
/**
 * 和OkHttp2.x有区别的是不能通过OkHttpClient直接设置超时时间和缓存了，而是通过OkHttpClient.Builder来设置，
 * 通过builder配置好OkHttpClient后用builder.build()来返回OkHttpClient，
 * 所以我们通常不会调用new OkHttpClient()来得到OkHttpClient，而是通过builder.build()：
 */
            //缓存目录
            //   File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
            int cacheSize = 10 * 1024 * 1024;
            HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
            CookieJar cookieJar = new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    cookieStore.put(httpUrl.host(), list);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            };
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    logger.info(message);
                }
            });
            //Okhttp3的拦截器日志分类 4种
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS);


            okHttpClient = builder.build();
        }
        return okHttpClient;
    }


    public int doGetcode(String url, File file) {
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建Request
        Request request = new Request.Builder().url(url).build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        OutputStream outputStream = null;
        try {
            Response response = call.execute();
            int data = response.code();
            logger.info("http url"+url+" ,code="+data);
            if (data == 200) {
                outputStream = new BufferedOutputStream(new FileOutputStream(file));
                outputStream.write(response.body().bytes());
                logger.info("http length=="+response.body().bytes().length);
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }


    /*
     * get请求
     * */
    public String doGet(String url, Callback callback) {
        //创建OkHttpClient请求对象
        getOkHttpClient();
        //创建Request
        Request request = new Request.Builder().url(url).build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行同步请求
        call.enqueue(callback);

        return null;
    }

    //同步
    public <T> T doGet(String url, Class<T> tClass) {
        T t = null;
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建Request
        Request request = new Request.Builder()//.header("source", "android")
                .url(url).build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                String data = response.body().string();
                if (null != data && !data.equals("")) {
                    t = JSONObject.parseObject(data, tClass);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    //同步
    public String doGet(String url, JSONObject query) {
        String data = null;
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();

        for(String key: query.keySet()){
            if(!url.contains("?")){
                url = url + "?" + key + "=" + query.getString(key);
            }

            url = url + "&" + key + "=" + query.getString(key);
        }

        //创建Request
        Request request = new Request.Builder()//.header("source", "android")
                .url(url).build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                data = response.body().string();
                if (null != data && !data.equals("")) {
                    return data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    //同步
    public <T> List<T> doGetList(String url, Class<T> tClass) {
        List<T> t = new ArrayList<>();
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建Request
        Request request = new Request.Builder()//.header("source", "android")
                .url(url).build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                String data = response.body().string();
                if (null != data && !data.equals("")) {
                    t = JSONObject.parseArray(data, tClass);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    //同步
    public <T> T doGetForAuth(String url, Class<T> tClass) {
        T t = null;
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();
        String credential = Credentials.basic("miaomiaom", "mamiao.910");

        Request request = new Request.Builder().header("Authorization", credential).url(url).build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                String data = response.body().string();
                if (null != data && !data.equals("")) {
                    t = JSONObject.parseObject(data, tClass);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    //json请求
    public String doPostJson(String url, JSONObject query, JSONObject params) {
        OkHttpClient okHttpClient = getOkHttpClient();

        for(String key: query.keySet()){
            if(!url.contains("?")){
                url = url + "?" + key + "=" + query.getString(key);
            }

            url = url + "&" + key + "=" + query.getString(key);
        }

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , params.toJSONString());
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public <T> T doPostObject(String url, Map<String, String> params, Class<T> tClass) {
//        logger.info("doPostObject url={}", url);
//        T t = null;
//        Response response = doPost(url, params, null);
//        if (response != null && response.body() != null) {
//            try {
//                String data = response.body().string();
//                if (data != null) {
//                    t = JSONObject.parseObject(data, tClass);
//                    return t;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return t;
//
//    }

    public String doPostForm(String url, JSONObject query, JSONObject params) {

        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();

        for(String key: query.keySet()){
            if(!url.contains("?")){
                url = url + "?" + key + "=" + query.getString(key);
            }

            url = url + "&" + key + "=" + query.getString(key);
        }

        //3.x版本post请求换成FormBody 封装键值对参数
        FormBody.Builder builder = new FormBody.Builder();
        //遍历集合
        for (String key : params.keySet()) {
            builder.add(key, params.getString(key));
        }
        //创建Request
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post请求上传文件
     * 参数1 url
     * 参数2 回调Callback
     */
    public void uploadPic(String url, File file, String fileName) {
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建RequestBody 封装file参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建RequestBody 设置类型等
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart
                ("file", fileName, fileBody).build();
        //创建Request
        Request request = new Request.Builder().url(url).post(requestBody).build();

        //得到Call
        Call call = okHttpClient.newCall(request);
        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //上传成功回调 目前不需要处理
            }
        });

    }

    /**
     * Post请求发送JSON数据
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public void doPostJsonCall(String url, JSONObject query, JSONObject params, Callback callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                params.toJSONString());
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(callback);
    }
}
