package com.example.chris.test4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.chris.test4.model.FlickrResponse;
import com.example.chris.test4.model.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    List<Item> pics;
    RecyclerView recyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        recyclerView = findViewById(R.id.rvPics);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
    
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    
        pics = new ArrayList<>();
    
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null)
        {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
    
        
        RetrofitHelper.getFlickr()
                .enqueue(new Callback<FlickrResponse>() {
                    @Override
                    public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response)
                    {
                        pics = response.body().getItems();
    
                        Log.d(TAG, "onResponse: "+pics.size());
    
                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(pics, ft);
    
                        recyclerView.setAdapter(recyclerAdapter);
                        
                        
                    }
                
                    @Override
                    public void onFailure(Call<FlickrResponse> call, Throwable t)
                    {
                    
                    }
                });
    }
}
