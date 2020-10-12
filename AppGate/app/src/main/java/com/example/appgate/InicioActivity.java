package com.example.appgate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEditar, btnEliminar, btnMostrar, btnSalir;
    TextView nombre;
    int id = 0;
    Usuario u;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        nombre = (TextView) findViewById(R.id.nombreUsuario);
        btnEditar = (Button) findViewById(R.id.btn_editar);
        btnEliminar = (Button) findViewById(R.id.btn_eliminar);
        btnMostrar = (Button) findViewById(R.id.btn_mostrar);
        btnSalir = (Button) findViewById(R.id.btn_salir);

        btnEditar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnMostrar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        id= b.getInt("Id");
        dao= new daoUsuario(this);
        u= dao.getUsurioById(id);
        nombre.setText(u.getNombre() + u.getApellidos());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_editar:
                Intent a = new Intent(InicioActivity.this, EditarActivity.class);
                startActivity(a);
                finish();
                break;
            case R.id.btn_eliminar:

                break;
            case R.id.btn_mostrar:
                Intent c = new Intent(InicioActivity.this, MostrarActivity.class);
                startActivity(c);
                finish();
                break;
            case R.id.btn_salir:
                Intent d = new Intent(InicioActivity.this, MainActivity.class);
                startActivity(d);
                finish();
                break;

        }
    }
}