package com.example.sunny.maps1;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sunny.maps1.fragments.NewsListFrag;


public class NewsActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        context=getApplicationContext();
        String countryCode=getIntent().getExtras().getString("CountryCode");
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.mainFrame,NewsListFrag.newInstance(countryCode));
        ft.commit();
    }


}
