package com.example.finalyearproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.adapters.AccidentsList;
import com.example.finalyearproject.adapters.CustomAdapter;
import com.example.finalyearproject.models.AccidentsLocationList;
import com.example.finalyearproject.models.LocationPost;
import com.example.finalyearproject.models.user;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class AccidentsListAttendanceActivity extends AppCompatActivity {

    public interface AccidentLocationGetterService {
        @GET("/api/accidents2")
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
        List<AccidentsList> accidentsLists = new ArrayList<>();
        accidentsLists.add(new AccidentsList("India", "1234567890"));
        accidentsLists.add(new AccidentsList("Zim", "1234567890"));
        accidentsLists.add(new AccidentsList("Mozw", "1234567890"));
        //========================GET Location=====================================
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://3e71-2c0f-f8f0-d348-0-a9dc-e54e-3381-1039.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try {
            AccidentsListAttendanceActivity.AccidentLocationGetterService service = retrofit.create(AccidentsListAttendanceActivity.AccidentLocationGetterService.class);
            Call<Object> call= service.getAccidentLocation();
            Response response = call.execute();
            Type listType = new TypeToken<List<AccidentsLocationList>>() {}.getType();
            List<AccidentsLocationList> accidentsLocationList = new Gson().fromJson(response.body().toString(), listType);
            System.out.println(response.body());

            setContentView(R.layout.activity_accidents_list_attendance);
            simpleList = (ListView) findViewById(R.id.simpleListView);
            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), accidentsLocationList);

            simpleList.setAdapter(customAdapter);

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