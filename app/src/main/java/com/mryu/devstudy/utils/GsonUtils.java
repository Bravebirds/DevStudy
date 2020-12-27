package com.mryu.devstudy.utils;
import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import java.lang.reflect.Type;

public class GsonUtils {
    private static  String TAG ="GsonUtils";
    static RequestQueue queue;
    private static Gson filterNullGson;
    private static Gson nullableGson;
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

