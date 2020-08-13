package com.example.sunny.maps1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sunny.maps1.models.NewsPojo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    String TAG =this.getClass().getName();
    private GoogleMap mMap;
    Intent intent;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTheme(R.style.SplaceTheme);//todo move to manifies
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        progressBar=findViewById(R.id.progressBar);
        intent = new Intent(getApplicationContext(), NewsActivity.class);
    }
    public void display_dialog(String Country,final String code)
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_input_get)
                .setTitle("Confirm the location")
                .setMessage("Do want to get headlines for "+ Country +" ?")
                .setPositiveButton("GET NEWS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ShowNews(code);
                    }
                })
                .setNegativeButton("NO",null)
                .show();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Address address = getCountryName(this,latLng.latitude,latLng.longitude);
        if(address!=null)
            display_dialog(address.getCountryName(),address.getCountryCode());
        else
            Toast.makeText(this,"Can't Get Country Name,",Toast.LENGTH_LONG).show();
    }

    public Address getCountryName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        Address ad=null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                ad= addresses.get(0);
            }
        } catch (IOException ignored) {
              Log.i("getCountryName",ignored.toString());
        }
        return ad;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;
       mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
       //mMap.setOnMapLongClickListener(this);
       mMap.setOnMapClickListener(this);
        LatLng india = new LatLng(22,60);
       mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
    }


    public  void ShowNews(String countryCode)
    {
        intent.putExtra("CountryCode",countryCode);
        startActivity(intent);
    }


}