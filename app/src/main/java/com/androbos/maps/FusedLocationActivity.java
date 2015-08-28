package com.androbos.maps;

import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FusedLocationActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener{
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_FAIL = 1;
    public static final String TAG_RECEIVER = "receiver";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_RESULT = "result";

    @InjectView(R.id.alamat)
    TextView alamat;

    private AddressResultReceiver addressResultReceiver;
    private Location location;
    private GoogleApiClient googleApiClient;
    private Boolean locationUpdate = false;
    private TextView update;
    private TextView update2;

    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fused_location);
        ButterKnife.inject(this);

        addressResultReceiver = new AddressResultReceiver(new Handler());

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        update = (TextView)findViewById(R.id.alamat);
        update2 = (TextView)findViewById(R.id.alamat2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()){
            if (locationUpdate)
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
            googleApiClient.disconnect();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fused_location, menu);
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
    public void onConnected(Bundle bundle) {
        Toast.makeText(FusedLocationActivity.this, "Connected", Toast.LENGTH_SHORT).show();
        if (location == null){
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location !=null){
                Toast.makeText(FusedLocationActivity.this, "Get location user : "+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
                startIntentService();
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, REQUEST,this);
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("tag", "1oupdate");
        Toast.makeText(FusedLocationActivity.this, "Location update : (" + location.getLatitude() + "," + location.getLongitude() + ") accuracy:" + location.getAccuracy(), Toast.LENGTH_SHORT).show();
        update.setText(Double.toString(location.getLongitude()));
        update2.setText(Double.toString(location.getLatitude()));

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,REQUEST,this);

    }

    protected void startIntentService(){
        Intent intent =new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(TAG_RECEIVER, addressResultReceiver);
        intent.putExtra(TAG_LOCATION,location);
        startService(intent);
    }
    class AddressResultReceiver extends ResultReceiver{

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }
    }
}
