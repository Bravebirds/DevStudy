package com.mryu.devstudy.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mryu.devstudy.R;

import org.json.JSONObject;

public class GsonUtils {
    private static  String TAG ="GsonUtils";
    static RequestQueue queue;
    public static void get(String path, final Context context, Response.Listener<JSONObject> res){
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        queue.add(new JsonObjectRequest(path, res, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG,"访问失败");
            }
        }));
    }


}
