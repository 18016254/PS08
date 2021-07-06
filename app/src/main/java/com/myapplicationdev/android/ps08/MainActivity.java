package com.myapplicationdev.android.ps08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Button btnNorth, btnCentral, btnEast;
    private GoogleMap map;
    LatLng poi_SG = new LatLng(1.352083, 103.819839);
    LatLng poi_North = new LatLng(1.463860, 103.812130);
    LatLng poi_Central = new LatLng(1.289440, 103.849983);
    LatLng poi_East = new LatLng(1.349860, 103.934190);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                if (map != null) {
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_SG, 10));

                Marker north = map.addMarker(new MarkerOptions().position(poi_North).title("HQ - North").snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm\n" +
                        "Tel:65433456\n").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

                Marker central = map.addMarker(new MarkerOptions().position(poi_Central).title("Central").snippet("Block 3A, Orchard Ave 3, 134542 \n" +
                        "Operating hours: 11am-8pm\n" +
                        "Tel:67788652\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                Marker east = map.addMarker(new MarkerOptions().position(poi_East).title("East").snippet("Block 555, Tampines Ave 3, 287788 \n" +
                        "Operating hours: 9am-5pm\n" +
                        "Tel:66776677\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(north)) {
                            Toast.makeText(MainActivity.this, "North", Toast.LENGTH_SHORT).show();
                        } else if (marker.equals(central)) {
                            Toast.makeText(MainActivity.this, "Central", Toast.LENGTH_SHORT).show();
                        } else if (marker.equals(east)) {
                            Toast.makeText(MainActivity.this, "East", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });

            }
        });

        btnNorth = findViewById(R.id.btnNorth);
        btnCentral = findViewById(R.id.btnCentral);
        btnEast = findViewById(R.id.btnEast);

        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North, 15));
                }

            }
        });

        btnCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central, 15));
                }
            }
        });

        btnEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East, 15));
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                    if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                        map.setMyLocationEnabled(true);
                    } else {
                        Log.e("GMap - Permission", "GPS access has not been granted");
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}