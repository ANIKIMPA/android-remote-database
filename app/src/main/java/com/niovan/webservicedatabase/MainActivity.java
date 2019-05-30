package com.niovan.webservicedatabase;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre, txtFecha_nacimiento;
    RadioButton rbMasculino, rbFemenina;
    Button btnRegistrar;
    ProgressBar barraProgreso;

    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.registro_nombre);
        txtFecha_nacimiento = findViewById(R.id.registro_fecha_nacimiento);
        rbMasculino = findViewById(R.id.registro_masculino);
        rbFemenina = findViewById(R.id.registro_femenina);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        configurarBarraProgreso();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
    }

    private void configurarBarraProgreso() {
        barraProgreso = findViewById(R.id.barra_progreso);

        final Timer t = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                barraProgreso.setProgress(counter);

                if (counter == 100)
                    t.cancel();
            }
        };

        t.schedule(timerTask, 0, 1000);
    }

    private void cargarWebService() {
        OkHttpClient client = new OkHttpClient();

        String url = "http://192.168.43.122:8500/test.cfm";

        RequestBody formBody = new FormBody.Builder()
                        .add("nombre", txtNombre.getText().toString())
                        .add("fecha_nacimiento", txtFecha_nacimiento.getText().toString())
                        .add("genero", (rbMasculino.isChecked()? rbMasculino.getText().toString(): rbFemenina.getText().toString()))
                        .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("Sucedio un ERROR", e.getMessage().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
                            Log.i("Correcto", myResponse);
                        }
                    });
                }
            }
        });

    }
}
