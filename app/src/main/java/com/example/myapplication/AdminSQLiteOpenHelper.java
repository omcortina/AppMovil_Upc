package com.example.myapplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Usuario(" +
                "id_persona integer primary key," +
                "cedula text," +
                "nombre text," +
                "email text," +
                "username text," +
                "tipo_usuario text" +
                ")");

        db.execSQL("create table Sitio(" +
                "id_sitio integer primary key," +
                "codigo text," +
                "nombre text," +
                "direccion text," +
                "descripcion text," +
                "latitud text," +
                "longitud text," +
                "ruta_foto text," +
                "id_dominio_tipo integer" +
                ")");

        db.execSQL("create table Dominio(" +
                "id_dominio integer primary key," +
                "nombre text" +
                ")");

        db.execSQL("create table Evento(" +
                "id_evento integer primary key," +
                "codigo text," +
                "nombre text," +
                "fecha_inicio text," +
                "fecha_fin text," +
                "descripcion text," +
                "ruta_foto text" +
                ")");

        db.execSQL("create table Actividad(" +
                "id_actividad integer primary key," +
                "codigo text," +
                "nombre text," +
                "descripcion text," +
                "ruta_foto text" +
                ")");

        db.execSQL("create table SitioEvento(" +
                "id_sitio_evento integer primary key autoincrement," +
                "id_sitio text," +
                "id_evento text" +
                ")");

        db.execSQL("create table SitioActividad(" +
                "id_sitio_actividad integer primary key autoincrement," +
                "id_sitio text," +
                "id_actividad text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
