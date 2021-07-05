package com.example.phppractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class productoPHP extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject> {
    RequestQueue rq;
    JsonRequest jrq;
    EditText referencia, nombreProducto, precio;
    Button guardar, eliminar, editar, leer, regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_php);


        referencia = findViewById(R.id.etref);
        nombreProducto = findViewById(R.id.etproducto);
        precio = findViewById(R.id.etprecio);
        guardar = findViewById(R.id.btnsave);
        eliminar = findViewById(R.id.btndelete);
        editar = findViewById(R.id.btnedit);
        leer = findViewById(R.id.btnsearch);
        regresar = findViewById(R.id.btnregresar);

        guardar.setOnClickListener(this);
        eliminar.setOnClickListener(this);
        editar.setOnClickListener(this);
        leer.setOnClickListener(this);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


    }


    @Override
    public void onClick(View v) {
        String xreferencia = referencia.getText().toString();
        String xproducto= nombreProducto.getText().toString();
        String xprecio= precio.getText().toString();
        switch (v.getId()) {
            case R.id.btnsave:
                addprod(xreferencia,xproducto,xprecio);
                break;

            case R.id.btnsearch:
                searchprod(xreferencia);
                break;
            case R.id.btnedit:
                editproduct(xreferencia,xproducto,xprecio);
                break;
            case R.id.btndelete:
                deleteproduct(xreferencia);
        }
    }

    private void deleteproduct(String xreferencia) {
        String url = "http://192.168.1.7/ServicioswebPHP/eliminarproducto.php?id="+xreferencia;
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


    private void editproduct(String xreferencia, String xproducto, String xprecio) {

    }

    private void searchprod(String xreferencia) {
        String url = "http://192.168.1.7/ServicioswebPHP/buscarproducto.php?id="+xreferencia;
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(),"Se ha encontrado el producto"+referencia.getText().toString(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(),"Se ha encontrado el usuario"+correo.getText().toString(),Toast.LENGTH_SHORT).show();
        producto objprod = new producto();
        //datos: arreglo que envía los datos en formato JSON, en el archivo php
        JSONArray jsonArray = response.optJSONArray("producto");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posición 0 del arreglo....
            objprod.setId(jsonObject.optString("id"));
            objprod.setProducto(jsonObject.optString("nombreproducto")); //opcional
            objprod.setValor(jsonObject.optString("valor"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        referencia.setText(objprod.getProducto());
        nombreProducto.setText(objprod.getProducto());
        precio.setText(objprod.getProducto());





    }

    private void addprod(String xreferencia, String xproducto, String xprecio) {
        String url = "http://192.168.1.8/ServicioswebPHP/agregarproducto.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Producto agregado correctamente!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Producto  no agregado!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id",xreferencia.trim());
                params.put("nombreProducto",xproducto.trim());
                params.put("valor",xprecio.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }


}

