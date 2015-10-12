package demo.ylf.com.firstdemo.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * @author YLF
 *
 *         HttpOk 访问网络工具类
 *
 */
public class HttpUtils {

    /**
     * 发送OK post 请求
     */
    public static String sendOkPost(String url, Map<String, String> param) {
        try {
            OkHttpClient client = new OkHttpClient();
            FormEncodingBuilder form = new FormEncodingBuilder();
            if (param.size() > 0) {
                for (Map.Entry<String, String> values : param.entrySet()) {
                    form.add(values.getKey(),
                            URLEncoder.encode(values.getValue(), "UTF-8"));
                }
            }
            RequestBody body = form.build();
            Request request = new Request.Builder().url(url).post(body).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "连接失败";
            }
        } catch (Exception e) {
        }
        return "连接失败";

    }

    /**
     * 发送OK get 请求
     */
    public static String sendOkGet(String url) {
        try {
            // 创建OK 浏览器
            OkHttpClient client = new OkHttpClient();
            // 创建请求
            Request request = new Request.Builder().url(url).get().build();
            // 获取回应
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String str = response.body().string();
                return str;
            } else {
                return "连接失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "连接失败";

    }

   /* *//**
     * @param path
     * @return 利用APACHE 的HttpClient 发送post 请求
     *//*
    public static String sendClictPost(String path, Map<String, String> param) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(path);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (param.size() > 0) {
                for (Map.Entry<String, String> enty : param.entrySet()) {
                    list.add(new BasicNameValuePair(enty.getKey(), URLEncoder
                            .encode(enty.getValue(), "UTF-8")));
                }
            }
            post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 访问成功
                String result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "连接失败";
    }

    *//**
     * @param url
     * @param param
     * @return 利用 APACHE HttpClient 发送get 请求
     *//*
    public static String sendClientGet(String url, Map<String, String> param) {
        try {
            HttpClient client = new DefaultHttpClient();
            StringBuffer buff = new StringBuffer(url);
            if (param.size() > 0) {
                buff.append("?");
                for (Entry<String, String> enty : param.entrySet()) {
                    buff.append(enty.getKey());
                    buff.append("=");
                    buff.append(URLEncoder.encode(enty.getValue(), "UTF-8"));
                    buff.append("&");
                }
                buff.deleteCharAt(buff.length() - 1);
            }
            HttpGet get = new HttpGet(buff.toString());
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 连接成功
                String result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "连接失败";

    }
*/
    /**
     * @param url
     * @return HttpURLConnection 发送post 请求
     */
    public static String sendHttpConnPost(String url) {
        try {
            URL path = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) path.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setReadTimeout(1000 * 10);
            InputStream is = conn.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            byte bs[] = new byte[dis.available()];
            dis.read(bs);
            return new String(bs, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @return HttpURLConnection 发送get 请求
     */
    public static String sendHttpConnGet(String url) {
        try {
            URL path = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) path.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
            conn.setRequestMethod("GET");
            conn.setReadTimeout(1000 * 10);
            InputStream is = conn.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            byte bs[] = new byte[dis.available()];
            dis.read(bs);
            return new String(bs, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
