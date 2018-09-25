package com.inf3m171.fernando.projetoandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmailEntrar, etSenhaEntrar;
    private Button btnCadastrarNovo, btnEntrar;

    private FirebaseAuth autenticacao;
    private FirebaseAuth.AuthStateListener stateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailEntrar = (EditText) findViewById(R.id.etEmailEntrar);
        etSenhaEntrar = (EditText) findViewById(R.id.etSenhaEntrar);
        btnCadastrarNovo = (Button) findViewById(R.id.btnCadastrarNovo);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);

        autenticacao = FirebaseAuth.getInstance();

        btnCadastrarNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar();
            }
        });

        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent i = new Intent(LoginActivity.this, ListaActivity.class);
                    startActivity(i);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        autenticacao.addAuthStateListener(stateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (stateListener != null){
            autenticacao.removeAuthStateListener(stateListener);
        }
    }

    private void entrar(){
        String email = etEmailEntrar.getText().toString();
        String senha = etSenhaEntrar.getText().toString();

        if (email.isEmpty())
            Toast.makeText(LoginActivity.this, "E-mail deve ser preenchido!", Toast.LENGTH_LONG).show();

        if (senha.isEmpty())
            Toast.makeText(LoginActivity.this, "E-mail deve ser preenchido!", Toast.LENGTH_LONG).show();

        if (!email.isEmpty() && !senha.isEmpty()){
            autenticacao.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                        Toast.makeText(LoginActivity.this, "Usuario ou senha inválidos!", Toast.LENGTH_LONG);
                }
            });
        }
    }
}
