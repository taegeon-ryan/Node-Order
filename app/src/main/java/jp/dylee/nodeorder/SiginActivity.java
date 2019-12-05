package jp.dylee.nodeorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SiginActivity extends Activity{

    //define view objects
    EditText sig_email;
    EditText sig_pw;
    Button sig_sig;
    String em="";
    String pw="";
    //define firebase object
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        sig_email = (EditText)findViewById(R.id.sig_email);
        sig_pw = (EditText)findViewById(R.id.sig_pw);
        sig_sig = (Button)findViewById(R.id.sig_signinbtn);
        //initializig firebase auth object
        mAuth = FirebaseAuth.getInstance();

        sig_sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                em = sig_email.getText().toString().trim();
                pw = sig_pw.getText().toString().trim();
                if(em.length()!=0 && pw.length()!=0)
                {
                    mAuth.createUserWithEmailAndPassword(em, pw)
                            .addOnCompleteListener(SiginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SiginActivity.this, "등록 성공", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SiginActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SiginActivity.this, "등록 에러. 비번은 최소 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                }else{
                    Toast.makeText(SiginActivity.this, "ID/PW를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }


}