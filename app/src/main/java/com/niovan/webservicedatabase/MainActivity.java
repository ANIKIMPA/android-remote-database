package com.niovan.webservicedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ocultar toolBar superior.
        getSupportActionBar().hide();

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticarUsuario();
            }
        });
    }

    private void autenticarUsuario() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (isValid(email, "^(.+)@(.+)$") && isValid(password, ".{4,12}")) {
            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "El email o la contraseña es incorrecto.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid(String input, String regex) {
        return !input.isEmpty() && input.matches(regex);
    }
}
