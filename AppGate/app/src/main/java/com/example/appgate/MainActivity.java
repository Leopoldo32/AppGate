package com.example.appgate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText user, pass;
    Button btnEntrar, btnRegistrar;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.user2);
        pass = (EditText) findViewById(R.id.pass2);
        btnEntrar = (Button) findViewById(R.id.btn_entrar);
        btnRegistrar = (Button) findViewById(R.id.btn_registrar);

        btnEntrar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

        dao = new daoUsuario(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_entrar:
                ocultarteclado();
                String u = user.getText().toString();
                String p = pass.getText().toString();
                if (u.equals("") && p.equals("")) {
                    Toast.makeText(this, getString(R.string.error_campos_vacios), Toast.LENGTH_LONG).show();
                } else if (dao.login(u, p) == 1) {
                    Usuario ux = dao.getUsuario(u, p);
                    Toast.makeText(this, getString(R.string.ok_datos_correctos), Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(MainActivity.this, InicioActivity.class);
                    i2.putExtra("Id",ux.getId());
                    startActivity(i2);
                    finish();
                }else{
                    Toast.makeText(this, getString(R.string.error_usuario_incorrecto), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_registrar:
                Intent i = new Intent(MainActivity.this, RegistrarActivity.class);
                startActivity(i);
                break;

        }

    }

    public void ocultarteclado(){
        /* hide keyboard */
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public void mostrarteclado(){
        /* show keyboard */
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}