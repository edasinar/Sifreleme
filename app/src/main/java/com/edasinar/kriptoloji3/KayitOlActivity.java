package com.edasinar.kriptoloji3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.edasinar.kriptoloji3.databinding.ActivityKayitOlBinding;
import com.edasinar.kriptoloji3.databinding.ActivityMainBinding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KayitOlActivity extends AppCompatActivity {

    private ActivityKayitOlBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKayitOlBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("Kayıt Ol");
    }

    public void kayit(View view){

        String name = binding.isimEdit.getText().toString().replace("\\s","");
        String surname = binding.soyisimEdit.getText().toString().replace("\\s","");
        String mail = binding.mailEdit.getText().toString().replace("\\s","");
        String password = binding.sifreEdit.getText().toString().replace("\\s","");
        if(password.length() < 8){
            Toast toast = Toast.makeText(getApplicationContext(), "Şifre 8 karakterden fazla olmalıdır.", Toast.LENGTH_LONG);
            binding.sifreEdit.setText(null);
            toast.show();
        }
        else {
            password = getHashMD5(password);
            SQLiteDatabase db = this.openOrCreateDatabase("Users",MODE_PRIVATE,null);
            db.execSQL("INSERT INTO UserInfo (Name, Surname, Email, Password) VALUES ('"+name+"','"+surname+"','"+mail+"','"+password+"')");

            Cursor cur = db.rawQuery("SELECT * FROM UserInfo",null);
            int nameIx = cur.getColumnIndex("Name");
            int surnameIx = cur.getColumnIndex("Surname");
            int mailIx = cur.getColumnIndex("Email");
            int passwIx = cur.getColumnIndex("Password");

            while(cur.moveToNext()){
                System.out.println(cur.getString(nameIx) + " " + cur.getString(surnameIx) + " " + cur.getString(mailIx) + " " + cur.getString(passwIx));
            }

            System.out.println();
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