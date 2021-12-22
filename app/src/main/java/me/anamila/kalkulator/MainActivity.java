package me.anamila.kalkulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText txtAngka1, txtAngka2;
    RadioGroup rbGroup;
    TextView tvHasil;

    public static SharedPreferences pref;
    public static RecyclerView record;
    public static Gson gson;
    public static ArrayList<String>historyCalc;
    public static String strListHistoryCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAngka1 = findViewById(R.id.edtAngka1);
        txtAngka2 = findViewById(R.id.edtAngka2);
        tvHasil = findViewById(R.id.tvHasil);
        rbGroup = findViewById(R.id.rbGroup);
        record = findViewById(R.id.record);

        pref = this.getSharedPreferences(getString(R.string.shared_key), Context.MODE_PRIVATE);
        gson = new GsonBuilder().create();

        strListHistoryCalc = pref.getString(getString(R.string.calcHistory_key),"[]");
        historyCalc = gson.fromJson(strListHistoryCalc, new TypeToken<ArrayList<String>>(){}.getType());
        if (historyCalc == null) historyCalc = new ArrayList<>();

        record.setAdapter(new AdapterCalculate(historyCalc));
        record.setLayoutManager(new LinearLayoutManager(this));
    }

    public void btnHitung(View view) {
        record.setAdapter(new AdapterCalculate(historyCalc));
        record.setLayoutManager(new LinearLayoutManager(this));
        String firstnum, scNum, thirdNum;
        firstnum = txtAngka1.getText().toString();
        scNum = txtAngka2.getText().toString();
        if(firstnum.matches("")||scNum.matches("")){
            Toast.makeText(this,"Inputkan angka terlebih dahulu!",Toast.LENGTH_SHORT).show();
        }
        else{
            double angka1, angka2;
            angka1 = Double.parseDouble(firstnum);
            angka2 = Double.parseDouble(scNum);
            switch (rbGroup.getCheckedRadioButtonId()){
                case R.id.radioTambah:
                    tvHasil.setText(angka1 + angka2 + "");
                    thirdNum = tvHasil.getText().toString();
                    historyCalc.add(firstnum+" + "+scNum+" = "+thirdNum);
                    strListHistoryCalc = gson.toJson(historyCalc);
                    pref.edit().putString(getString(R.string.calcHistory_key),strListHistoryCalc).apply();
                    break;
                case R.id.radioKurang:
                    tvHasil.setText(angka1 - angka2 + "");
                    thirdNum = tvHasil.getText().toString();
                    historyCalc.add(firstnum+" - "+scNum+" = "+thirdNum);
                    strListHistoryCalc = gson.toJson(historyCalc);
                    pref.edit().putString(getString(R.string.calcHistory_key),strListHistoryCalc).apply();
                    break;
                case R.id.radioKali:
                    tvHasil.setText(angka1 * angka2 + "");
                    thirdNum = tvHasil.getText().toString();
                    historyCalc.add(firstnum+" x "+scNum+" = "+thirdNum);
                    strListHistoryCalc = gson.toJson(historyCalc);
                    pref.edit().putString(getString(R.string.calcHistory_key),strListHistoryCalc).apply();
                    break;
                case R.id.radioBagi:
                    tvHasil.setText(angka1 / angka2 + "");
                    thirdNum = tvHasil.getText().toString();
                    historyCalc.add(firstnum+" : "+scNum+" = "+thirdNum);
                    strListHistoryCalc = gson.toJson(historyCalc);
                    pref.edit().putString(getString(R.string.calcHistory_key),strListHistoryCalc).apply();
                    break;

            }
        }

    }

    public void remove(View view) {
        if (historyCalc.size()>0){
            historyCalc.clear();
            record.setAdapter(new AdapterCalculate(historyCalc));
            record.setLayoutManager(new LinearLayoutManager(this));
            strListHistoryCalc = gson.toJson(historyCalc);
            pref.edit().putString(getString(R.string.calcHistory_key),strListHistoryCalc).apply();
        }
    }

}