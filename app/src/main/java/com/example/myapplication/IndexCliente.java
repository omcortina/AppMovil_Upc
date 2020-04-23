package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.Dominio.Sitio;
import com.example.myapplication.Dominio.SitioEvento;
import com.example.myapplication.Servicios.ListarActividadesService;
import com.example.myapplication.Servicios.ListarEventosService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class IndexCliente extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RevisarPermisos();
        setContentView(R.layout.activity_index_cliente2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        View itemEvento = (View) findViewById(R.id.item_eventos);
        View itemActividad = (View) findViewById(R.id.item_actividades);

        itemEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListarEventosService servicio = new ListarEventosService(IndexCliente.this);
                servicio.execute();
            }
        });

        itemActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListarActividadesService servicio = new ListarActividadesService(IndexCliente.this);
                servicio.execute();
            }
        });
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
        mMap = googleMap;
        PolylineOptions lineOptions = null;
        ArrayList<LatLng> points = null;
        LatLng valledupar = new LatLng(getMyPosition().getLatitude(), getMyPosition().getLongitude());
        mMap.addMarker(new MarkerOptions().position(valledupar).title("Valledupar"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(valledupar,15));
        Intent intent = getIntent();
        int id_evento = intent.getIntExtra("id_evento",0);
        if (id_evento != 0){
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();
            List<Sitio> array_sitios = SitioEvento.FindAllSitiosPorEvento(this, id_evento);
            points.add(valledupar);
            for(Sitio s: array_sitios){
                double latitud = Double.parseDouble(s.getLatitud());
                double longitud = Double.parseDouble(s.getLongitud());
                LatLng punto = new LatLng(latitud, longitud);
                mMap.addMarker(new MarkerOptions().position(punto).title(s.getNombre()));
                points.add(punto);
            }

            

            lineOptions.addAll(points);
            lineOptions.width(2);
            lineOptions.color(Color.BLUE);

            mMap.addPolyline(lineOptions);
        }
    }

    public Location getMyPosition(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return  location;
    }

    public void RevisarPermisos(){
        int permissionCheck = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        permissionCheck = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        //Add a marker in Sydney and move the camera
        Location my_posicion = getMyPosition();
        double lat;
        double lon;
        if (my_posicion==null){
            //colocamos la localizacion de colombia si no lee la actual
            lat = 2.8894434;
            lon = -73.783892;
            //Snackbar.make(getCurrentFocus(), "Por favor asegurese de tener el GPS encendido es necesario para el uso de la aplicacion", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            Toast.makeText(this, "Por favor asegurese de tener el GPS encendido es necesario para el uso de la aplicacion", Toast.LENGTH_LONG).show();
        }else{
            lat = my_posicion.getLatitude();
            lon = my_posicion.getLongitude();
        }
    }
}
