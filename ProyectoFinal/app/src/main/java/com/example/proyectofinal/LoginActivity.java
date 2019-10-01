package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proyectofinal.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

/**
 * Actividad que maneja el inicio de sesión por parte del usuario
 */
public class LoginActivity extends AppCompatActivity {
    private Button loginButton, createNewAccount;
    private EditText inputPassword, inputEmail;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private CheckBox checkBox;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_button);
        createNewAccount = (Button) findViewById(R.id.create_button);
        inputEmail = (EditText) findViewById(R.id.login_email_input);
        inputPassword = (EditText) findViewById(R.id.login_password_input);
        checkBox = (CheckBox) findViewById(R.id.remember_check_input);

        Paper.init(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        // Barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Iniciar Sesión");
        }

        mAuth = FirebaseAuth.getInstance();

        // Realizamos el login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateForms();
            }
        });

        // Creamos la cuenta nueva
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newAccount();
            }
        });
    }

    /**
     * Validamos los datos introducidos por el usuario antes de comprobarlos
     */
    private void ValidateForms() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Introduce un correo electrónico.");
            inputEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Introduce un correo electrónico válido");
            inputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Introduce una contraseña.");
            inputPassword.requestFocus();
        } else if (password.length() < 6) {
            inputPassword.setError("La contraseña debe contener 6 caracteres como mínimo.");
            inputPassword.requestFocus();
        } else {
            // Todos los datos validados, procedemos al login
            login(email, password);
        }
    }

    /**
     * Con todos los datos comprobados, procedemos al login comprobando los datos
     * en la base da datos
     * @param email user email
     * @param password user password
     */
    private void login(final String  email, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            if(checkBox.isChecked())
                            {
                                Paper.book().write(Prevalent.emailKey, email);
                                Paper.book().write(Prevalent.passwordKey, password);
                            }

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Logeado correctamente", Toast.LENGTH_SHORT).show();

                            if(getIntent().getExtras() != null) {
                                if (getIntent().getExtras().get("key").equals("Order_Login")) {
                                    Intent intent_order = new Intent(LoginActivity.this, OrderActivity.class);
                                    intent_order.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent_order);
                                }
                            } else {
                                Intent intent_login = new Intent(LoginActivity.this, MainActivity.class);
                                intent_login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent_login);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Dirigimos al usuario a la actividad de registro
     */
    private void newAccount()
    {   // Start Register Activity
        Intent intent_create = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent_create);
    }
}
