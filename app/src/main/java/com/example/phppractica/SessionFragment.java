package com.example.phppractica;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SessionFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    RequestQueue rq;
    JsonRequest jrq;
    EditText correo, clave;
    Button sesion;
    TextView registrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_session, container, false);
        View vista = inflater.inflate(R.layout.fragment_session,container,false);
        correo = vista.findViewById(R.id.etcorreo);
        clave = vista.findViewById(R.id.etclave);
        sesion = vista.findViewById(R.id.btniniciarsesion);
        registrar = vista.findViewById(R.id.tvregistrar);
        rq = Volley.newRequestQueue(getContext());//requerimiento Volley

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),registrar.class));
            }
        });

        sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarsesion(); //192.168.1.8
            }
        });
        return vista;



    }

    private void iniciarsesion() {
        String url = "http://192.168.1.7/ServicioswebPHP/buscarusuario.php?correo="+correo.getText().toString()+"&clave="+clave.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se ha encontrado el usuario: "+correo.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"Se ha encontrado el usuario"+correo.getText().toString(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(),"Se ha encontrado el usuario"+correo.getText().toString(),Toast.LENGTH_SHORT).show();
        usuario objusuario = new usuario();
        //datos: arreglo que envía los datos en formato JSON, en el archivo php
        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posición 0 del arreglo....
            objusuario.setUsr(jsonObject.optString("usr"));
            objusuario.setClave(jsonObject.optString("clave")); //opcional
            objusuario.setNombre(jsonObject.optString("nombre"));
            objusuario.setCorreo(jsonObject.optString("correo"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Intent intencion = new Intent(getContext(),MainActivity2.class);
        intencion.putExtra(MainActivity2.nombre,objusuario.getNombre());
        startActivity(intencion);
        //startActivity(new Intent(getContext(),productoPHP.class));
    }


}