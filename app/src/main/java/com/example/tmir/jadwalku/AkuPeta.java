package com.example.tmir.jadwalku;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AkuPeta extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    float zoom = 18.0f;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aku_peta);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

      // gMap.setMapStyle(GoogleMap.MAP_TYPE_NORMAL);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng itera = new LatLng(-5.358378, 105.313438);
        gMap.addMarker(new MarkerOptions().position(itera).title("Institut Teknologi Sumatera").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_school_black_24dp)));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(itera,15));
    }

    private void enableMyLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            gMap.setMyLocationEnabled(true);
        }
    }


    public void onRequestPermissionResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults){
        switch (requestCode){
            case REQUEST_LOCATION_PERMISSION:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    enableMyLocation();
                    break;
                }
        }
    }

    @Override
    public void onBackPressed()
    {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
