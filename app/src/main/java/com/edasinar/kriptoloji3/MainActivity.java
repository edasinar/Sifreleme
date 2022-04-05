package com.edasinar.kriptoloji3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.edasinar.kriptoloji3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private SQLiteDatabase userDataB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //getActionBar().setTitle("Giriş Ekranı");
        setTitle("Ana Menü");

        sharedPreferences = this.getSharedPreferences("prof_pref", Context.MODE_PRIVATE);
        if(sharedPreferences.getInt("isOpenDatabase",0) == 0){
            setSharedPreferences();
        }

    }

    private void setSharedPreferences() {
        if(sharedPreferences.getInt("isOpenDatabase",0) == 0){
            userDB();
            sharedPreferences.edit().putInt("isOpenDatabase", 1).apply();
        }
    }

    private void userDB() {
        userDataB = this.openOrCreateDatabase("Users", MODE_PRIVATE,null);
        userDataB.execSQL("CREATE TABLE IF NOT EXISTS UserInfo (id INTEGER PRIMARY KEY, Name VARCHAR, Surname VARCHAR, Email VARCHAR, Password VARCHAR)");
    }

    public void kayitOl(View view){
        Intent intent = new Intent(this, KayitOlActivity.class);
        startActivity(intent);
    }

    public void girisYap(View view){
        Intent intent = new Intent(this, GirisYapActivity.class);
        startActivity(intent);
    }
}