package com.example.phppractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    public static final String nombre="nombre";
    TextView rusuario;
    Button produtcos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rusuario = findViewById((R.id.tvusuario));
        produtcos = findViewById(R.id.btnproductos);
        String mnombre = getIntent().getStringExtra("nombre");
        rusuario.setText(mnombre);
        produtcos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),productoPHP.class));
            }
        });
    }
}