package com.example.artem.mapapp;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initMap();

    }

    public void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocationZoom(46.5, 30.5, 5);
//        goToLocationZoom();
        mGoogleMap.setOnPoiClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);


    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
//    private void goToLocationZoom() {
//        LatLng ll = new LatLng(lat, lng);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
//        mGoogleMap.moveCamera(update);

        LatLngBounds ODESSA = new LatLngBounds(new LatLng(46.35, 30.55), new LatLng(46.6, 30.85));

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ODESSA.getCenter(), 12));
        mGoogleMap.setLatLngBoundsForCameraTarget(ODESSA);

//        mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(1));

    }

//    public void geoLocate(View view) throws IOException {
//
//        Geocoder gc = new Geocoder(this);
//        List<Address> list = gc.getFromLocationName("Odessa", 1);
//        Address address = list.get(0);
//        String locality = address.getLocality();
//
//        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
//
//        double lat = address.getLatitude();
//        double lng = address.getLongitude();
//        LatLng ll = new LatLng(lat, lng);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
//        mGoogleMap.moveCamera(update);
//
//    }

    Marker marker;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickTest(View view) {
        double randomLat = ThreadLocalRandom.current().nextDouble(46.43, 46.5);
        double lat = Math.round(randomLat * 100);
        lat /= 100;
        double randomLng = ThreadLocalRandom.current().nextDouble(30.69, 30.8);
        double lng = Math.round(randomLng * 100);
        lng /= 100;
        if (marker != null){
            marker.remove();
        }
        marker = mGoogleMap.addMarker(new MarkerOptions().draggable(true).position(new LatLng(lat, lng)).icon(
                BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPoiClick(PointOfInterest poi) {
        Toast.makeText(getApplicationContext(), poi.name, Toast.LENGTH_LONG).show();
    }
}