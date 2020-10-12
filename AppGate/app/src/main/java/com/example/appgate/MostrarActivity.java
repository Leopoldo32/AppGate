package com.example.appgate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MostrarActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lista;
    daoUsuario dao;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);
        lista = (ListView) findViewById(R.id.lista);

        Bundle b = getIntent().getExtras();
        id = b.getInt("Id");

        dao = new daoUsuario(this);
        ArrayList<Usuario> l = dao.selectUsuario();

        ArrayList<String> list = new ArrayList<String>();
        for (Usuario u : l) {
            list.add(u.getNombre() + " " + u.getApellidos());
        }
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        lista.setAdapter(a);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(MostrarActivity.this, InicioActivity.class);
        a.putExtra("Id",id);
        startActivity(a);
        finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }
}