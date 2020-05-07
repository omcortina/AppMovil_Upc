package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.Dominio.Dominio;
import com.example.myapplication.GeneradoresLista.RecyclerViewSitios;
import com.example.myapplication.GeneradoresLista.RecyclerViewTiposDeSitios;

public class ListaSitios extends AppCompatActivity {
    private RecyclerView recyclerView_tipos_sitios;
    public static RecyclerView recyclerView_sitios;
    private RecyclerViewTiposDeSitios adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sitios);

        recyclerView_tipos_sitios = (RecyclerView) findViewById(R.id.recycler_tipos_sitios);
        recyclerView_tipos_sitios.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView_sitios = (RecyclerView) findViewById(R.id.recycler_sitios);
        recyclerView_sitios.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewTiposDeSitios(Dominio.FindAll(this), this, this);
        recyclerView_tipos_sitios.setAdapter(adapter);
    }
}
