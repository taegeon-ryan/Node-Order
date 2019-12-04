package jp.dylee.nodeorder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;


public class ChooseActivity extends Activity  {
    Button student, teacher;
    AlertDialog.Builder alert;
    EditText code;
    String sc = "dylee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        alert = new AlertDialog.Builder(this);

        student = (Button)findViewById(R.id.student);
        teacher = (Button)findViewById(R.id.teacher);

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
                        String a = edittext.getText().toString();
                        Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();

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
