package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;


public class AddParcelActivity extends AppCompatActivity implements OnMapReadyCallback {
    Polygon polygon = null;
    List<LatLng> latLngDataList = new ArrayList<>();
    List<Marker> markerDataList = new ArrayList<>();

    private List<LatLng> latLngList1 = new ArrayList<>();
    private List<Marker> markerList1 = new ArrayList<>();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parcel);
        latLngList1 = (List<LatLng>) getIntent().getSerializableExtra("latLngList");
        markerList1 = (List<Marker>) getIntent().getSerializableExtra("markerList");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map1);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        PolygonOptions polygonOptions = new PolygonOptions();
        for (LatLng getLatLng : latLngList1) {
            polygonOptions.add(getLatLng);
        }
        mMap.addPolygon(polygonOptions);


        mMap.setOnMapClickListener(latLng -> {


//Todo polygon inside polygon condition
            boolean contain = PolyUtil.containsLocation(latLng,latLngList1, true);
            Log.d("hjhg", "onMapReady: "+contain);
            //Todo condition true  than create

            if(contain){
                for (Marker marker : markerDataList) marker.remove();
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                Marker marker = mMap.addMarker(markerOptions);
                latLngDataList.add(latLng);
                markerDataList.add(marker);
                if (polygon != null) {
                    polygon.remove();
                }
                PolygonOptions polygonOptions1 = new PolygonOptions();
                polygonOptions1.addAll(latLngDataList);
                polygon = mMap.addPolygon(polygonOptions1);
                polygon.setFillColor(R.color.cardview_dark_background);
                polygon.setStrokeColor(Color.RED);
                polygon.setStrokeWidth(4);
            }else{

            }

        });

    }

}

