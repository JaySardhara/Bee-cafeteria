package com.cmpe272.beecafeteria.base;

import android.app.Application;
import android.support.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cmpe272.beecafeteria.others.OkHttpStack;
import com.cmpe272.beecafeteria.parser.ParseUtils;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Rushil on 11/12/2015.
 */
public class App extends Application
{
    // Singleton application sInstance
    private static App sInstance;

    // Volley request queue
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate()
    {
        super.onCreate();

        sInstance = this;
        // register with parse
        ParseUtils.registerParse(this);
    }

    /**
     * @return the application singleton instance
     */
    public static App getInstance()
    {
        return sInstance;
    }

    /**
     * Returns a Volley request queue for creating network requests
     *
     * @return {@link com.android.volley.RequestQueue}
     */
    public RequestQueue getVolleyRequestQueue()
    {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    /**
     * Adds a request to the Volley request queue
     *
     * @param request to be added to the Volley requests queue
     */
    private static void addRequest(@NonNull final Request<?> request)
    {
        getInstance().getVolleyRequestQueue().add(request);
    }

    /**
     * Adds a request to the Volley request queue with a given tag
     *
     * @param request is the request to be added
     * @param tag tag identifying the request
     */
    public static void addRequest(@NonNull final Request<?> request, @NonNull final String tag)
    {
        request.setTag(tag);
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        addRequest(request);
    }

    /**
     * Cancels all the request in the Volley queue for a given tag
     *
     * @param tag associated with the Volley requests to be cancelled
     */
    public static void cancelAllRequests(@NonNull final String tag)
    {
        if (getInstance().getVolleyRequestQueue() != null)
        {
            getInstance().getVolleyRequestQueue().cancelAll(tag);
        }
    }

}
