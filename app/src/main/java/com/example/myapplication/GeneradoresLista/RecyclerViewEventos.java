package com.example.myapplication.GeneradoresLista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dominio.Evento;
import com.example.myapplication.R;
import com.example.myapplication.Routes.Routes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewEventos extends RecyclerView.Adapter<RecyclerViewEventos.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_nombre_evento, txt_fecha_inicio_evento, txt_fecha_fin_evento, txt_descripcion_evento;
        private LinearLayout btn_editar_evento, btn_eliminar_evento;
        private ImageView img_evento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre_evento = (TextView) itemView.findViewById(R.id.txt_nombre_evento);
            txt_fecha_inicio_evento = (TextView) itemView.findViewById(R.id.txt_fecha_inicio_evento);
            txt_fecha_fin_evento = (TextView) itemView.findViewById(R.id.txt_fecha_fin_evento);
            txt_descripcion_evento = (TextView) itemView.findViewById(R.id.txt_descripcion_evento);
            btn_editar_evento = (LinearLayout) itemView.findViewById(R.id.btn_mostrar_evento);
            btn_eliminar_evento = (LinearLayout) itemView.findViewById(R.id.btn_evento_favorito);
            img_evento = (ImageView) itemView.findViewById(R.id.img_evento);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Evento evento = listaEventos.get(position);
        holder.txt_nombre_evento.setText(evento.getNombre().toUpperCase());
        holder.txt_fecha_inicio_evento.setText("Fecha Inicio: "+evento.getFechaInicio());
        holder.txt_fecha_fin_evento.setText("Fecha Fin: "+evento.getFechaFin());
        holder.txt_descripcion_evento.setText("Descripcion: "+evento.getDescripcion());
        Picasso.get()
                .load(Routes.directorio_imagenes+evento.getRutaFoto())
                //.resize(70,70)
                //.placeholder(R)
                //.transform(new CropCircleTransformation())
                .into(holder.img_evento);



        holder.txt_nombre_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.txt_fecha_inicio_evento.getVisibility() == v.GONE ){
                    holder.txt_fecha_inicio_evento.setVisibility(v.VISIBLE);
                    holder.txt_fecha_fin_evento.setVisibility(v.VISIBLE);
                    holder.txt_descripcion_evento.setVisibility(v.VISIBLE);
                }else{
                    holder.txt_fecha_inicio_evento.setVisibility(v.GONE);
                    holder.txt_fecha_fin_evento.setVisibility(v.GONE);
                    holder.txt_descripcion_evento.setVisibility(v.GONE);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listaEventos.size();
    }
}
