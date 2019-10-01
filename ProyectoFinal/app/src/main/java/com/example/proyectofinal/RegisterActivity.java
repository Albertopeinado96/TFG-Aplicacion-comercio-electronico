package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Actividad para manejar el registro del usuario
 */
public class RegisterActivity extends AppCompatActivity {
    private Button createAccountButton;
    private EditText inputName, inputEmail, inputPassword, inputConfirmPassword, inputNumber;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = findViewById(R.id.register_button);
        inputName = findViewById(R.id.register_fullname_input);
        inputEmail = findViewById(R.id.register_email_input);
        inputPassword = findViewById(R.id.register_password_input);
        inputConfirmPassword = findViewById(R.id.register_confirmpassword_input);
        inputNumber = findViewById(R.id.register_phone_input);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        // Barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Registrar Usuario");
        }

        mAuth = FirebaseAuth.getInstance();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateForms();
            }
        });
    }

    /**
     * Metodo que comprueba que los campos del proceso de registro estan correctos
     */
    private void ValidateForms() {
        String full_name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirm_password = inputConfirmPassword.getText().toString();
        String phone_number = inputNumber.getText().toString();

        if (TextUtils.isEmpty(full_name)) {
            inputName.setError("Introduce un nombre.");
            inputName.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
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
        } else if (TextUtils.isEmpty(confirm_password)) {
            inputConfirmPassword.setError("Vuelve a escribir la contraseña.");
            inputConfirmPassword.requestFocus();
        } else if (!password.equals(confirm_password)) {
            inputConfirmPassword.setError("Las contraseñas no coinciden.");
            inputConfirmPassword.requestFocus();
        } else if (TextUtils.isEmpty(phone_number) || (phone_number.length() != 9)) {
            inputNumber.setError("Introduce un número de teléfono válido.");
            inputNumber.requestFocus();
        } else {
            // Todos los datos validados, procedemos al registro
            RegisterAccount(full_name, email, password, phone_number);
        }
    }

    /**
     * Accedemos a la BD para introducir al usuario
     * @param name nombre del usuario
     * @param email correo electronico del usuario
     * @param password contraseña del usuario
     * @param phonenumber numero de telefono del usuario
     */
    private void RegisterAccount(final String name, final String email, final String password, final String phonenumber)
    {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    // Store aditional info on database
                    User user = new User(name, email, phonenumber);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Intent intent_login = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent_login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent_login);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                            } else {
                                // Fallo al añadir datos adicionales a la database
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    // Fallo al registrar usuario
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
