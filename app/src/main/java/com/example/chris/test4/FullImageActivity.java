package com.example.chris.test4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullImageActivity extends AppCompatActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        
        String pic = getIntent().getStringExtra("pic");
    
        ImageView ivImage = findViewById(R.id.ivImage);
        Glide.with(this).load(pic).into(ivImage);
    }
}
