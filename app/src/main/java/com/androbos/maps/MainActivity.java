package com.androbos.maps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends ActionBarActivity implements OnMapReadyCallback {
    GoogleMap m_map;
    boolean mapReady = false;
    private Button btnMap;
    private Button btnSatelitte;
    private Button btnHybrid;
    static final CameraPosition NEWYORK = CameraPosition.builder()
            .target(new LatLng(40.784,-73.9857))
            .zoom(21)
            .bearing(0)
            .tilt(45)
            .build();
    static final CameraPosition SEATTLE = CameraPosition.builder()
            .target(new LatLng(47.6204,-122.3491))
            .zoom(17)
            .bearing(0)
            .tilt(45)
            .build();
    static final CameraPosition TOKYO = CameraPosition.builder()
            .target(new LatLng(35.6895,139.6917))
            .zoom(17)
            .bearing(0)
            .tilt(45)
            .build();
    static final CameraPosition DUBLIN = CameraPosition.builder()
            .target(new LatLng(53.3478,-6.2597))
            .zoom(17)
            .bearing(0)
            .tilt(45)
            .build();

    MarkerOptions renton;

    MarkerOptions kirkland;

    MarkerOptions everett;

    MarkerOptions lynnwood;

    MarkerOptions montlake;

    MarkerOptions kent;


    LatLng rentonn=new LatLng(47.489805, -122.120502);
    LatLng kirklandd=new LatLng(47.7301986, -122.1768858);
    LatLng everettt=new LatLng(47.978748,-122.202001);
    LatLng lynnwoodd=new LatLng(47.819533,-122.32288);
    LatLng montlakee=new LatLng(47.7973733,-122.3281771);
    LatLng kentt=new LatLng(47.385938,-122.258212);
    LatLng showaree=new LatLng(47.38702,-122.23986);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button FusedLocation = (Button)findViewById(R.id.FusedLocation);
        FusedLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FusedLocationActivity.class);
                startActivity(intent);
            }
        });
        renton = new MarkerOptions()
                .position(new LatLng(47.489805, -122.120502))
                .title("Renton")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        renton = new MarkerOptions()
                .position(new LatLng(47.489805, -122.120502))
                .title("Renton")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

        kirkland = new MarkerOptions()
                .position(new LatLng(47.7301986, -122.1768858))
                .title("Kirkland")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

        everett = new MarkerOptions()
                .position(new LatLng(47.978748,-122.202001))
                .title("Everett")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

        lynnwood = new MarkerOptions()
                .position(new LatLng(47.819533,-122.32288))
                .title("Lynnwood")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

        montlake = new MarkerOptions()
                .position(new LatLng(47.7973733,-122.3281771))
                .title("Montlake Terrace")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

        kent = new MarkerOptions()
                .position(new LatLng(47.385938,-122.258212))
                .title("Kent Valley")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));


        Button Seattle = (Button)findViewById(R.id.seattle);
        Seattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady){
                    flyTo(SEATTLE);
                }
            }
        });
        Button tokyo = (Button)findViewById(R.id.tokyo);
        tokyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady){
                    flyTo(TOKYO);
                }
            }
        });
        Button dublin = (Button)findViewById(R.id.dublin);
        dublin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady){
                    flyTo(DUBLIN);
                }
            }
        });
        btnMap = (Button)findViewById(R.id.btnMap);
        btnSatelitte = (Button)findViewById(R.id.btnSatellite);
        btnHybrid = (Button)findViewById(R.id.btnHybrid);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady) {
                    m_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }

            }
        });
        btnSatelitte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady) {
                    m_map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });
        btnHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady) {
                    m_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }
        });
        Button streetView = (Button)findViewById(R.id.streetView);
        streetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StreetViewActivity.class);
                startActivity(intent);
            }
        });
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap Map) {
        mapReady=true;
        m_map = Map;

        m_map.addMarker(renton);
        m_map.addMarker(kirkland);
        m_map.addMarker(everett);
        m_map.addMarker(lynnwood);
        m_map.addMarker(montlake);
        m_map.addMarker(kent);


        //LatLng newYork = new LatLng(40.7484,-73.9857);
        //CameraPosition target = CameraPosition.builder().target(newYork).zoom(14).build();
        //m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
        flyTo(SEATTLE);
        Map.addCircle(new CircleOptions()
                .center(rentonn)
                .radius(5000)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(64, 0, 255, 0)));



    }
    private void flyTo(CameraPosition target)
    {
        m_map.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);



    }
}
