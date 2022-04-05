package com.edasinar.kriptoloji3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.edasinar.kriptoloji3.databinding.ActivityGirisYapBinding;
import com.edasinar.kriptoloji3.databinding.ActivityMainBinding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GirisYapActivity extends AppCompatActivity {

    private ActivityGirisYapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGirisYapBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("Giriş Yap");
    }

    public void giris(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        String mail = binding.mailGirisEdit.getText().toString();
        String password = getHashMD5(binding.sifreGirisEdit.getText().toString());


        SQLiteDatabase db = this.openOrCreateDatabase("Users",MODE_PRIVATE,null);

        Cursor cur = db.rawQuery("SELECT * FROM UserInfo",null);
        int passwIx = cur.getColumnIndex("Password");
        int mailIx = cur.getColumnIndex("Email");
        String tempPass = null;
        System.out.println("\n");
        System.out.println("\n");

        while(cur.moveToNext()){
            if(cur.getString(mailIx).equals(mail.replace("\\s",""))){
                tempPass = cur.getString(passwIx);
            }
        }

        if(tempPass != null){
            if(tempPass.equals(password)) {
                Toast toast = Toast.makeText(getApplicationContext(), "Başarıyla giriş yapılmıştır.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Girdiğiniz şifre yanlıştır.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Girdiğiniz mail adresi tanımlı değildir tekrar deneyiniz.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }


    }

    public String getHashMD5(String password){
        byte [] hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(password.getBytes());
        }

        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuilder strBuilder = new StringBuilder();

        for(byte b:hash) {
            strBuilder.append(String.format("%02x", b));
        }

        String strHash = strBuilder.toString();

        return strHash;
    }

}