package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String NAME = "NAME";
    public static final String HEIGHT = "HEIGHT";
    public static final String WEIGHT = "WEIGHT";
    public static final String FLAG ="FLAG";
    public static final String GENDER = "MALE";
    private EditText name_edttxt, heigt_edttxt,weight_edttxt;
    private Spinner spinner;
    private CheckBox save_checkbox;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextView txtview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save_checkbox = findViewById(R.id.save_checkbox);
        spinner = findViewById(R.id.gender_spinner);
        name_edttxt= findViewById(R.id.name_edittxt);
        heigt_edttxt = findViewById(R.id.height_editxt);
        weight_edttxt = findViewById(R.id.weight_edittxt);
        txtview = findViewById(R.id.textView);
        populateSpinner();
        setupSharedPreferences();
        checkPreferences();


    }

    private void checkPreferences(){
        boolean flag = preferences.getBoolean(FLAG, false);
        if (flag){
            String name = preferences.getString(NAME,"");
            int height = preferences.getInt(HEIGHT,0);
            int weight = preferences.getInt(WEIGHT,0);
            String gender = preferences.getString(GENDER,"MALE");
            name_edttxt.setText(name);
            heigt_edttxt.setText(String.valueOf(height));
            weight_edttxt.setText(String.valueOf(weight));
            int gender_pos = 0;
            if (gender.equalsIgnoreCase("male"))
                gender_pos =1;
            spinner.setSelection(gender_pos);
            save_checkbox.setChecked(true);
            float h = Integer.parseInt(heigt_edttxt.getText().toString());
            h/=100;
            float w = Integer.parseInt(weight_edttxt.getText().toString());
            float BMI = w/(h*h);
            txtview.setText(String.valueOf(BMI));
        }
    }

    private void setupSharedPreferences(){
        preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }

    public void StartTimer(View view) {
        String name = name_edttxt.getText().toString();
        int height = Integer.parseInt(heigt_edttxt.getText().toString());
        int width = Integer.parseInt(weight_edttxt.getText().toString());
        String gender = spinner.getSelectedItem().toString();
        if (save_checkbox.isChecked()){
            editor.putString(NAME,name);
            editor.putInt(HEIGHT,height);
            editor.putInt(WEIGHT,width);
            editor.putString(GENDER,gender);
            editor.putBoolean(FLAG,true);
            editor.commit();
        }
        startActivity(new Intent(this, TimerActivity.class));
    }

    private void populateSpinner(){
        ArrayList genders = getGenders();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,genders);
        spinner.setAdapter(adapter);
    }

    public ArrayList<String> getGenders(){
        ArrayList<String> genders = new ArrayList<>();
        genders.add("Female");
        genders.add("Male");
        return genders;
    }
    //this method will be implemented when clicking the Height or weight EditTexts
    public void CalculateBMI(View view) {
        float height = Integer.parseInt(heigt_edttxt.getText().toString());
        height/=100;
        float weight = Integer.parseInt(weight_edttxt.getText().toString());
        float BMI = weight/(height*height);
        if (!heigt_edttxt.getText().equals(null) && !weight_edttxt.getText().equals(null)){
            txtview.setText(String.valueOf(BMI));
        }
    }
}