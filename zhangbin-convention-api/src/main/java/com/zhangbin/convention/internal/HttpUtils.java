package com.zhangbin.convention.internal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zhangbin
 * @Type HttpUtils
 * @Desc
 * @date 2018-11-14
 * @Version V1.0
 */
public class HttpUtils {

    private static final int TIME_OUT = 5000;


    public static void reqHttpPostJson(String urlPath, String content) {
        OutputStream outputStream;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(TIME_OUT);
            urlConnection.setReadTimeout(TIME_OUT);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Type", "application/json");

            outputStream = urlConnection.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            urlConnection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
