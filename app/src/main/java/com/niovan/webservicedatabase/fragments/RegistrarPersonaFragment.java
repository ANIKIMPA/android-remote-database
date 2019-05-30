package com.niovan.webservicedatabase.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.niovan.webservicedatabase.MainActivity;
import com.niovan.webservicedatabase.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarPersonaFragment extends Fragment implements View.OnClickListener {


    public RegistrarPersonaFragment() {
        // Required empty public constructor
    }

    private EditText etNombre, etFecha;
    private RadioButton rbMasculino, rbFemenina;

    Button btnRegistrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_registrar_persona, container, false);

        etNombre = vista.findViewById(R.id.registro_nombre);
        etFecha = vista.findViewById(R.id.registro_fecha_nacimiento);
        rbMasculino = vista.findViewById(R.id.registro_masculino);
        rbFemenina = vista.findViewById(R.id.registro_femenina);

        btnRegistrar = vista.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

        return vista;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRegistrar){
            crearWebService();
        }
    }

    private void crearWebService() {
        OkHttpClient client = new OkHttpClient();

        String url = "http://192.168.43.122:8500/test.cfm";

        RequestBody formBody = new FormBody.Builder()
                .add("nombre", etNombre.getText().toString())
                .add("fecha_nacimiento", etFecha.getText().toString())
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

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Registro exitoso!", Toast.LENGTH_SHORT).show();
                            Log.i("Correcto", myResponse);
                        }
                    });
                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
    }
}
