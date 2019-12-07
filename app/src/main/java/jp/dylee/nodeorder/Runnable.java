package jp.dylee.nodeorder;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Runnable implements java.lang.Runnable {
    String[] names= {"곽은상", "김강훈", "김규리", "김나연", "김재빈", "김지웅", "남승우", "박건도", "박종효", "신엽", "유정화", "유태건", "유희정", "이성범", "이준표", "임다희", "임태건","정지원", "정훈","최다원"};
    String Text, genzai_namae;
    DatabaseReference returnRef = null;
    HashMap<String, Object> childUpdates = new HashMap<>();
    DatabaseReference allRef = FirebaseDatabase.getInstance().getReference();
    @Override
    public void run() {
        try{
            while(true){
                for(int i = 0; i < names.length; i++)
                {
                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Text = dataSnapshot.getValue().toString();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    };
                    genzai_namae = names[i];
                    returnRef = FirebaseDatabase.getInstance().getReference().child("student").child(genzai_namae).child("s_return");
                    returnRef.addValueEventListener(listener);
                    deletework(Text);
                }
                Thread.sleep(1000);
            }
        }catch(Exception e){ }

    }

    private void deletework(String text) {
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
}
