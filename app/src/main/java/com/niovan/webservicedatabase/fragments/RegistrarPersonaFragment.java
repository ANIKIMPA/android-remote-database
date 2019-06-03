package com.niovan.webservicedatabase.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

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

    private static final int PERMISSION_CODE = 1000;
    private static final int COD_GALERIA = 10;

    private EditText etNombre, etFecha;
    private RadioButton rbMasculino, rbFemenina;
    private Button btnFoto, btnRegistrar;
    private ImageView imgViewFoto;

    Uri image_uri;

    public RegistrarPersonaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_registrar_persona, container, false);

        // Asignar referencias.
        etNombre = vista.findViewById(R.id.registro_nombre);
        etFecha = vista.findViewById(R.id.registro_fecha_nacimiento);
        rbMasculino = vista.findViewById(R.id.registro_masculino);
        rbFemenina = vista.findViewById(R.id.registro_femenina);
        btnFoto = vista.findViewById(R.id.btnFoto);
        imgViewFoto = vista.findViewById(R.id.imgViewFoto);
        btnRegistrar = vista.findViewById(R.id.btnRegistrar);

        // Asignar evento onClick en botones.
        btnFoto.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

        return vista;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRegistrar) {
            crearWebService();
        }
        else if(v.getId() == R.id.btnFoto) {
            mostrarDialogOpciones();
        }
    }

    private void mostrarDialogOpciones() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opción")
                .setItems(R.array.opciones_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                verificarPermisosCamara();
                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/");
                                startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_GALERIA);
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    private void verificarPermisosCamara() {
        // If system os is >= marshmallow, request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                // Permission not enabled, request it
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                // show popup to request permissions
                requestPermissions(permission, PERMISSION_CODE);
            }
            else {
                // permission already granted.
                abrirCamara();
            }
        }
        else {
            // system os < marshmallow
            abrirCamara();
        }

    }

    private void abrirCamara() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
//        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    // Handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // this method is called, when user pressed Allow or Deny from Permission Request Popup.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission from popup was granted.
                    abrirCamara();
                }
                else {
                    // permission from popup was denied.
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COD_GALERIA:
                Uri miPath = data.getData();
                imgViewFoto.setImageURI(miPath);
                break;
//            case
//                break;
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
