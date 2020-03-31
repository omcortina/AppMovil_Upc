package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.Dominio.Evento;
import com.example.myapplication.GeneradoresLista.RecyclerViewEventos;

public class ListaEventos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewEventos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerEventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewEventos(Evento.FindAll(this), this);
        recyclerView.setAdapter(adapter);
    }
}
