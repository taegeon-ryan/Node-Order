package jp.dylee.nodeorder;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

    TextView shi, bun;
    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    ArrayAdapter<CharSequence>  adspin;
    TextView namae, thistime, location;
    private DatabaseReference mPostReference;
    String name;
    Button go, clock;
    TimePickerDialog dialog;
    String hour, min;
    CheckBox hazong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        go = (Button)findViewById(R.id.go);
        name = user.getDisplayName();
        namae = (TextView)findViewById(R.id.namae);
        namae.setText(name);
        location = (TextView)findViewById(R.id.location);
        mPostReference = FirebaseDatabase.getInstance().getReference() .child("student").child(name).child("site");
        thistime = (TextView) findViewById(R.id.thistime);
        getTime();
        mDBReference = FirebaseDatabase.getInstance().getReference();
        childUpdates = new HashMap<>();
        shi = (TextView) findViewById(R.id.shi);
        bun = (TextView) findViewById(R.id.bun);
        clock = (Button)findViewById(R.id.clock);
        hazong = (CheckBox)findViewById(R.id.hazong);
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = Integer.toString(hourOfDay);
                min = Integer.toString(minute);
                shi.setText(hour);
                bun.setText(min);
            }
        };

        SimpleDateFormat dialogFormat_H = new SimpleDateFormat ( "H");
        SimpleDateFormat dialogFormat_M = new SimpleDateFormat ( "m");
        Date time = new Date();
        int nowtime_H = Integer.parseInt(dialogFormat_H.format(time));
        int nowtime_M = Integer.parseInt(dialogFormat_M.format(time));
        dialog = new TimePickerDialog(this, listener, nowtime_H, nowtime_M, true);

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.spin_heya);
        spinner.setPrompt("어디로 갈까요?");

        adspin = ArrayAdapter.createFromResource(this, R.array.heyas,    android.R.layout.simple_spinner_item);

        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spinner.getSelectedItem().toString();
                childUpdates.put("/student/" + name +"/site", text);
                mDBReference.updateChildren(childUpdates);
                if(hazong.isChecked()){
                    childUpdates.put("/student/" + name + "/s_return", "하루종일");
                    mDBReference.updateChildren(childUpdates);
                }else{
                    String text0 = hour + " : " + min;
                    childUpdates.put("/student/" + name + "/s_return", text0);
                    mDBReference.updateChildren(childUpdates);
                }
                Toast.makeText(getApplicationContext(),"설정되었습니다.",Toast.LENGTH_LONG).show();
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

    public void getTime(){
        final Handler handler = new Handler() {
            SimpleDateFormat simpleDate = new SimpleDateFormat("h시 m분");
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