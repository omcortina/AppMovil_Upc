package com.example.myapplication.Dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.AdminSQLiteOpenHelper;
import com.example.myapplication.Config.Config;

public class SitioActividad {
    private int Id;
    private int id_sitio;
    private int id_actividad;

    public SitioActividad() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId_sitio() {
        return id_sitio;
    }

    public void setId_sitio(int id_sitio) {
        this.id_sitio = id_sitio;
    }

    public int getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(int id_actividad) {
        this.id_actividad = id_actividad;
    }

    public void Save(Context context){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, Config.database_name, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("id_sitio_actividad", this.Id);
        registro.put("id_sitio", this.id_sitio);
        registro.put("id_actividad", this.id_actividad);

        db.insert("SitioActividad", null, registro);
        db.close();
    }

    public static void EliminarSitioActividad(Context context, int id_actividad){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, Config.database_name, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String sql_sitio = "delete from Sitio where id_sitio in (select id_sitio from SitioActividad where id_actividad = "+id_actividad+")";
        db.execSQL(sql_sitio);
        String sql = "delete from SitioActividad where id_actividad ="+id_actividad;
        db.execSQL(sql);
    }
}
