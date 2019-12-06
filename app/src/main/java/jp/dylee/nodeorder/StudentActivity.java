package jp.dylee.nodeorder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends Activity {

    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    UserInfo userInfo = null;
    ArrayAdapter<CharSequence>  adspin;
    TextView namae, thistime, location;
    private DatabaseReference mPostReference;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 성범아 사랑해
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name = user.getDisplayName();
        namae = (TextView)findViewById(R.id.namae);
        namae.setText(name);
        location = (TextView)findViewById(R.id.location);
        mPostReference = FirebaseDatabase.getInstance().getReference() .child("student").child(name).child("site");
        thistime = (TextView) findViewById(R.id.thistime);
        jikan_mitai();

        Query select = FirebaseDatabase.getInstance().getReference().child("User_info");

        final Spinner spinner = (Spinner) findViewById(R.id.spin_heya);
        spinner.setPrompt("어디로 갈까요?");

        adspin = ArrayAdapter.createFromResource(this, R.array.heyas,    android.R.layout.simple_spinner_item);

        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String text = spinner.getSelectedItem().toString();
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDBReference = FirebaseDatabase.getInstance().getReference();
                childUpdates = new HashMap<>();
                childUpdates.put("/student/" + name +"/site", text);
                mDBReference.updateChildren(childUpdates);
            }
            public void onNothingSelected(AdapterView<?>  parent) {
            }
        });

        ValueEventListener locationListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                location.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}

        };

        mPostReference.addValueEventListener(locationListener);


    }

    public void jikan_mitai(){
        final Handler handler = new Handler() {
            SimpleDateFormat simpleDate = new SimpleDateFormat("hh시 mm분");
            SimpleDateFormat hour = new SimpleDateFormat("HH");
            SimpleDateFormat min = new SimpleDateFormat("mm");
            @Override
            public void handleMessage(Message msg){
                String time = simpleDate.format(new Date());
                String h = hour.format(new Date());
                String m = min.format(new Date());
                int hh = Integer.parseInt(h);
                int mm = Integer.parseInt(m);
                Log.d("tt",  Integer.toString(hh));
                String timetable = timemethod(hh,mm);
                thistime.setText(time + "(" + timetable + ")");
            }
        };

        Runnable task = new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    handler.sendEmptyMessage(1);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    private String timemethod(int hh, int mm) {
        String timetable="";
        switch(hh){
            case 8:
                if(mm<40)
                    timetable="아침시간";
                else
                    timetable="1교시";
                break;
            case 9:
                if(mm<30)
                    timetable="1교시";
                else if(mm<40)
                    timetable="1교시 쉬는시간";
                else
                    timetable="2교시";
                break;
            case 10:
                if(mm<30)
                    timetable="2교시";
                else if(mm<40)
                    timetable="2교시 쉬는시간";
                else
                    timetable="3교시";
                break;
            case 11:
                if(mm<30)
                    timetable="3교시";
                else if(mm<40)
                    timetable="3교시 쉬는시간";
                else
                    timetable="4교시";
                break;
            case 12:
                if(mm<30)
                    timetable="4교시";
                else
                    timetable="점심시간";
                break;
            case 13:
                if(mm<30)
                    timetable="점심시간";
                else
                    timetable="5교시";
                break;
            case 14:
                if(mm<20)
                    timetable="5교시";
                else if(mm<30)
                    timetable="5교시 쉬는시간";
                else
                    timetable="6교시";
                break;
            case 15:
                if(mm<20)
                    timetable="6교시";
                else if(mm<30)
                    timetable="6교시 쉬는시간";
                else
                    timetable="7교시";
                break;
            case 16:
                if(mm<20)
                    timetable="7교시";
                else if(mm<40)
                    timetable="청소시간";
                else
                    timetable="8교시";
                break;
            case 17:
                if(mm<30)
                    timetable="8교시";
                else if(mm<40)
                    timetable="8교시 쉬는시간";
                else
                    timetable="9교시";
                break;
            case 18:
                if(mm<30)
                    timetable="9교시";
                else
                    timetable="저녁시간";
                break;
            case 19:
                if(mm<30)
                    timetable="저녁시간";
                else
                    timetable="10교시";
                break;
            case 20:
                if(mm<20)
                    timetable="10교시";
                else if(mm<30)
                    timetable="10교시 쉬는시간";
                else
                    timetable="11교시";
                break;
            case 21:
                if(mm<20)
                    timetable="11교시";
                else
                    timetable="하교";
                break;
             default: timetable="하교";
        }

        return timetable;
    }

}