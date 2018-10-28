package com.example.natanael.appgmmultimarcas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.natanael.appgmmultimarcas.dao.PessoaDAO;
import com.example.natanael.appgmmultimarcas.model.Pessoa;

public class FormPessoa extends AppCompatActivity {

    EditText editNome, editIdade, editEndereco, editTelefone;
    Button btnVariavel;
    Pessoa pessoa, altPessoa;
    PessoaDAO pessoaDAO;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pessoa);

        Intent intent = getIntent(); // Vai Buscar uma Entidade que já foi usada
        altPessoa = (Pessoa) intent.getSerializableExtra("pessoa-enviada");

        pessoa = new Pessoa();//Instanceia a Class Pessoa
        pessoaDAO = new PessoaDAO(FormPessoa.this); //Instanceia a Class Pessoa Passando a tela que estou Usando(FormPessoa)

        editNome = (EditText) findViewById(R.id.editNome);
        editIdade = (EditText) findViewById(R.id.editIdade);
        editEndereco = (EditText) findViewById(R.id.editEndereco);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        if(altPessoa != null){
            btnVariavel.setText("Alterar");
        }else{
            btnVariavel.setText("Salvar");
        }

        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa.setNome(editNome.getText().toString());
                pessoa.setIdade(Integer.parseInt(editIdade.getText().toString()));
                pessoa.setEndereco(editEndereco.getText().toString());
                pessoa.setTelefone(editTelefone.getText().toString());

                //VERIFICA SE E PARA SALVAR OU ALTERAR -> PESSOA
                if ((btnVariavel.getText().toString()).equals("Salvar")){
                    retornoDB = pessoaDAO.salvarPessoaDAO(pessoa);

                    if (retornoDB == -1){
                        alert("Erro ao Cadastrar!");
                    }else{
                        alert("Cadastrado Realizado com Sucesso!");
                    }
                }
                finish();
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
