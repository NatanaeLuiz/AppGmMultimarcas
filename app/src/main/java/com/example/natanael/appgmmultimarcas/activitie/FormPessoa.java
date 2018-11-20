package com.example.natanael.appgmmultimarcas.activitie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.natanael.appgmmultimarcas.R;
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

        this.pessoa = new Pessoa();//Instanceia a Class Pessoa
        this.pessoaDAO = new PessoaDAO(FormPessoa.this); //Instanceia a Class Pessoa Passando a tela que estou Usando(FormPessoa)

        editNome = (EditText) findViewById(R.id.editNome);
        editIdade = (EditText) findViewById(R.id.editIdade);
        editEndereco = (EditText) findViewById(R.id.editEndereco);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        //VERIFICA SE E PARA SALVAR OU ALTERAR -> PESSOA
        if(altPessoa != null){
            btnVariavel.setText("Alterar");
            editNome.setText(altPessoa.getNome());
            editIdade.setText(altPessoa.getIdade() + "");
            editEndereco.setText(altPessoa.getEndereco());
            editTelefone.setText(altPessoa.getTelefone());

            pessoa.setId(altPessoa.getId());
        }else{
            btnVariavel.setText("Salvar");
        }

        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validar = "NÃO"; //Verifica se os Campos estão Vazios
                if(editNome.getText().toString().isEmpty()
                        || editIdade.getText().toString().isEmpty()
                        || editEndereco.getText().toString().isEmpty()
                        || editTelefone.getText().toString().isEmpty()) {

                    validar = "NÃO";
                }else{
                    if (btnVariavel.getText().toString().equals("Alterar")){ // Só pega o ID se for pra alterar
                        pessoa.setId(altPessoa.getId());
                    }

                    // Pega os dados para Alterar/Adicionar
                    pessoa.setNome(editNome.getText().toString());
                    pessoa.setIdade(Integer.parseInt(editIdade.getText().toString()));
                    pessoa.setEndereco(editEndereco.getText().toString());
                    pessoa.setTelefone(editTelefone.getText().toString());
                    validar = "SIM";
                }

                if (validar.equals("SIM")){
                    if ((btnVariavel.getText().toString()).equals("Salvar")){//VERIFICA SE E PARA ADICIONAR(SALVAR) PESSOA OU ALTERAR(ALTERAR) PESSOA
                        retornoDB = pessoaDAO.salvarPessoaDAO(pessoa);
                        pessoaDAO.close();
                        if (retornoDB == -1){//CADASTRAR
                            alert("Erro ao Cadastrar!");
                        }else{
                            alert("Cadastrado Realizado com Sucesso!");
                        }
                    }else {//ALTERAR
                        retornoDB = pessoaDAO.alterarPessoaDAO(pessoa);
                        pessoaDAO.close();
                        if (retornoDB == -1) {
                            alert("Erro ao Alterar");
                        }else {
                            alert("Atualização realizada com Sucesso!");
                        }
                    }
                    finish();
                }else {
                    alert("Todos Campos Obrigatorios!");
                }
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
