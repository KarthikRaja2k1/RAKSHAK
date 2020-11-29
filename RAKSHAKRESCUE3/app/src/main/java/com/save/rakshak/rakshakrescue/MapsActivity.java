package com.save.rakshak.rakshakrescue;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String DATA_STRING;
    String lat,lng;
    Double d_lat,d_lng;
    public static Handler handler;
    public static Thread thread1;
    public static Boolean status=false;
    public static Button button;
    public static LatLng latlng,latlng_currentloc;
    public static byte count=0;
    public static Marker now;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        startAct();

    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mMap.setMyLocationEnabled(true);
    }


    public void showLoc(View v){
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }


    public void exitMap(View v){
        status=false;
        finish();
    }


    public void startAct(){
        count=0;
        status=true;
        thread1=new Thread(new Loopclass());
        thread1.start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(getApplicationContext(),"count= "+count,Toast.LENGTH_SHORT).show();
                new BGTask().execute();
            }
        };
    }


    class BGTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(Void... params) {
            String locurl = "http://krazydomain.in/test.php";
            try {
                URL url = new URL(locurl);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                InputStream is = http.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                while ((DATA_STRING = br.readLine()) != null) {
                    sb.append(DATA_STRING + "\n");
                }
                br.close();
                is.close();
                http.disconnect();
                return sb.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            int i=0;
            while(s.charAt(i)!=' '){i++;}
            lat=s.substring(0,i-1);
            lng=s.substring(i+1);
            d_lat= Double.parseDouble(lat);
            d_lng= Double.parseDouble(lng);
            latlng = new LatLng(d_lat,d_lng);
            if(now!=null)
                now.remove();
            if(count==0)
            {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                count++;
            }
            else
            {

                now=mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_drop_black_24dp)).title("My loc"));

            }

        }
    }

    class Loopclass implements Runnable{

        @Override
        public void run() {
            while(true){
                if(status==false)break;

                Message msg=Message.obtain();
                msg.arg1=8;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}


        }
    }
}
