package com.example.finalyearproject;




import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MasterListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addCard("John Doe");
       /* EdgeToEdge.enable(this);
        setContentView(R.layout.activity_master_list_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }); */
    }

    private void addCard(String name){
        View view = getLayoutInflater().inflate(R.layout.card, null);
        TextView nameView = view.findViewById(R.id.name);
        Button attend = view.findViewById(R.id.attend);

        nameView.setText(name);

        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add attendance
                System.out.println("Attendance added for " + name);
            }
        });


    }
}