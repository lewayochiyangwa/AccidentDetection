package com.example.finalyearproject;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.example.finalyearproject.models.AccidentsLocationList;
import com.example.finalyearproject.models.Helpers;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.finalyearproject.databinding.ActivityMapsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    public interface AccidentLocationGetterService {
        @GET("/api/accidents3")
        Call<Object> getAccidentLocation();
    }
     List<AccidentsLocationList> accidentsLocationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //=================================================

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //==================================================

        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Helpers.connection())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            MapsActivity.AccidentLocationGetterService service = retrofit.create(MapsActivity.AccidentLocationGetterService.class);
            Call<Object> call = service.getAccidentLocation();
            Response response = call.execute();
            String v ="";
            Type listType = new TypeToken<List<AccidentsLocationList>>() {}.getType();
            accidentsLocationList = new Gson().fromJson(response.body().toString(), listType);



        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }

        //===================================================

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        SupportMapFragment googleMap=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap = googleMap;

        for(AccidentsLocationList accident:accidentsLocationList){
            LatLng sydney = new LatLng(Double.parseDouble(accident.getLatitude()), Double.parseDouble(accident.getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }



        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker clickedMarker) {

                Intent intent = new Intent(MapsActivity.this, AccidentsListAttendanceActivity.class);
                startActivity(intent);
                return false; // Return false to allow default marker click behavior
            }
        });
    }
}