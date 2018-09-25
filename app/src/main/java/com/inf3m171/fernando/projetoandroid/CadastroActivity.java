package com.inf3m171.fernando.projetoandroid;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha, etConfirmaSenha;
    private Button btnCadastrar;

    private FirebaseAuth autenticacao;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String erro = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);
        etConfirmaSenha = (EditText) findViewById(R.id.etConfirmaSenha);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarUsuario();
            }
        });
    }

    private void criarUsuario(){
        final String nome = etNome.getText().toString();
        final String senha = etSenha.getText().toString();
        String confirma = etConfirmaSenha.getText().toString();
        final String email = etEmail.getText().toString();

        if (!nome.isEmpty() && !senha.isEmpty() && senha.equals(confirma) && !email.isEmpty()){
            autenticacao = FirebaseAuth.getInstance();
            autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        database = FirebaseDatabase.getInstance();
                        String id = autenticacao.getCurrentUser().getUid();
                        reference = database.getReference("usuarios").child(id);
                        reference.child("nome").setValue(nome);
                        reference.child("email").setValue(email);
                        reference.child("senha").setValue(senha);
                    }else {
                        erro = "Não foi possivel criar o usuário!";
                    }
                }
            });
        }else {
            erro = "O campo e-mail deve ser preenchido e os campos de senha devem ser iguais";
        }

        if (!erro.isEmpty()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Atenção");
            alerta.setMessage(erro);
            alerta.setNeutralButton("OK", null);
            alerta.show();
        }else {
            finish();
        }
    }
}
