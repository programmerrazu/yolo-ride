package com.intense.yolo.networks.manager.api;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CallApi {

    /************** get with out header
     * **********
     * @param client
     * @param url
     * @return
     * @throws IOException
     */
    public static String GET(OkHttpClient client, HttpUrl url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute().body().string();
    }

    /************** get with header
     * *************
     * @param client
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public static String GET(OkHttpClient client, HttpUrl url, Headers header) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(header)
                .build();
        return client.newCall(request).execute().body().string();
    }

    /***************** post with out header
     * ***************
     * @param client
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    public static String POST(OkHttpClient client, HttpUrl url, RequestBody body) throws IOException {

//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody bodys = RequestBody.create(JSON, body.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute().body().string();
    }

    /*********** post with header
     * *************
     * @param client
     * @param url
     * @param body
     * @param header
     * @return
     * @throws IOException
     */
    public static String POST(OkHttpClient client, HttpUrl url, RequestBody body, Headers header) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(header)
                .post(body)
                .build();
        return client.newCall(request).execute().body().string();
    }

    /************** put with out header
     * ***************
     * @param client
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    public static String PUT(OkHttpClient client, HttpUrl url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        return client.newCall(request).execute().body().string();
    }

    /************** put with header
     * ***********
     * @param client
     * @param url
     * @param body
     * @param header
     * @return
     * @throws IOException
     */
    public static String PUT(OkHttpClient client, HttpUrl url, RequestBody body, Headers header) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(header)
                .put(body)
                .build();
        return client.newCall(request).execute().body().string();
    }

    /************* multipart post body with out header
     * ************
     * @param client
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
   /* public static String POST(OkHttpClient client, HttpUrl url, MultipartBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute().body().string();
    } */

    /************ multipart post body with header
     * **************
     * @param client
     * @param url
     * @param body
     * @param header
     * @return
     * @throws IOException
     */
    /* public static String POST(OkHttpClient client, HttpUrl url, MultipartBody body, Headers header) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(header)
                .post(body)
                .build();
        return client.newCall(request).execute().body().string();
    } */
}