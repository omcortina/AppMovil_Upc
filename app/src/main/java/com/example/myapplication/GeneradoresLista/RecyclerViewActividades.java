package com.example.myapplication.GeneradoresLista;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dominio.Actividad;
import com.example.myapplication.Dominio.Evento;
import com.example.myapplication.R;
import com.example.myapplication.Routes.Routes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewActividades extends RecyclerView.Adapter<RecyclerViewActividades.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_nombre_actividad;
        private LinearLayout btn_mostrar_actividad, btn_actividad_favorita;
        private ImageView img_actividad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre_actividad = (TextView) itemView.findViewById(R.id.txt_nombre_actividad);
            btn_mostrar_actividad = (LinearLayout) itemView.findViewById(R.id.btn_mostrar_actividad);
            btn_actividad_favorita = (LinearLayout) itemView.findViewById(R.id.btn_actividad_favorita);
            img_actividad = (ImageView) itemView.findViewById(R.id.img_actividad);
        }
    }

    public List<Actividad> listaActividades;
    public Context context;

    public RecyclerViewActividades(List<Actividad> listaActividades, Context context) {
        this.listaActividades = listaActividades;
        this.context = context;
    }

    @Override
    public RecyclerViewActividades.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad,parent,false);
        RecyclerViewActividades.ViewHolder viewHolder = new RecyclerViewActividades.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewActividades.ViewHolder holder, int position) {
        final Actividad actividad = listaActividades.get(position);
        holder.txt_nombre_actividad.setText(actividad.getNombre().toUpperCase());
        Bitmap foto = actividad.descargarImagen();
        Picasso.get()
                .load(Routes.directorio_imagenes+actividad.getRutaFoto())
                //.resize(70,70)
                //.placeholder(R)
                //.transform(new CropCircleTransformation())
                .into(holder.img_actividad);
    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }
}
