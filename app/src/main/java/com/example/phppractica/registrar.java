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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registrar extends AppCompatActivity {

    EditText usr,nombre,correo,clave;
    Button registrarr,regresarr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        usr = findViewById(R.id.etuserr);
        nombre = findViewById(R.id.etnombrer);
        correo = findViewById(R.id.etcorreor);
        clave = findViewById(R.id.etclaver);
        registrarr = findViewById(R.id.btnregistrarr);
        regresarr = findViewById(R.id.btnregresarr);
        registrarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarusuario();
            }
        });
        regresarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    private void registrarusuario() {
        String url = "http://192.168.1.7/ServicioswebPHP/agregarusuario.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registro de usuario realizado correctamente!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Registro de usuario incorrecto!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("usr",usr.getText().toString().trim());
                params.put("nombre",nombre.getText().toString().trim());
                params.put("correo",correo.getText().toString().trim());
                params.put("clave",clave.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }
}