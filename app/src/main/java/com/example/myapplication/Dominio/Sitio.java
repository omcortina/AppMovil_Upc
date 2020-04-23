package com.example.myapplication.Dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.AdminSQLiteOpenHelper;
import com.example.myapplication.Config.Config;

import java.util.ArrayList;
import java.util.List;

public class Sitio {
    private int Id;
    private String Codigo;
    private String Nombre;
    private String Direccion;
    private String Descripcion;
    private String Latitud;
    private String Longitud;
    private String Tipo;

    public Sitio() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public void Save(Context context){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, Config.database_name, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("id_sitio", this.Id);
        registro.put("codigo", this.Codigo);
        registro.put("nombre", this.Nombre);
        registro.put("direccion", this.Direccion);
        registro.put("descripcion", this.Descripcion);
        registro.put("latitud", this.Latitud);
        registro.put("longitud", this.Longitud);
        registro.put("tipo", this.Tipo);

        db.insert("Sitio", null, registro);
        db.close();
    }

    public Sitio Find(Context context, int id){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, Config.database_name, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String sql = "select * from Sitio where id_sitio="+id;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            this.Id = Integer.parseInt(cursor.getString(0));
            this.Codigo = cursor.getString(1);
            this.Nombre = cursor.getString(2);
            this.Direccion = cursor.getString(3);
            this.Descripcion = cursor.getString(4);
            this.Latitud = cursor.getString(5);
            this.Longitud = cursor.getString(6);
            this.Tipo = cursor.getString(7);
            return this;
        }
        db.close();
        return null;
    }

    public static List<Sitio> FindAll(Context context){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, Config.database_name, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        List<Sitio> lista = new ArrayList<>();

        String sql = "select * from Sitio";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do{
                Sitio sitio = new Sitio();
                sitio.Id = Integer.parseInt(cursor.getString(0));
                sitio.Codigo = cursor.getString(1);
                sitio.Nombre = cursor.getString(2);
                sitio.Direccion = cursor.getString(3);
                sitio.Descripcion = cursor.getString(4);
                sitio.Latitud = cursor.getString(5);
                sitio.Longitud = cursor.getString(6);
                sitio.Tipo = cursor.getString(7);
                lista.add(sitio);
            }while(cursor.moveToNext());
            return lista;
        }
        db.close();
        return null;
    }
}
