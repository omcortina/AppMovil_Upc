package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Dominio.Usuario;
import com.example.myapplication.Servicios.ValidarLoginService;

public class Login extends AppCompatActivity {

    private EditText txt_username, txt_password;
    private Button btn_iniciar_sesion;
    private Button btn_crear_cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Usuario usuario = new Usuario().Find(this);
        if (usuario != null){
            if (usuario.getTipoUsuario().equals("usuario")){
                IniciarUsuario(this);
            }else{
                IniciarAdmin(this);
            }
        }else{
            setContentView(R.layout.activity_login);
            txt_username = (EditText) findViewById(R.id.txt_username);
            txt_password = (EditText) findViewById(R.id.txt_password);
            btn_iniciar_sesion = (Button) findViewById(R.id.btn_iniciar_sesion);
            btn_crear_cuenta = (Button) findViewById(R.id.btn_crear);

            btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Iniciar_Sesion();
                }
            });
            btn_crear_cuenta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentCrearCuenta();
                }
            });
        }
    }

    public void Iniciar_Sesion(){
        String username = txt_username.getText().toString();
        String password= txt_password.getText().toString();
        ValidarLoginService service = new ValidarLoginService(this, username, password);
        service.execute();
    }

    public static void IniciarUsuario(Context context){
        Intent iniciar = new Intent(context, MainActivity.class);
        context.startActivity(iniciar);
    }
    public static void IniciarAdmin(Context context){
        Intent iniciar = new Intent(context, InicioAdmin.class);
        context.startActivity(iniciar);
    }

    public void IntentCrearCuenta(){
        Intent iniciar = new Intent(this, CrearCuenta.class);
        startActivity(iniciar);
    }
}
