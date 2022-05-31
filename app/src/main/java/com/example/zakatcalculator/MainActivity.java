package com.example.zakatcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText w;
    EditText p;
    Button calc;
    Button reset;
    Spinner spinner;
    TextView output;
    TextView output2;
    TextView output3;
    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        w = (EditText) findViewById(R.id.etWeight);
        p = (EditText) findViewById(R.id.etPrice);
        calc = (Button) findViewById(R.id.btnCalculate);
        reset = (Button) findViewById(R.id.btnReset);
        spinner = (Spinner) findViewById(R.id.spinner);
        output = (TextView) findViewById(R.id.tvOutput);
        output2 = (TextView) findViewById(R.id.tvOutput2);
        output3 = (TextView) findViewById(R.id.tvOutput3);

        calc.setOnClickListener(this);
        reset.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        float weight = sharedPref.getFloat("weight",0.0F);

        sharedPref2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        float value = sharedPref2.getFloat("value",0.0F);

        output.setText("" + weight);
        output2.setText("" + value);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case R.id.about   :

                //Toast.makeText(this, "About us", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnCalculate:

                try {

                    calculate();

                } catch (java.lang.NumberFormatException nfe){

                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();

                } catch (Exception exp){

                    Toast.makeText(this, "Unknown Exception" + exp.getMessage() , Toast.LENGTH_SHORT).show();

                    Log.d("Exception", exp.getMessage());
                }
                break;

            case R.id.btnReset:

                reset();
                break;
        }
    }

    public void calculate(){

        float weight = Float.parseFloat(w.getText().toString());
        float value = Float.parseFloat(p.getText().toString());
        float totalvalue = weight * value;
        double uruf;
        double payable;
        double totalzakat;
        String type = spinner.getSelectedItem().toString();
        DecimalFormat df = new DecimalFormat("0.00");

        if(type.equals("Keep")){
            uruf = weight - 85;

            if(uruf > 0){
                payable = uruf * value;
                totalzakat = payable * 0.025 ;
            }
            else{
                payable = 0.0;
                totalzakat = payable * 0.025 ;
            }
        }
        else{
            uruf = weight - 200;

            if(uruf > 0){
                payable = uruf * value;
                totalzakat = payable * 0.025 ;
            }
            else{
                payable = 0.0;
                totalzakat = payable * 0.025 ;
            }
        }

        output.setText("RM " + df.format(totalvalue));
        output2.setText("RM " + df.format(payable));
        output3.setText("RM " + df.format(totalzakat));

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("weight", weight);
        editor.apply();

        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", value);
        editor2.apply();
    }

    public void reset(){

        w.setText("");
        p.setText("");
        output.setText("");
        output2.setText("");
        output3.setText("");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String type = String.valueOf(adapterView.getItemAtPosition(i));
        Toast.makeText(this,type, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}