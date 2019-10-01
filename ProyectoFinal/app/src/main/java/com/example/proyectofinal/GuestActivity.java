package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GuestActivity extends AppCompatActivity {

    private TextView login_text;
    private Button guestButton;
    private EditText guestEmail;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /**
     * Manejamos los elementos con los que interacciona el usuario
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        // Barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Proceso de compra");
        }

        guestEmail = findViewById(R.id.editText_country);
        guestButton = findViewById(R.id.button_envio_done);
        login_text = findViewById(R.id.iniciar_sesion);

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guest_email = guestEmail.getText().toString();

                if (TextUtils.isEmpty(guest_email)) {
                    guestEmail.setError("Introduce un correo electrónico.");
                    guestEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(guest_email).matches()) {
                    guestEmail.setError("Introduce un correo electrónico válido");
                    guestEmail.requestFocus();
                } else {
                    Intent intent = new Intent(GuestActivity.this, OrderActivity.class);
                    intent.putExtra("guest_email", guest_email);
                    startActivity(intent);
                }
            }
        });

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestActivity.this, LoginActivity.class);
                intent.putExtra("key", "Order_Login"); // Le pasamos un dato al intent.
                startActivity(intent);
            }
        });

    }
}
