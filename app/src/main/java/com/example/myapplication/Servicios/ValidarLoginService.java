package com.example.myapplication.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.myapplication.Dominio.Usuario;
import com.example.myapplication.Login;
import com.example.myapplication.Routes.Routes;

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
import java.net.URLEncoder;
import java.util.Iterator;

public class ValidarLoginService extends AsyncTask<Void,Void,String> {

    String username, password;
    Context context;
    ProgressDialog progressDialog;
    Boolean error;

    public ValidarLoginService(Context context, String username, String password) {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(this.context,"Ingreso","Validando informacion...");
    }

    @Override
    protected String doInBackground(Void... voids) {

        String uri = Routes.validar_login;
        URL url = null;
        try {
            url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            JSONObject parametros = new JSONObject();
            parametros.put("username", this.username);
            parametros.put("password", this.password);

            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
            bw.write(getPostDataString(parametros));
            bw.flush();
            bw.close();
            os.close();

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

                JSONObject json = null;
                json = new JSONObject(jo);
                if (json.getBoolean("error") == false){
                    JSONObject response = json.getJSONObject("usuario");
                    Usuario usuario = new Usuario();
                    usuario.setId(response.getInt("id_persona"));
                    usuario.setNombre(response.getString("cedula"));
                    usuario.setApellido(response.getString("nombre"));
                    usuario.setEmail(response.getString("email"));
                    usuario.setUsername(response.getString("username"));
                    usuario.setTipoUsuario(response.getString("tipo_usuario"));

                    usuario.Save(this.context);
                    this.error = false;
                    return "ok";

                }else{
                    this.error = true;
                    return json.getString("mensaje");
                }
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
            Toast.makeText(context, respuesta, Toast.LENGTH_SHORT).show();
        }else{
            //aca puedes hacer un intent
            Usuario usuario = new Usuario().Find(context);
                if (usuario.getTipoUsuario().equals("usuario")){
                    Login.IniciarUsuario(context);
                }else {
                    Login.IniciarAdmin(context);
                }
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}
