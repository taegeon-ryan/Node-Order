package jp.dylee.nodeorder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class StudentActivity extends Activity {
    ArrayAdapter<CharSequence>  adspin;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 성범아 사랑해
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);

        Spinner spinner = (Spinner) findViewById(R.id.spin_heya);
        spinner.setPrompt("시/도 를 선택하세요.");

        adspin = ArrayAdapter.createFromResource(this, R.array.heyas,    android.R.layout.simple_spinner_item);

        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            public void onNothingSelected(AdapterView<?>  parent) {
            }
        });
    }
}
