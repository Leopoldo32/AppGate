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

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener {

    EditText us, pass, nm, ap;
    Button btn_registrar, btn_cancelar;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        us = (EditText)findViewById(R.id.regUser2);
        pass = (EditText)findViewById(R.id.regPass2);
        nm = (EditText)findViewById(R.id.regName2);
        ap = (EditText)findViewById(R.id.regLastname2);
        btn_registrar = (Button)findViewById(R.id.btn_cancelar);
        btn_cancelar = (Button)findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(this);
        btn_cancelar.setOnClickListener(this);

        dao = new daoUsuario(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancelar:
                finish();
                break;
            case R.id.btn_registrar:

                Usuario u = new Usuario();
                u.setUsuario(us.getText().toString());
                u.setPassword(pass.getText().toString());
                u.setNombre(nm.getText().toString());
                u.setApellidos(ap.getText().toString());
                if(!u.isNull()){
                    Toast.makeText(this,getString(R.string.error_campos_vacios),Toast.LENGTH_LONG).show();
                }else if(dao.insertUsuario(u)){
                    Toast.makeText(this,getString(R.string.ok_registro_exitoso),Toast.LENGTH_LONG).show();
                    us.setText(""); pass.setText(""); nm.setText(""); ap.setText("");
                    us.setFocusable(false); pass.setFocusable(false); nm.setFocusable(false); ap.setFocusable(false);
                }else{
                    Toast.makeText(this,getString(R.string.error_usuario_ya_registrado),Toast.LENGTH_LONG).show();
                }
                ocultarteclado();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        finish();
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