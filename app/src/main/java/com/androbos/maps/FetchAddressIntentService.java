package com.androbos.maps;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by tommy on 28/08/15.
 */
public class FetchAddressIntentService extends IntentService {
    protected ResultReceiver receiver;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public FetchAddressIntentService() {
        super("fetchaddress");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String error = "";
        Location location = intent.getParcelableExtra(FusedLocationActivity.TAG_LOCATION);
        receiver = intent.getParcelableExtra(FusedLocationActivity.TAG_RECEIVER);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> listAddress = null;
        try {
            Log.d("debug", "get location: ");
            listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
        }catch (IOException e){

        }catch (IllegalArgumentException ex){

        }
        if (listAddress == null && listAddress.size() == 0){
            Log.d("debug","gagal dapat alamat");
            deliverResultToReceiver(FusedLocationActivity.RESULT_FAIL, "no address found");

        }else {
            Log.d("debug", "berhasil dapat alamat");
            Address address = listAddress.get(0);
            ArrayList<String> addressFrag = new ArrayList<>();
            for (int i = 0; i<address.getMaxAddressLineIndex(); i++){
                addressFrag.add(address.getAddressLine(i));
            }
            deliverResultToReceiver(FusedLocationActivity.RESULT_SUCCESS,
                    TextUtils.join(System.getProperty("line.separator"), addressFrag));
        }

    }
    private void deliverResultToReceiver(int resultCode, String message){
        Bundle bundle = new Bundle();
        bundle.putString(FusedLocationActivity.TAG_RESULT, message);
        receiver.send(resultCode,bundle);
    }
}
