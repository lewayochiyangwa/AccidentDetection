package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonReader;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalyearproject.adapters.AccidentsList;
import com.example.finalyearproject.adapters.CustomAdapter;
import com.example.finalyearproject.models.AccidentsLocationList;
import com.example.finalyearproject.models.AccidentsLocationList2;
import com.example.finalyearproject.models.Helpers;
import com.example.finalyearproject.models.LocationPost;
import com.example.finalyearproject.models.user;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class AccidentsListAttendanceActivity extends AppCompatActivity {

    public interface AccidentLocationGetterService {
        @GET("/api/accidents3")
        Call<Object> getAccidentLocation();
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accidents_list_attendance);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }*/

    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};


 //   int flags[] = {R.drawable.india, R.drawable.china, R.drawable.australia, R.drawable.portugle, R.drawable.america, R.drawable.new_zealand};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

       /* List<AccidentsList> accidentsLists = new ArrayList<>();
        accidentsLists.add(new AccidentsList("India", "1234567890"));
        accidentsLists.add(new AccidentsList("Zim", "1234567890"));
        accidentsLists.add(new AccidentsList("Mozw", "1234567890"));*/
        //========================GET Location=====================================
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
            AccidentsListAttendanceActivity.AccidentLocationGetterService service = retrofit.create(AccidentsListAttendanceActivity.AccidentLocationGetterService.class);
            Call<Object> call= service.getAccidentLocation();
            Response response = call.execute();
            String bbb = response.body().toString();

            //******************************

            //*****************************
            Type listType = new TypeToken<List<AccidentsLocationList2>>() {}.getType();
         List<AccidentsLocationList2> accidentsLocationList = new Gson().fromJson(response.body().toString(), listType);


            System.out.println(response.body());

            setContentView(R.layout.activity_accidents_list_attendance);
            simpleList = (ListView) findViewById(R.id.simpleListView);
            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), accidentsLocationList);
          //  CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), filteredList);


            customAdapter.setOnDeletedListener(new CustomAdapter.OnDeletedListener() {
                @Override
                public void onDeleted(int position) {
                    // Update the ListView or the underlying data source
                    accidentsLocationList.remove(position);
                    customAdapter.notifyDataSetChanged();
                }
            });

            simpleList.setAdapter(customAdapter);
            Button back = findViewById(R.id.back);
            back.setOnClickListener(v -> {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DashboardFragment fragment = new DashboardFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.simpleListView, fragment);
                transaction.commit();
            });

            CustomAdapter xc = new CustomAdapter();
           int x =  xc.getReturnVal();

           if(x == 1){
               AlertDialog alertDialog3 = new AlertDialog.Builder(AccidentsListAttendanceActivity.this).create();
               alertDialog3.setTitle("Alert");
               alertDialog3.setMessage("Updated Successfully");
               alertDialog3.show();
           }
            if(x == 0){


            }
           System.out.println(x);

        } catch (IOException e) {
            Exception cv = e;
            System.out.println(cv);

        } catch (Exception ex) {
            Exception cv = ex;
            System.out.println(cv);
        }

        //==============================================================



    }


}