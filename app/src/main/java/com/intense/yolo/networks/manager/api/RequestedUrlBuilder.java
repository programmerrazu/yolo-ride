package com.intense.yolo.networks.manager.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import okhttp3.HttpUrl;

public class RequestedUrlBuilder {

    /*************** build url with out data
     * ************
     * @param url
     * @return
     */
    public static HttpUrl buildRequestedUrl(String url) {
        return HttpUrl.parse(url);
    }

    /*************** build url with data
     * ***************
     * @param url
     * @param urlData
     * @return
     */
    public static HttpUrl buildRequestedUrl(String url, JSONObject urlData) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (urlData != null) {
            Iterator<String> urlKey = urlData.keys();
            while (urlKey.hasNext()) {
                String key = urlKey.next();
                try {
                    Object urlValue = urlData.get(key);
                    urlBuilder.addQueryParameter(key, urlValue.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return urlBuilder.build();
    }

    /* public static HttpUrl buildUrlWithParameter(String protocol, String host) {
        return new HttpUrl.Builder()
                .scheme(protocol)   //https
                .host(host)   // "www.somehostname.com"
                .addPathSegment("/")   //adds "/pathSegment" at the end of hostname
                .addQueryParameter("param", "value")   //add query parameters to the URL
                .addEncodedQueryParameter("encodedName", "encodedValue")   //add encoded query parameters to the URL
                .build();

         // The return URL:
        // https:
        // www.somehostname.com / pathSegment ? param1 = value1 & encodedName = encodedValue
    } */

   /* public static HttpUrl.Builder buildUrl(String url) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        return urlBuilder.addQueryParameter("_id", "");
    } */
}