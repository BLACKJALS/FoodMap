package com.example.alejandro.foodmap;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat = 0.0;
    double lng = 0.0;
    LatLng estacion;
    static double latitud;
    static double longitud;
    ArrayList<LatLng> estaciones;
    ArrayList<String> nombres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final Location localizacion = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (localizacion != null) {
            lat =  Double.valueOf(localizacion.getLatitude());
            lng =  Double.valueOf(localizacion.getLongitude());
        }

        android.location.LocationListener locListener = new android.location.LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLocationChanged(Location localizacion) {
                lat =  Double.valueOf(localizacion.getLatitude());
                lng = Double.valueOf(localizacion.getLongitude());
            }
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locListener);
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
        LatLng ubicacionActual = new LatLng(lat, lng);
        //mMap.addMarker(new MarkerOptions().position(ubicacionActual).title("Tu ubicación"));
        googleMap.addMarker(new MarkerOptions().position(ubicacionActual).title("Tu ubicación").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionActual));
        CameraPosition cameraPosition = CameraPosition.builder().target(ubicacionActual).zoom(15).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



        String []valores;
        estaciones = new ArrayList<>();
        nombres = new ArrayList<>();
        String nombre = "";
        for(int i = 0; i < Inicio.coordenadas.size(); i++){
            valores = Inicio.coordenadas.get(i).split(",");
            for(int j = 0; j < valores.length; j++){
                if(j == 0) {
                    nombre = valores[j];
                }
                if(j == 1) {
                    latitud = Double.parseDouble(valores[j]);
                }
                if(j == valores.length-1) {
                    longitud = Double.parseDouble(valores[j]);
                }
                estacion = new LatLng(longitud,latitud);
            }
            nombres.add(nombre);
            estaciones.add(estacion);
        }

        //estacion = new LatLng(lng,lat);
        String est = "";
        for(int i = 0; i < estaciones.size(); i++) {
            googleMap.addMarker(new MarkerOptions().position(estaciones.get(i)).title(nombres.get(i)));
        }

    }
}
