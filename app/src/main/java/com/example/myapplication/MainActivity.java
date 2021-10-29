package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment supportMapFragment;
    GoogleMap gMap;
    Button clear;
    Polygon polygon = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
            assert supportMapFragment != null;
            supportMapFragment.getMapAsync(this);
            clear = findViewById(R.id.btn_draw_polygon);
            clear.setOnClickListener(view -> {
            //List<LatLng> latLngList = new ArrayList<>();//Todo recreate object value 0
            List<Marker> markerList = new ArrayList<>();
            Intent intent = new Intent(MainActivity.this, AddParcelActivity.class);
            intent.putExtra("latLngList", (Serializable) latLngList);
            intent.putExtra("markerList", (Serializable) markerList);
            startActivity(intent);
            //location symbol hide
            if (polygon != null) {
                polygon.remove();
            }
            for (Marker marker : markerList) marker.remove();
            latLngList.clear();
            markerList.clear();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            gMap = googleMap;
            gMap.setOnMapClickListener(latLng -> {
            for (Marker marker : markerList) marker.remove();
            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            Marker marker = gMap.addMarker(markerOptions);
            latLngList.add(latLng);
            markerList.add(marker);
            if (polygon != null) {
                polygon.remove();
            }
            PolygonOptions polygonOptions = new PolygonOptions().addAll(latLngList).clickable(false);
            polygon = gMap.addPolygon(polygonOptions);
            polygon.setFillColor(R.color.cardview_dark_background);
            polygon.setStrokeColor(Color.GREEN);
            polygon.setStrokeWidth(4);
            });
    }
}