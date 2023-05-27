package ru.lairon.laberis;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.lairon.laberis.data.DataProvider;

public class LaberisActivity extends AppCompatActivity {

    protected void toast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected DataProvider getDataProvider(){
        return Application.getDataProvider();
    }

    protected void startActivity(Class<? extends LaberisActivity> aClass){
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }
}
