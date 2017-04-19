package com.apimdb;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by ZUP on 11/04/2017.
 */

public class MyApplication extends Application {
    private static MyApplication myInstance;
    private static RequestQueue myRequestQueue;
    private static String TAG = "DEFAULT";
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        myInstance = this;
        initImageLoader();
    }

    private void initImageLoader() {
        myRequestQueue = Volley.newRequestQueue(getApplicationContext());
        imageLoader = new ImageLoader(this.myRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });
    }

    public static synchronized MyApplication getInstance() {
        return myInstance;
    }

    public RequestQueue getRequestQueue() {
        if (myRequestQueue == null) {

            myRequestQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
        return myRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag) {
        if (myRequestQueue != null) {
            myRequestQueue.cancelAll(tag);
        }
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }

}
