package com.example.onurhuseyincantay.myshoppingcart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddList extends AppCompatActivity {

    private String[] types={"Kg","Litre","Adet"};
    private Spinner typeSpinner;
    private ArrayAdapter<String> typesDataForSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        typesDataForSpinner =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        typesDataForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typesDataForSpinner);
    }
}
