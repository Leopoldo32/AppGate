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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener {

    EditText us, pass, nm, ap;
    TextInputLayout passError;
    Button btn_registrar, btn_cancelar;
    daoUsuario dao;
    String EXPRESIONREGULAR = "^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}$";
    boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        us = (EditText) findViewById(R.id.regUser2);
        pass = (EditText) findViewById(R.id.regPass2);
        passError = (TextInputLayout) findViewById(R.id.regPass1);
        nm = (EditText) findViewById(R.id.regName2);
        ap = (EditText) findViewById(R.id.regLastname2);
        btn_registrar = (Button) findViewById(R.id.btn_registrar);
        btn_cancelar = (Button) findViewById(R.id.btn_cancelar);

        btn_registrar.setEnabled(false);
        dao = new daoUsuario(this);

        btn_registrar.setOnClickListener(this);
        btn_cancelar.setOnClickListener(this);

        pass.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                Pattern ps = Pattern.compile(EXPRESIONREGULAR);
                Matcher ms = ps.matcher(pass.getText().toString());
                boolean bs = ms.matches();

                if (bs) {
                    //es valido
                    passError.setError(null);
                    passError.setErrorEnabled(false);
                    passError.setErrorTextAppearance(R.style.InputError_Red);
                    status = true;
                } else {
                    //no es valido
                    passError.setErrorEnabled(true);
                    passError.setError(getString(R.string.error_password));
                    passError.setErrorTextAppearance(R.style.InputError_Red);
                    status = false;
                }
                validarestado();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        us.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (us.getText().toString().equals("")) {
                    status = false;
                } else {
                    status = true;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        nm.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (nm.getText().toString().equals("")) {
                    status = false;
                } else {
                    status = true;
                }
                validarestado();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        ap.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (ap.getText().toString().equals("")) {
                    status = false;
                } else {
                    status = true;
                }
                validarestado();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    public void validarestado() {
        if (!us.getText().toString().equals("") && !pass.getText().toString().equals("") && !nm.getText().toString().equals("") && !ap.getText().toString().equals("") && status == true) {
            btn_registrar.setEnabled(true);
        } else {
            btn_registrar.setEnabled(false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancelar:
                finish();
                break;
            case R.id.btn_registrar:

                Usuario u = new Usuario();
                u.setUsuario(us.getText().toString());
                u.setPassword(pass.getText().toString());
                u.setNombre(nm.getText().toString());
                u.setApellidos(ap.getText().toString());
                if (!u.isNull()) {
                    Toast.makeText(this, getString(R.string.error_campos_vacios), Toast.LENGTH_LONG).show();
                } else if (dao.insertUsuario(u)) {
                    Toast.makeText(this, getString(R.string.ok_registro_exitoso), Toast.LENGTH_LONG).show();
                    us.setText("");
                    pass.setText("");
                    nm.setText("");
                    ap.setText("");
                    us.setFocusable(false);
                    pass.setFocusable(false);
                    nm.setFocusable(false);
                    ap.setFocusable(false);
                } else {
                    Toast.makeText(this, getString(R.string.error_usuario_ya_registrado), Toast.LENGTH_LONG).show();
                }
                ocultarteclado();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        finish();
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