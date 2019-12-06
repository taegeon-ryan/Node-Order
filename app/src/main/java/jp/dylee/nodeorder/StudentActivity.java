package jp.dylee.nodeorder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends Activity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference("student");
    ArrayAdapter<CharSequence>  adspin;
    Map<String, Object> childUpdates = new HashMap<>();
    TextView namae;
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 성범아 사랑해
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);

        namae = (TextView)findViewById(R.id.namae);

        final Spinner spinner = (Spinner) findViewById(R.id.spin_heya);
        spinner.setPrompt("어디로 갈까요?");

        adspin = ArrayAdapter.createFromResource(this, R.array.heyas,    android.R.layout.simple_spinner_item);

        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key = mDatabase.child("student").push().getKey();
                String saru = (String)spinner.getSelectedItem();
                Student student = new Student(namae.getText().toString(), , saru);
                Map<String, Object> postValues = student.toMap();


                childUpdates.put("/student/" + key, postValues);

                mDatabase.updateChildren(childUpdates);

            }
            public void onNothingSelected(AdapterView<?>  parent) {
            }
        });
    }
}