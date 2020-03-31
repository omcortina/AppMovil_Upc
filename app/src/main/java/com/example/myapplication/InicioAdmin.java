package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Servicios.ListarEventosService;

public class InicioAdmin extends AppCompatActivity {

    public CardView cv_eventos, cv_sitios, cv_actividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_admin);

        cv_eventos = (CardView) findViewById(R.id.cv_eventos);
        cv_sitios = (CardView) findViewById(R.id.cv_sitios);
        cv_actividades = (CardView) findViewById(R.id.cv_actividades);

        cv_eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListarEventos();
            }
        });
    }

    public void ListarEventos(){
        ListarEventosService service = new ListarEventosService(this);
        service.execute();
    }
}
