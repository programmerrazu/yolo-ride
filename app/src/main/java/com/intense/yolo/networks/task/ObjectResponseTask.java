package com.intense.yolo.networks.task;

import android.os.AsyncTask;
import android.util.Log;

import com.intense.yolo.networks.manager.api.CallApi;
import com.intense.yolo.networks.manager.api.RequestedBodyBuilder;
import com.intense.yolo.networks.manager.api.RequestedHeaderBuilder;
import com.intense.yolo.networks.manager.api.RequestedUrlBuilder;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ObjectResponseTask extends AsyncTask<String, Integer, String> {

    private TaskListener taskListener;
    private OkHttpClient okHttpClient;
    private String methodType;
    private String url;
    private JSONObject body;
    private JSONObject header;

    public ObjectResponseTask(String methodType, String url, JSONObject body, JSONObject header) {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            okHttpClient = builder.build();
        }
        this.methodType = methodType;
        this.url = url;
        this.body = body;
        this.header = header;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = null;
        try {
            if (methodType.equalsIgnoreCase("GET")) {
                response = CallApi.GET(
                        okHttpClient,
                        RequestedUrlBuilder.buildRequestedUrl(url, body),
                        RequestedHeaderBuilder.buildRequestedHeader(header)
                );
            } else if (methodType.equalsIgnoreCase("POST")) {
                response = CallApi.POST(
                        okHttpClient,
                        RequestedUrlBuilder.buildRequestedUrl(url),
                        RequestedBodyBuilder.buildRequestedBody(body),
                        RequestedHeaderBuilder.buildRequestedHeader(header)
                );
            } else if (methodType.equalsIgnoreCase("PUT")) {
                response = CallApi.PUT(
                        okHttpClient,
                        RequestedUrlBuilder.buildRequestedUrl(url),
                        RequestedBodyBuilder.buildRequestedBody(body),
                        RequestedHeaderBuilder.buildRequestedHeader(header)
                );
            }
            Log.i("TaskWithHeader", "ok_http_response " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//RiderTaskParser.taskParse(response);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            //  taskListener.dataGetSuccessfully(result);
        } else {
            // taskListener.dataGetFailed(result);
        }
    }

    public TaskListener getTaskListener() {
        return taskListener;
    }

    public void setTaskListener(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    public interface TaskListener {

        void dataGetSuccessfully(String riderInfo);

        void dataGetFailed(String riderInfo);
    }
}