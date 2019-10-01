package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

/**
 * Actividad que cierra la sesión del usuario
 */
public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Paper.book().destroy();
        Intent intent_create = new Intent(LogoutActivity.this, MainActivity.class);
        startActivity(intent_create);
    }
}
