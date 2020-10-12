package com.example.appgate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditarActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editUser, editPass, editNombre, editApellido;
    Button btnActualizar, btnCancelar;
    TextInputLayout passError;
    int id = 0;
    Usuario u;
    daoUsuario dao;
    Intent x;
    String EXPRESIONREGULAR = "^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        editUser = (EditText) findViewById(R.id.editUser2);
        editPass = (EditText) findViewById(R.id.editPass2);
        passError = (TextInputLayout)findViewById(R.id.editPass1);
        editNombre = (EditText) findViewById(R.id.editName2);
        editApellido = (EditText) findViewById(R.id.editLastname2);
        btnActualizar = (Button) findViewById(R.id.btn_actualizar);
        btnCancelar = (Button) findViewById(R.id.btn_cancelar);


        btnActualizar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        id = b.getInt("Id");
        dao = new daoUsuario(this);
        u = dao.getUsurioById(id);
        editUser.setText(u.getUsuario());
        editPass.setText(u.getPassword());
        editNombre.setText(u.getNombre());
        editApellido.setText(u.getApellidos());


        editPass.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                Pattern ps = Pattern.compile(EXPRESIONREGULAR);
                Matcher ms = ps.matcher(editPass.getText().toString());
                boolean bs = ms.matches();

                if (bs) {
                    //es valido
                    passError.setError(null);
                    passError.setErrorEnabled(false);
                    passError.setErrorTextAppearance(R.style.InputError_Red);
                }else{
                    //no es valido
                    passError.setErrorEnabled(true);
                    passError.setError(getString(R.string.error_password));
                    passError.setErrorTextAppearance(R.style.InputError_Red);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(EditarActivity.this, InicioActivity.class);
        a.putExtra("Id",id);
        startActivity(a);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_actualizar:
                u.setUsuario(editUser.getText().toString());
                u.setPassword(editPass.getText().toString());
                u.setNombre(editNombre.getText().toString());
                u.setApellidos(editApellido.getText().toString());
                if (!u.isNull()) {
                    Toast.makeText(this, getString(R.string.error_campos_vacios), Toast.LENGTH_LONG).show();
                } else if (dao.updateUsuario(u)) {
                    Toast.makeText(this, getString(R.string.ok_actualizacion_exitosa), Toast.LENGTH_LONG).show();
                    Intent a = new Intent(EditarActivity.this, InicioActivity.class);
                    a.putExtra("Id",id);
                    startActivity(a);
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.error_usuario_no_actualizar), Toast.LENGTH_LONG).show();
                }
                ocultarteclado();
                break;
            case R.id.btn_cancelar:
                Intent a = new Intent(EditarActivity.this, InicioActivity.class);
                a.putExtra("Id",id);
                startActivity(a);
                finish();
                break;

        }
    }

    public void ocultarteclado() {
        /* hide keyboard */
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public void mostrarteclado() {
        /* show keyboard */
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}