package jp.dylee.nodeorder;

import android.app.Activity;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TableActivity extends Activity {
    TextView timee,one,two,three,four;
    String[] names= {"곽은상", "김강훈", "김규리", "김나연", "김재빈", "김지웅", "남승우", "박건도", "박종효", "신엽", "유정화", "유태건", "유희정", "이성범", "이준표", "임다희", "임태건","정지원", "정훈","최다원"};
    DatabaseReference mPostReference;
    Button ref;
    ValueEventListener Listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String oneval, twoval, thrval, fouval;
            String no = dataSnapshot.child("number").getValue().toString();
            String name = dataSnapshot.child("name").getValue().toString();
            String site = dataSnapshot.child("site").getValue().toString();
            String s_return = dataSnapshot.child("s_return").getValue().toString();
            oneval = one.getText().toString();
            one.setText(no + "\n" + oneval);
            twoval = two.getText().toString();
            two.setText(name + "\n" + twoval);
            thrval = three.getText().toString();
            three.setText(site + "\n" + thrval);
            fouval = four.getText().toString();
            four.setText(s_return + "\n" + fouval);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_table);
        timee = (TextView)findViewById(R.id.time);
        one = (TextView)findViewById(R.id.one);
        two = (TextView)findViewById(R.id.two);
        three = (TextView)findViewById(R.id.three);
        four = (TextView)findViewById(R.id.four);
        jikan_mitai();
        ref = (Button)findViewById(R.id.refresh);

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<names.length; i++) {
                    one.setText("");
                    two.setText("");
                    three.setText("");
                    four.setText("");
                    mPostReference = FirebaseDatabase.getInstance().getReference().child("student").child(names[i]);
                    mPostReference.addValueEventListener(Listener);
                }
            }
        });




        for(int i=0; i<names.length; i++){
            mPostReference = FirebaseDatabase.getInstance().getReference() .child("student").child(names[i]);
            mPostReference.addValueEventListener(Listener);
        }



    }

    public void jikan_mitai(){
        final Handler handler = new Handler() {
            SimpleDateFormat hour = new SimpleDateFormat("HH");
            SimpleDateFormat min = new SimpleDateFormat("mm");
            @Override
            public void handleMessage(Message msg){
                String h = hour.format(new Date());
                String m = min.format(new Date());
                int hh = Integer.parseInt(h);
                int mm = Integer.parseInt(m);
                Log.d("tt",  Integer.toString(hh));
                String timetable = timemethod(hh,mm);
                timee.setText(timetable);
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
                    timetable="하교시간";
                break;
            default: timetable="하교시간";
        }

        return timetable;
    }

}
