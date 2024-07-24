package com.example.finalyearproject.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.finalyearproject.LoginActivity;
import com.example.finalyearproject.R;
import com.example.finalyearproject.models.AccidentsLocationList;
import com.example.finalyearproject.models.AccidentsLocationList2;
import com.example.finalyearproject.models.Helpers;
import com.example.finalyearproject.models.PostId;
import com.example.finalyearproject.models.SmsMessage;
import com.example.finalyearproject.models.UserRequest;
import com.example.finalyearproject.models.user;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

//public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

public class CustomAdapter extends BaseAdapter {
    Context context;
    int returnVal;
    String countryList[];
    int flags[];
    LayoutInflater inflter;
    List<AccidentsList> accidentsLists;
    List<AccidentsLocationList2> accidentsLists2;
    public CustomAdapter(){

    }
    // public CustomAdapter(Context applicationContext, String[] countryList) {
          public CustomAdapter(Context applicationContext, String[] countryList) {
        this.context = context;
        this.countryList = countryList;
        this.flags = flags;
        inflter = (LayoutInflater.from(applicationContext));

    }

 /*   public CustomAdapter(Context applicationContext, List<AccidentsList> accidentsLists) {
        this.context = context;
        this.accidentsLists = accidentsLists;
        inflter = (LayoutInflater.from(applicationContext));
    }
*/

    public interface AccidentsAttendService {

        @POST("/api/accidentsAttend")
        Call<Object> accidentsAttend(@Body PostId userRequest);

    }

    public interface AccidentsSmsService {

        @POST("/api/sms")
        Call<Object> accidentsSms(@Body SmsMessage userRequest);

    }

    public CustomAdapter(Context applicationContext, List<AccidentsLocationList2> accidentsLists2) {
        this.context = context;
        this.accidentsLists2 = accidentsLists2;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return accidentsLists2.size(); //countryList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

              view = inflter.inflate(R.layout.card, null);
        TextView country = (TextView) view.findViewById(R.id.name);
        country.setText(accidentsLists2.get(i).getContactnumber());

        Button attend = view.findViewById(R.id.attend);
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add attendance==================
                Retrofit retrofit = null;
                try {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Helpers.connection())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                CustomAdapter.AccidentsAttendService service = retrofit.create(CustomAdapter.AccidentsAttendService.class);
                PostId postId = new PostId();
                postId.setId(accidentsLists2.get(i).getId());
                String to = accidentsLists2.get(i).getContactnumber();


                try {
                    Call <Object> call= service.accidentsAttend(postId);
                    Response response = call.execute();
                    System.out.println(response.body());
                    UpdateStatus u = new Gson().fromJson(response.body().toString(), UpdateStatus.class);
                    if(u.value == 1) {
                        CustomAdapter va = new CustomAdapter();
                        va.returnVal = 1;
                        if (onDeletedListener != null) {
                            onDeletedListener.onDeleted(i);

                            //====================SuccessFull On Attend=====================================

                            Retrofit retrofit3 = null;
                            try {
                                retrofit3 = new Retrofit.Builder()
                                        .baseUrl(Helpers.connection())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            CustomAdapter.AccidentsSmsService service3 = retrofit3.create(CustomAdapter.AccidentsSmsService.class);
                            SmsMessage smsMessage = new SmsMessage();
                            smsMessage.setFrom(Helpers.validTwilioNumber());
                            smsMessage.setBody("Accident has been attended");
                            smsMessage.setTo(to);

                            Call <Object> call3= service3.accidentsSms(smsMessage);
                            Response response3 = call3.execute();
                            System.out.println(response3.body());
                            //=========================================================
                        }
                    }
                    if(u.value == 0) {
                        CustomAdapter va = new CustomAdapter();
                        va.returnVal = 0;

                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                //===========================
             //   System.out.println("Attendance added for " + accidentsLists2.get(i).getId());
            }
        });
        return view;
    }

    public interface OnDeletedListener {
        void onDeleted(int position);
    }

    private OnDeletedListener onDeletedListener;

    public void setOnDeletedListener(OnDeletedListener listener) {
        this.onDeletedListener = listener;
    }

    public class UpdateStatus{
        public int value;

        public int getStatus() {
            return value;
        }

        public void setStatus(int status) {
            this.value = status;
        }

        public UpdateStatus(int status) {
            this.value = status;
        }
    }

    public int getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(int returnVal) {
        this.returnVal = returnVal;
    }
}