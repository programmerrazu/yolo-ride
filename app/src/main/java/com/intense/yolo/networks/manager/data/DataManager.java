package com.intense.yolo.networks.manager.data;

import com.intense.yolo.networks.listener.ObjectDataParserListener;
import com.intense.yolo.networks.listener.StringDataParserListener;
import com.intense.yolo.networks.task.ApiHitTask;
import com.intense.yolo.networks.task.ObjectResponseTask;
import com.intense.yolo.networks.task.StringResponseTask;

import org.json.JSONObject;

public class DataManager {

    /**************** object data task with header
     * **************
     * @param methodType
     * @param url
     * @param body
     * @param header
     * @param odpListener
     */
    public static void taskManager(String methodType, String url, JSONObject body, JSONObject header, final ObjectDataParserListener odpListener) {
        ObjectResponseTask task = new ObjectResponseTask(methodType, url, body, header);
        task.setTaskListener(new ObjectResponseTask.TaskListener() {
            @Override
            public void dataGetSuccessfully(String riderInfo) {
                if (riderInfo == null) {
                    odpListener.onDataLoadFailed(riderInfo);
                } else {
                   /* if (riderInfo.getResponseStatus()) {
                        odpListener.onDataLoadSuccessfully(riderInfo);
                    } else {
                        odpListener.onDataLoadFailed(riderInfo);
                    }*/
                }
            }

            @Override
            public void dataGetFailed(String riderInfo) {
                odpListener.onDataLoadFailed(riderInfo);
            }
        });
        task.execute();
    }


    /******************* string data task with header
     * ********************
     * @param methodType
     * @param url
     * @param body
     * @param header
     * @param sdpListener
     */
    public static void taskManager(String methodType, String url, JSONObject body, JSONObject header, final StringDataParserListener sdpListener) {
        StringResponseTask task = new StringResponseTask(methodType, url, body, header);
        task.setTaskListener(new StringResponseTask.TaskListener() {
            @Override
            public void dataGetSuccessfully(String response) {
                if (response == null) {
                    sdpListener.onDataLoadFailed(response);
                } else {
                    sdpListener.onDataLoadSuccessfully(response);
                }
            }

            @Override
            public void dataGetFailed(String response) {
                sdpListener.onDataLoadFailed(response);
            }
        });
        task.execute();
    }

    public static void taskManager(String methodType, final StringDataParserListener sdpListener) {
        ApiHitTask task = new ApiHitTask(methodType);
        task.setTaskListener(new ApiHitTask.TaskListener() {
            @Override
            public void dataGetSuccessfully(String response) {
                if (response == null) {
                    sdpListener.onDataLoadFailed(response);
                } else {
                    sdpListener.onDataLoadSuccessfully(response);
                }
            }

            @Override
            public void dataGetFailed(String response) {
                sdpListener.onDataLoadFailed(response);
            }
        });
        task.execute();
    }
}