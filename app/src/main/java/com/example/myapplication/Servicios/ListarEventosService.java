package com.example.myapplication.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.myapplication.AdminSQLiteOpenHelper;
import com.example.myapplication.Config.Config;
import com.example.myapplication.Dominio.Evento;
import com.example.myapplication.Dominio.Usuario;
import com.example.myapplication.ListaEventos;
import com.example.myapplication.Login;
import com.example.myapplication.Routes.Routes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ListarEventosService extends AsyncTask<Void,Void,String> {
    Context context;
    ProgressDialog progressDialog;
    Boolean error;

    public ListarEventosService(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(this.context,"Ingreso","Validando informacion...");
    }

    @Override
    protected String doInBackground(Void... voids) {

        String uri = Routes.listar_eventos;
        URL url = null;
        try {
            url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            int response_code = urlConnection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String linea = "";
                while ((linea = br.readLine()) != null){
                    sb.append(linea);
                    break;
                }
                br.close();

                String jo = "";
                jo = sb.toString();

                /*JSONArray jo_array = null;
                jo_array = new JSONArray(json);
                 */

                JSONArray array = null;
                array = new JSONArray(jo);
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, Config.database_name, null, 1);
                SQLiteDatabase db = admin.getWritableDatabase();

                db.execSQL("DELETE FROM Evento");
                db.close();

                for (int i=0; i<array.length(); i++){
                    JSONObject json = array.getJSONObject(i);
                    Evento evento = new Evento();
                    evento.setId(json.getInt("id_evento"));
                    evento.setCodigo(json.getString("codigo"));
                    evento.setNombre(json.getString("nombre"));
                    evento.setDescripcion(json.getString("descripcion"));
                    evento.setFechaInicio(json.getString("fecha_inicio"));
                    evento.setFechaFin(json.getString("fecha_fin"));
                    evento.Save(this.context);
                }
                this.error = false;
                return "ok";
            }

        } catch (MalformedURLException e) {
            error = true;
            return "Error de ruta: "+e.getMessage();
        } catch (IOException e) {
            error = true;
            return "Error de conexion a la ruta: "+e.getMessage();
        } catch (JSONException e) {
            error = true;
            return "Error con los parametros del json de envio: "+e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String respuesta) {
        super.onPostExecute(respuesta);
        progressDialog.dismiss();
        if (error){
            Toast.makeText(context, "No se pudieron cargar los eventos", Toast.LENGTH_SHORT).show();
        }else{
            //aca puedes hacer un intent
            Intent intent = new Intent(context, ListaEventos.class);
            context.startActivity(intent);
        }
    }
}
