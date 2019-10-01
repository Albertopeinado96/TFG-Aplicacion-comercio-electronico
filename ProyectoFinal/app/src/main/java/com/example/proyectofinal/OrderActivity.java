package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OrderActivity extends AppCompatActivity {
    private EditText editTextNombre, editTextApellidos, editTextDireccion, editTextDireccion2, editTextCP,editTextCiudad,editTextPhone, editTextCountry;
    private Button button_done;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        editTextNombre = findViewById(R.id.editText_nombre);
        editTextApellidos = findViewById(R.id.editText_apellidos);
        editTextDireccion = findViewById(R.id.editText_direccion);
        editTextDireccion2 = findViewById(R.id.editText_direccion2);
        editTextCP = findViewById(R.id.editText_cp);
        editTextCiudad = findViewById(R.id.editText_ciudad);
        editTextPhone = findViewById(R.id.editText_phone);
        editTextCountry = findViewById(R.id.editText_country);
        button_done = findViewById(R.id.button_envio_done);

        // Barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Informacion de envío");
        }

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextNombre.getText().toString())) {
                    editTextNombre.setError("Este campo no puede estar vacío");
                    editTextNombre.requestFocus();
                } else if (TextUtils.isEmpty(editTextApellidos.getText().toString())) {
                    editTextApellidos.setError("Este campo no puede estar vacío");
                    editTextApellidos.requestFocus();
                } else if (TextUtils.isEmpty(editTextDireccion.getText().toString())) {
                    editTextDireccion.setError("Este campo no puede estar vacío");
                    editTextDireccion.requestFocus();
                } else if (TextUtils.isEmpty(editTextCP.getText().toString())) {
                    editTextCP.setError("Este campo no puede estar vacío");
                    editTextCP.requestFocus();
                } else if (TextUtils.isEmpty(editTextCiudad.getText().toString())) {
                    editTextCiudad.setError("Este campo no puede estar vacío");
                    editTextCiudad.requestFocus();
                } else if (TextUtils.isEmpty(editTextPhone.getText().toString())) {
                    editTextPhone.setError("Este campo no puede estar vacío");
                    editTextPhone.requestFocus();
                } else if (TextUtils.isEmpty(editTextCountry.getText().toString())) {
                    editTextCountry.setError("Este campo no puede estar vacío");
                    editTextCountry.requestFocus();
                } else {
                    // Mandamos el producto al resumen del pedido para que se pueda almacenar en la base de datos
                    Intent intent = new Intent(OrderActivity.this, SummaryActivity.class);
                    intent.putExtra("Address",
                                    new Address(editTextNombre.getText().toString(),
                                    editTextApellidos.getText().toString(),
                                    editTextDireccion.getText().toString(),
                                    editTextDireccion2.getText().toString(),
                                    editTextCP.getText().toString(),
                                    editTextCiudad.getText().toString(),
                                    editTextPhone.getText().toString(),
                                    editTextCountry.getText().toString()));

                    if(getIntent().getExtras() != null) {
                        String correo = getIntent().getExtras().get("guest_email").toString();
                        intent.putExtra("guest_email", correo);
                    }
                    startActivity(intent);
                }
            }
        });
    }
}
