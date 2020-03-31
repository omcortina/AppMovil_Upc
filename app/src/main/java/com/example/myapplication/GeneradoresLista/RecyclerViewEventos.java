package com.example.myapplication.GeneradoresLista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dominio.Evento;
import com.example.myapplication.ListaEventos;
import com.example.myapplication.R;

import java.util.List;

public class RecyclerViewEventos extends RecyclerView.Adapter<RecyclerViewEventos.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_nombre_evento, txt_codigo_evento, txt_fecha_inicio_evento, txt_fecha_fin_evento, txt_descripcion_evento;
        private LinearLayout btn_editar_evento, btn_eliminar_evento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre_evento = (TextView) itemView.findViewById(R.id.txt_nombre_evento);
            txt_codigo_evento = (TextView) itemView.findViewById(R.id.txt_codigo_evento);
            txt_fecha_inicio_evento = (TextView) itemView.findViewById(R.id.txt_fecha_inicio_evento);
            txt_fecha_fin_evento = (TextView) itemView.findViewById(R.id.txt_fecha_fin_evento);
            txt_descripcion_evento = (TextView) itemView.findViewById(R.id.txt_descripcion_evento);
            btn_editar_evento = (LinearLayout) itemView.findViewById(R.id.btn_editar_evento);
            btn_eliminar_evento = (LinearLayout) itemView.findViewById(R.id.btn_eliminar_evento);
        }
    }



    public List<Evento> listaEventos;
    public Context context;

    public RecyclerViewEventos(List<Evento> lista , Context context) {

        this.listaEventos = lista;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Evento evento = listaEventos.get(position);
        holder.txt_nombre_evento.setText(evento.getNombre().toUpperCase());
        holder.txt_codigo_evento.setText("Codigo: "+evento.getCodigo());
        holder.txt_fecha_inicio_evento.setText("Fecha Inicio: "+evento.getFechaInicio());
        holder.txt_fecha_fin_evento.setText("Fecha Fin: "+evento.getFechaFin());
        holder.txt_descripcion_evento.setText("Descripcion: "+evento.getDescripcion());

        holder.btn_eliminar_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar(evento.getId());
            }
        });

    }

    public void Eliminar(int id_evento){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage("Seguro que desea eliminar este registro")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }
}
