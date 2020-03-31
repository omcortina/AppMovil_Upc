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

        db.execSQL("create table Evento(" +
                "id_evento integer primary key," +
                "codigo text," +
                "nombre text," +
                "fecha_inicio text," +
                "fecha_fin text," +
                "descripcion text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
