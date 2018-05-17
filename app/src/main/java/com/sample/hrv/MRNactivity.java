package com.sample.hrv;

/**
 * Created by sean on 10/04/2018.
 */

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sample.hrv.sensor.BleHeartRateSensor;

public class MRNactivity extends Activity{

    public static Context contextOfApplication;
    private static SharedPreferences sharedpreferences;
    TextView MRN;
    public static final String mypreference = "mypref";
    public static final String Name = "MRN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);
        setContentView(R.layout.activity_mrn);
        MRN = (TextView) findViewById(R.id.MRN);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    public void Save(View view) {
        String n = MRN.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, n);
        editor.commit();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("PATIENT_MRN_RAW_DATA");
        myRef.push().child(n);
        myRef.child(n).child("IBI").setValue(0);
        myRef.child(n).child("HR").setValue(0);

        finish();
    }
}