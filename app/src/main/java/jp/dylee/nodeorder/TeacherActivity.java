package jp.dylee.nodeorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TeacherActivity extends Activity {
    TextView imananji, name;
    String names;
    Button select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        names = user.getDisplayName();
        name = (TextView)findViewById(R.id.name);
        name.setText(names);
        select = (Button)findViewById(R.id.select);
        imananji = (TextView) findViewById(R.id.imananji);
        jikan_mitai();

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TeacherActivity.this, "시연용으로는 2학년 2반의 정보만 제공됩니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherActivity.this, TableActivity.class);
                startActivity(intent);
            }
        });
    }

    public void jikan_mitai(){
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
                imananji.setText(time + "(" + timetable + ")");
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
