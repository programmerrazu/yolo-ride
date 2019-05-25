package com.intense.yolo.networks.manager.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RequestedBodyBuilder {

    /******************* build body
     * ****************
     * @param bodyData
     * @return
     */
    public static RequestBody buildRequestedBody(JSONObject bodyData) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        Iterator<String> bodyKey = bodyData.keys();
        while (bodyKey.hasNext()) {
            String key = bodyKey.next();
            try {
                Object bodyValue = bodyData.get(key);
                bodyBuilder.add(key, bodyValue.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bodyBuilder.build();
    }

    /************** build multipart body
     * *************
     * @param title
     * @param imageFormat
     * @param file
     * @return
     */
    public static MultipartBody buildRequestedMultipartBody(String title, String imageFormat, File file) {
        MediaType MEDIA_TYPE = MediaType.parse("image/" + imageFormat); // e.g. "image/png"
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("filename", title) //e.g. title.png --> imageFormat = png
                .addFormDataPart("file", "...", RequestBody.create(MEDIA_TYPE, file))
                .build();
    }

   /* public static RequestBody requestedBody(JSONObject postBodyData) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBodyData.toString());
    } */

    /* public static RequestBody requestBody(String username, String password, String token) {
        return new FormBody.Builder()
                .add("action", "login")
                .add("format", "json")
                .add("username", username)
                .add("password", password)
                .add("login_token", token)
                .build();
    } */
}