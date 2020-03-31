package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Config.Config;

public class MainActivity extends AppCompatActivity {
    private Button btn_count;
    private TextView txt_num;
    private Button btn_zero;
    public static int contador;
    public int color_btn_zero;
    public int color_btn_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_count = (Button) findViewById(R.id.btn_count);
        txt_num = (TextView) findViewById(R.id.txt_num);
        btn_zero = (Button) findViewById(R.id.btn_zero);

        if (color_btn_zero == 0) color_btn_zero = Color.parseColor("#7A7979");
        if (color_btn_count == 0) color_btn_count = Color.parseColor("#FF963B");

        btn_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contar();
            }
        });

        btn_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reiniciar();
            }
        });
    }

    public void Contar(){
        contador ++;
        if (txt_num != null){
            txt_num.setText(String.valueOf(contador));
            if (contador > 1){
                btn_count.setBackgroundColor(Color.GREEN);
                color_btn_count = Color.GREEN;
                btn_zero.setBackgroundColor(Color.parseColor("#9C70E5"));
                color_btn_zero = Color.parseColor("#9C70E5");
            }
        }
    }

    public void Reiniciar(){
        contador = 0;
        txt_num.setText("0");
        btn_zero.setBackgroundColor(Color.parseColor("#7A7979"));
        btn_count.setBackgroundColor(Color.parseColor("#FF963B"));
        color_btn_count = Color.parseColor("#FF963B");
        color_btn_zero = Color.parseColor("#7A7979");
    }

    public void toast(View view) {
        Toast.makeText(this, "Hola mundo", Toast.LENGTH_LONG).show();
        CerrarSesion();
        //Intent intent = new Intent (view.getContext(), Main2Activity.class);
        //startActivityForResult(intent, 0);
    }
    public void CerrarSesion(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, Config.database_name, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        db.execSQL("DELETE FROM Usuario");
        db.close();

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", contador);
        outState.putInt("color_btn_zero", color_btn_zero);
        outState.putInt("color_btn_count", color_btn_count);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        contador = savedInstanceState.getInt("count");
        txt_num.setText(String.valueOf(contador));
        color_btn_zero = savedInstanceState.getInt("color_btn_zero");
        color_btn_count = savedInstanceState.getInt("color_btn_count");
        btn_zero.setBackgroundColor(color_btn_zero);
        btn_count.setBackgroundColor(color_btn_count);
    }

    int contadoratras = 0;
    @Override
    public void onBackPressed() {
        if (contadoratras==0){
            Toast.makeText(this, "Presione nuevamente para salir", Toast.LENGTH_LONG).show();
            contadoratras++;
        }else{
            super.onBackPressed();
            moveTaskToBack(true);
            this.finish();
            System.exit(0);
        }
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                contadoratras = 0;
            }
        };
    }
}
