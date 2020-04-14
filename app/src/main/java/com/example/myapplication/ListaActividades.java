package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.Dominio.Actividad;
import com.example.myapplication.Dominio.Evento;
import com.example.myapplication.GeneradoresLista.RecyclerViewActividades;
import com.example.myapplication.GeneradoresLista.RecyclerViewEventos;

public class ListaActividades extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewActividades adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerActividades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewActividades(Actividad.FindAll(this), this);
        recyclerView.setAdapter(adapter);
    }
}
