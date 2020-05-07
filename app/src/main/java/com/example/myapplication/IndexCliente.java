package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Dominio.Sitio;
import com.example.myapplication.Dominio.SitioEvento;
import com.example.myapplication.Routes.Routes;
import com.example.myapplication.Servicios.ListarActividadesService;
import com.example.myapplication.Servicios.ListarEventosService;
import com.example.myapplication.Servicios.ListarSitiosService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.maps.android.PolyUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndexCliente extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    JSONObject json;
    LatLng posicionActual;
    private TextView txt_nombre_sitio, txt_direccion_sitio, txt_descripcion_sitio;
    private RoundedImageView imagen_sitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RevisarPermisos();
        setContentView(R.layout.activity_index_cliente);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        View itemEvento = (View) findViewById(R.id.item_eventos);
        View itemActividad = (View) findViewById(R.id.item_actividades);
        View itemSitios = (View) findViewById(R.id.item_sitios);


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

        itemSitios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListarSitiosService servicio = new ListarSitiosService(IndexCliente.this);
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
        //PolylineOptions lineOptions = null;
        ArrayList<LatLng> points = null;
        posicionActual = new LatLng(getMyPosition().getLatitude(), getMyPosition().getLongitude());
        mMap.addMarker(new MarkerOptions().position(posicionActual).title("Aqui estoy yo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionActual,14));

        Intent intent = getIntent();
        int id_evento = intent.getIntExtra("id_evento",0);
        if (id_evento != 0){
            points = new ArrayList<LatLng>();
            //lineOptions = new PolylineOptions();
            List<Sitio> array_sitios = SitioEvento.FindAllSitiosPorEvento(this, id_evento);
            points.add(posicionActual);
            for(Sitio s: array_sitios){
                double latitud = Double.parseDouble(s.getLatitud());
                double longitud = Double.parseDouble(s.getLongitud());
                LatLng punto = new LatLng(latitud, longitud);
                mMap.addMarker(new MarkerOptions().position(punto).title(s.getNombre()));
                points.add(punto);
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    LatLng posicion_sitio = marker.getPosition();
                    Sitio sitio = Sitio.FindMarkerSitio(IndexCliente.this, posicion_sitio.latitude, posicion_sitio.longitude);
                    if(sitio != null){
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(IndexCliente.this, R.style.BottomSheetDialogTheme);
                        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_sitios, (LinearLayout) findViewById(R.id.bottom_sheet_sitio));
                        txt_nombre_sitio = bottomSheetView.findViewById(R.id.txt_nombre_sitio);
                        txt_direccion_sitio = bottomSheetView.findViewById(R.id.txt_direccion_sitio);
                        txt_descripcion_sitio = bottomSheetView.findViewById(R.id.txt_descripcion_sitio);
                        imagen_sitio = bottomSheetView.findViewById(R.id.imagen_sitio);

                        Picasso.get()
                                .load(Routes.directorio_imagenes+sitio.getRutaFoto())
                                //.resize(70,70)
                                .placeholder(R.drawable.loginn)
                                //.transform(new CropCircleTransformation())
                                .into(imagen_sitio);
                        txt_nombre_sitio.setText(sitio.getNombre());
                        txt_direccion_sitio.setText(sitio.getDireccion());
                        txt_descripcion_sitio.setText(sitio.getDescripcion());

                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetDialog.show();
                    }
                    return true;
                }
            });
        }

        Intent ubicar_sitio = getIntent();
        int id_sitio = ubicar_sitio.getIntExtra("id_sitio", 0);
        if(id_sitio != 0){
            Sitio sitio = Sitio.Find(IndexCliente.this, id_sitio);
            double latitud_sitio = Double.parseDouble(sitio.getLatitud());
            double longitud_sitio = Double.parseDouble(sitio.getLongitud());
            LatLng punto_sitio = new LatLng(latitud_sitio, longitud_sitio);
            mMap.addMarker(new MarkerOptions().position(punto_sitio).title(sitio.getNombre()));
        }
    }

    private void trazarRuta(JSONObject jso) {

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){

                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length();j++){

                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k<jSteps.length();k++){
                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.GRAY).width(5));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
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
