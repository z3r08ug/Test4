package com.example.chris.test4;

import com.example.chris.test4.model.FlickrResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Admin on 11/16/2017.
 */

public class RetrofitHelper
{
    public static final String BASE_URL = "http://api.flickr.com/services/feeds/";

    public static Retrofit create()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    //call interface to get response
    public static Call<FlickrResponse> getFlickr()
    {
        Retrofit retrofit = create();
        RetrofitService service = retrofit.create(RetrofitService.class);
        return service.getFlickr();
    }

    //create an interface for http verbs
    public interface RetrofitService
    {
        @GET("photos_public.gne?tag=kitten&format=json&nojsoncallback=1")
        Call<FlickrResponse> getFlickr();
    }
}