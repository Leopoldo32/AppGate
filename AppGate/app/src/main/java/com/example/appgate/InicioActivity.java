package com.example.appgate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
        id = b.getInt("Id");
        dao = new daoUsuario(this);
        u = dao.getUsurioById(id);
        nombre.setText(getString(R.string.title_bienvenida) + " " + u.getNombre() + " " + u.getApellidos());

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_editar:
                Intent a = new Intent(InicioActivity.this, EditarActivity.class);
                a.putExtra("Id",id);
                startActivity(a);
                finish();
                break;
            case R.id.btn_eliminar:
                //Dialogo para eliminar registro
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage(getString(R.string.alert_msg));
                b.setCancelable(false);
                b.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dao.deleteUsuario(id)){
                            Toast.makeText(InicioActivity.this, getString(R.string.ok_eliminar_usuario), Toast.LENGTH_LONG).show();
                            Intent a = new Intent(InicioActivity.this, MainActivity.class);
                            startActivity(a);
                            finish();
                        }else{
                            Toast.makeText(InicioActivity.this, getString(R.string.error_eliminar_usuario), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                b.show();
                break;
            case R.id.btn_mostrar:
                Intent c = new Intent(InicioActivity.this, MostrarActivity.class);
                c.putExtra("Id",id);
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