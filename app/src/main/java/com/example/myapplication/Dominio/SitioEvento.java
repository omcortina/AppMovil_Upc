package com.example.myapplication.Dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.AdminSQLiteOpenHelper;
import com.example.myapplication.Config.Config;

import java.util.ArrayList;
import java.util.List;

public class SitioEvento {
    private int Id;
    private int id_sitio;
    private int id_evento;

    public SitioEvento() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public int getId_sitio() {
        return id_sitio;
    }

    public void setId_sitio(int id_sitio) {
        this.id_sitio = id_sitio;
    }

    public void Save(Context context){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, Config.database_name, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("id_sitio", this.id_sitio);
        registro.put("id_evento", this.id_evento);

        db.insert("SitioEvento", null, registro);
        db.close();
    }

    public static void EliminarSitiosDelEvento(Context context, int id_evento){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, Config.database_name, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String sql_sitio = "delete from Sitio where id_sitio in (select id_sitio from SitioEvento where id_evento = "+id_evento+")";
        db.execSQL(sql_sitio);
        String sql = "delete from SitioEvento where id_evento ="+id_evento;
        db.execSQL(sql);
    }

    public static List<Sitio> FindAllSitiosPorEvento(Context context, int id_evento){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, Config.database_name, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        List<Sitio> lista = new ArrayList<>();
        String sql = "select id_sitio from SitioEvento where id_evento="+id_evento;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do{
                int id_sitio = cursor.getInt(0);
                lista.add(new Sitio().Find(context, id_sitio));
            }while(cursor.moveToNext());
            return lista;
        }
        db.close();
        return null;

    }
}
