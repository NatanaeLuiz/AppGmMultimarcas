package com.example.natanael.appgmmultimarcas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.natanael.appgmmultimarcas.dao.PessoaDAO;
import com.example.natanael.appgmmultimarcas.model.Pessoa;

import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {

    ListView listVisivel;
    Button btnNovoCadastro;
    Pessoa pessoa;
    PessoaDAO pessoaDAO;
    ArrayList<Pessoa> arrayListPessoa;
    ArrayAdapter<Pessoa> arrayAdapterPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        listVisivel = (ListView) findViewById(R.id.listPessoas);
        btnNovoCadastro = (Button) findViewById(R.id.btnNovoCadastro);

        //METODO PARA IR PARA PROXIMA TELA DE CADASTRO
        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteActivity.this, FormPessoa.class);
                startActivity(intent);
            }
        });
    }

    //METODO
    public void populaLista(){
        pessoaDAO = new PessoaDAO(ClienteActivity.this);

        arrayListPessoa = pessoaDAO.selectAllPessoas();
        pessoaDAO.close();

        if (listVisivel != null){
            arrayAdapterPessoa = new ArrayAdapter<Pessoa>(ClienteActivity.this,
                    android.R.layout.simple_list_item_1, arrayListPessoa);

            listVisivel.setAdapter(arrayAdapterPessoa);

        }
    }

    protected void onResume(){
        super.onResume();
        populaLista();
    }

}
