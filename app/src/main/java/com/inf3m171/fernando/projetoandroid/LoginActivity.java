package com.inf3m171.fernando.projetoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText etEmailEntrar, etSenhaEntrar;
    Button btnCadastrarNovo, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailEntrar = (EditText) findViewById(R.id.etEmailEntrar);
        etSenhaEntrar = (EditText) findViewById(R.id.etSenhaEntrar);
        btnCadastrarNovo = (Button) findViewById(R.id.btnCadastrarNovo);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);

        btnCadastrarNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
