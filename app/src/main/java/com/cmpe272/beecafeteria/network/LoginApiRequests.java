package com.cmpe272.beecafeteria.network;

import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.cmpe272.beecafeteria.BuildConfig;
import com.cmpe272.beecafeteria.modelResponse.PostResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Rushil on 11/13/2015.
 */
public class LoginApiRequests {
    private static final Gson gson = new GsonBuilder()
            .create();
    /**
     * Returns a dummy object
     *
     * @param listener is the listener for the correct answer
     * @param errorListener is the listener for the error response
     *
     * @return {@link GsonGetRequest}
     */
    public static GsonGetRequest<PostResponse> getDummyObject
    (
            @NonNull final Response.Listener<PostResponse> listener,
            @NonNull final Response.ErrorListener errorListener
    )
    {
        final String url = BuildConfig.apiDomainName + "/v2/55973508b0e9e4a71a02f05f";

        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<PostResponse>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

    /**
     * Returns a dummy object's array
     *
     * @param listener is the listener for the correct answer
     * @param errorListener is the listener for the error response
     *
     * @return {@link GsonGetRequest}
     */
    public static GsonGetRequest<ArrayList<PostResponse>> getDummyObjectArray
    (
            @NonNull final Response.Listener<ArrayList<PostResponse>> listener,
            @NonNull final Response.ErrorListener errorListener,
            String... params
    )
    {
        final String url = BuildConfig.apiDomainName + "/v2/5597d86a6344715505576725";

        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<ArrayList<PostResponse>>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }


    /**
     * An Login call that would send data and authenticate user through volley
     *
     * @param listener is the listener for the success response
     * @param errorListener is the listener for the error response
     * @param params parameters tht will be send in post request
     *
     * @return {@link GsonPostRequest}
     */
    public static GsonPostRequest postLoginRequest
    (
            @NonNull final Response.Listener<PostResponse> listener,
            @NonNull final Response.ErrorListener errorListener,
            String... params
    )
    {
        final String url = BuildConfig.apiDomainName + "/login";

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", params[0]);
        jsonObject.addProperty("pass", params[1]);

        /*final JsonArray squareGuys = new JsonArray();
        final JsonObject dev1 = new JsonObject();
        final JsonObject dev2 = new JsonObject();
        dev1.addProperty("name", "Jake Wharton");
        dev2.addProperty("name", "Jesse Wilson");
        squareGuys.add(dev1);
        squareGuys.add(dev2);

        jsonObject.add("squareGuys", squareGuys);*/

        final GsonPostRequest gsonPostRequest = new GsonPostRequest<>
                (
                        url,
                        jsonObject.toString(),
                        new TypeToken<PostResponse>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );

        gsonPostRequest.setShouldCache(false);

        return gsonPostRequest;
    }

}
