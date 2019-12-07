package jp.dylee.nodeorder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class ChooseActivity extends Activity  {
    HashMap<String, Object> childUpdates = null;
    String Text, genzai_namae;
    Button student, teacher;
    AlertDialog.Builder alert;
    DatabaseReference returnRef = null;
    DatabaseReference allRef = null;
    String[] names= {"곽은상", "김강훈", "김규리", "김나연", "김재빈", "김지웅", "남승우", "박건도", "박종효", "신엽", "유정화", "유태건", "유희정", "이성범", "이준표", "임다희", "임태건","정지원", "정훈","최다원"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        alert = new AlertDialog.Builder(this);
        childUpdates = new HashMap<>();
        student = (Button)findViewById(R.id.student);
        teacher = (Button)findViewById(R.id.teacher);

        allRef = FirebaseDatabase.getInstance().getReference();

        overtime_delete();

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(intent);
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });




    }

    public void overtime_delete(){
        Runnable task = new Runnable() {
            @Override
            public void run() {

                while(true){
                    try{
                        for(int i = 0; i < names.length; i++)
                        {
                            ValueEventListener listener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Text = dataSnapshot.getValue().toString();
                                    Log.w("이거다!", Text);
                                    if(!Text.equals("하루종일") && !Text.equals("")){
                                        SimpleDateFormat dialogFormat_H = new SimpleDateFormat ( "H");
                                        SimpleDateFormat dialogFormat_M = new SimpleDateFormat ( "m");
                                        Date time = new Date();
                                        int nowtime_H = Integer.parseInt(dialogFormat_H.format(time));
                                        int nowtime_M = Integer.parseInt(dialogFormat_M.format(time));
                                        String cnth = Text.split(":")[0].trim();
                                        String cntm = Text.split(":")[1].trim();
                                        int hhh = Integer.parseInt(cnth);
                                        int mmm = Integer.parseInt(cntm);
                                        if(nowtime_H>hhh){
                                            childUpdates.put("/student/" + genzai_namae + "/s_return", "");
                                            allRef.updateChildren(childUpdates);
                                            childUpdates.put("/student/" + genzai_namae + "/site", "교실");
                                            allRef.updateChildren(childUpdates);
                                        }else if(nowtime_H == hhh){
                                            if(nowtime_M>mmm){
                                                childUpdates.put("/student/" + genzai_namae + "/s_return", "");
                                                returnRef.updateChildren(childUpdates);
                                                childUpdates.put("/student/" + genzai_namae + "/site", "교실");
                                                returnRef.updateChildren(childUpdates);
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {}
                            };
                            genzai_namae = names[i];
                            returnRef = FirebaseDatabase.getInstance().getReference().child("student").child(genzai_namae).child("s_return");
                            returnRef.addValueEventListener(listener);
                            Log.w("뭐 시발", "돌았어");

                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();

            /*private void deletework(String Text) {
                if(!Text.equals("하루종일") && !Text.equals("")){
                    SimpleDateFormat dialogFormat_H = new SimpleDateFormat ( "H");
                    SimpleDateFormat dialogFormat_M = new SimpleDateFormat ( "m");
                    Date time = new Date();
                    int nowtime_H = Integer.parseInt(dialogFormat_H.format(time));
                    int nowtime_M = Integer.parseInt(dialogFormat_M.format(time));
                    String cnth = Text.split(":")[0].trim();
                    String cntm = Text.split(":")[1].trim();
                    int hhh = Integer.parseInt(cnth);
                    int mmm = Integer.parseInt(cntm);
                    if(nowtime_H>hhh){
                        childUpdates.put("/student/" + genzai_namae + "/s_return", "");
                        allRef.updateChildren(childUpdates);
                        childUpdates.put("/student/" + genzai_namae + "/site", "교실");
                        allRef.updateChildren(childUpdates);
                    }else if(nowtime_H == hhh){
                        if(nowtime_M>mmm){
                            childUpdates.put("/student/" + genzai_namae + "/s_return", "");
                            returnRef.updateChildren(childUpdates);
                            childUpdates.put("/student/" + genzai_namae + "/site", "교실");
                            returnRef.updateChildren(childUpdates);
                        }
                    }
                }
            }*/
        }


    void show()
    {

        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("시크릿 코드 입력");
        builder.setMessage("안내받은 시크릿 코드를 입력해주세요");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String a = edittext.getText().toString().trim();
                        if(a.equals("dylee")){
                            Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"인증 완료되었습니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
}


