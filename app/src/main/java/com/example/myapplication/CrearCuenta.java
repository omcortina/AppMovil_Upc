package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Servicios.CrearCuentaService;

public class CrearCuenta extends AppCompatActivity {
    private EditText txt_cedula, txt_nombre, txt_email, txt_username, txt_password, txt_password_confirm;
    private Button btn_crear_cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        txt_cedula = (EditText) findViewById(R.id.txt_cedula);
        txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_password_confirm = (EditText) findViewById(R.id.txt_password_confirm);
        btn_crear_cuenta = (Button) findViewById(R.id.btn_crear_cuenta);

        btn_crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crear();
            }
        });
    }

    public void Crear(){
        String cedula = txt_cedula.getText().toString();
        String nombre = txt_nombre.getText().toString();
        String email = txt_email.getText().toString();
        String username = txt_username.getText().toString();
        String password = txt_password.getText().toString();
        String confirm_paswsword = txt_password_confirm.getText().toString();
        if (password.equals(confirm_paswsword)){
            CrearCuentaService service = new CrearCuentaService(this, cedula, nombre, email, username, password);
            service.execute();
        }else{
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

    }


}
